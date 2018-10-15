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

package osp.leobert.android.pandora.rv;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;

import java.util.Collection;

import osp.leobert.android.pandora.PandoraBoxAdapter;
import osp.leobert.android.pandora.PandoraException;
import osp.leobert.android.pandora.WrapperDataSet;

/**
 * <p><b>Package:</b> osp.leobert.android.pandorarv </p>
 * <p><b>Project:</b> Pandorarv </p>
 * <p><b>Classname:</b> PandoraWrapperRvDataSet </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2018/10/11.
 */
public class PandoraWrapperRvDataSet<T extends DataSet.D> extends DataSet {
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
    public D getItem(int position) {
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
        wrapperDataSet.addChild(sub);
    }

    public boolean hasBind2Parent() {
        return wrapperDataSet.hasBind2Parent();
    }

    public void removeFromOriginalParent() {
        wrapperDataSet.removeFromOriginalParent();
    }

    public void removeSub(PandoraBoxAdapter<T> sub) {
        wrapperDataSet.removeChild(sub);
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

    public String getAlias() {
        return wrapperDataSet.getAlias();
    }

    public void setAlias(String alias) {
        try {
            wrapperDataSet.setAlias(alias);
        } catch (PandoraException e) {
            e.printStackTrace();
        }
    }
}
