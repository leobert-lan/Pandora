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

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * <p><b>Package:</b> osp.leobert.android.pandorarv </p>
 * <p><b>Project:</b> Pandorarv </p>
 * <p><b>Classname:</b> DataSet </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2018/10/10.
 */
public abstract class DataSet {

    /**
     *
     * @param <DATA>
     * @param <VH>
     */
    public interface D<DATA, VH extends AbsViewHolder<? super DATA>> {
        void setToViewHolder(VH viewHolder);
    }

    public interface Data<DA extends D, V extends AbsViewHolder<? super DA>> extends D<DA, V> {

    }

    private final DateVhMappingPool dateVhMappingPool = new DateVhMappingPool();

    public abstract int getCount();

    public abstract D getItem(int position);

    private final List<WeakReference<DataObserver>> observersRef
            = new ArrayList<>();

    public void addDataObserver(DataObserver dataObserver) {
        observersRef.add(new WeakReference<>(dataObserver));
    }

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

    public int getItemViewTypeV2(int pos) { //getItemViewType
        String key = getItem(pos).getClass().getName();
        D data = getItem(pos);

        return dateVhMappingPool.getItemViewTypeV2(key, data);
    }

    public AbsViewHolder createViewHolderV2(ViewGroup parent, int viewType) {
        return dateVhMappingPool.createViewHolderV2(parent, viewType);
    }

    protected int getViewTypeCount() {
        return dateVhMappingPool.getViewTypeCount();
    }

    public synchronized void registerDVRelation(@NonNull Class<?> dataClz, @NonNull ViewHolderCreator viewHolderCreator) {
        dateVhMappingPool.registerDVRelation(dataClz, viewHolderCreator);
    }

    public synchronized void registerDvRelation(DateVhMappingPool.DVRelation... dvRelations) {
        dateVhMappingPool.registerDvRelation(dvRelations);
    }

    public synchronized void registerDVRelation(DateVhMappingPool.DVRelation<?> dvRelation) {
        dateVhMappingPool.registerDvRelation(dvRelation);
    }

}
