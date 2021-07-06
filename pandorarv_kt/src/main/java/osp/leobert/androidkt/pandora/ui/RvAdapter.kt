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
package osp.leobert.androidkt.pandora.ui

import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import osp.leobert.android.pandora.Logger
import osp.leobert.android.pandora.PandoraException
import osp.leobert.androidkt.pandora.rv.DataObserver
import osp.leobert.androidkt.pandora.rv.DataSet
import osp.leobert.androidkt.pandora.rv.DataSet.Companion.helpSetToViewHolder
import osp.leobert.androidkt.pandora.rv.IViewHolder

/**
 *
 * **Package:** osp.leobert.android.pandorasample
 *
 * **Project:** Pandora
 *
 * **Classname:** RvAdapter
 *
 * **Description:** it is just a demo, you can just use it in your project or copy
 * the core code into your Adapter. Considering the existing Adapters about something updateLike "pull-to-refresh",
 * "load-more" provided by some libraries used in your app, it's much more enjoyable to use a Composition pattern
 * Created by leobert on 2018/10/11.
 */
class RvAdapter<D : DataSet<*>>(
    val dataSet: D,
    var tag: String = "not set"
) : RecyclerView.Adapter<AbsViewHolder<*>>(), DataObserver {
    private val attachedViewInfo = ""

    init {
        dataSet.addDataObserver(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbsViewHolder<*> {
        return try {
//            if (ret == null) {
//                logCreateViewHolderNull(parent, viewType)
//                ret = InvisibleVH2.Creator.sInstance.createViewHolder(parent)
//            }
            dataSet.createViewHolderV2(parent, viewType) as AbsViewHolder<*>
        } catch (e: PandoraException) {
            Logger.e(Logger.TAG, tag, e)
            logCreateViewHolderNull(parent, viewType)
            InvisibleVH2.Creator.sInstance.createViewHolder(parent)
        }
    }

    private fun logCreateViewHolderNull(parent: ViewGroup, viewType: Int) {
        val info =
            "RvAdapter create null ViewHolder! viewType is: $viewType$attachedViewInfo's adatpter; tag:$tag";
        if (TextUtils.isEmpty(attachedViewInfo)) {
            createAttachedViewInfo(parent)
        }
//
//        if (AppUtil.isDebuggable()) {
//            throw new NullPointerException(info);
//        }
        Log.e("RvAdapter", info);
//        dataSet.logDVMappingInfo();
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        createAttachedViewInfo(recyclerView)
    }

    private fun createAttachedViewInfo(view: View) {
//        try {
//            String viewGlobalName = ResourceHelper.Companion.getGlobalIdName(view);
//            if (TextUtils.isEmpty(tag))
//                tag = viewGlobalName + "'s adp";
//
//            attachedViewInfo = viewGlobalName;
//            L.d("RvAdapter2", "create attached view info:" + attachedViewInfo);
//
//        } catch (Exception e) {
//            L.e(e);
//        }
    }

    override fun onBindViewHolder(holder: AbsViewHolder<*>, position: Int) {
        Log.i("Pandora", "onBindViewHolder: $position")
        try {
            helpSetToViewHolder(dataSet.getItem(position), holder as IViewHolder<DataSet.Data>)
        } catch (e: Exception) {
            Logger.e(Logger.TAG, tag, e)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.getCount()
    }

    override fun getItemViewType(position: Int): Int {
        return try {
            dataSet.getItemViewTypeV2(position)
        } catch (e: PandoraException) {
            Logger.e(Logger.TAG, tag, e)
            -1
        }
    }

    override fun onViewAttachedToWindow(holder: AbsViewHolder<*>) {
        super.onViewAttachedToWindow(holder)
        holder.onViewAttachedToWindow()
    }

    override fun onViewDetachedFromWindow(holder: AbsViewHolder<*>) {
        super.onViewDetachedFromWindow(holder)
        holder.onViewDetachedFromWindow()
    }

    override fun onDataSetChanged() {
        super.notifyDataSetChanged()
    }
}