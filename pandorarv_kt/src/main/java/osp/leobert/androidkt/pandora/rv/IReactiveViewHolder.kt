package osp.leobert.androidkt.pandora.rv

import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import osp.leobert.android.pandora.Logger

/**
 * <p><b>Package:</b> osp.leobert.androidkt.pandora.rv </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> IReactiveViewHolder </p>
 * Created by leobert on 2021/7/6.
 */
interface IReactiveViewHolder<T> : IViewHolder<T> {
    companion object {

        val MAKE_SURE_UNBIND_VISITOR: IViewHolder.Visitor<Any> =
            object : IViewHolder.Visitor<Any>() {

                override fun visit(holder: IReactiveViewHolder<Any>) {
                    super.visit(holder)
                    holder.getReactiveDataIfExist()?.unbindReactiveVh(holder)
                }
            }

        val MAKE_SURE_BIND_VISITOR: IViewHolder.Visitor<Any> =
            object : IViewHolder.Visitor<Any>() {
                override fun visit(holder: IReactiveViewHolder<Any>) {
                    super.visit(holder)

                    holder.getReactiveDataIfExist()?.let {

                        try {
                            it.bindReactiveVh(holder)
                        } catch (e: Exception) {
                            Logger.e(Logger.TAG, "exception when binding reactive data!", e)
                        }
                    }

                }
            }
    }

    val observable: BaseObservable

    fun getReactiveDataIfExist(): DataSet.ReactiveData<T>?

    fun onPropertyChanged(sender: Observable?, data: T, propertyId: Int)
}