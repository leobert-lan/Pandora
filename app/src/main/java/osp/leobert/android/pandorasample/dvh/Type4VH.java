package osp.leobert.android.pandorasample.dvh;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import osp.leobert.android.pandorasample.AbsViewHolder;
import osp.leobert.android.pandorasample.R;
import osp.leobert.android.pandorasample.ViewHolderCreator;


/**
 * <p><b>Package:</b> osp.leobert.android.pandorasample.dvh </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> Type2 </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2018/10/11.
 */
public class Type4VH extends FooVH<Type4VO> {
    private final ItemInteract mItemInteract;

    private Type4VO mData;


    public Type4VH(View itemView, ItemInteract mItemInteract) {
        super(itemView);
        this.mItemInteract = mItemInteract;
    }

    @Override
    public void setData(Type4VO data) {
        mData = data;
        setText(data.getText());
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
                    .inflate(R.layout.app_vh_type4, null,false);
            return new Type4VH(view, itemInteract);
        }
    }

    public interface ItemInteract {
    }


}