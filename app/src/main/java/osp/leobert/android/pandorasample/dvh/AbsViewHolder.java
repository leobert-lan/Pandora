package osp.leobert.android.pandorasample.dvh;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import osp.leobert.android.pandora.rv.IViewHolder;

/**
 * <p><b>Package:</b> osp.leobert.android.pandorasample.dvh </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> AbsViewHolder </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2018/10/16.
 */
public abstract class AbsViewHolder<T> extends RecyclerView.ViewHolder implements IViewHolder<T> {
    public AbsViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public RecyclerView.ViewHolder asViewHolder() {
        return this;
    }

    protected Context getContext() {
        return itemView.getContext();
    }

    @Override
    public void onViewAttachedToWindow() {

    }

    @Override
    public void onViewDetachedFromWindow() {

    }
}
