package osp.leobert.android.pandorasample.dvh;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import osp.leobert.android.pandora.rv.AbsViewHolder;
import osp.leobert.android.pandorasample.R;
import osp.leobert.android.pandora.rv.ViewHolderCreator;


/**
 * <p><b>Package:</b> osp.leobert.android.pandorasample.dvh </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> Type2 </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2018/10/11.
 */
public class Type5VH extends FooVH<Type5VO> {
    private final ItemInteract mItemInteract;

    private Type5VO mData;


    public Type5VH(View itemView, ItemInteract mItemInteract) {
        super(itemView);
        this.mItemInteract = mItemInteract;
    }

    @Override
    public void setData(Type5VO data) {
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
                    .inflate(R.layout.app_vh_type5, null, false);
            return new Type5VH(view, itemInteract);
        }
    }

    public interface ItemInteract {
    }


}