package osp.leobert.android.pandorasample;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import osp.leobert.android.pandorasample.nds.DataObserver;
import osp.leobert.android.pandorasample.nds.DateVhMappingPool;

/**
 * <p><b>Package:</b> osp.leobert.android.pandorasample </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> DataSet </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2018/10/10.
 */
public abstract class DataSet {
    public interface Data<D extends Data, V extends AbsViewHolder<D>> {
        void setToViewHolder(V viewHolder);
    }

    private final DateVhMappingPool dateVhMappingPool = new DateVhMappingPool();

    public abstract int getCount();

    public abstract DataSet.Data getItem(int position);

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
        DataSet.Data data = getItem(pos);

        return dateVhMappingPool.getItemViewTypeV2(key,data);
    }

    public AbsViewHolder createViewHolderV2(ViewGroup parent, int viewType) {
        return dateVhMappingPool.createViewHolderV2(parent,viewType);
    }

    protected int getViewTypeCount() {
        return dateVhMappingPool.getViewTypeCount();
    }

    public synchronized void registerDVRelation(@NonNull Class<?> dataClz, @NonNull ViewHolderCreator viewHolderCreator) {
        dateVhMappingPool.registerDVRelation(dataClz,viewHolderCreator);
    }

    public synchronized void registerDvRelation(DateVhMappingPool.DVRelation... dvRelations) {
        dateVhMappingPool.registerDvRelation(dvRelations);
    }

    public synchronized void registerDVRelation(DateVhMappingPool.DVRelation<?> dvRelation) {
        dateVhMappingPool.registerDvRelation(dvRelation);
    }

}
