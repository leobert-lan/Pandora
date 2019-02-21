package osp.leobert.androidkt.pandora.rv

import android.util.SparseArray
import android.view.ViewGroup
import osp.leobert.android.pandora.Logger

/**
 * <p><b>Package:</b> osp.leobert.androidkt.pandora.rv </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> DateVhMappingPool </p>
 * <p><b>Description:</b> a pool to restore and fetch the relationship between VO and VH  </p>
 * Created by leobert on 2019/2/19.
 */
class DateVhMappingPool<T : D<T, IViewHolder<T>>> {
    private val viewTypeCache = SparseArray<TypeCell<Any>>()
    private var maxSize = 5

    @Synchronized
    fun removeDVRelation(dataClz: Class<*>) {
        synchronized(viewTypeCache) {
            var i = 0
            while (i < viewTypeCache.size()) {
                try {
                    val typeCell = viewTypeCache.valueAt(i)
                    if (typeCell.workFor(dataClz.name)) {
                        val key = viewTypeCache.keyAt(i)
                        viewTypeCache.remove(key)
                        i--
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Logger.e(Logger.TAG, "DateVhMappingPool removeDVRelation error", e)
                }

                i++
            }
        }
    }

    @Synchronized
    fun <VO : D<VO, IViewHolder<VO>>, Impl : VO> registerDVRelation(dataClz: Class<Impl>, viewHolderCreator: ViewHolderCreator) {
        this.registerDVRelation(DataVhRelation(dataClz, viewHolderCreator))
    }

//    @Synchronized
//    fun <T /*: D<T, IViewHolder<T>>*/> registerDvRelation(vararg dvRelations: DVRelation<Any>) {
//        for (dvRelation in dvRelations)
//            registerDVRelation(dvRelation)
//    }

    @Synchronized
    fun <VO : D<VO, IViewHolder<VO>>, Impl : VO> registerDVRelation(dvRelation: DVRelation<Impl>?) {
        dvRelation?.let {
            synchronized(viewTypeCache) {
                val n = it.one2N()

                while (n > maxSize) {
                    maxSize *= 2
                    for (i in 0 until viewTypeCache.size()) {
                        viewTypeCache.get(i).updateMaxSize(maxSize)
                    }
                }

                viewTypeCache.size().let { index ->
                    TypeCell(index, it).let { typeCell ->
                        typeCell.updateMaxSize(maxSize)
                        viewTypeCache.put(index, typeCell as TypeCell<Any>)
                    }
                }

//                val index = viewTypeCache.size()
//                val typeCell = TypeCell(index, it)
//                typeCell.updateMaxSize(maxSize)
//                viewTypeCache.put(index, typeCell)
            }
        }

    }

    fun getItemViewTypeV2(key: String, data: T): Int { //getItemViewType
        for (i in 0 until viewTypeCache.size()) {//折半查找可能效率更高一点
            if (viewTypeCache.get(i).workFor(key)) {
                return viewTypeCache.get(i).getItemViewType(data)
            }
        }
        val e = RuntimeException("have you register for:$key")
        Logger.e(Logger.TAG, "missing type register", e)
        throw e
    }

    fun getViewTypeCount(): Int {//getViewTypeCount
        var ret = 0
        for (i in 0 until viewTypeCache.size()) {
            ret += viewTypeCache.get(i).getSubTypeCount()
        }
        return ret
    }

    fun createViewHolderV2(parent: ViewGroup, viewType: Int): IViewHolder<*> { // createViewHolder(ViewGroup parent, int viewType)
        val index = viewType / maxSize
        val subIndex = viewType % maxSize
        return viewTypeCache.get(index).getVhCreator(subIndex).createViewHolder(parent)
    }


    interface DVRelation<in T> {

        val dataClz: Class<in T>

        fun one2N(): Int

        fun subTypeToken(data: T): String

        fun getVhCreator(subTypeToken: String): ViewHolderCreator

        companion object {
            const val SINGLE_TYPE_TOKEN = "type_one"
        }
    }

    private class DataVhRelation<T> internal constructor(override val dataClz: Class<T>, private val vhCreator: ViewHolderCreator) : DVRelation<T> {

        override fun one2N(): Int {
            return 1
        }

        override fun subTypeToken(data: T): String {
            return DateVhMappingPool.DVRelation.SINGLE_TYPE_TOKEN
        }

        override fun getVhCreator(subTypeToken: String): ViewHolderCreator {
            return vhCreator
        }
    }
}
