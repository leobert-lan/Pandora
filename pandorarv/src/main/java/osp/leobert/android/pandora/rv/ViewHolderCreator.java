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

import androidx.annotation.LayoutRes;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.InvocationTargetException;

/**
 * <p><b>Package:</b> osp.leobert.android.pandorarv </p>
 * <p><b>Project:</b> Pandorarv </p>
 * <p><b>Classname:</b> ViewHolderCreator </p>
 * <p><b>Description:</b> creator to create view holders</p>
 * Created by leobert on 2018/10/10.
 */
public abstract class ViewHolderCreator {

    public abstract IViewHolder createViewHolder(ViewGroup parent);

    /**
     * just lazy. But in many real cases, we don't need it.
     * @param <T> ViewHolder Type to be created,the 'View' itemView should be the first params in the constructor
     */
    @Deprecated
    public static class LazyCreator<T extends IViewHolder> extends ViewHolderCreator {
        @LayoutRes
        private final int layoutRes;

        private Class<T> target;

        private Pair<Class,Object>[] ext;

        /**
         * @param layoutRes layout xml res to be used to create the ViewHolder
         * @param target ViewHolder Type to be created
         */
        public LazyCreator(@LayoutRes int layoutRes, Class<T> target) {
            this(layoutRes, target, (Pair<Class, Object>[]) null);
        }

        /**
         * @param layoutRes layout xml res to be used to create the ViewHolder
         * @param target ViewHolder Type to be created
         * @param exts extended params used to create the Target ViewHolder. every one should in original order.
         */
        @SafeVarargs
        public LazyCreator(@LayoutRes int layoutRes, Class<T> target, Pair<Class,Object>... exts) {
            this.layoutRes = layoutRes;
            this.target = target;
            this.ext = exts;
        }


        @Override
        public IViewHolder createViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(layoutRes, parent, false);

            try {
                IViewHolder ret = null;
                Class[] pt;
                if (ext == null) {
                    pt = new Class[]{View.class};
                } else {
                    pt = new Class[1 + ext.length];
                    pt[0] = View.class;
                    for (int i = 0; i < ext.length; i++) {
                        pt[i + 1] = ext[i].first;
                    }
                }

                Object[] pa;
                if (ext == null) {
                    pa = new Object[]{view};
                } else {
                    pa = new Object[1 + ext.length];
                    pa[0] = view;
                    for (int i = 0; i < ext.length; i++) {
                        pa[i + 1] = ext[i].second;
                    }
                }

                ret = target.getConstructor(pt).newInstance(pa);
                return ret;

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            throw new RuntimeException("参数，参数类型，构造器对不上");
        }
    }
}
