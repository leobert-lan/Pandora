package osp.leobert.android.pandorasample.dvh;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import osp.leobert.android.pandorasample.AbsViewHolder;
import osp.leobert.android.pandorasample.R;
import osp.leobert.android.pandorasample.ViewHolderCreator;


/**
 * <p><b>Package:</b> osp.leobert.android.pandorasample.dvh </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> Type1 </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2018/10/11.
 */
public class Type1VH extends AbsViewHolder<Type1VO> {
    private final ItemInteract mItemInteract;

    private Type1VO mData;


    public Type1VH(View itemView, ItemInteract mItemInteract) {
        super(itemView);
        this.mItemInteract = mItemInteract;
    }

    @Override
    public void setData(Type1VO data) {
        mData = data;

    }

    public static final class Creator extends ViewHolderCreator {
        private final
        ItemInteract itemInteract;

        public Creator(ItemInteract itemInteract) {
            this.itemInteract = itemInteract;
        }

        @Override
        public AbsViewHolder createViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.app_vh_type1, parent);
            return new Type1VH(view, itemInteract);
        }
    }

    public interface ItemInteract {
        void foo();
    }


}