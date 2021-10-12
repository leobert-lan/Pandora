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

import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import osp.leobert.android.pandora.Logger;
import osp.leobert.android.pandora.PandoraException;

/**
 * <p><b>Package:</b> osp.leobert.android.pandorarv </p>
 * <p><b>Project:</b> Pandorarv </p>
 * <p><b>Classname:</b> DataSet </p>
 * <p><b>Description:</b> a data set supply for android.support.v7.widget.RecyclerView.Adapter
 * supporting 'Multi-Type'</p>
 * Created by leobert on 2018/10/10.
 */
public abstract class DataSet {

    public static <DATA, VH extends IViewHolder<? super DATA>>
    void helpSetToViewHolder(D<DATA, VH> data, VH viewHolder) {
        //make sure it will dispose the binding to old reactive data
        //even though someone will write the logic in osp.leobert.android.pandora.rv.DataSet.D.setToViewHolder
        viewHolder.accept(IReactiveViewHolder.MAKE_SURE_UNBIND_VISITOR);

        data.setToViewHolder(viewHolder);

        //make sure the vh binds to new reactive data
        //even though someone has written the logic in osp.leobert.android.pandora.rv.DataSet.D.setToViewHolder
        viewHolder.accept(IReactiveViewHolder.MAKE_SURE_BIND_VISITOR);

    }

    /**
     * @param <DATA> if this VO (View Object) is only use in 'single-type', you can declare the VO type. Otherwise,
     *               just declare as Data
     * @param <VH>   the VH type, de-generic with the VO thus you can access the API in VO
     */
    public interface D<DATA, VH extends IViewHolder<? super DATA>> {
        /**
         * invoke this in adapter,  android.support.v7.widget.RecyclerView.Adapter#onBindViewHolder(RecyclerView.ViewHolder, int)
         * <em>it's important to do type check</em>
         * remember call {@linkplain IViewHolder#setData(Object)} to set data to viewHolder.
         *
         * @param viewHolder view holder to be bind this this data
         */
        void setToViewHolder(VH viewHolder);
    }

//    /**
//     * Because we don't supply a Super Class of data, and we used sth like ? extends D {@linkplain D}
//     * in the library, thus we must have something used when de-generic in the "Multi-Type" case
//     * use in the generic
//     *
//     * @param <DA> if this VO (View Object) is only use in 'single-type', you can declare the VO type. Otherwise,
//     *             just declare as Data
//     * @param <V>  the VH type, de-generic with the VO thus you can access the API in VO
//     *
//     * @deprecated use {@link Data2} instead
//     */
//    @Deprecated
//    public interface Data<DA extends D, V extends IViewHolder<? super DA>> extends D<DA, V> {
//
//    }

    /**
     * Because we don't supply a Super Class of data, and we used sth like ? extends D {@linkplain D}
     * in the library, thus we must have something used when de-generic in the "Multi-Type" case
     * use in the generic
     */
    public interface Data extends D<Data, IViewHolder<Data>> {

    }

    protected final DataVhMappingPool dataVhMappingPool = new DataVhMappingPool();

    public DataVhMappingPool getDataVhMappingPool() {
        return dataVhMappingPool;
    }

    /**
     * @return the count of data in the data set
     */
    public abstract int getCount();

    /**
     * @param position target position to fetch data
     * @return data
     */
    public abstract D getItem(int position);

    private final List<WeakReference<DataObserver>> observersRef = new ArrayList<>();

    public void addDataObserver(DataObserver dataObserver) {
        observersRef.add(new WeakReference<>(dataObserver));
    }

    public int getItemViewTypeV2(int pos) throws PandoraException { //getItemViewType
        String key = getItem(pos).getClass().getName();
        D data = getItem(pos);
        try {
            return dataVhMappingPool.getItemViewTypeV2(key, data);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PandoraException(e);
        }
    }

    public RecyclerView.ViewHolder createViewHolderV2(ViewGroup parent, int viewType) throws PandoraException {
        try {
            return dataVhMappingPool.createViewHolderV2(parent, viewType).asViewHolder();
        } catch (Exception e) {
            e.printStackTrace();
            throw new PandoraException(e);
        }
    }

    protected int getViewTypeCount() {
        return dataVhMappingPool.getViewTypeCount();
    }

    public synchronized void registerDVRelation(@NonNull Class<?> dataClz, @NonNull ViewHolderCreator viewHolderCreator) {
        dataVhMappingPool.registerDVRelation(dataClz, viewHolderCreator);
    }

    public synchronized void registerDvRelation(DataVhMappingPool.DVRelation... dvRelations) {
        dataVhMappingPool.registerDvRelation(dvRelations);
    }

    public synchronized void registerDVRelation(DataVhMappingPool.DVRelation<?> dvRelation) {
        dataVhMappingPool.registerDvRelation(dvRelation);
    }

    public synchronized void removeDVRelation(@NonNull Class<?> dataClz) {
        dataVhMappingPool.removeDVRelation(dataClz);
    }

    ///////////////////////////////////////////////////////////////////////////
    // notify change
    ///////////////////////////////////////////////////////////////////////////

    public void notifyChanged() {
        for (int i = 0; i < observersRef.size(); i++) {
            WeakReference<DataObserver> reference = observersRef.get(i);
            if (reference == null) {
                observersRef.remove(i);
                i--;
                continue;
            }
            if (reference.get() == null) {
                observersRef.remove(i);
                i--;
                continue;
            }
            reference.get().onDataSetChanged();
        }
    }

    public void notifyItemChanged(int position) {
        for (int i = 0; i < observersRef.size(); i++) {
            WeakReference<DataObserver> reference = observersRef.get(i);
            if (reference == null) {
                observersRef.remove(i);
                i--;
                continue;
            }
            if (reference.get() == null) {
                observersRef.remove(i);
                i--;
                continue;
            }
            reference.get().notifyItemChanged(position);
        }
    }

    public void notifyItemChanged(int position, Object payload) {
        for (int i = 0; i < observersRef.size(); i++) {
            WeakReference<DataObserver> reference = observersRef.get(i);
            if (reference == null) {
                observersRef.remove(i);
                i--;
                continue;
            }
            if (reference.get() == null) {
                observersRef.remove(i);
                i--;
                continue;
            }
            reference.get().notifyItemChanged(position, payload);
        }
    }

    public void notifyItemRangeChanged(int positionStart, int itemCount) {
        for (int i = 0; i < observersRef.size(); i++) {
            WeakReference<DataObserver> reference = observersRef.get(i);
            if (reference == null) {
                observersRef.remove(i);
                i--;
                continue;
            }
            if (reference.get() == null) {
                observersRef.remove(i);
                i--;
                continue;
            }
            reference.get().notifyItemRangeChanged(positionStart, itemCount);
        }
    }

    public void notifyItemRangeChanged(int positionStart, int itemCount, Object payload) {
        for (int i = 0; i < observersRef.size(); i++) {
            WeakReference<DataObserver> reference = observersRef.get(i);
            if (reference == null) {
                observersRef.remove(i);
                i--;
                continue;
            }
            if (reference.get() == null) {
                observersRef.remove(i);
                i--;
                continue;
            }
            reference.get().notifyItemRangeChanged(positionStart, itemCount, payload);
        }
    }

    public void notifyItemInserted(int position) {
        for (int i = 0; i < observersRef.size(); i++) {
            WeakReference<DataObserver> reference = observersRef.get(i);
            if (reference == null) {
                observersRef.remove(i);
                i--;
                continue;
            }
            if (reference.get() == null) {
                observersRef.remove(i);
                i--;
                continue;
            }
            reference.get().notifyItemInserted(position);
        }
    }

    public void notifyItemMoved(int fromPosition, int toPosition) {
        for (int i = 0; i < observersRef.size(); i++) {
            WeakReference<DataObserver> reference = observersRef.get(i);
            if (reference == null) {
                observersRef.remove(i);
                i--;
                continue;
            }
            if (reference.get() == null) {
                observersRef.remove(i);
                i--;
                continue;
            }
            reference.get().notifyItemMoved(fromPosition, toPosition);
        }
    }

    public void notifyItemRangeInserted(int positionStart, int itemCount) {
        for (int i = 0; i < observersRef.size(); i++) {
            WeakReference<DataObserver> reference = observersRef.get(i);
            if (reference == null) {
                observersRef.remove(i);
                i--;
                continue;
            }
            if (reference.get() == null) {
                observersRef.remove(i);
                i--;
                continue;
            }
            reference.get().notifyItemRangeInserted(positionStart, itemCount);
        }
    }

    public void notifyItemRemoved(int position) {
        for (int i = 0; i < observersRef.size(); i++) {
            WeakReference<DataObserver> reference = observersRef.get(i);
            if (reference == null) {
                observersRef.remove(i);
                i--;
                continue;
            }
            if (reference.get() == null) {
                observersRef.remove(i);
                i--;
                continue;
            }
            reference.get().notifyItemRemoved(position);
        }
    }

    public void notifyItemRangeRemoved(int positionStart, int itemCount) {
        for (int i = 0; i < observersRef.size(); i++) {
            WeakReference<DataObserver> reference = observersRef.get(i);
            if (reference == null) {
                observersRef.remove(i);
                i--;
                continue;
            }
            if (reference.get() == null) {
                observersRef.remove(i);
                i--;
                continue;
            }
            reference.get().notifyItemRangeRemoved(positionStart, itemCount);
        }
    }

    public void logDVMappingInfo() {
        if (Logger.DEBUG) {
            try {
                Logger.i("logDVMappingInfo:\r\n" + dataVhMappingPool.toString());
            } catch (Exception ignore) {

            }
        }
    }
}
