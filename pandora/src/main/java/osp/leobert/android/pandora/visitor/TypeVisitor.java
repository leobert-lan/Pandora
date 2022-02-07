package osp.leobert.android.pandora.visitor;

import androidx.annotation.NonNull;

/**
 * <p><b>Package:</b> osp.leobert.android.pandora.visitor </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> TypeVisitor </p>
 * <p><b>Description:</b> TypeVisitor </p>
 * Created by leobert on 2018/12/6.
 */
public abstract class TypeVisitor<T> {
    public TypeVisitor(@NonNull Class<T> targetClz) {
        this.targetClz = targetClz;
    }

    @NonNull
    private final Class<T> targetClz;

    @SuppressWarnings("unchecked")
    public final void visit(Object element) {
        if (element == null) {
            onMissed();
            return;
        }

        boolean hit = targetClz.isAssignableFrom(element.getClass());

        if (hit) {
            // TODO: 2022/2/7 change to :  onHit(targetClz.cast(element));
            onHit((T) element);
        } else {
            onMissed();
        }
    }
    public abstract void onHit(T element);

    public void onMissed() {

    }
}
