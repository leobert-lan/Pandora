package osp.leobert.android.pandora.rv;

/**
 * <p><b>Package:</b> osp.leobert.android.pandora.rv </p>
 * <p><b>Classname:</b> ReactiveData </p>
 * Created by leobert on 2019-07-16.
 */
public interface ReactiveData<DA extends DataSet.D, V extends IViewHolder<? super DA>> extends DataSet.Data<DA, V> {

    void bindReactiveVh(IReactiveViewHolder<DA> viewHolder);

    void unbindReactiveVh();
}
