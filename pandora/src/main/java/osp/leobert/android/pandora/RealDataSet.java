package osp.leobert.android.pandora;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p><b>Package:</b> osp.leobert.android.pandora </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> RealDataSet </p>
 * <p><b>Description:</b> one impl of PandoraBoxAdapter, using ArrayList to store data </p>
 * Created by leobert on 2018/9/29.
 */
public class RealDataSet<T> extends PandoraBoxAdapter<T> {

    private final List<T> oldList = new ArrayList<>();
    private final List<T> data = new ArrayList<>();
    private boolean useTransaction = false;
    private int groupIndex;// = NO_GROUP_INDEX;
    private int startIndex;

    @Nullable
    private PandoraBoxAdapter<T> parent;

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
            if (old == null)
                return now == null;
            else
                return old.equals(now);
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            if (oldItemPosition >= oldList.size())
                return false;
            if (newItemPosition >= getDataCount())
                return false;

            T old = oldList.get(oldItemPosition);
            T now = getDataByIndex(newItemPosition);
            if (old == null)
                return now == null;
            else
                return old.equals(now);
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
        if (index >= startIndex && index < (startIndex + getDataCount()))
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
        if (index >= data.size())
            return null;
        return data.get(index);
    }

    @Override
    public void clearAllData() {
        data.clear();
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
    public void setData(Collection<T> collection) {
        onBeforeChanged();
        this.data.clear();
        if (collection != null) {
            this.data.addAll(collection);
        }
        onAfterChanged();
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
    protected void hasAddToParent(@NonNull PandoraBoxAdapter<T> parent) {
        this.parent = parent;
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

    private void snapshot() {
        oldList.clear();
        oldList.addAll(data);
    }

    private void calcChangeAndNotify() {
        if (listUpdateCallback != null) {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(diffCallback);
            result.dispatchUpdatesTo(listUpdateCallback);
        }
    }
}
