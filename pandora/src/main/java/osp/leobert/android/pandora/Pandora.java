package osp.leobert.android.pandora;

import android.support.annotation.NonNull;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView;

/**
 * <p><b>Package:</b> osp.leobert.android.pandora </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> Pandora </p>
 * <p><b>Description:</b> TODO </p>
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

    private static final class ListUpdateCallbackImpl implements ListUpdateCallback {

        private final @NonNull
        RecyclerView.Adapter recyclerViewAdapter;

        public ListUpdateCallbackImpl(@NonNull RecyclerView.Adapter recyclerViewAdapter) {
            this.recyclerViewAdapter = recyclerViewAdapter;
        }

        @Override
        public void onInserted(int position, int count) {
            try {
                recyclerViewAdapter.notifyItemRangeInserted(position, count);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
