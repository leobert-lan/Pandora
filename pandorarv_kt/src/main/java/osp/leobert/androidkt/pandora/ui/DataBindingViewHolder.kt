package osp.leobert.androidkt.pandora.ui

import androidx.databinding.ViewDataBinding

/**
 * <p><b>Package:</b> com.sceneray.manager.base.ui.rv </p>
 * <p><b>Project:</b> Manager </p>
 * <p><b>Classname:</b> DataBindingViewHolder </p>
 * Created by leobert on 2021/6/4.
 */
abstract class DataBindingViewHolder<T, VDB : ViewDataBinding>(
    val viewDataBinding: VDB
) : AbsViewHolder<T>(viewDataBinding.root) {

    override fun setData(data: T) {
    }
}