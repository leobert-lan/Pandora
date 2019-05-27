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

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import android.text.TextUtils;
import android.util.Log;
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
 * <p><b>Classname:</b> WrapperDataSet </p>
 * <p><b>Description:</b> a wrapper contains any PandoraBoxAdapter </p>
 * Created by leobert on 2018/9/29.
 */
public class WrapperDataSet<T> extends PandoraBoxAdapter<T> {
    private void log(int p, String msg) {
        Log.println(p, "WrapperDataSet", msg);
    }

    private final List<T> oldList = new ArrayList<>();
    private final SparseIntArray oldDataItemHash = new SparseIntArray();

    private boolean useTransaction = false;
    private final DiffUtil.Callback diffCallback = new DiffUtil.Callback() {
        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return getDataCount();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            if (oldItemPosition >= oldList.size()) return false;
            if (newItemPosition >= getDataCount()) return false;

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

    private int groupIndex;// = NO_GROUP_INDEX;
    private int startIndex;

    @Nullable
    private PandoraBoxAdapter<T> parent;

    private final List<PandoraBoxAdapter<T>> subs = new ArrayList<>();

    public WrapperDataSet(int groupIndex, int startIndex) {
        this.startIndex = startIndex;
        this.groupIndex = groupIndex;
    }

    public WrapperDataSet() {
        this.startIndex = 0;
        this.groupIndex = NO_GROUP_INDEX;
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
        if (TextUtils.equals(alias, getAlias()))
            return true;
        for (PandoraBoxAdapter childAdapter : subs) {
            if (childAdapter == null) continue;
            if (childAdapter.isAliasConflict(alias))
                return true;
        }
        return false;
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

    /**
     * Attention:
     * <ul>
     * <li> alias check won't be started!</li>
     * <li> data change will not be apply to listener in the method</li>
     * </ul>
     *
     * @param sub sub node to be bind
     */
    @Override
    public void addChild(PandoraBoxAdapter<T> sub) {
        if (sub == null)
            return;

        if (sub.hasBind2Parent()) {
            sub.removeFromOriginalParent();
        }

        onBeforeChanged();
        int groupIndex = subs.size();
        sub.setGroupIndex(groupIndex);

        long count = getDataCount();
        sub.setStartIndex((int) count);
        sub.notifyHasAddToParent(this);
        subs.add(sub);
        onAfterChanged();
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
        if (subs.contains(sub)) {
            onBeforeChanged();
            subs.remove(sub);
            sub.notifyHasRemoveFromParent(); // make sure
            onAfterChanged();
        }
    }

    @Nullable
    public PandoraBoxAdapter<T> getChild(int index) {
        if (index + 1 > subs.size() || 0 > index)
            return null;
        return subs.get(index);
    }

    @Nullable
    public PandoraBoxAdapter<T> findByAlias(@NonNull String targetAlias) {
        if (targetAlias == null) return null;
        if (TextUtils.equals(getAlias(), targetAlias))
            return this;

        Iterator<PandoraBoxAdapter<T>> iterator = subs.iterator();
        PandoraBoxAdapter<T> ret = null;
        while (iterator.hasNext() && ret == null) {
            ret = iterator.next().findByAlias(targetAlias);
        }
        return ret;
    }

    @Override
    public final int getDataCount() {
        int ret = 0;
        for (PandoraBoxAdapter subAdapter : subs) {
            if (subAdapter == null) continue;
            ret += subAdapter.getDataCount();
        }
        return ret;
    }

    @Override
    @Nullable
    public final T getDataByIndex(int indexResolved/*must be resolved pos*/) {
        int realIndex = indexResolved + startIndex;
        log(Log.DEBUG, "getDataByResolvedIndex" + indexResolved + "   ; real index:" + realIndex);
        if (0 <= indexResolved && getDataCount() > indexResolved) {
            //find the sub

            PandoraBoxAdapter<T> targetSub = null;

            int mid = subs.size() / 2;
            PandoraBoxAdapter<T> sub = subs.get(mid);


            if (realIndex >= sub.getStartIndex() && realIndex < (sub.getStartIndex() + sub.getDataCount())) {
                targetSub = sub;
            }

            int start = 0;
            int end = subs.size() - 1;
            while (start <= end) {
                mid = (end - start) / 2 + start;
                PandoraBoxAdapter<T> adapter = subs.get(mid);

                if (realIndex < adapter.getStartIndex()) {
                    end = mid - 1;
                } else if (adapter.getDataCount() == 0 || realIndex >= (adapter.getStartIndex() + adapter.getDataCount())) {
                    start = mid + 1;
                } else {
                    targetSub = adapter;
                    break;
                }
            }
            if (targetSub == null) {
                log(Log.ERROR, "getDataByRealIndex" + realIndex + ";no child find");
                return null;
            }

            log(Log.DEBUG, "getDataByIndex" + realIndex + targetSub.getAlias() + " - " + targetSub.toString());

            int resolvedIndex = realIndex - targetSub.getStartIndex();

            return targetSub.getDataByIndex(resolvedIndex);
        }
        return null;
    }

    @Override
    public void clearAllData() {
        startTransaction();
        for (PandoraBoxAdapter<T> sub : subs) {
            if (sub == null) continue;
            sub.clearAllData();
        }
        endTransaction();
    }

    public void clearAllChildren() {
        if (!subs.isEmpty()) {
            onBeforeChanged();
            while (!subs.isEmpty()) {
                PandoraBoxAdapter<T> sub = subs.remove(0);
                sub.notifyHasRemoveFromParent(); // make sure
            }
            onAfterChanged();
        }
    }

    public int getChildCount() {
        return subs.size();
    }

    @Override
    public void add(T item) {
        startTransaction();
        if (subs.size() > 0) {
            subs.get(subs.size() - 1).add(item);
        }
        endTransaction();
    }

    @Override
    public void add(int pos, T item) {
        if (pos < 0)
            return;
        startTransaction();
        if (pos >= getDataCount()) { //append at the last
            add(item);
        } else {
            Pair<PandoraBoxAdapter<T>, Integer> target = retrieveAdapterByDataIndex2(pos);

            if (target == null)
                log(Log.ERROR, "bug,cannot find target adapter");
            else {
                target.first.add(target.second, item);
            }
        }

        endTransaction();
    }

    @Override
    public void addAll(Collection<T> collection) {
        startTransaction();
        if (subs.size() > 0) {
            subs.get(subs.size() - 1).addAll(collection);
        }
        endTransaction();
    }

    @Override
    public void remove(Object item) {
        startTransaction();
        for (PandoraBoxAdapter<T> sub : subs) {
            if (sub == null) continue;
            try {
                sub.remove(item); // safety
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        endTransaction();
    }

    @Override
    public void removeAtPos(int position) {
        startTransaction();
        if (position < 0 || position >= getDataCount()) {
            log(Log.ERROR, "index out of boundary");
        } else {
            Pair<PandoraBoxAdapter<T>, Integer> target = retrieveAdapterByDataIndex2(position);
            if (target == null)
                log(Log.ERROR, "bug,cannot find target adapter");
            else {
                target.first.removeAtPos(target.second);
            }
        }
        endTransaction();
    }

    @Override
    public boolean replaceAtPosIfExist(int position, T item) throws PandoraException {
        if (getDataCount() > position + 1 || position < 0)
            return false;
        Pair<PandoraBoxAdapter<T>, Integer> tmp = retrieveAdapterByDataIndex2(position);
        if (tmp == null)
            return false;
        if (tmp.first == null || tmp.second == null)
            return false;
        try {
            return tmp.first.replaceAtPosIfExist(tmp.second, item);
        } catch (ClassCastException e) {
            e.printStackTrace();
            throw new PandoraException(e);
        }
    }

    @Override
    public void setData(Collection<T> collection) {
        //no-op
    }

    @Override
    protected final void onBeforeChanged() {
        if (!inTransaction())
            snapshot();

        if (parent != null) {
            parent.onBeforeChanged();
        }
    }

    @Override
    protected final void onAfterChanged() {
        rebuildSubNodes();
        if (parent != null)
            parent.onAfterChanged();
        if (!inTransaction())
            calcChangeAndNotify();

    }

    private void calcChangeAndNotify() {
        if (listUpdateCallback != null) {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(diffCallback);
            result.dispatchUpdatesTo(listUpdateCallback);
        }
    }

    @Override
    protected void rebuildSubNodes() {
        int subCounts = subs.size();
        int offset = 0;
        for (int i = 0; i < subCounts; i++) {
            PandoraBoxAdapter<T> sub = subs.get(i);
            if (sub == null) continue;

            sub.setGroupIndex(i);
            sub.setStartIndex(getStartIndex() + offset);
            sub.rebuildSubNodes();
            offset += sub.getDataCount();
        }
    }


    private void snapshot() {
        oldList.clear();
        oldDataItemHash.clear();
        long count = getDataCount();
        for (int i = 0; i < count; i++) {
            T data = getDataByIndex(i);
            oldList.add(i, data);
            oldDataItemHash.put(i, Pandora.hash(data));
        }
    }


    @Override
    protected boolean inTransaction() {
        return useTransaction || isParentInTransaction();
    }

    private boolean isParentInTransaction() {
        if (parent != null)
            return parent.inTransaction();
        return false;
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
    @CheckResult
    @Nullable
    public PandoraBoxAdapter<T> retrieveAdapterByDataIndex(int index) {
        Pair<PandoraBoxAdapter<T>, Integer> temp = retrieveAdapterByDataIndex2(index);
        if (temp == null)
            return null;
        return temp.first;
    /*    if (startIndex <= index && startIndex + getDataCount() > index) {
            //find the sub

            PandoraBoxAdapter<T> targetSub = null;

            int mid = subs.size() / 2;
            PandoraBoxAdapter<T> sub = subs.get(mid);
            if (index >= sub.getStartIndex() && index < (sub.getStartIndex() + sub.getDataCount())) {
                targetSub = sub;
            }

            int start = 0;
            int end = subs.size() - 1;
            while (start <= end) {
                mid = (end - start) / 2 + start;
                PandoraBoxAdapter<T> adapter = subs.get(mid);

                if (index < adapter.getStartIndex()) {
                    end = mid - 1;
                } else if (index > (adapter.getStartIndex() + adapter.getDataCount())) {
                    start = mid + 1;
                } else {
                    targetSub = adapter;
                    break;
                }
            }

            if (targetSub == null)
                return null;

            int resolvedIndex = index - targetSub.getStartIndex();

            return targetSub.retrieveAdapterByDataIndex(resolvedIndex);
        }
        return null;*/
    }

    @Nullable
    @Override
    public Pair<PandoraBoxAdapter<T>, Integer> retrieveAdapterByDataIndex2(int index/*must be resolved pos*/) {
        int realIndex = getStartIndex() + index;
        if (startIndex <= realIndex && startIndex + getDataCount() > realIndex) {
            //find the sub

            PandoraBoxAdapter<T> targetSub = null;

            int mid = subs.size() / 2;
            PandoraBoxAdapter<T> sub = subs.get(mid);
            if (realIndex >= sub.getStartIndex() && realIndex < (sub.getStartIndex() + sub.getDataCount())) {
                targetSub = sub;
            }

            int start = 0;
            int end = subs.size() - 1;
            while (start <= end) {
                mid = (end - start) / 2 + start;
                PandoraBoxAdapter<T> adapter = subs.get(mid);

                if (realIndex < adapter.getStartIndex()) {
                    end = mid - 1;
                } else if (adapter.getDataCount() == 0 || realIndex >= (adapter.getStartIndex() + adapter.getDataCount())) {
                    start = mid + 1;
                } else {
                    targetSub = adapter;
                    break;
                }
            }

            if (targetSub == null)
                return null;

            int resolvedIndex = realIndex - targetSub.getStartIndex();

            return targetSub.retrieveAdapterByDataIndex2(resolvedIndex);
        }
        return null;
    }

    @Override
    public int indexOf(T item) {
        Iterator<PandoraBoxAdapter<T>> iterator = subs.iterator();
        int index = -1;
        while (iterator.hasNext()) {
            PandoraBoxAdapter<T> sub = iterator.next();
            int i = sub.indexOf(item);
            if (i >= 0) {
                index = sub.getStartIndex() + i;
                break;
            }
        }
        if (index == -1)
            return -1;
        return getStartIndex() + index;
    }

    /**
     * <em>Attention:Caution with method!It will apply all data change then use loop
     * to create a snapshot list.And any changes e.g. remove() will make none positive effects</em>
     */
    @NonNull
    @Override
    public Iterator<T> iterator() {
        endTransaction();
        snapshot();
        return oldList.iterator();
    }

    public void accept(int pos, @NonNull TypeVisitor typeVisitor) {
        if (pos < 0 || pos >= getDataCount()) {
            typeVisitor.onMissed();
        }

        typeVisitor.visit(getDataByIndex(pos));
    }
}
