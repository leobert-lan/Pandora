package osp.leobert.android.pandora;

import androidx.annotation.NonNull;

/**
 * <p><b>Package:</b> osp.leobert.android.pandora </p>
 * <p><b>Classname:</b> Transaction </p>
 * Created by leobert on 2020/11/3.
 */
public class Transaction<T> {
    public interface Function<T> {

        /**
         * Applies this function to the given argument.
         *
         * @param t the function argument
         */
        void apply(T t);
    }

    private final PandoraBoxAdapter<T> adapter;

    public Transaction(PandoraBoxAdapter<T> adapter) {
        this.adapter = adapter;
    }

    private void prepare() {
        adapter.startTransaction();
    }

    public void apply(@NonNull Function<PandoraBoxAdapter<T>> runnable) {
        prepare();
        try {
            runnable.apply(adapter);
        } catch (Exception e) {
            Logger.e(Logger.TAG, "transaction failure", e);
            restore();
        }
    }


    private void restore() {
        adapter.restore();
    }
}
