/*
 * MIT License
 *
 * Copyright (c) 2018 leobert-lan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package osp.leobert.android.pandorasample.dvh;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import osp.leobert.android.pandora.rv.ViewHolderCreator;
import osp.leobert.android.pandorasample.R;


/**
 * <p><b>Package:</b> osp.leobert.android.pandorasample.dvh </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> Type2 </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2018/10/11.
 */
public class Type2VH extends FooVH<Type2VO> {
    private final ItemInteract mItemInteract;

    private Type2VO mData;


    public Type2VH(View itemView, ItemInteract mItemInteract) {
        super(itemView);
        this.mItemInteract = mItemInteract;
    }

    @Override
    public void setData(Type2VO data) {
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
                    .inflate(R.layout.app_vh_type2, parent, false);
            return new Type2VH(view, itemInteract);
        }
    }

    public interface ItemInteract {
    }


}