package osp.leobert.android.pandora.rv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * <p><b>Package:</b> osp.leobert.android.pandorarv </p>
 * <p><b>Project:</b> Pandorarv </p>
 * <p><b>Classname:</b> AbsViewHolder </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2018/10/10.
 */
public  abstract class AbsViewHolder<T> extends RecyclerView.ViewHolder {
    public AbsViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void setData(T data);

    protected Context getContext() {
        return itemView.getContext();
    }

    public void onViewAttachedToWindow() {

    }

    public void onViewDetachedFromWindow() {

    }
}
