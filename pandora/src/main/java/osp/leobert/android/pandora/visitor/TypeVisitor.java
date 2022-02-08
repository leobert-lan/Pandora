package osp.leobert.android.pandora.visitor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * <p><b>Package:</b> osp.leobert.android.pandora.visitor </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> TypeVisitor </p>
 * <p><b>Description:</b> TypeVisitor </p>
 * Created by leobert on 2018/12/6.
 */
public class TypeVisitor<T> {
    public TypeVisitor(@NonNull Class<T> targetClz) {
        this.targetClz = targetClz;
    }

    @NonNull
    private final Class<T> targetClz;

    @Nullable
    public final T visit(Object element) {
        if (element == null) {
            onMissed();
            return null;
        }

        boolean hit = targetClz.isAssignableFrom(element.getClass());

        if (hit) {
            T ret = targetClz.cast(element);
            onHit(ret);
            return ret;
        } else {
            onMissed();
            return null;
        }
    }

    public void onHit(T element) {

    }

    public void onMissed() {

    }
}
