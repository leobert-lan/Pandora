package osp.leobert.android.pandorasample.kt

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import osp.leobert.android.pandora.Logger
import osp.leobert.android.pandora.PandoraException
import osp.leobert.androidkt.pandora.rv.D
import osp.leobert.androidkt.pandora.rv.DataSet
import osp.leobert.androidkt.pandora.rv.IViewHolder

/**
 * <p><b>Package:</b> osp.leobert.android.pandorasample.kt </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> KtRvAdapter </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2019/2/20.
 */
class KtRvAdapter<Item : D<Item, IViewHolder<Item>>, DS :DataSet<Item>
        >(private val dataSet: DS, private val tag: String)
    : RecyclerView.Adapter<KtAbsViewHolder<Item>>() {
    override fun onBindViewHolder(holder: KtAbsViewHolder<Item>, position: Int) {
        Log.i("Pandora", "onBindViewHolder: $position")
        try {
            DataSet.helpSetToViewHolder(dataSet.getItem(position), holder)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun dataSet(): DataSet<Item> {
        return dataSet
    }

    init {

//        dataSet.addDataObserver(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KtAbsViewHolder<Item> {

        try {
            return dataSet.createViewHolderV2(parent, viewType) as KtAbsViewHolder<Item>
//            throw PandoraException("parent is null when onCreateViewHolder")
        } catch (e: PandoraException) {
            e.printStackTrace()
            Logger.e(Logger.TAG, tag, e)
            throw e
        }
    }


    override fun getItemCount(): Int {
        return dataSet.getCount()
    }

    override fun getItemViewType(position: Int): Int {
        return try {
            dataSet.getItemViewTypeV2(position)
        } catch (e: PandoraException) {
            e.printStackTrace()
            Logger.e(Logger.TAG, tag, e)
            -1
        }

    }



    override fun onViewAttachedToWindow(holder: KtAbsViewHolder<Item>) {
        super.onViewAttachedToWindow(holder)
        holder?.onViewAttachedToWindow()
    }


    override fun onViewDetachedFromWindow(holder: KtAbsViewHolder<Item>) {
        super.onViewDetachedFromWindow(holder)
        holder?.onViewDetachedFromWindow()
    }

}