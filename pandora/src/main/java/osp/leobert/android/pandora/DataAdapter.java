package osp.leobert.android.pandora;

import java.util.Collection;

/**
 * <p><b>Package:</b> osp.leobert.android.pandora </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> DataAdapter </p>
 * <p><b>Description:</b> adapter of a set of data </p>
 * Created by leobert on 2018/9/29.
 */
public interface DataAdapter<T> {
    int getDataCount();

    T getDataByIndex(int index);

    void clearAllData();

    void add(T item);

    void add(int pos, T item);

    void addAll(Collection<T> collection);

    void remove(Object item);

    void removeAtPos(int position);

    void setData(Collection<T> collection);
}
