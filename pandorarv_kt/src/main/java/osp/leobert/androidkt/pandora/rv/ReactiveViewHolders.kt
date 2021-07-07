package osp.leobert.androidkt.pandora.rv

import osp.leobert.android.pandora.Logger

/**
 * <p><b>Package:</b> osp.leobert.androidkt.pandora.rv </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> ReactiveViewHolders </p>
 * Created by leobert on 2021/7/7.
 */
class ReactiveViewHolders<VO> {
    val viewHolders by lazy { mutableSetOf<IReactiveViewHolder<VO>>() }

    fun add(viewHolder: IReactiveViewHolder<VO>) {
        viewHolders.add(viewHolder)
    }

    fun remove(viewHolder: IReactiveViewHolder<VO>) {
        viewHolders.remove(viewHolder)
    }

    fun notifyPropChanged(vo: VO, propId: Int) {
        viewHolders.forEach {
            try {
                it.observable.notifyPropertyChanged(propId)
            } catch (e: Exception) {
                Logger.e(Logger.TAG, "notify vo prop changed error", e)
            }
        }
    }
}