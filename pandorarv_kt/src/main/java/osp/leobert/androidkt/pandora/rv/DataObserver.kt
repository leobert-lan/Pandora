package osp.leobert.androidkt.pandora.rv

/*
 * <p><b>Package:</b> osp.leobert.androidkt.pandora.rv </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> DataObserver </p>
 * <p><b>Description:</b> one observer register to DataSet, see {@link DataSet#addDataObserver(DataObserver)}  </p>
 * Created by leobert on 2019/2/19.
 */
interface DataObserver {
    /*
     * invoked when data changed.
     * *now it only be invoked when a manual notify called * [DataSet.notifyChanged]
     */
    fun onDataSetChanged()

    fun notifyItemChanged(position: Int)

    fun notifyItemChanged(position: Int, payload: Any)

    fun notifyItemRangeChanged(positionStart: Int, itemCount: Int)

    fun notifyItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any)

    fun notifyItemInserted(position: Int)

    fun notifyItemMoved(fromPosition: Int, toPosition: Int)

    fun notifyItemRangeInserted(positionStart: Int, itemCount: Int)

    fun notifyItemRemoved(position: Int)

    fun notifyItemRangeRemoved(positionStart: Int, itemCount: Int)
}