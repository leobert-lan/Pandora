package osp.leobert.androidkt.pandora.rv

import androidx.annotation.IntRange

/**
 * <p><b>Package:</b> osp.leobert.androidkt.pandora.rv </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> TypeCell </p>
 * <p><b>Description:</b> model for a type of item used in RecyclerView </p>
 * Created by leobert on 2019/2/19.
 */
class TypeCell<T>(@IntRange(from = 0) private val index: Int, private val dvRelation: DateVhMappingPool.DVRelation<T>) {
//    @IntRange(from = 0)
//    private val index: Int = index

    private var subTypeTokens: MutableList<String> = mutableListOf()

    @IntRange(from = 1)
    private var maxSize: Int = 1

    fun updateMaxSize(@IntRange(from = 1) maxSize: Int) {
        this.maxSize = maxSize
    }

    fun workFor(clz: String): Boolean {
        return clz == dvRelation.dataClz.name
    }

    fun getSubTypeCount(): Int {
        return dvRelation.one2N()
    }

    fun getItemViewType(data: T): Int {
        //            dvRelation.getDataClz().equals(data.getClass()) 暂不做校验
        val token = dvRelation.subTypeToken(data)
        if (!subTypeTokens.contains(token)) {
            subTypeTokens.add(token)
        }
        val index = subTypeTokens.indexOf(token)
        return this.index * maxSize + index
    }

    fun getVhCreator(subTypeIndex: Int): ViewHolderCreator {
        val subTypeToken = subTypeTokens[subTypeIndex]
        return dvRelation.getVhCreator(subTypeToken)
    }
}