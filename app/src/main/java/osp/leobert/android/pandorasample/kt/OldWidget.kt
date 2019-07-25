//package osp.leobert.android.pandorasample.kt
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.databinding.DataBindingUtil
//import androidx.databinding.Observable
//import androidx.recyclerview.widget.RecyclerView
//import osp.leobert.android.pandora.rv.*
//
//
//import osp.leobert.android.pandorasample.R
//import osp.leobert.android.pandorasample.dvh.AbsViewHolder
//
//
//interface OldVO2 : DataSet.Data<OldVO2, AbsViewHolder<OldVO2>>,
//        ReactiveData<OldVO2,AbsViewHolder<OldVO2>> {
//    override fun setToViewHolder(viewHolder: AbsViewHolder<OldVO2>?) {
//        viewHolder?.setData(this)
//    }
//
//    class Impl :OldVO2 {
//        override fun bindReactiveVh(viewHolder: IReactiveViewHolder<OldVO2>?) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        }
//
//        override fun unbindReactiveVh() {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        }
//
//    }
//}
//
//
//class OldVHCreator(private val itemInteract: OldItemInteract?) : ViewHolderCreator(){
//
//    override fun createViewHolder(parent: ViewGroup): DataBindingViewHolder<OldVO2, AppVhOldBinding>  {
//        val binding = DataBindingUtil.inflate<AppVhOldBinding>(
//            LayoutInflater.from(parent.context),
//            R.layout.app_vh_old, parent, false
//        )
//
//        val vh = object : DataBindingViewHolder<OldVO2, AppVhOldBinding>(binding) {
//            var mData: OldVO2? = null
//
//            override fun setData(data: OldVO2?) {
//                super.setData(data)
//                mData = data
//                binding.vo = data
//            }
//        }
//
//        binding.vh = vh
//        binding.itemInteract = itemInteract
//
//        return vh
//    }
//}
//
//object a :IReactiveViewHolder<OldVO2> {
//    override fun asViewHolder(): RecyclerView.ViewHolder {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getReactiveDataIfExist(): ReactiveData<out OldVO2, out IViewHolder<OldVO2>> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun setData(data: OldVO2?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun onViewAttachedToWindow() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun onViewDetachedFromWindow() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun accept(visitor: IViewHolder.Visitor) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun onPropertyChanged(sender: Observable?, data: OldVO2?, propertyId: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//}
//
//
//
//interface OldItemInteract {
//   //TODO: define interact functions here
//}
//
