package osp.leobert.android.pandorasample.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BaseObservable
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.recyclerview.widget.GridLayoutManager
import osp.leobert.android.pandora.Pandora
import osp.leobert.android.pandora.rv.*
import osp.leobert.android.pandorasample.R
import osp.leobert.android.pandorasample.RvAdapter
import osp.leobert.android.pandorasample.databinding.AppVhCollectionsBinding
import osp.leobert.android.pandorasample.dvh.AbsViewHolder
import osp.leobert.android.pandorasample.dvh.DataBindingViewHolder

interface CollectionsVO2 : DataSet.Data, ReactiveData<CollectionsVO2, AbsViewHolder<CollectionsVO2>> {
    override fun setToViewHolder(viewHolder: IViewHolder<DataSet.Data>?) {
        viewHolder?.setData(this)
    }

    val items: List<DataSet.Data>

    class Impl : CollectionsVO2 {
        private var viewHolder: IReactiveViewHolder<CollectionsVO2>? = null

        private val observable = BaseObservable().apply {
            addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    viewHolder?.onPropertyChanged(sender ?: this@apply, this@Impl, propertyId)
                }

            })
        }
        override val items: List<DataSet.Data> = arrayListOf(
                SingleItemVO2.Impl(),
                SingleItemVO2.Impl(),
                SingleItemVO2.Impl(),
                SingleItemVO2.Impl()
        )

        override fun bindReactiveVh(viewHolder: IReactiveViewHolder<CollectionsVO2>?) {
            this.viewHolder = viewHolder
        }

        override fun unbindReactiveVh() {
            viewHolder = null
        }
    }
}

class CollectionsVHCreator(private val itemInteract: CollectionsItemInteract?) : ViewHolderCreator() {

    override fun createViewHolder(parent: ViewGroup): DataBindingViewHolder<CollectionsVO2, AppVhCollectionsBinding> {
        val binding = DataBindingUtil.inflate<AppVhCollectionsBinding>(
                LayoutInflater.from(parent.context),
                R.layout.app_vh_collections, parent, false
        )

        val vh = object : DataBindingViewHolder<CollectionsVO2, AppVhCollectionsBinding>(binding), IReactiveViewHolder<CollectionsVO2> {

            var mData: CollectionsVO2? = null
            private val dataSet = PandoraRealRvDataSet<DataSet.Data>(Pandora.real())

            init {
                val adapter = RvAdapter(dataSet)
                Pandora.bind2RecyclerViewAdapter(dataSet.getRealDataSet(), adapter)
                binding.rv.isNestedScrollingEnabled = false

                dataSet.registerDVRelation(SingleItemVO2.Impl::class.java, SingleItemVHCreator(null))

                binding.rv.layoutManager = GridLayoutManager(context, 4)
                binding.rv.adapter = adapter
            }


            override fun setData(data: CollectionsVO2?) {
                super.setData(data)
                mData = data
                binding.vh = this
                binding.vo = data

                dataSet.setData(data?.items)
                binding.executePendingBindings()
            }

            override fun getReactiveDataIfExist(): ReactiveData<out CollectionsVO2, out IViewHolder<CollectionsVO2>>? = mData

            override fun accept(visitor: IViewHolder.Visitor) {
                visitor.visit(this)
            }

            override fun onPropertyChanged(sender: Observable?, data: CollectionsVO2, propertyId: Int) {
                setData(data)
            }
        }

        binding.vh = vh
        binding.itemInteract = itemInteract

        return vh
    }
}

interface CollectionsItemInteract {
}


