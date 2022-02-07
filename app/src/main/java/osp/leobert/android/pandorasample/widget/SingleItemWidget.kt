package osp.leobert.android.pandorasample.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BaseObservable
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import osp.leobert.android.pandora.rv.*
import osp.leobert.android.pandorasample.R
import osp.leobert.android.pandorasample.databinding.AppVhSingleItemBinding
import osp.leobert.android.pandorasample.dvh.AbsViewHolder
import osp.leobert.android.pandorasample.dvh.DataBindingViewHolder

interface SingleItemVO2 : DataSet.Data, ReactiveData<SingleItemVO2> {

    override fun setToViewHolder(viewHolder: IViewHolder<DataSet.Data>?) {
        viewHolder?.setData(this)
    }

    val text: String

    class Impl : SingleItemVO2 {
        private var viewHolder: IReactiveViewHolder<SingleItemVO2>? = null

        private val observable = BaseObservable().apply {
            addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    viewHolder?.onPropertyChanged(sender ?: this@apply, this@Impl, propertyId)
                }

            })
        }
        override val text: String = "Not yet implemented"

        override fun bindReactiveVh(viewHolder: IReactiveViewHolder<SingleItemVO2>?) {
            this.viewHolder = viewHolder
        }

        override fun unbindReactiveVh() {
            viewHolder = null
        }
    }
}

class SingleItemVHCreator(private val itemInteract: SingleItemItemInteract?) : ViewHolderCreator() {

    override fun createViewHolder(parent: ViewGroup): DataBindingViewHolder<SingleItemVO2, AppVhSingleItemBinding> {
        val binding = DataBindingUtil.inflate<AppVhSingleItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.app_vh_single_item, parent, false
        )

        val vh = object : DataBindingViewHolder<SingleItemVO2, AppVhSingleItemBinding>(binding), IReactiveViewHolder<SingleItemVO2> {

            var mData: SingleItemVO2? = null

            override fun setData(data: SingleItemVO2?) {
                super.setData(data)
                mData = data
                binding.vh = this
                binding.vo = data
                binding.executePendingBindings()
            }

            override fun getReactiveDataIfExist(): ReactiveData<SingleItemVO2>? = mData

            override fun accept(visitor: IViewHolder.Visitor) {
                visitor.visit(this)
            }

            override fun onPropertyChanged(sender: Observable?, data: SingleItemVO2, propertyId: Int) {
            }
        }

        binding.vh = vh
        binding.itemInteract = itemInteract

        return vh
    }
}

interface SingleItemItemInteract {
    //TODO: define interact functions here
}


