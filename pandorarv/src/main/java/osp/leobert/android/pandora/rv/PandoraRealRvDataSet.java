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

import osp.leobert.android.pandora.RealDataSet;

/**
 * <p><b>Package:</b> osp.leobert.android.pandorarv </p>
 * <p><b>Project:</b> Pandorarv </p>
 * <p><b>Classname:</b> PandoraRealRvDataSet </p>
 * <p><b>Description:</b> a data set can support 'Multi-Type' and 'Pandora Real Box' </p>
 * Created by leobert on 2018/10/11.
 */
@SuppressWarnings("unused")
public class PandoraRealRvDataSet<T extends DataSet.Data> extends PandoraDataSet<T,RealDataSet<T>> {
//    @NonNull
//    @Deprecated
//    private final RealDataSet<T> realDataSet;

    public PandoraRealRvDataSet(@NonNull RealDataSet<T> realDataSet) {
        super(realDataSet);
//        if (realDataSet == null)
//            throw new IllegalArgumentException("realDataSet cannot be null");
//        this.realDataSet = realDataSet;
    }

    /**
     * @deprecated use {@link #getDataSet()} instead
     */
    @NonNull
    @Deprecated
    public RealDataSet<T> getRealDataSet() {
//        return realDataSet;
        return getDataSet();
    }

    public void setGroupIndex(int groupIndex) {
        dataSet.setGroupIndex(groupIndex);
    }

    //    @Override
//    public int getCount() {
//        return realDataSet.getDataCount();
//    }
//
//
//    /**
//     * @param position target position to fetch data
//     * @return data，null if out of range
//     */
//    @Override
//    @Nullable
//    public T getItem(int position) {
//        return realDataSet.getDataByIndex(position);
//    }
//
//    @Nullable
//    @Override
//    public <T2> T2 accept(int pos, @NonNull TypeVisitor<T2> typeVisitor) {
//        return realDataSet.accept(pos, typeVisitor);
//    }
//
//    public void startTransaction() {
//        realDataSet.startTransaction();
//    }
//
//    public void endTransaction() {
//        realDataSet.endTransaction();
//    }
//
//    public void endTransactionSilently() {
//        realDataSet.endTransactionSilently();
//    }
//
//    @Nullable
//    public PandoraBoxAdapter<T> retrieveAdapterByDataIndex(int index) {
//        return realDataSet.retrieveAdapterByDataIndex(index);
//    }
//
//    @Nullable
//    public Pair<PandoraBoxAdapter<T>, Integer> retrieveAdapterByDataIndex2(int index) {
//        return realDataSet.retrieveAdapterByDataIndex2(index);
//    }
//
//    /**
//     * use {@link #getItem(int)} instead
//     */
//    public T getDataByIndex(int index) {
//        return realDataSet.getDataByIndex(index);
//    }
//
//    public void clearAllData() {
//        realDataSet.clearAllData();
//    }
//
//    public void add(T item) {
//        realDataSet.add(item);
//    }
//
//    public void add(int pos, T item) {
//        realDataSet.add(pos, item);
//    }
//
//    public void addAll(Collection<T> collection) {
//        realDataSet.addAll(collection);
//    }
//
//    public void remove(Object item) {
//        realDataSet.remove(item);
//    }
//
//    public void removeAtPos(int position) {
//        realDataSet.removeAtPos(position);
//    }
//
//    public void setData(Collection<T> collection) {
//        realDataSet.setData(collection);
//    }
//
//    public boolean hasBind2Parent() {
//        return realDataSet.hasBind2Parent();
//    }
//
//    public void removeFromOriginalParent() {
//        realDataSet.removeFromOriginalParent();
//    }
//
//    public void removeSub(PandoraBoxAdapter<T> sub) {
//        realDataSet.removeChild(sub);
//    }
//
//    public int getStartIndex() {
//        return realDataSet.getStartIndex();
//    }
//
//    public String getAlias() {
//        return realDataSet.getAlias();
//    }
//
//    public void setAlias(String alias) {
//        try {
//            realDataSet.setAlias(alias);
//        } catch (PandoraException e) {
//            e.printStackTrace();
//        }
//    }
}
