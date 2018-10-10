package osp.leobert.android.pandorasample.nds;

import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.ViewGroup;

import osp.leobert.android.pandorasample.AbsViewHolder;
import osp.leobert.android.pandorasample.DataSet;
import osp.leobert.android.pandorasample.TypeCell;
import osp.leobert.android.pandorasample.ViewHolderCreator;

/**
 * <p><b>Package:</b> osp.leobert.android.pandorasample.nds </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> DateVhMappingPool </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2018/10/10.
 */
public class DateVhMappingPool {
    private SparseArray<TypeCell> viewTypeCache = new SparseArray<>();
    private int maxSize = 5;

    public synchronized void registerDVRelation(@NonNull Class<?> dataClz, @NonNull ViewHolderCreator viewHolderCreator) {
        this.registerDVRelation(new DateVhMappingPool.DataVhRelation<>(dataClz, viewHolderCreator));
    }

    public synchronized void registerDvRelation(DateVhMappingPool.DVRelation... dvRelations) {
        for (DateVhMappingPool.DVRelation dvRelation : dvRelations)
            registerDVRelation(dvRelation);
    }

    public synchronized void registerDVRelation(DateVhMappingPool.DVRelation<?> dvRelation) {
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

    public AbsViewHolder createViewHolderV2(ViewGroup parent, int viewType) { // createViewHolder(ViewGroup parent, int viewType)
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

    public static class DataVhRelation<T> implements DVRelation<T> {
        private Class<T> dataClz;
        private ViewHolderCreator vhCreator;

        protected DataVhRelation(@NonNull Class<T> dataClz, ViewHolderCreator vhCreator) {
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
