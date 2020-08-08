package osp.leobert.android.pandorasample.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import osp.leobert.android.pandora.rv.DataSet
import osp.leobert.android.pandora.rv.ViewHolderCreator
import osp.leobert.android.pandorasample.R
import osp.leobert.android.pandorasample.databinding.AppVhHeaderShortCutBinding
import osp.leobert.android.pandorasample.dvh.AbsViewHolder
import osp.leobert.android.pandorasample.dvh.DataBindingViewHolder

interface HeaderShortCutVO2 : DataSet.Data<HeaderShortCutVO2, AbsViewHolder<HeaderShortCutVO2>> {
    override fun setToViewHolder(viewHolder: AbsViewHolder<HeaderShortCutVO2>?) {
        viewHolder?.setData(this)
    }

    class VO : HeaderShortCutVO2
}

class HeaderShortCutVHCreator(private val itemInteract: HeaderShortCutItemInteract?) : ViewHolderCreator() {

    override fun createViewHolder(parent: ViewGroup): DataBindingViewHolder<HeaderShortCutVO2, AppVhHeaderShortCutBinding> {
        val binding = DataBindingUtil.inflate<AppVhHeaderShortCutBinding>(
                LayoutInflater.from(parent.context),
                R.layout.app_vh_header_short_cut, parent, false
        )

        val vh = object : DataBindingViewHolder<HeaderShortCutVO2, AppVhHeaderShortCutBinding>(binding) {
            var mData: HeaderShortCutVO2? = null

            override fun setData(data: HeaderShortCutVO2?) {
                super.setData(data)
                mData = data
                binding.vo = data
                binding.executePendingBindings()
            }
        }

        binding.vh = vh
        binding.itemInteract = itemInteract

        return vh
    }
}


interface HeaderShortCutItemInteract {
    //TODO: define interact functions here
}


