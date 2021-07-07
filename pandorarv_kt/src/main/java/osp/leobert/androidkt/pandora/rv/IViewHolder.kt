package osp.leobert.androidkt.pandora.rv

import androidx.recyclerview.widget.RecyclerView

/*
 * <p><b>Package:</b> osp.leobert.androidkt.pandora.rv </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> IViewHolder </p>
 * <p><b>Description:</b> abstract view holder </p>
 * considering the original project may have some library using a base RecyclerView.Adapter. We change
 * the class AbsViewHolder to interface IViewHolder to improve compatibility with original project.
 * <p>
 * now, you can declare a abstract class like AbsViewHolder extend the BaseViewHolder in your project
 * and implement this interface. And I will post a blog to guide how to create a custom plugin of as-IDE
 * for fast create VO and VH.
 * <p>
 * The AbsViewHolder may like following:
 * <pre>
 * public abstract class AbsViewHolder&lt;T&gt; extends BaseViewHolder implements IViewHolder&lt;T&gt; {
 *      //the constructor lies on your BaseViewHolder
 *      public AbsViewHolder(View itemView) {
 *          super(itemView);
 *      }
 *      <code>@Override</code>
 *      public final RecyclerView.ViewHolder asViewHolder() {
 *      return this;
 *      }
 *      //@Override
 *      //public void setData(T data) {
 *      //  let it abstract
 *      //}
 *      <code>@Override</code>
 *      public void onViewAttachedToWindow(){
 *
 *      }
 *      <code>@Override</code>
 *      public void onViewDetachedFromWindow(){
 *
 *      }
 * }
 * </pre>
 * <p>
 * Created by leobert on 2018/10/10.
 */

interface IViewHolder<T> {

    fun asViewHolder(): RecyclerView.ViewHolder

    /**
     * invoked in RecyclerView.Adapter#onBindViewHolder(RecyclerView.ViewHolder, int)
     *
     * @param data data set to this ViewHolder
     */
    fun setData(data: T)

    fun onViewAttachedToWindow()

    fun onViewDetachedFromWindow()

    fun accept(visitor: Visitor<T>)

    abstract class Visitor<T> {
        //now only have this sub functional type
        open fun visit(holder: IReactiveViewHolder<T>) {}
    }
}