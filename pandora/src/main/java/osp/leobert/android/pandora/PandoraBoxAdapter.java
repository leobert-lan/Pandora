package osp.leobert.android.pandora;

import android.support.annotation.CheckResult;
import android.support.annotation.Nullable;
import android.support.v7.util.ListUpdateCallback;

/**
 * <p><b>Package:</b> osp.leobert.android.pandora </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> PandoraBoxAdapter </p>
 * <p><b>Description:</b> PandoraBoxAdapter </p>
 * Created by leobert on 2018/9/29.
 */
public abstract class PandoraBoxAdapter<T> implements Node<PandoraBoxAdapter<T>>, DataAdapter<T> {
    protected ListUpdateCallback listUpdateCallback;

    public void setListUpdateCallback(ListUpdateCallback listUpdateCallback) {
        this.listUpdateCallback = listUpdateCallback;
    }

    protected abstract void onBeforeChanged();

    protected abstract void onAfterChanged();

    protected abstract boolean inTransaction();

    public abstract void startTransaction();

    public abstract void endTransaction();

    public abstract void endTransactionSilently();

    protected abstract void setGroupIndex(int groupIndex);

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

    public abstract int getStartIndex();

    protected abstract void setStartIndex(int startIndex);
}
