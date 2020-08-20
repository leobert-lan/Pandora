package osp.leobert.androidkt.pandora.rv

import android.util.SparseArray
import android.view.ViewGroup
import osp.leobert.android.pandora.Logger
import osp.leobert.android.pandora.PandoraException

/**
 * <p><b>Package:</b> osp.leobert.androidkt.pandora.rv </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> DateVhMappingPool </p>
 * <p><b>Description:</b> a pool to restore and fetch the relationship between VO and VH  </p>
 * Created by leobert on 2019/2/19.
 */

class DataVhMappingPool {
    internal val viewTypeCache = SparseArray<TypeCell<DataSet.Data>>()
    private var maxSize = 5

    private var internalErrorTypeCell: TypeCell<*>? = null
    private var typeCellKey: Int = 0

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

                        if (Logger.DEBUG)
                            Logger.i(Logger.TAG, "want to remove:" + dataClz.name + "; executed typeCell:" + typeCell)

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
    fun registerDVRelation(dataClz: Class<out DataSet.Data>, viewHolderCreator: ViewHolderCreator) {
        this.registerDVRelation(DataVhRelation(dataClz, viewHolderCreator))
    }


    @Synchronized
    @Suppress("unchecked")
    @SuppressWarnings("unchecked")
    fun registerDVRelation(dvRelation: DVRelation<out DataSet.Data>?) {
        dvRelation?.let {
            synchronized(viewTypeCache) {
                val n = it.one2N()

                while (n > maxSize) {
                    maxSize *= 2
                    for (i in 0 until viewTypeCache.size()) {
                        viewTypeCache.valueAt(i).updateMaxSize(maxSize)
                    }
                }
                val index = typeCellKey

                TypeCell(index, it).let { typeCell ->
                    typeCell.updateMaxSize(maxSize)
                    if (Logger.DEBUG) {
                        Logger.i(Logger.TAG, "registerDVRelation: cacheKey" + index + "hasKey?" + (viewTypeCache[index] != null) + " ; typeCell:" + typeCell)
                    }

                    viewTypeCache.put(index, typeCell as TypeCell<DataSet.Data>)
                }
                typeCellKey++
            }
        }
    }

    @Synchronized
    private fun registerDVRelation(typeCell: TypeCell<DataSet.Data>?) {
        if (typeCell == null) return
        synchronized(viewTypeCache) {
            val index = typeCellKey
            val copy: TypeCell<DataSet.Data> = TypeCell.of(index, typeCell)
            val n: Int = copy.getSubTypeCount()
            while (n > maxSize) {
                maxSize *= 2
                for (i in 0 until viewTypeCache.size()) {
                    viewTypeCache.valueAt(i).updateMaxSize(maxSize)
                }
            }
            copy.updateMaxSize(maxSize)
            if (Logger.DEBUG) {
                Logger.i(Logger.TAG, "registerDVRelation: cacheKey" + index + "hasKey?" + (viewTypeCache[index] != null) + " ; typeCell:" + copy)
            }
            viewTypeCache.put(index, copy)
            typeCellKey++
        }
    }

    fun merge(pool:DataVhMappingPool) {
        for (i in 0 until pool.typeCellKey) {
            registerDVRelation(pool.viewTypeCache.get(i))
        }
    }

    fun whenInternalError(viewHolderCreator: ViewHolderCreator) {
        this.internalErrorTypeCell = TypeCell(Integer.MAX_VALUE,
                DataVhRelation(DataSet.Data::class.java, viewHolderCreator))
    }

    //fun <VO : D<VO, IViewHolder<VO>>> getItemViewTypeV2(key: String, data: VO): Int { //getItemViewType
    fun getItemViewTypeV2(key: String, data: DataSet.Data): Int { //getItemViewType
        for (i in 0 until viewTypeCache.size()) {//折半查找可能效率更高一点
            val typeCell = viewTypeCache.valueAt(i)
            if (typeCell?.workFor(key) == true) {
                return typeCell.getItemViewType(data)
            }
        }
        if (Logger.DEBUG) {
            val e = RuntimeException("have you register for:$key")
            Logger.e(Logger.TAG, "missing type register", e)
            throw e
        } else {
            return Integer.MAX_VALUE
        }
    }

    fun getViewTypeCount(): Int {//getViewTypeCount
        var ret = 0
        internalErrorTypeCell?.let { ret++ }
        for (i in 0 until viewTypeCache.size()) {
            ret += viewTypeCache.valueAt(i).getSubTypeCount()
        }
        return ret
    }

    fun createViewHolderV2(parent: ViewGroup, viewType: Int): IViewHolder<*> { // createViewHolder(ViewGroup parent, int viewType)

        try {
            val index = viewType / maxSize
            val subIndex = viewType % maxSize
//            return viewTypeCache.valueAt(index).getVhCreator(subIndex).createViewHolder(parent)
            return viewTypeCache.get(index).getVhCreator(subIndex).createViewHolder(parent)
        } catch (e: Exception) {
            if (Logger.DEBUG) {
                if (internalErrorTypeCell != null)
                    return internalErrorTypeCell!!.getVhCreator(0).createViewHolder(parent)
                Logger.e(Logger.TAG, "missing viewType:$viewType ?", e)
                throw e
            } else {
                return (internalErrorTypeCell?.getVhCreator(0) ?: throw PandoraException(e))
                        .createViewHolder(parent)
            }
        }

    }


    interface DVRelation<T> {

        val dataClz: Class<T>

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
            return DVRelation.SINGLE_TYPE_TOKEN
        }

        override fun getVhCreator(subTypeToken: String): ViewHolderCreator {
            return vhCreator
        }
    }
}
