package osp.leobert.androidkt.pandora.rv

/*
 * @param <DATA> if this VO (View Object) is only use in 'single-type', you can declare the VO type. Otherwise,
 * just declare as Data
 * @param <VH>   the VH type, de-generic with the VO thus you can access the API in VO
</VH></DATA> </p>
 * Created by leobert on 2019/2/19.
 */
interface D<DATA, VH : IViewHolder<DATA>> {
    /*
     * invoke this in adapter,  android.support.v7.widget.RecyclerView.Adapter#onBindViewHolder(RecyclerView.ViewHolder, int)
     * *it's important to do type check*
     * remember call [IViewHolder.setData] to set data to viewHolder.
     *
     * @param viewHolder view holder to be bind this this data
     */
    fun setToViewHolder(viewHolder: VH)
}