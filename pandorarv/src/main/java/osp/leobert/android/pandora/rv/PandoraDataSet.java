package osp.leobert.android.pandora.rv;

import android.util.Pair;

import androidx.annotation.CheckResult;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.Iterator;

import osp.leobert.android.pandora.PandoraBoxAdapter;
import osp.leobert.android.pandora.PandoraException;
import osp.leobert.android.pandora.visitor.TypeVisitor;

/**
 * <p><b>Package:</b> osp.leobert.android.pandora.rv </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> PandoraDataSet </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2022/2/13.
 */
@SuppressWarnings("unused")
public abstract class PandoraDataSet<T extends DataSet.Data, D extends PandoraBoxAdapter<T>> extends DataSet<T> {

    @NonNull
    protected final D dataSet;

    public PandoraDataSet(@NonNull D dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    public D getDataSet() {
        return dataSet;
    }

    @Override
    public int getCount() {
        return getDataCount();
    }

    /**
     * @param position target position to fetch data
     * @return data，null if out of range
     */
    @Nullable
    @Override
    public T getItem(int position) {
        return dataSet.getDataByIndex(position);
    }


    //region delegate for dataSet

    public void startTransaction() {
        dataSet.startTransaction();
    }

    public void endTransaction() {
        dataSet.endTransaction();
    }

    public void endTransactionSilently() {
        dataSet.endTransactionSilently();
    }

    public String getAlias() {
        return dataSet.getAlias();
    }

    public void setAlias(@NonNull String alias) {
        try {
            dataSet.setAlias(alias);
        } catch (PandoraException e) {
            e.printStackTrace();
        }
    }

    @CheckResult
    @Nullable
    public PandoraBoxAdapter<T> retrieveAdapterByDataIndex(int index) {
        return dataSet.retrieveAdapterByDataIndex(index);
    }

    @CheckResult
    @Nullable
    public Pair<PandoraBoxAdapter<T>, Integer> retrieveAdapterByDataIndex2(int index) {
        return dataSet.retrieveAdapterByDataIndex2(index);
    }

    public int getStartIndex() {
        return dataSet.getStartIndex();
    }

    public PandoraBoxAdapter<T> findByAlias(@NonNull String targetAlias) {
        return dataSet.findByAlias(targetAlias);
    }

    public void runForeach(PandoraBoxAdapter.Consumer<? super T> action) {
        dataSet.runForeach(action);
    }

    @Nullable
    @Override
    public <T2> T2 accept(int pos, @NonNull TypeVisitor<T2> typeVisitor) {
        return dataSet.accept(pos, typeVisitor);
    }

    public int getGroupIndex() {
        return dataSet.getGroupIndex();
    }


    public void addChild(PandoraBoxAdapter<T> sub) {
        dataSet.addChild(sub);
    }

    public boolean hasBind2Parent() {
        return dataSet.hasBind2Parent();
    }

    public void removeFromOriginalParent() {
        dataSet.removeFromOriginalParent();
    }

    public void removeChild(PandoraBoxAdapter<T> sub) {
        dataSet.removeChild(sub);
    }

    @Override
    public int getDataCount() {
        return dataSet.getDataCount();
    }

    @Override
    public T getDataByIndex(int index) {
        return dataSet.getDataByIndex(index);
    }

    @Override
    public void clearAllData() {
        dataSet.clearAllData();
    }

    @Override
    public void add(T item) {
        dataSet.add(item);
    }

    @Override
    public void add(int pos, T item) {
        dataSet.add(pos, item);
    }

    @Override
    public void addAll(Collection<T> collection) {
        dataSet.addAll(collection);
    }

    @Override
    public void remove(Object item) {
        dataSet.remove(item);
    }

    @Override
    public void removeAtPos(int position) {
        dataSet.removeAtPos(position);
    }

    @Override
    public boolean replaceAtPosIfExist(int position, T item) throws PandoraException {
        return dataSet.replaceAtPosIfExist(position, item);
    }

    @Override
    public void setData(Collection<T> collection) {
        dataSet.setData(collection);
    }

    @IntRange(from = -1L)
    @Override
    public int indexOf(T item) {
        return dataSet.indexOf(item);
    }

    public Iterator<T> iterator() {
        return dataSet.iterator();
    }

    //endregion

}
