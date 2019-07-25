package osp.leobert.android.pandorasample.menu;

import androidx.annotation.IntDef;

import osp.leobert.android.pandora.rv.DataSet;
import osp.leobert.android.pandorasample.dvh.AbsViewHolder;

/**
 * <p><b>Package:</b> osp.leobert.android.pandorasample.menu </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> Menu </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2019/2/20.
 */
public interface MenuVO2 extends DataSet.Data<DataSet.Data, AbsViewHolder<DataSet.Data>> {

    @IntDef({Level.l1, Level.l2})
    @interface Level {
        int l1 = 1;
        int l2 = 2;
    }

    @Level
    int level();

    String getName();
}