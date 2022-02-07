/*
 * MIT License
 *
 * Copyright (c) 2018 leobert-lan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package osp.leobert.android.pandora.rv;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <p><b>Package:</b> osp.leobert.android.pandorarv </p>
 * <p><b>Project:</b> Pandorarv </p>
 * <p><b>Classname:</b> AbsViewHolder </p>
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

public interface IViewHolder<DATA> {

    RecyclerView.ViewHolder asViewHolder();

    /**
     * invoked in RecyclerView.Adapter#onBindViewHolder(RecyclerView.ViewHolder, int)
     *
     * @param data data set to this ViewHolder
     */
    void setData(DATA data);

    void onViewAttachedToWindow();

    void onViewDetachedFromWindow();

    void accept(@NonNull Visitor visitor);

    abstract class Visitor {
        //now only have this sub functional type
        public void visit(IReactiveViewHolder holder) {

        }
    }
}
