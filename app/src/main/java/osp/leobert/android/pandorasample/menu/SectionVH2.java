package osp.leobert.android.pandorasample.menu;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import osp.leobert.android.pandora.rv.ViewHolderCreator;
import osp.leobert.android.pandorasample.R;
import osp.leobert.android.pandorasample.databinding.AppVhSectionBinding;
import osp.leobert.android.pandorasample.dvh.AbsViewHolder;
import osp.leobert.android.pandorasample.dvh.DataBindingViewHolder;


/**
 * <p><b>Package:</b> osp.leobert.android.pandorasample.menu </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> Section </p>
 * Created by leobert on 2019/2/20.
 */
public class SectionVH2 extends DataBindingViewHolder<MenuVO2, AppVhSectionBinding> {
    private final ItemInteract mItemInteract;

    private MenuVO2 mData;

    public SectionVH2(AppVhSectionBinding binding, ItemInteract itemInteract) {
        super(binding);
        this.mItemInteract = itemInteract;
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
            AppVhSectionBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.app_vh_section, parent, false);
            binding.setItemInteract(itemInteract);
//            return new MenuVH2(binding, itemInteract);
//            View view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.app_vh_section, parent, false);
            return new SectionVH2(binding, itemInteract);
        }
    }

    public interface ItemInteract {
        void onSectionItemClicked(int pos, MenuVO2 data);
    }


}