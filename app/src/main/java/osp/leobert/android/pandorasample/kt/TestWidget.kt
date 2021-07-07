package osp.leobert.android.pandorasample.kt

import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import osp.leobert.android.pandorasample.databinding.AppVhTestBinding
import osp.leobert.androidkt.pandora.rv.ViewHolderCreator
import osp.leobert.android.pandorasample.R
import osp.leobert.androidkt.pandora.ui.DataBindingViewHolder
import androidx.databinding.Observable
import androidx.databinding.BaseObservable

import osp.leobert.androidkt.pandora.ui.AbsViewHolder
import osp.leobert.androidkt.pandora.rv.*

interface TestVO2 : DataSet.Data, DataSet.ReactiveData<TestVO2> {
    class Impl : TestVO2 {
        private val viewHolders by lazy { ReactiveViewHolders<TestVO2>() }

        override fun bindReactiveVh(viewHolder: IReactiveViewHolder<TestVO2>) {
            viewHolders.add(viewHolder)
        }

        override fun unbindReactiveVh(viewHolder: IReactiveViewHolder<TestVO2>) {
            viewHolders.remove(viewHolder)
        }
    }
}

class TestVH(val binding: AppVhTestBinding) :
    DataBindingViewHolder<TestVO2, AppVhTestBinding>(binding),
    IReactiveViewHolder<TestVO2> {

    var mData: TestVO2? = null

    override fun setData(data: TestVO2) {
        super.setData(data)
        mData = data
        binding.vh = this
        binding.vo = data
        binding.executePendingBindings()
    }

    override fun getReactiveDataIfExist(): DataSet.ReactiveData<TestVO2>? = mData

    override fun accept(visitor: IViewHolder.Visitor<TestVO2>) {
        visitor.visit(this)
    }

    override fun onPropertyChanged(sender: Observable?, data: TestVO2, propertyId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override val observable: BaseObservable = BaseObservable().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                mData?.let { data ->
                    this@TestVH.onPropertyChanged(sender ?: this@apply, data, propertyId)
                }
            }
        })
    }
}

class TestVHCreator(private val itemInteract: TestItemInteract?) : ViewHolderCreator() {

    override fun createViewHolder(parent: ViewGroup): DataBindingViewHolder<TestVO2, AppVhTestBinding> {
        val binding = DataBindingUtil.inflate<AppVhTestBinding>(
            LayoutInflater.from(parent.context),
            R.layout.app_vh_test, parent, false
        )

        val vh = TestVH(binding)
        binding.vh = vh
        binding.itemInteract = itemInteract

        return vh
    }
}

interface TestItemInteract {
    //TODO: define interact functions here
}


