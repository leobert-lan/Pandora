/*
 * MIT License
 *
 * Copyright (c) 2018 leobert-lan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package osp.leobert.android.pandora;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import android.text.TextUtils;
import android.util.Pair;
import android.util.SparseIntArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import osp.leobert.android.pandora.visitor.TypeVisitor;

/**
 * <p><b>Package:</b> osp.leobert.android.pandora </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> RealDataSet </p>
 * <p><b>Description:</b> one impl of PandoraBoxAdapter, using ArrayList to store data </p>
 * Created by leobert on 2018/9/29.
 */
public class RealDataSet<T> extends PandoraBoxAdapter<T> {

    private final List<T> oldList = new ArrayList<>();
    private final SparseIntArray oldDataItemHash = new SparseIntArray();

    private final List<T> data = new ArrayList<>();
    private boolean useTransaction = false;
    private int groupIndex;// = NO_GROUP_INDEX;
    private int startIndex;

    @Nullable
    private PandoraBoxAdapter<T> parent;

    private Transaction<T> transaction = new Transaction<>(this);

    private final DiffUtil.Callback diffCallback = new DiffUtil.Callback() {
        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return data.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            if (oldItemPosition >= oldList.size())
                return false;
            if (newItemPosition >= getDataCount())
                return false;

            T old = oldList.get(oldItemPosition);
            T now = getDataByIndex(newItemPosition);
            return Pandora.equals(old, now);
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            if (oldItemPosition >= oldList.size())
                return false;
            if (newItemPosition >= getDataCount())
                return false;

            T old = oldList.get(oldItemPosition);
            T now = getDataByIndex(newItemPosition);
            boolean itemSame = Pandora.equals(old, now);

            return itemSame && (oldDataItemHash.get(oldItemPosition) == Pandora.hash(now));
        }
    };

    public RealDataSet(int groupIndex, int startIndex) {
        this.startIndex = startIndex;
        this.groupIndex = groupIndex;
    }

    public RealDataSet() {
        this(0, NO_GROUP_INDEX);
    }

    @Override
    protected void onBeforeChanged() {
        if (!inTransaction())
            snapshot();

        if (parent != null) {
            parent.onBeforeChanged();
        }
    }

    @Override
    protected void onAfterChanged() {
        if (parent != null)
            parent.onAfterChanged();
        if (!inTransaction())
            calcChangeAndNotify();
    }

    @Override
    protected void rebuildSubNodes() {
//        ignore
    }

    @Override
    protected boolean inTransaction() {
        return useTransaction || isParentInTransaction();
    }

    private boolean isParentInTransaction() {
//        if (parent != null)
//            return parent.inTransaction();
//        return false;
        return parent != null && parent.inTransaction();
    }

    @Override
    public void startTransaction() {
        useTransaction = true;
        snapshot();
    }

    @Override
    public void endTransaction() {
        useTransaction = false;
        calcChangeAndNotify();
    }

    @Override
    public void endTransactionSilently() {
        useTransaction = false;
    }

    @Override
    @Nullable
    public PandoraBoxAdapter<T> retrieveAdapterByDataIndex(int index) {
        if (0 <= index && index < getDataCount())
            return this;
        return null;
    }

    @Nullable
    @Override
    public Pair<PandoraBoxAdapter<T>, Integer> retrieveAdapterByDataIndex2(int index) {
        PandoraBoxAdapter<T> temp = retrieveAdapterByDataIndex(index);
        if (temp == null)
            return null;

        return Pair.create(temp, index);
    }

    @Override
    public int getDataCount() {
        return data.size();
    }

    @Override
    public T getDataByIndex(int index) {
        if (index >= data.size() || index < 0)
            return null;
        return data.get(index);
    }

    @Override
    public void clearAllData() {
        onBeforeChanged();
        data.clear();
        onAfterChanged();
    }

    @Override
    public void add(T item) {
        onBeforeChanged();
        data.add(item);
        onAfterChanged();
    }

    @Override
    public void add(int pos, T item) {
        onBeforeChanged();
        data.add(pos, item);
        onAfterChanged();
    }

    @Override
    public void addAll(Collection<T> collection) {
        onBeforeChanged();
        data.addAll(collection);
        onAfterChanged();
    }

    @Override
    public void remove(Object item) {
        onBeforeChanged();
        data.remove(item);
        onAfterChanged();
    }

    @Override
    public void removeAtPos(int position) {
        onBeforeChanged();
        if (position < data.size())
            data.remove(position);
        onAfterChanged();
    }

    @Override
    public boolean replaceAtPosIfExist(int position, T item) {
        if (getDataCount() > position + 1 || position < 0)
            return false;
        onBeforeChanged();
        data.set(position, item);
        onAfterChanged();
        return true;
    }

    @Override
    public void setData(Collection<T> collection) {
        onBeforeChanged();
        this.data.clear();
        if (collection != null) {
            this.data.addAll(collection);
        }
        onAfterChanged();
    }

    @Override
    public int indexOf(T item) {
        try {
            return data.indexOf(item);
        } catch (ClassCastException | NullPointerException e) {
            e.printStackTrace();
            return -1;
        }
    }


    @Override
    public int getGroupIndex() {
        return groupIndex;
    }

    @Override
    public void setGroupIndex(int groupIndex) {
        this.groupIndex = groupIndex;
    }

    @Override
    protected void notifyHasAddToParent(@NonNull PandoraBoxAdapter<T> parent) {
        this.parent = parent;
    }

    @Override
    protected void notifyHasRemoveFromParent() {
        parent = null;
    }

    @Nullable
    @Override
    protected PandoraBoxAdapter<T> getParent() {
        return parent;
    }

    @Override
    public void addChild(PandoraBoxAdapter<T> sub) throws IllegalStateException {
        throw new IllegalStateException("simpleDataSet is not allowed to add sub data set");
    }

    @Override
    public boolean hasBind2Parent() {
        return parent != null;
    }

    @Override
    public void removeFromOriginalParent() {
        if (parent != null) {
            parent.removeChild(this);
            parent = null;
        }
    }

    @Override
    public void removeChild(PandoraBoxAdapter<T> sub) {
        throw new IllegalStateException("simpleDataSet is not allowed to add sub data set, so no need to remove");
    }

    @Override
    public int getStartIndex() {
        return startIndex;
    }

    @Override
    protected void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    @Override
    boolean isAliasConflict(@NonNull String alias) {
        return TextUtils.equals(alias, getAlias());
    }

    @Override
    public PandoraBoxAdapter<T> findByAlias(@NonNull String targetAlias) {
        if (targetAlias == null) return null;
        if (TextUtils.equals(getAlias(), targetAlias))
            return this;
        return null;
    }

    @Override
    protected void restore() {
        data.clear();
        data.addAll(oldList);
    }

    private void snapshot() {
        oldList.clear();
        oldList.addAll(data);
        oldDataItemHash.clear();
        for (int i = 0; i < oldList.size(); i++) {
            T item = oldList.get(i);
            oldDataItemHash.put(i, Pandora.hash(item));
        }
    }

    private void calcChangeAndNotify() {
        if (listUpdateCallback != null) {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(diffCallback);
            result.dispatchUpdatesTo(listUpdateCallback);
        }
    }

    @NonNull
    @Override
    public Iterator<T> iterator() {
        return data.iterator();
    }

    public <T> T accept(int pos, @NonNull TypeVisitor<T> typeVisitor) {
        if (pos < 0 || pos >= getDataCount()) {
            typeVisitor.onMissed();
            return null;
        }

        return typeVisitor.visit(getDataByIndex(pos));
    }
}
