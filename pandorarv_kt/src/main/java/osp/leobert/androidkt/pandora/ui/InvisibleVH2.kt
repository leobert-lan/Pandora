package osp.leobert.androidkt.pandora.ui

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import osp.leobert.androidkt.pandora.rv.DataSet
import osp.leobert.androidkt.pandora.rv.ViewHolderCreator

class InvisibleVH2(itemView: View) : AbsViewHolder<DataSet.Data>(itemView) {

    override fun setData(data: DataSet.Data) {
    }

    class Creator : ViewHolderCreator() {
        override fun createViewHolder(parent: ViewGroup): AbsViewHolder<DataSet.Data> {
            val view: View = FrameLayout(parent.context)
            view.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 0)
            return InvisibleVH2(view)
        }

        companion object {
            @JvmField
            var sInstance = Creator()
        }
    }
}