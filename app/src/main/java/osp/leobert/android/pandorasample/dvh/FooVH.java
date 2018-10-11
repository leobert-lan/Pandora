package osp.leobert.android.pandorasample.dvh;

import android.view.View;
import android.widget.TextView;

import osp.leobert.android.pandorasample.AbsViewHolder;
import osp.leobert.android.pandorasample.R;

/**
 * <p><b>Package:</b> osp.leobert.android.pandorasample.dvh </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> FooVH </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2018/10/11.
 */
public abstract class FooVH<T> extends AbsViewHolder<T> {
    protected TextView tv;

    public FooVH(View itemView) {
        super(itemView);
        tv = itemView.findViewById(R.id.test_item_tv);
    }

    protected void setText(String text) {
        if (tv != null)
            tv.setText(text);
    }
}
