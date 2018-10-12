package osp.leobert.android.pandora;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.ListUpdateCallback;
import android.util.Pair;

/**
 * <p><b>Package:</b> osp.leobert.android.pandora </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> PandoraBoxAdapter </p>
 * <p><b>Description:</b> PandoraBoxAdapter </p>
 * Created by leobert on 2018/9/29.
 */
public abstract class PandoraBoxAdapter<T> implements Node<PandoraBoxAdapter<T>>, DataAdapter<T> {
    protected ListUpdateCallback listUpdateCallback;

    /**
     * @param listUpdateCallback callback used when the data set changed.
     */
    final void setListUpdateCallback(ListUpdateCallback listUpdateCallback) {
        this.listUpdateCallback = listUpdateCallback;
    }

    /**
     * invoked before the data set will be changed, invoke this method in your custom data set operation
     */
    protected abstract void onBeforeChanged();

    /**
     * invoked after the data set has been changed, invoke this method in your custom data set operation
     */
    protected abstract void onAfterChanged();

    /**
     * @return true if this data set adapter or it's parent wrapper is in transaction
     */
    protected abstract boolean inTransaction();

    /**
     * to start a transaction to wrapper a serious of changes apply to the data set
     */
    public abstract void startTransaction();

    /**
     * to end the transaction after a serious of changes apply to the data set, and the changes will
     * dispatched to the {@link android.support.v7.widget.RecyclerView.Adapter}. if you just want change
     * the data set but ignore display the changes immediately, see {@link #endTransactionSilently()}
     */
    public abstract void endTransaction();

    /**
     * like {@link #endTransaction()}, but no view refresh after data set changed
     */
    public abstract void endTransactionSilently();

    /**
     * @param groupIndex set the index of this data set in the parent data set
     */
    protected abstract void setGroupIndex(int groupIndex);

    /**
     * notify this data set that it has been add to one Parent data set.
     * @param parent the parent wrapper data set holds this one
     */
    protected abstract void notifyHasAddToParent(@NonNull PandoraBoxAdapter<T> parent);

    private String alias;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Nullable
    @CheckResult
    public abstract PandoraBoxAdapter<T> retrieveAdapterByDataIndex(int index);

    @Nullable
    @CheckResult
    public abstract Pair<PandoraBoxAdapter<T>,Integer> retrieveAdapterByDataIndex2(int index);

    public abstract int getStartIndex();

    protected abstract void setStartIndex(int startIndex);

    abstract boolean isAliasConflict(@NonNull String alias);
}
