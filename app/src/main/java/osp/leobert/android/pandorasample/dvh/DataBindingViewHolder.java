package osp.leobert.android.pandorasample.dvh;

import android.view.View;

import androidx.annotation.CallSuper;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <p><b>Package:</b> com.example.myapplication </p>
 * <p><b>Project:</b> MyApplication </p>
 * <p><b>Classname:</b> AbsViewHolder2 </p>
 * <p>
 * Created by leobert on 2019-06-03.
 */
public abstract class DataBindingViewHolder<T, VDB extends ViewDataBinding> extends AbsViewHolder<T> {
    public DataBindingViewHolder(VDB viewDataBinding) {
        this(viewDataBinding, viewDataBinding.getRoot());
    }

    /**
     * 一般是被特定布局所包裹的情况，比如侧滑菜单
     */
    public DataBindingViewHolder(VDB viewDataBinding, View rootView) {
        super(rootView);
        this.viewDataBinding = viewDataBinding;
    }

    protected final VDB viewDataBinding;

    @Override
    public RecyclerView.ViewHolder asViewHolder() {
        return this;
    }

    @Override
    public void setData(T data) {
    }

    @Override
    public void onViewAttachedToWindow() {

    }

    @Override
    @CallSuper
    public void onViewDetachedFromWindow() {
        super.onViewDetachedFromWindow();
    }
}
