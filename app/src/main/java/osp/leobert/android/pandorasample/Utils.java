package osp.leobert.android.pandorasample;

import android.util.SparseArray;

import androidx.annotation.NonNull;

import java.util.Arrays;

/**
 * <p><b>Package:</b> osp.leobert.android.pandorasample </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> Utils </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2018/10/22.
 */
public class Utils {
    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

    public static int hash(Object... values) {
        return Arrays.hashCode(values);
    }

    public static String sparseArrayToString(SparseArray<?> target) {

        if (target == null)
            return "null";

        final int mSize = target.size();
        if (mSize <= 0) {
            return "{}";
        }


        StringBuilder buffer = new StringBuilder(mSize * 28);
        buffer.append('{');
        for (int i = 0; i < mSize; i++) {
            if (i > 0) {
                buffer.append(", \r\n");
            }
            int key = target.keyAt(i);
            buffer.append(key);
            buffer.append('=');
            Object value = target.valueAt(i);
            if (value != target) {
                buffer.append(value);
            } else {
                buffer.append("(this Map)");
            }
        }
        buffer.append('}');
        return buffer.toString();

    }
}
