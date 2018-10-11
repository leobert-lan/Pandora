package osp.leobert.android.pandora;

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
}
