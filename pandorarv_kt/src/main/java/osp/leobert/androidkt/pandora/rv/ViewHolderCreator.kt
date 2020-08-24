package osp.leobert.androidkt.pandora.rv

import android.view.ViewGroup

/*
 * <p><b>Package:</b> osp.leobert.androidkt.pandora.rv </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> ViewHolderCreator </p>
 * <p><b>Description:</b> creator to create view holders </p>
 * Created by leobert on 2019/2/19.
 */
abstract class ViewHolderCreator {
    abstract fun createViewHolder(parent: ViewGroup): IViewHolder<*>
}