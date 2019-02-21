package osp.leobert.android.pandorasample.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import osp.leobert.android.pandora.rv.ViewHolderCreator;
import osp.leobert.android.pandorasample.R;
import osp.leobert.android.pandorasample.dvh.AbsViewHolder;


/**
 * <p><b>Package:</b> osp.leobert.android.pandorasample.menu </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> Menu </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2019/2/20.
 */
public class MenuVH2 extends AbsViewHolder<MenuVO2> {
    private final ItemInteract mItemInteract;

    private MenuVO2 mData;

    private TextView tvName;


    public MenuVH2(View itemView, ItemInteract itemInteract) {
        super(itemView);
        this.mItemInteract = itemInteract;
        tvName = itemView.findViewById(R.id.vh_menu_name);

        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemInteract != null)
                    mItemInteract.onMenuItemClicked(getAdapterPosition(), mData);
            }
        });
    }

    @Override
    public void setData(MenuVO2 data) {
        mData = data;
        tvName.setText(data.getName());
    }

    public static final class Creator extends ViewHolderCreator {
        private final
        ItemInteract itemInteract;

        public Creator(ItemInteract itemInteract) {
            this.itemInteract = itemInteract;
        }

        @Override
        public AbsViewHolder<MenuVO2> createViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.app_vh_menu, parent, false);
            return new MenuVH2(view, itemInteract);
        }
    }

    public interface ItemInteract {
        void onMenuItemClicked(int pos, MenuVO2 data);
    }


}