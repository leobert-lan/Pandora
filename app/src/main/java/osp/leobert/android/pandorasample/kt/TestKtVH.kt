package osp.leobert.android.pandorasample.kt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import osp.leobert.android.pandorasample.R
import osp.leobert.androidkt.pandora.rv.ViewHolderCreator

/**
 * <p><b>Package:</b> osp.leobert.android.pandorasample.kt </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> TestKtVH </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2019/2/20.
 */
class TestKtVH(itemView: View) : KtAbsViewHolder<TestKtVO>(itemView) /*, IViewHolder<TestKtVO>*/ {
    var tv :TextView = itemView.findViewById(R.id.test_item_tv)

    override fun setData(x: TestKtVO) {
        tv.text = x.getData()
    }


    override fun asViewHolder(): RecyclerView.ViewHolder {
        return this
    }


    override fun onViewAttachedToWindow() {

    }

    override fun onViewDetachedFromWindow() {

    }

    class Creator(private val itemInteract: ItemInteract?) : ViewHolderCreator() {

        override fun createViewHolder(parent: ViewGroup): TestKtVH {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.app_vh_type1, parent, false)
            return TestKtVH(view)
        }
    }

    interface ItemInteract//TODO: define your actions here
}