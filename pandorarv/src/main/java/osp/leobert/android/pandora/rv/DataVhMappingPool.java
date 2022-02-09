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

import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import osp.leobert.android.pandora.Logger;


/**
 * <p><b>Package:</b> osp.leobert.android.pandorarv </p>
 * <p><b>Project:</b> Pandorarv </p>
 * <p><b>Classname:</b> DataVhMappingPool </p>
 * <p><b>Description:</b> a pool to restore and fetch the relationship between VO and VH </p>
 * Created by leobert on 2018/10/10.
 */
//@SuppressWarnings({"unchecked", "cast", "rawtype", "unused"})
@SuppressWarnings("unused")
public class DataVhMappingPool {
    private final SparseArray<TypeCell> viewTypeCache = new SparseArray<>();
    private int maxSize = 5;

    @Nullable
    private TypeCell<?> internalErrorTypeCell;

    private int typeCellKey = 0;

    public synchronized void removeDVRelation(@NonNull Class<?> dataClz) {
        synchronized (viewTypeCache) {
            for (int i = 0; i < viewTypeCache.size(); i++) {
                try {
                    TypeCell<?> typeCell = viewTypeCache.valueAt(i);
                    if (typeCell.workFor(dataClz.getName())) {
                        int key = viewTypeCache.keyAt(i);
                        viewTypeCache.remove(key);
                        i--;

                        if (Logger.DEBUG)
                            Logger.i(Logger.TAG, "want to remove:" + dataClz.getName() + "; executed typeCell:" + typeCell);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.e(Logger.TAG, "DateVhMappingPool removeDVRelation error", e);
                }
            }
        }
    }

    public synchronized void registerDVRelation(@NonNull Class<?> dataClz, @NonNull ViewHolderCreator viewHolderCreator) {
        this.registerDVRelation(new DataVhRelation<>(dataClz, viewHolderCreator));
    }

    @Deprecated
    public synchronized void registerDvRelation(DVRelation... dvRelations) {
        for (DVRelation dvRelation : dvRelations)
            registerDVRelation(dvRelation);
    }

    public synchronized void registerDVRelation(DVRelation<?> dvRelation) {
        if (dvRelation == null)
            return;
        synchronized (viewTypeCache) {
            int n = dvRelation.one2N();

            while (n > maxSize) {
                maxSize *= 2;
                for (int i = 0; i < viewTypeCache.size(); i++) {
                    viewTypeCache.valueAt(i).updateMaxSize(maxSize);
                }
            }

            final int index = typeCellKey;
            TypeCell<?> typeCell = new TypeCell<>(index, dvRelation);
            typeCell.updateMaxSize(maxSize);
            if (Logger.DEBUG) {
                Logger.i(Logger.TAG, "registerDVRelation: cacheKey" + index + "hasKey?" + (viewTypeCache.get(index) != null) + " ; typeCell:" + typeCell);
            }
            viewTypeCache.put(index, typeCell);
            typeCellKey++;
        }
    }

    private synchronized void registerDVRelation(TypeCell<?> typeCell) {
        if (typeCell == null)
            return;
        synchronized (viewTypeCache) {
            final int index = typeCellKey;
            TypeCell<?> copy = TypeCell.of(index, typeCell);

            int n = copy.getSubTypeCount();

            while (n > maxSize) {
                maxSize *= 2;
                for (int i = 0; i < viewTypeCache.size(); i++) {
                    viewTypeCache.valueAt(i).updateMaxSize(maxSize);
                }
            }

            copy.updateMaxSize(maxSize);
            if (Logger.DEBUG) {
                Logger.i(Logger.TAG, "registerDVRelation: cacheKey" + index + "hasKey?" + (viewTypeCache.get(index) != null) + " ; typeCell:" + copy);
            }
            viewTypeCache.put(index, copy);
            typeCellKey++;
        }
    }

    public void whenInternalError(@NonNull ViewHolderCreator viewHolderCreator) {
        this.internalErrorTypeCell = new TypeCell<>(Integer.MAX_VALUE, new DataVhRelation<>(DataSet.Data.class, viewHolderCreator));
    }

    @SuppressWarnings("unchecked")
    public <T> int getItemViewTypeV2(String key, T data) { //getItemViewType
        for (int i = 0; i < viewTypeCache.size(); i++) {//折半查找可能效率更高一点
            TypeCell<T> typeCell = viewTypeCache.valueAt(i);
            if (typeCell == null) continue;
            if (typeCell.workFor(key)) {
                return typeCell.getItemViewType(data);
            }
        }
        if (Logger.DEBUG) {
            RuntimeException e = new RuntimeException("have you register for:" + key);
            Logger.e(Logger.TAG, "missing type register", e);
            Logger.d(Logger.TAG, "debug typeCells:" + viewTypeCache);
            throw e;
        } else {
            return Integer.MAX_VALUE;
        }
    }

    public int getViewTypeCount() {//getViewTypeCount
        int ret = 0;
        if (internalErrorTypeCell != null)
            ret += 1;
        for (int i = 0; i < viewTypeCache.size(); i++) {
            try {
                ret += viewTypeCache.valueAt(i).getSubTypeCount();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public IViewHolder createViewHolderV2(ViewGroup parent, int viewType) { // createViewHolder(ViewGroup parent, int viewType)
        try {
            int index = viewType / maxSize;
            int subIndex = viewType % maxSize;

            Logger.v(Logger.TAG, "createViewHolderV2:index" + index + " ,subIndex:" + subIndex + "  viewtype:" + viewType);
            //valueAt不合适，index是key  return viewTypeCache.valueAt(index).getVhCreator(subIndex).createViewHolder(parent);
            return viewTypeCache.get(index).getVhCreator(subIndex).createViewHolder(parent);
        } catch (Exception e) {
            if (Logger.DEBUG) {
                if (internalErrorTypeCell != null)
                    return internalErrorTypeCell.getVhCreator(0).createViewHolder(parent);
                Logger.e(Logger.TAG, "missing viewType:" + viewType + " ?", e);
                throw e;
            } else {
                if (internalErrorTypeCell != null)
                    return internalErrorTypeCell.getVhCreator(0).createViewHolder(parent);
                return null;
            }
        }
    }

    public final void merge(DataVhMappingPool pool) {
        for (int i = 0; i < pool.typeCellKey; i++) {
            registerDVRelation(pool.viewTypeCache.get(i));
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "DataVhMappingPool{" +
                "\r\n    maxSize=" + maxSize +
                ",\r\n    internalErrorTypeCell=" + internalErrorTypeCell +
                ",\r\n    typeCellKey=" + typeCellKey +
                ",\r\n    viewTypeCache=" + sparseArrayToString(viewTypeCache) +
                '}';
    }

    public interface DVRelation<T> {
        String SINGLE_TYPE_TOKEN = "type_one";

        Class<T> getDataClz();

        int one2N();

        String subTypeToken(@NonNull T data);

        ViewHolderCreator getVhCreator(@NonNull String subTypeToken);
    }

    private static class DataVhRelation<T> implements DVRelation<T> {
        private final Class<T> dataClz;
        private final ViewHolderCreator vhCreator;

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

        @NonNull
        @Override
        public String toString() {
            return "DataVhRelation{" +
                    "dataClz=" + dataClz +
                    ", vhCreator=" + vhCreator.getClass().getName() +
                    '}';
        }
    }

    static String dvRelationToString(DVRelation<?> dvRelation, int subTypeCount) {
        if (dvRelation instanceof DataVhMappingPool.DataVhRelation) {
            return "DataVhRelation{" +
                    "D=" + ((DataVhRelation<?>) dvRelation).dataClz.getName() +
                    ", vhCreator=" + ((DataVhRelation<?>) dvRelation).vhCreator.getClass().getName() +
                    '}';
        }
        return "{ D:" + dvRelation.getDataClz().toString() + "; sub_type_count:" + subTypeCount + "; dvRelation:" + dvRelation + "}";
    }


    private static String sparseArrayToString(SparseArray<?> target) {

        if (target == null)
            return "null";

        final int mSize = target.size();
        if (mSize <= 0) {
            return "{}";
        }


        StringBuilder buffer = new StringBuilder(mSize * 28);
        buffer.append("{\r\n    ");
        for (int i = 0; i < mSize; i++) {
            if (i > 0) {
                buffer.append(", \r\n    ");
            }
            int key = target.keyAt(i);
            buffer.append(key);
            buffer.append('=');
            Object value = target.valueAt(i);
            if (value != target) {
                buffer.append(value);
            } else {
                buffer.append("(this Map)");
            }
        }
        buffer.append("\r\n}");
        return buffer.toString();

    }
}

