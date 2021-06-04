package osp.leobert.androidkt.pandora.rv

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import osp.leobert.android.pandora.PandoraException
import java.lang.ref.WeakReference
import java.util.*

/*
 * <p><b>Package:</b> osp.leobert.androidkt.pandora.rv </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> DataSet </p>
 * Created by leobert on 2019/2/19.
 */
@SuppressWarnings("unused")
@SuppressLint("unused")
abstract class DataSet<T : DataSet.Data> {

    companion object {
        fun <DATA, VH : IViewHolder<DATA>> helpSetToViewHolder(data: D<DATA, VH>, viewHolder: VH) {
            data.setToViewHolder(viewHolder)
        }
    }


    /**
     * Because we don't supply a Super Class of data, and we used sth like ? extends D [D]
     * in the library, thus we must have something used when de-generic in the "Multi-Type" case
     * use in the generic
     *
     * @param <DA> if this VO (View Object) is only use in 'single-type', you can declare the VO type. Otherwise,
     * just declare as Data
     * @param <V>  the VH type, de-generic with the VO thus you can access the API in VO
    </V></DA> */
//    interface Data<DA : D<*, *>, V : IViewHolder<in DA>> : D<DA, V>


//    interface Data<VO> : D<Data<VO>, IViewHolder<Data<VO>>> {
//        override fun setToViewHolder(viewHolder: IViewHolder<Data<VO>>) {
//            viewHolder.setData(this)
//
//        }
//    }

//    interface Data<VO :D<VO,IViewHolder<VO>>> : D<VO, IViewHolder<VO>> {
//    }

    interface Data : D<Data, IViewHolder<Data>> {
//        override fun setToViewHolder(viewHolder: IViewHolder<VO>) {
//            viewHolder.setData(this)
//        }

        override fun setToViewHolder(viewHolder: IViewHolder<Data>) {
            viewHolder.setData(this)
        }
    }

    private val dateVhMappingPool = DataVhMappingPool()

    /**
     * @return the count of data in the data set
     */
    abstract fun getCount(): Int

    /**
     * @param position target position to fetch data
     * @return data
     */
    abstract fun getItem(position: Int): T

    private val observersRef = ArrayList<WeakReference<DataObserver>>()

    fun addDataObserver(dataObserver: DataObserver) {
        observersRef.add(WeakReference(dataObserver))
    }

    @Throws(PandoraException::class)
    fun getItemViewTypeV2(pos: Int): Int { //getItemViewType
        val key = getItem(pos).javaClass.name
        val data = getItem(pos)
        try {
            return dateVhMappingPool.getItemViewTypeV2(key, data)
        } catch (e: Exception) {
            e.printStackTrace()
            throw PandoraException(e)
        }

    }

    @Throws(PandoraException::class)
    fun createViewHolderV2(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        try {
            return dateVhMappingPool.createViewHolderV2(parent, viewType).asViewHolder()
        } catch (e: Exception) {
            e.printStackTrace()
            throw PandoraException(e)
        }

    }

    protected fun getViewTypeCount(): Int {
        return dateVhMappingPool.getViewTypeCount()
    }

    @Synchronized
    fun registerDVRelation(dataClz: Class<out Data>, viewHolderCreator: ViewHolderCreator) {
        dateVhMappingPool.registerDVRelation(dataClz, viewHolderCreator)
    }

    /*<VO : D<VO, IViewHolder<VO>>, Impl : VO>*/

    @Synchronized
    fun /*<VO : D<VO, IViewHolder<VO>>, Impl : VO>*/ registerDVRelation(dvRelation: DataVhMappingPool.DVRelation<out Data>) {
        dateVhMappingPool.registerDVRelation(dvRelation)
    }

    @Synchronized
    fun removeDVRelation(dataClz: Class<*>) {
        dateVhMappingPool.removeDVRelation(dataClz)
    }

    ///////////////////////////////////////////////////////////////////////////
    // notify change
    ///////////////////////////////////////////////////////////////////////////

    fun notifyChanged() {
        var i = 0
        while (i < observersRef.size) {
            val reference = observersRef[i]
            if (reference.get() == null) {
                observersRef.removeAt(i)
                continue
            }
            reference.get()?.onDataSetChanged()
            i++
        }
    }

    fun notifyItemChanged(position: Int) {
        var i = 0
        while (i < observersRef.size) {
            val reference = observersRef[i]
            if (reference.get() == null) {
                observersRef.removeAt(i)
                continue
            }
            reference.get()?.notifyItemChanged(position)
            i++
        }
    }

    fun notifyItemChanged(position: Int, payload: Any) {
        var i = 0
        while (i < observersRef.size) {
            val reference = observersRef[i]
            if (reference.get() == null) {
                observersRef.removeAt(i)
                continue
            }
            reference.get()?.notifyItemChanged(position, payload)
            i++
        }
    }

    fun notifyItemRangeChanged(positionStart: Int, itemCount: Int) {
        var i = 0
        while (i < observersRef.size) {
            val reference = observersRef[i]
            if (reference.get() == null) {
                observersRef.removeAt(i)
                continue
            }
            reference.get()?.notifyItemRangeChanged(positionStart, itemCount)
            i++
        }
    }

    fun notifyItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any) {
        var i = 0
        while (i < observersRef.size) {
            val reference = observersRef[i]
            if (reference.get() == null) {
                observersRef.removeAt(i)
                continue
            }
            reference.get()?.notifyItemRangeChanged(positionStart, itemCount, payload)
            i++
        }
    }

    fun notifyItemInserted(position: Int) {
        var i = 0
        while (i < observersRef.size) {
            val reference = observersRef[i]
            if (reference.get() == null) {
                observersRef.removeAt(i)
                i--
                i++
                continue
            }
            reference.get()?.notifyItemInserted(position)
            i++
        }
    }

    fun notifyItemMoved(fromPosition: Int, toPosition: Int) {
        var i = 0
        while (i < observersRef.size) {
            val reference = observersRef[i]
            if (reference.get() == null) {
                observersRef.removeAt(i)
                continue
            }
            reference.get()?.notifyItemMoved(fromPosition, toPosition)
            i++
        }
    }

    fun notifyItemRangeInserted(positionStart: Int, itemCount: Int) {
        var i = 0
        while (i < observersRef.size) {
            val reference = observersRef[i]
            if (reference.get() == null) {
                observersRef.removeAt(i)
                continue
            }
            reference.get()?.notifyItemRangeInserted(positionStart, itemCount)
            i++
        }
    }

    fun notifyItemRemoved(position: Int) {
        var i = 0
        while (i < observersRef.size) {
            val reference = observersRef[i]
            if (reference.get() == null) {
                observersRef.removeAt(i)
                continue
            }
            reference.get()?.notifyItemRemoved(position)
            i++
        }
    }

    fun notifyItemRangeRemoved(positionStart: Int, itemCount: Int) {
        var i = 0
        while (i < observersRef.size) {
            val reference = observersRef[i]
            if (reference.get() == null) {
                observersRef.removeAt(i)
                continue
            }
            reference.get()?.notifyItemRangeRemoved(positionStart, itemCount)
            i++
        }
    }
}