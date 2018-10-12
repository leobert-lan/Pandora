package osp.leobert.android.pandorasample;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import osp.leobert.android.pandora.rv.AbsViewHolder;
import osp.leobert.android.pandora.rv.DataObserver;
import osp.leobert.android.pandora.rv.DataSet;

/**
 * <p><b>Package:</b> osp.leobert.android.pandorasample </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> RvAdapter </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2018/10/11.
 */
public class RvAdapter<D extends DataSet> extends RecyclerView.Adapter<AbsViewHolder>
        implements DataObserver {

    private final D dataSet;

    protected D dataSet() {
        return dataSet;
    }

    public RvAdapter(D dataSet) {
        this.dataSet = dataSet;
        dataSet.addDataObserver(this);
    }

    @Override
    public AbsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return dataSet.createViewHolderV2(parent, viewType);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(AbsViewHolder holder, int position) {
        Log.i("Pandora", "onBindViewHolder: " + position);
        try {
            dataSet.getItem(position).setToViewHolder(holder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return dataSet.getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return dataSet.getItemViewTypeV2(position);
    }

    @Override
    public void onViewAttachedToWindow(AbsViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.onViewAttachedToWindow();
    }


    @Override
    public void onViewDetachedFromWindow(AbsViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.onViewDetachedFromWindow();
    }

    @Override
    public void onDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
