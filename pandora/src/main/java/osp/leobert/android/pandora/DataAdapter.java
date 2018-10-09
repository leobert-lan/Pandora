package osp.leobert.android.pandora;

/**
 * <p><b>Package:</b> osp.leobert.android.pandora </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> DataAdapter </p>
 * <p><b>Description:</b> adapter of a set of data </p>
 * Created by leobert on 2018/9/29.
 */
public interface DataAdapter<T> {
    long getDataCount();

    T getDataByIndex(int index);
}
