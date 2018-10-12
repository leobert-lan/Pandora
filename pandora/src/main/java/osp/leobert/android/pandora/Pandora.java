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

package osp.leobert.android.pandora;

import android.support.annotation.NonNull;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView;

/**
 * <p><b>Package:</b> osp.leobert.android.pandora </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> Pandora </p>
 * <p><b>Description:</b> Pandora </p>
 * Created by leobert on 2018/9/29.
 */
public final class Pandora {
    private Pandora() {
        throw new RuntimeException("not support to create an instance");
    }

    public static <T> RealDataSet<T> real() {
        return new RealDataSet<>();
    }

    public static <T> WrapperDataSet<T> wrapper() {
        return new WrapperDataSet<>();
    }

    public static void bind2RecyclerViewAdapter(@NonNull PandoraBoxAdapter pandoraBoxAdapter,
                                                @NonNull RecyclerView.Adapter recyclerViewAdapter) {
        pandoraBoxAdapter.setListUpdateCallback(new ListUpdateCallbackImpl(recyclerViewAdapter));
    }

    public static boolean checkAliasUnique(@NonNull PandoraBoxAdapter pandoraBoxAdapter,
                                           @NonNull String alias) {
        return !pandoraBoxAdapter.isAliasConflict(alias);
    }

    private static final class ListUpdateCallbackImpl implements ListUpdateCallback {

        @NonNull
        private final
        RecyclerView.Adapter recyclerViewAdapter;

        public ListUpdateCallbackImpl(@NonNull RecyclerView.Adapter recyclerViewAdapter) {
            this.recyclerViewAdapter = recyclerViewAdapter;
        }

        @Override
        public void onInserted(int position, int count) {
            recyclerViewAdapter.notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            recyclerViewAdapter.notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            recyclerViewAdapter.notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onChanged(int position, int count, Object payload) {
            recyclerViewAdapter.notifyItemRangeChanged(position, count, payload);
        }
    }
}
