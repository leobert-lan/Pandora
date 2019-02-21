package osp.leobert.androidkt.pandora.rv

import android.util.Pair
import osp.leobert.android.pandora.PandoraBoxAdapter
import osp.leobert.android.pandora.PandoraException
import osp.leobert.android.pandora.RealDataSet

/**
 * <p><b>Package:</b> osp.leobert.androidkt.pandora.rv </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> PandoraRealRvDataSet </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2019/2/19.
 */
class PandoraRealRvDataSet<T : D<T, IViewHolder<T>>>(private val realDataSet: RealDataSet<T>) : DataSet<T>() {

    fun getRealDataSet(): RealDataSet<T> {
        return realDataSet
    }

    override fun getCount(): Int {
        return realDataSet.dataCount
    }

    override fun getItem(position: Int): T {
        return realDataSet.getDataByIndex(position)
    }

    fun startTransaction() {
        realDataSet.startTransaction()
    }

    fun endTransaction() {
        realDataSet.endTransaction()
    }

    fun endTransactionSilently() {
        realDataSet.endTransactionSilently()
    }

    fun retrieveAdapterByDataIndex(index: Int): PandoraBoxAdapter<T>? {
        return realDataSet.retrieveAdapterByDataIndex(index)
    }

    fun retrieveAdapterByDataIndex2(index: Int): Pair<PandoraBoxAdapter<T>, Int>? {
        return realDataSet.retrieveAdapterByDataIndex2(index)
    }

    fun getDataByIndex(index: Int): T {
        return realDataSet.getDataByIndex(index)
    }

    fun clearAllData() {
        realDataSet.clearAllData()
    }

    fun add(item: T) {
        realDataSet.add(item)
    }

    fun add(pos: Int, item: T) {
        realDataSet.add(pos, item)
    }

    fun addAll(collection: Collection<T>) {
        realDataSet.addAll(collection)
    }

    fun remove(item: Any) {
        realDataSet.remove(item)
    }

    fun removeAtPos(position: Int) {
        realDataSet.removeAtPos(position)
    }

    fun setData(collection: Collection<T>) {
        realDataSet.setData(collection)
    }

    fun hasBind2Parent(): Boolean {
        return realDataSet.hasBind2Parent()
    }

    fun removeFromOriginalParent() {
        realDataSet.removeFromOriginalParent()
    }

    fun removeSub(sub: PandoraBoxAdapter<T>) {
        realDataSet.removeChild(sub)
    }

    fun getStartIndex(): Int {
        return realDataSet.startIndex
    }


    fun getAlias(): String {
        return realDataSet.alias
    }

    fun setAlias(alias: String) {
        try {
            realDataSet.alias = alias
        } catch (e: PandoraException) {
            e.printStackTrace()
        }
    }
}