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

package osp.leobert.android.pandorasample.dvh;


import osp.leobert.android.pandora.rv.DataSet;
import osp.leobert.android.pandora.rv.IReactiveViewHolder;
import osp.leobert.android.pandora.rv.IViewHolder;
import osp.leobert.android.pandorasample.Utils;

/**
 * <p><b>Package:</b> osp.leobert.android.pandorasample.dvh </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> Type1VOImpl </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2018/10/11.
 */
public class Type1VOImpl implements Type1VO {
    private String s;

    public Type1VOImpl(String s) {
        this.s = s;
    }

    @Override
    public String getData() {
        return s;
    }

    @Override
    public void resetData(String data) {
        this.s = data;
    }

    @Override
    public String toString() {
        return "Type1VOImpl{" +
                "s='" + s + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Type1VOImpl type1VO = (Type1VOImpl) o;
        return Utils.equals(s, type1VO.s);
    }

    @Override
    public int hashCode() {
        return Utils.hash(s);
    }


    @Override
    public void bindReactiveVh(IReactiveViewHolder<Type1VO> viewHolder) {

    }

    @Override
    public void unbindReactiveVh() {

    }

    @Override
    public void setToViewHolder(IViewHolder<DataSet.Data> viewHolder) {
        viewHolder.setData(this);
    }
}
