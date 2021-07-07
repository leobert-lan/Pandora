package osp.leobert.androidkt.pandora.ui

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import osp.leobert.androidkt.pandora.rv.IViewHolder

abstract class AbsViewHolder<T>(
    itemView: View
) : RecyclerView.ViewHolder(itemView), IViewHolder<T> {

    val context: Context
        get() = itemView.context

    override fun asViewHolder(): RecyclerView.ViewHolder {
        return this
    }

    override fun onViewAttachedToWindow() {
    }

    override fun onViewDetachedFromWindow() {
    }

    override fun accept(visitor: IViewHolder.Visitor<T>) {
    }
}