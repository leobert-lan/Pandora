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

package osp.leobert.android.pandorasample;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;


import osp.leobert.android.pandora.rv.DataObserver;
import osp.leobert.android.pandora.rv.DataSet;
import osp.leobert.android.pandorasample.dvh.AbsViewHolder;

/**
 * <p><b>Package:</b> osp.leobert.android.pandorasample </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> RvAdapter </p>
 * <p><b>Description:</b> it is just a demo, you can just use it in your project or copy
 * the core code into your Adapter. Considering the existing Adapters about something like "pull-to-refresh",
 * "load-more" provided by some libraries used in your app, it's much more enjoyable to use a Composition pattern</p>
 * Created by leobert on 2018/10/11.
 */
public class RvAdapter<D extends DataSet> extends RecyclerView.Adapter<AbsViewHolder>
        implements DataObserver {

    private final D dataSet;

    protected D dataSet() {
        return dataSet;
    }

    public RvAdapter(D dataSet) {
        this.dataSet = dataSet;
        dataSet.addDataObserver(this);
    }

    @Override
    public AbsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return (AbsViewHolder) dataSet.createViewHolderV2(parent, viewType);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(AbsViewHolder holder, int position) {
        Log.i("Pandora", "onBindViewHolder: " + position);
        try {
            DataSet.helpSetToViewHolder(dataSet.getItem(position),holder);
//            dataSet.getItem(position).setToViewHolder(holder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return dataSet.getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return dataSet.getItemViewTypeV2(position);
    }

    @Override
    public void onViewAttachedToWindow(AbsViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.onViewAttachedToWindow();
    }


    @Override
    public void onViewDetachedFromWindow(AbsViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.onViewDetachedFromWindow();
    }

    @Override
    public void onDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
