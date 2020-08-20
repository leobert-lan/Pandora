package osp.leobert.android.pandorasample.menu;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import osp.leobert.android.pandora.rv.ViewHolderCreator;
import osp.leobert.android.pandorasample.R;
import osp.leobert.android.pandorasample.databinding.AppVhMenuBinding;
import osp.leobert.android.pandorasample.dvh.AbsViewHolder;
import osp.leobert.android.pandorasample.dvh.DataBindingViewHolder;


/**
 * <p><b>Package:</b> osp.leobert.android.pandorasample.menu </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> Menu </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2019/2/20.
 */
public class MenuVH2 extends DataBindingViewHolder<MenuVO2,AppVhMenuBinding> {
    private final ItemInteract mItemInteract;

    private MenuVO2 mData;

    public MenuVH2(AppVhMenuBinding viewDataBinding, ItemInteract mItemInteract) {
        super(viewDataBinding);
        this.mItemInteract = mItemInteract;
    }

    @Override
    public void setData(MenuVO2 data) {
        mData = data;
        viewDataBinding.setVh(this);
        viewDataBinding.setVo(data);
        viewDataBinding.executePendingBindings();
    }

    public static final class Creator extends ViewHolderCreator {
        private final
        ItemInteract itemInteract;

        public Creator(ItemInteract itemInteract) {
            this.itemInteract = itemInteract;
        }

        @Override
        public AbsViewHolder<MenuVO2> createViewHolder(ViewGroup parent) {

            AppVhMenuBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.app_vh_menu, parent, false);
            binding.setItemInteract(itemInteract);
            return new MenuVH2(binding, itemInteract);
        }
    }

    public interface ItemInteract {
        void onMenuItemClicked(int pos, MenuVO2 data);
    }


}