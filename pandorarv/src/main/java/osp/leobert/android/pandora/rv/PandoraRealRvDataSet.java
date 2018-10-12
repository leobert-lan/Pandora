package osp.leobert.android.pandora.rv;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;

import java.util.Collection;

import osp.leobert.android.pandora.PandoraBoxAdapter;
import osp.leobert.android.pandora.RealDataSet;

/**
 * <p><b>Package:</b> osp.leobert.android.pandorarv </p>
 * <p><b>Project:</b> Pandorarv </p>
 * <p><b>Classname:</b> PandoraRealRvDataSet </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2018/10/11.
 */
public class PandoraRealRvDataSet<T extends DataSet.D> extends DataSet {
    @NonNull
    private final RealDataSet<T> realDataSet;

    public PandoraRealRvDataSet(@NonNull RealDataSet<T> realDataSet) {
        if (realDataSet == null)
            throw new IllegalArgumentException("realDataSet cannot be null");
        this.realDataSet = realDataSet;
    }

    @NonNull
    public RealDataSet<T> getRealDataSet() {
        return realDataSet;
    }

    @Override
    public int getCount() {
        return realDataSet.getDataCount();
    }

    @Override
    public D getItem(int position) {
        return realDataSet.getDataByIndex(position);
    }

    public void startTransaction() {
        realDataSet.startTransaction();
    }

    public void endTransaction() {
        realDataSet.endTransaction();
    }

    public void endTransactionSilently() {
        realDataSet.endTransactionSilently();
    }

    @Nullable
    public PandoraBoxAdapter<T> retrieveAdapterByDataIndex(int index) {
        return realDataSet.retrieveAdapterByDataIndex(index);
    }

    @Nullable
    public Pair<PandoraBoxAdapter<T>, Integer> retrieveAdapterByDataIndex2(int index) {
        return realDataSet.retrieveAdapterByDataIndex2(index);
    }

    public T getDataByIndex(int index) {
        return realDataSet.getDataByIndex(index);
    }

    public void clearAllData() {
        realDataSet.clearAllData();
    }

    public void add(T item) {
        realDataSet.add(item);
    }

    public void add(int pos, T item) {
        realDataSet.add(pos, item);
    }

    public void addAll(Collection<T> collection) {
        realDataSet.addAll(collection);
    }

    public void remove(Object item) {
        realDataSet.remove(item);
    }

    public void removeAtPos(int position) {
        realDataSet.removeAtPos(position);
    }

    public void setData(Collection<T> collection) {
        realDataSet.setData(collection);
    }

    public boolean hasBind2Parent() {
        return realDataSet.hasBind2Parent();
    }

    public void removeFromOriginalParent() {
        realDataSet.removeFromOriginalParent();
    }

    public void removeSub(PandoraBoxAdapter<T> sub) {
        realDataSet.removeChild(sub);
    }

    public int getStartIndex() {
        return realDataSet.getStartIndex();
    }


    public String getAlias() {
        return realDataSet.getAlias();
    }

    public void setAlias(String alias) {
        realDataSet.setAlias(alias);
    }
}
