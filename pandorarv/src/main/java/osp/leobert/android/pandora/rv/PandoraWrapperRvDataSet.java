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

    public PandoraWrapperRvDataSet(@NonNull WrapperDataSet<T> wrapperDataSet) {
        super(wrapperDataSet);
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

}
