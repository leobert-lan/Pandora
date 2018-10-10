package osp.leobert.android.pandorasample.nds;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import osp.leobert.android.pandorasample.Check;
import osp.leobert.android.pandorasample.DataSet;

/**
 * <p><b>Package:</b> osp.leobert.android.pandorasample.nds </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> ListDataSet </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2018/10/10.
 */
public class ListDataSet<T extends DataSet.Data> extends DataSet {

    @NonNull
    protected List<T> data = new ArrayList<>();

    public void setData(List<T> data) {
        if (!Check.isListNullOrEmpty(data))
            this.data = data;
        else
            this.data = new ArrayList<>();
        notifyChanged();
    }

    public void appendData(List<T> appends) {
        if (!Check.isListNullOrEmpty(appends)) {
            data.addAll(appends);
            notifyChanged();
        }
    }

    public void appendData(T append) {
        if (append != null) {
            data.add(append);
            notifyChanged();
        }
    }

    public void appendData(@IntRange(from = 0) int index, T append) {
        if (append != null) {
            data.add(index, append);
            notifyChanged();
        }
    }

    public void clear() {
        if (data != null) {
            data.clear();
            notifyChanged();
        }
    }

    public List<T> getData() {
        return data;
    }


    @Override
    public int getCount() {
        if (data == null)
            return 0;
        else
            return data.size();
    }

    @Override
    public Data getItem(int position) {
        if (data == null || data.size() <= position) {
            return null;
        }
        return data.get(position);
    }

    public T getItem2(int position) {
        if (data == null || data.size() <= position) {
            return null;
        }
        return data.get(position);
    }
}
