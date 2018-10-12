package osp.leobert.android.pandorasample.dvh;

import osp.leobert.android.pandora.rv.AbsViewHolder;
import osp.leobert.android.pandora.rv.DataSet;

/**
 * <p><b>Package:</b> osp.leobert.android.pandorasample.dvh </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> Type1VOImpl </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2018/10/11.
 */
public class Type1VOImpl implements Type1VO {
    private final String s;

    public Type1VOImpl(String s) {
        this.s = s;
    }

    @Override
    public String getData() {
        return toString();
    }

    @Override
    public void setToViewHolder(AbsViewHolder<DataSet.Data> viewHolder) {
        viewHolder.setData(this);
    }

    @Override
    public String toString() {
        return "Type1VOImpl{" +
                "s='" + s + '\'' +
                '}';
    }
}
