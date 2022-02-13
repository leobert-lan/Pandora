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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import osp.leobert.android.pandora.PandoraBoxAdapter;
import osp.leobert.android.pandora.WrapperDataSet;

/**
 * <p><b>Package:</b> osp.leobert.android.pandorarv </p>
 * <p><b>Project:</b> Pandorarv </p>
 * <p><b>Classname:</b> PandoraWrapperRvDataSet </p>
 * <p><b>Description:</b> a data set can support 'Multi-Type' and 'Pandora Wrapper Box' </p>
 * Created by leobert on 2018/10/11.
 */
@SuppressWarnings("unused")
public class PandoraWrapperRvDataSet<T extends DataSet.Data> extends PandoraDataSet<T,WrapperDataSet<T>> {
//    @NonNull
//    @Deprecated
//    private final WrapperDataSet<T> wrapperDataSet;

    public PandoraWrapperRvDataSet(@NonNull WrapperDataSet<T> wrapperDataSet) {
        super(wrapperDataSet);
//        if (wrapperDataSet == null)
//            throw new IllegalArgumentException("wrapperDataSet cannot be null");
//        this.wrapperDataSet = wrapperDataSet;
    }

    @NonNull
    public WrapperDataSet<T> getDataSet() {
        return dataSet;
    }

    public void setGroupIndex(int groupIndex) {
        dataSet.setGroupIndex(groupIndex);
    }

    public void addSub(PandoraBoxAdapter<T> sub) {
        dataSet.addChild(sub);
    }

    public void merge(DataVhMappingPool pool) {
        dataVhMappingPool.merge(pool);
    }

    public void removeSub(PandoraBoxAdapter<T> sub) {
        dataSet.removeChild(sub);
    }

    @Nullable
    public PandoraBoxAdapter<T> getChild(int index) {
        return dataSet.getChild(index);
    }

    public void clearAllChildren() {
        dataSet.clearAllChildren();
    }

    //    @Override
//    public int getCount() {
//        return dataSet.getDataCount();
//    }
//
//    /**
//     * @param position target position to fetch data
//     * @return data，null if out of range
//     */
//    @Override
//    @Nullable
//    public T getItem(int position) {
//        return dataSet.getDataByIndex(position);
//    }
//
//    @Nullable
//    @Override
//    public <T2> T2 accept(int pos, @NonNull TypeVisitor<T2> typeVisitor) {
//        return dataSet.accept(pos,typeVisitor);
//    }
//
//    public int getStartIndex() {
//        return dataSet.getStartIndex();
//    }
//
//    public int getGroupIndex() {
//        return dataSet.getGroupIndex();
//    }
//
//    public boolean hasBind2Parent() {
//        return dataSet.hasBind2Parent();
//    }
//
//    public void removeFromOriginalParent() {
//        dataSet.removeFromOriginalParent();
//    }
//
//    /**
//     * also see {@link #getItem(int)}
//     */
//    @Nullable
//    public T getDataByIndex(int index) {
//        return dataSet.getDataByIndex(index);
//    }
//
//    public void clearAllData() {
//        dataSet.clearAllData();
//    }
//
//    public void add(T item) {
//        dataSet.add(item);
//    }
//
//    public void add(int pos, T item) {
//        dataSet.add(pos, item);
//    }
//
//    public void addAll(Collection<T> collection) {
//        dataSet.addAll(collection);
//    }
//
//    public void remove(Object item) {
//        dataSet.remove(item);
//    }
//
//    public void removeAtPos(int position) {
//        dataSet.removeAtPos(position);
//    }
//
//    public void startTransaction() {
//        dataSet.startTransaction();
//    }
//
//    public void endTransaction() {
//        dataSet.endTransaction();
//    }
//
//    public void endTransactionSilently() {
//        dataSet.endTransactionSilently();
//    }
//
//    @CheckResult
//    @Nullable
//    public PandoraBoxAdapter<T> retrieveAdapterByDataIndex(int index) {
//        return dataSet.retrieveAdapterByDataIndex(index);
//    }
//
//    @Nullable
//    public Pair<PandoraBoxAdapter<T>, Integer> retrieveAdapterByDataIndex2(int index) {
//        return dataSet.retrieveAdapterByDataIndex2(index);
//    }
//
//    public String getAlias() {
//        return dataSet.getAlias();
//    }
//
//    public void setAlias(String alias) {
//        try {
//            dataSet.setAlias(alias);
//        } catch (PandoraException e) {
//            e.printStackTrace();
//        }
//    }
}
