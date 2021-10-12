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
import osp.leobert.android.pandora.rv.IViewHolder;

/**
 * <p><b>Package:</b> osp.leobert.android.pandorasample.dvh </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> Type2VOImpl </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2018/10/11.
 */
public class Type5VOImpl implements Type5VO {
    private final int i;

    public Type5VOImpl(int i) {
        this.i = i;
    }

    @Override
    public String getText() {
        return toString();
    }

    @Override
    public void setToViewHolder(IViewHolder<DataSet.Data> viewHolder) {
        viewHolder.setData(this);
    }

    @Override
    public String toString() {
        return "Type5VOImpl{" +
                "i=" + i +
                '}';
    }
}
