package osp.leobert.android.pandorasample;

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
}
