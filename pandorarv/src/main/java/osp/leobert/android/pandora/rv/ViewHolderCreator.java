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

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.InvocationTargetException;

/**
 * <p><b>Package:</b> osp.leobert.android.pandorarv </p>
 * <p><b>Project:</b> Pandorarv </p>
 * <p><b>Classname:</b> ViewHolderCreator </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2018/10/10.
 */
public abstract class ViewHolderCreator {

    public abstract AbsViewHolder createViewHolder(ViewGroup parent);

    public static class LazyCreator<T extends AbsViewHolder> extends ViewHolderCreator {

        //        private final int viewType;
        @LayoutRes
        private final int layoutRes;

        private Class<T> target;
        private Object[] targetParams;
        private Class[] paramsType;

        public LazyCreator(int layoutRes, Class<T> target) {
            this(layoutRes,target,null);
        }

        public LazyCreator(/*int viewType,*/ int layoutRes, Class<T> clz,
                                             Class[] paramType/*itemview的不需要*/,
                                             Object... targetParams/*itemview不需要*/) {
//            this.viewType = viewType;
            this.layoutRes = layoutRes;
            this.target = clz;
            this.paramsType = paramType;
            this.targetParams = targetParams;
        }

        @Override
        public AbsViewHolder createViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(layoutRes, null, false);

            try {
                AbsViewHolder ret = null;
                Class[] pt;
                if (paramsType == null) {
                    pt = new Class[]{View.class};
                } else {
                    pt = new Class[1 + paramsType.length];
                    pt[0] = View.class;
                    for (int i = 0; i < paramsType.length; i++) {
                        pt[i + 1] = paramsType[i];
                    }
                }

                Object[] pa;
                if (targetParams == null) {
                    pa = new Object[]{view};
                } else {
                    pa = new Object[1 + targetParams.length];
                    pa[0] = view;
                    for (int i = 0; i < targetParams.length; i++) {
                        pa[i + 1] = targetParams[i];
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

//            return null;
        }
    }
}
