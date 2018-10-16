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
import android.util.SparseArray;
import android.view.ViewGroup;


/**
 * <p><b>Package:</b> osp.leobert.android.pandorarv </p>
 * <p><b>Project:</b> Pandorarv </p>
 * <p><b>Classname:</b> DateVhMappingPool </p>
 * <p><b>Description:</b> a pool to restore and fetch the relationship between VO and VH </p>
 * Created by leobert on 2018/10/10.
 */
public class DateVhMappingPool {
    private SparseArray<TypeCell> viewTypeCache = new SparseArray<>();
    private int maxSize = 5;

    public synchronized void registerDVRelation(@NonNull Class<?> dataClz, @NonNull ViewHolderCreator viewHolderCreator) {
        this.registerDVRelation(new DataVhRelation<>(dataClz, viewHolderCreator));
    }

    public synchronized void registerDvRelation(DVRelation... dvRelations) {
        for (DVRelation dvRelation : dvRelations)
            registerDVRelation(dvRelation);
    }

    public synchronized void registerDVRelation(DVRelation<?> dvRelation) {
        if (dvRelation == null)
            return;
        synchronized (DataSet.class) {
            int n = dvRelation.one2N();

            while (n > maxSize) {
                maxSize *= 2;
                for (int i = 0; i < viewTypeCache.size(); i++) {
                    viewTypeCache.get(i).updateMaxSize(maxSize);
                }
            }

            int index = viewTypeCache.size();
            TypeCell typeCell = new TypeCell<>(index, dvRelation);
            typeCell.updateMaxSize(maxSize);
            viewTypeCache.put(index, typeCell);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> int getItemViewTypeV2(String key,T data) { //getItemViewType
        for (int i = 0; i < viewTypeCache.size(); i++) {//折半查找可能效率更高一点
            if (viewTypeCache.get(i).workFor(key)) {
                int viewType = viewTypeCache.get(i).getItemViewType(data);
                return viewType;
            }
        }
        throw new RuntimeException("have you register for:" + key);
    }

    public int getViewTypeCount() {//getViewTypeCount
        int ret = 0;
        for (int i = 0; i < viewTypeCache.size(); i++) {
            ret += viewTypeCache.get(i).getSubTypeCount();
        }
        return ret;
    }

    public IViewHolder createViewHolderV2(ViewGroup parent, int viewType) { // createViewHolder(ViewGroup parent, int viewType)
        int index = viewType / maxSize;
        int subIndex = viewType % maxSize;
        return viewTypeCache.get(index).getVhCreator(subIndex).createViewHolder(parent);
    }


    public interface DVRelation<T> {
        String SINGLE_TYPE_TOKEN = "type_one";

        Class<T> getDataClz();

        int one2N();

        String subTypeToken(@NonNull T data);

        ViewHolderCreator getVhCreator(@NonNull String subTypeToken);
    }

    private static class DataVhRelation<T> implements DVRelation<T> {
        private Class<T> dataClz;
        private ViewHolderCreator vhCreator;

        DataVhRelation(@NonNull Class<T> dataClz, ViewHolderCreator vhCreator) {
            this.dataClz = dataClz;
            this.vhCreator = vhCreator;
        }

        @Override
        public final Class<T> getDataClz() {
            return dataClz;
        }

        @Override
        public final int one2N() {
            return 1;
        }

        @Override
        public final String subTypeToken(@NonNull T data) {
            return SINGLE_TYPE_TOKEN;
        }

        @Override
        public final ViewHolderCreator getVhCreator(@NonNull String subTypeToken) {
            return vhCreator;
        }
    }
}
