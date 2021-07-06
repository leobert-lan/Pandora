package osp.leobert.android.pandorasample.kt

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import osp.leobert.androidkt.pandora.rv.IViewHolder

/**
 * <p><b>Package:</b> osp.leobert.android.pandorasample.kt </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> KtAbsViewHolder </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2019/2/20.
 */
abstract class KtAbsViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView), IViewHolder<T> {
    override fun asViewHolder(): RecyclerView.ViewHolder {
        return this
    }

    protected fun getContext(): Context {
        return itemView.context
    }

    override fun onViewAttachedToWindow() {

    }

    override fun onViewDetachedFromWindow() {

    }

    override fun accept(visitor: IViewHolder.Visitor) {
    }
}