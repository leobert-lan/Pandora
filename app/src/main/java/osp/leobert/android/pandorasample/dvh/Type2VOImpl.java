package osp.leobert.android.pandorasample.dvh;

import osp.leobert.android.pandora.rv.AbsViewHolder;
import osp.leobert.android.pandora.rv.DataSet;

/**
 * <p><b>Package:</b> osp.leobert.android.pandorasample.dvh </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> Type2VOImpl </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2018/10/11.
 */
public class Type2VOImpl implements Type2VO {
    private final int i;

    public Type2VOImpl(int i) {
        this.i = i;
    }

    @Override
    public String getText() {
        return toString();
    }

    @Override
    public void setToViewHolder(AbsViewHolder<DataSet.Data> viewHolder) {
        viewHolder.setData(this);
    }

    @Override
    public String toString() {
        return "Type2VOImpl{" +
                "i=" + i +
                '}';
    }
}
