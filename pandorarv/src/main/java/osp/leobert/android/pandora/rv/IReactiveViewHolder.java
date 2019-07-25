package osp.leobert.android.pandora.rv;

import androidx.databinding.Observable;

import osp.leobert.android.pandora.Logger;

/**
 * <p><b>Package:</b> osp.leobert.android.pandora.rv </p>
 * <p><b>Classname:</b> IReactiveViewHolder </p>
 * Created by leobert on 2019-07-16.
 */
public interface IReactiveViewHolder<T> extends IViewHolder<T> {
    Visitor MAKE_SURE_UNBIND_VISITOR = new Visitor() {
        @Override
        public void visit(IReactiveViewHolder holder) {
            super.visit(holder);
            ReactiveData oldBinding = holder.getReactiveDataIfExist();
            if (oldBinding != null)
                oldBinding.unbindReactiveVh();
        }
    };

    @SuppressWarnings("unchecked")
    Visitor MAKE_SURE_BIND_VISITOR = new Visitor() {
        @Override
        public void visit(IReactiveViewHolder holder) {
            super.visit(holder);
            ReactiveData reactiveData = holder.getReactiveDataIfExist();
            if (reactiveData != null)
                try {
                    reactiveData.bindReactiveVh(holder);
                } catch (Exception e) {
                    Logger.e(Logger.TAG, "exception when binding reactive data!", e);
                }
        }
    };

    ReactiveData getReactiveDataIfExist();

    void onPropertyChanged(Observable sender, T data, int propertyId);

}
