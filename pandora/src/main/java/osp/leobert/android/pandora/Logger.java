package osp.leobert.android.pandora;

import android.util.Log;

import androidx.annotation.IntDef;

/**
 * <p><b>Package:</b> osp.leobert.android.pandora </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> Logger </p>
 * Created by leobert on 2019/1/14.
 */
public final class Logger {
    public static String TAG = "Pandora";
    public static boolean DEBUG = false;

    @IntDef({Level.v, Level.d, Level.i, Level.w, Level.e})
    @interface Level {
        int v = Log.VERBOSE;
        int d = Log.DEBUG;
        int i = Log.INFO;
        int w = Log.WARN;
        int e = Log.ERROR;
    }

    public static void println(@Level int level, String tag, String msg) {
        if (DEBUG && require(level)) {
            Log.println(level, tag, msg);
        }
    }

    @Level
    public static int level = Level.d;

    public static void tag(String tag) {
        TAG = tag;
    }

    public static void setLevel(@Level int level) {
        Logger.level = level;
    }

    private static boolean require(@Level int level) {
        return Logger.level <= level;
    }

    public static void v(String msg) {
        if (DEBUG && require(Level.v)) {
            Log.v(TAG, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (DEBUG && require(Level.v)) {
            Log.v(tag, msg);
        }
    }

    public static void d(String msg) {
        if (DEBUG && require(Level.d)) {
            Log.d(TAG, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG && require(Level.d)) {
            Log.d(tag, msg);
        }
    }

    public static void i(String msg) {
        if (DEBUG && require(Level.i)) {
            Log.i(TAG, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG && require(Level.i)) {
            Log.i(tag, msg);
        }
    }

    public static void w(String msg) {
        if (DEBUG && require(Level.w)) {
            Log.w(TAG, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (DEBUG && require(Level.w)) {
            Log.w(tag, msg);
        }
    }

    public static void e(String msg) {
        if (DEBUG && require(Level.e)) {
            Log.e(TAG, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG && require(Level.e)) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable e) {
        if (DEBUG && require(Level.e)) {
            Log.e(tag, msg, e);
        }
    }
}
