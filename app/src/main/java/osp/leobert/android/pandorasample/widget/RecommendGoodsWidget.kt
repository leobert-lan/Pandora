package osp.leobert.android.pandorasample.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import osp.leobert.android.pandora.rv.DataSet
import osp.leobert.android.pandora.rv.ViewHolderCreator
import osp.leobert.android.pandorasample.R
import osp.leobert.android.pandorasample.databinding.AppVhRecommendGoodsBinding
import osp.leobert.android.pandorasample.dvh.AbsViewHolder
import osp.leobert.android.pandorasample.dvh.DataBindingViewHolder

interface RecommendGoodsVO2 : DataSet.Data<RecommendGoodsVO2, AbsViewHolder<RecommendGoodsVO2>> {
    override fun setToViewHolder(viewHolder: AbsViewHolder<RecommendGoodsVO2>?) {
        viewHolder?.setData(this)
    }

    class VO : RecommendGoodsVO2
}

class RecommendGoodsVHCreator(private val itemInteract: RecommendGoodsItemInteract?) : ViewHolderCreator() {

    override fun createViewHolder(parent: ViewGroup): DataBindingViewHolder<RecommendGoodsVO2, AppVhRecommendGoodsBinding> {
        val binding = DataBindingUtil.inflate<AppVhRecommendGoodsBinding>(
                LayoutInflater.from(parent.context),
                R.layout.app_vh_recommend_goods, parent, false
        )

        val vh = object : DataBindingViewHolder<RecommendGoodsVO2, AppVhRecommendGoodsBinding>(binding) {

            var mData: RecommendGoodsVO2? = null

            override fun setData(data: RecommendGoodsVO2?) {
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


interface RecommendGoodsItemInteract {
}


