package osp.leobert.android.pandora.rv;

/**
 * <p><b>Package:</b> osp.leobert.android.pandora.rv </p>
 * <p><b>Classname:</b> ReactiveData </p>
 * Created by leobert on 2019-07-16.
 */
public interface ReactiveData<DA> extends DataSet.Data {

    void bindReactiveVh(IReactiveViewHolder<DA> viewHolder);

    void unbindReactiveVh();
}
