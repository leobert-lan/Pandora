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
public abstract class PandoraBoxAdapter<T> implements Node<PandoraBoxAdapter<T>>,
        DataAdapter<T>,Iterable<T> {
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
     * dispatched to the RecyclerView.Adapter. if you just want change
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
     *
     * @param parent the parent wrapper data set holds this one
     */
    protected abstract void notifyHasAddToParent(@NonNull PandoraBoxAdapter<T> parent);

    /**
     * notify this data set that it has been removed from parent.
     */
    protected abstract void notifyHasRemoveFromParent();

    @Nullable
    protected abstract PandoraBoxAdapter<T> getParent();

    private String alias;

    public String getAlias() {
        return alias;
    }

    /**
     * set alias to this adapter ,<em>attention, if one tree or node merged to this as sub-node, none check will be taken!</em>
     *
     * @param alias alias want to set to this node
     * @throws PandoraException throws if alias conflict with any node in this tree
     */
    public final void setAlias(@NonNull String alias) throws PandoraException {
        PandoraBoxAdapter checkRoot = this;
        while (checkRoot.hasBind2Parent()) {
            PandoraBoxAdapter tmp = checkRoot.getParent();
            if (tmp == null) break;
            checkRoot = tmp;
        }
        if (!Pandora.checkAliasUnique(checkRoot, alias)) {
            throw PandoraException.aliasConflict(alias);
        }
        this.alias = alias;
    }

    /**
     * @param index the index must has been resolved offset
     * @return target adapter
     */
    @Nullable
    @CheckResult
    public abstract PandoraBoxAdapter<T> retrieveAdapterByDataIndex(int index);

    @Nullable
    @CheckResult
    public abstract Pair<PandoraBoxAdapter<T>, Integer> retrieveAdapterByDataIndex2(int index);

    public abstract int getStartIndex();

    protected abstract void setStartIndex(int startIndex);

    abstract boolean isAliasConflict(@NonNull String alias);

    public abstract PandoraBoxAdapter<T> findByAlias(@NonNull final String targetAlias);

}
