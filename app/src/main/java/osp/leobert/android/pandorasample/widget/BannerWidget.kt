package osp.leobert.android.pandorasample.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import osp.leobert.android.pandora.rv.DataSet
import osp.leobert.android.pandora.rv.ViewHolderCreator
import osp.leobert.android.pandorasample.R
import osp.leobert.android.pandorasample.databinding.AppVhBannerBinding
import osp.leobert.android.pandorasample.dvh.AbsViewHolder
import osp.leobert.android.pandorasample.dvh.DataBindingViewHolder

interface BannerVO2 : DataSet.Data<BannerVO2, AbsViewHolder<BannerVO2>> {
    override fun setToViewHolder(viewHolder: AbsViewHolder<BannerVO2>?) {
        viewHolder?.setData(this)
    }

    class VO : BannerVO2
}

class BannerVHCreator(private val itemInteract: BannerItemInteract?) : ViewHolderCreator() {

    override fun createViewHolder(parent: ViewGroup): DataBindingViewHolder<BannerVO2, AppVhBannerBinding> {
        val binding = DataBindingUtil.inflate<AppVhBannerBinding>(
                LayoutInflater.from(parent.context),
                R.layout.app_vh_banner, parent, false
        )

        val vh = object : DataBindingViewHolder<BannerVO2, AppVhBannerBinding>(binding) {

            var mData: BannerVO2? = null

            override fun setData(data: BannerVO2?) {
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


interface BannerItemInteract {
    //TODO: define interact functions here
}


