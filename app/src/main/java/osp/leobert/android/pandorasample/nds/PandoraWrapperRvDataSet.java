package osp.leobert.android.pandorasample.nds;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.ListUpdateCallback;
import android.util.Pair;

import java.util.Collection;

import osp.leobert.android.pandora.PandoraBoxAdapter;
import osp.leobert.android.pandora.WrapperDataSet;
import osp.leobert.android.pandorasample.AbsViewHolder;
import osp.leobert.android.pandorasample.DataSet;

/**
 * <p><b>Package:</b> osp.leobert.android.pandorasample.nds </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> PandoraWrapperRvDataSet </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2018/10/11.
 */
public class PandoraWrapperRvDataSet<T extends DataSet.Data<T ,? extends AbsViewHolder<T>>> extends DataSet {
    @NonNull
    private final WrapperDataSet<T> wrapperDataSet;

    public PandoraWrapperRvDataSet(@NonNull WrapperDataSet<T> wrapperDataSet) {
        if (wrapperDataSet == null)
            throw new IllegalArgumentException("wrapperDataSet cannot be null");
        this.wrapperDataSet = wrapperDataSet;
    }

    @NonNull
    public WrapperDataSet<T> getDataSet() {
        return wrapperDataSet;
    }

    @Override
    public int getCount() {
        return wrapperDataSet.getDataCount();
    }

    @Override
    public Data getItem(int position) {
        return wrapperDataSet.getDataByIndex(position);
    }

    public int getStartIndex() {
        return wrapperDataSet.getStartIndex();
    }

    public int getGroupIndex() {
        return wrapperDataSet.getGroupIndex();
    }

    public void setGroupIndex(int groupIndex) {
        wrapperDataSet.setGroupIndex(groupIndex);
    }

    public void addSub(PandoraBoxAdapter<T> sub) {
        wrapperDataSet.addSub(sub);
    }

    public boolean hasBind2Parent() {
        return wrapperDataSet.hasBind2Parent();
    }

    public void removeFromOriginalParent() {
        wrapperDataSet.removeFromOriginalParent();
    }

    public void removeSub(PandoraBoxAdapter<T> sub) {
        wrapperDataSet.removeSub(sub);
    }

    @Nullable
    public T getDataByIndex(int index) {
        return wrapperDataSet.getDataByIndex(index);
    }

    public void clearAllData() {
        wrapperDataSet.clearAllData();
    }

    public void add(T item) {
        wrapperDataSet.add(item);
    }

    public void add(int pos, T item) {
        wrapperDataSet.add(pos, item);
    }

    public void addAll(Collection<T> collection) {
        wrapperDataSet.addAll(collection);
    }

    public void remove(Object item) {
        wrapperDataSet.remove(item);
    }

    public void removeAtPos(int position) {
        wrapperDataSet.removeAtPos(position);
    }

    public void startTransaction() {
        wrapperDataSet.startTransaction();
    }

    public void endTransaction() {
        wrapperDataSet.endTransaction();
    }

    public void endTransactionSilently() {
        wrapperDataSet.endTransactionSilently();
    }

    @CheckResult
    @Nullable
    public PandoraBoxAdapter<T> retrieveAdapterByDataIndex(int index) {
        return wrapperDataSet.retrieveAdapterByDataIndex(index);
    }

    @Nullable
    public Pair<PandoraBoxAdapter<T>, Integer> retrieveAdapterByDataIndex2(int index) {
        return wrapperDataSet.retrieveAdapterByDataIndex2(index);
    }

    public void setListUpdateCallback(ListUpdateCallback listUpdateCallback) {
        wrapperDataSet.setListUpdateCallback(listUpdateCallback);
    }

    public String getAlias() {
        return wrapperDataSet.getAlias();
    }

    public void setAlias(String alias) {
        wrapperDataSet.setAlias(alias);
    }
}
