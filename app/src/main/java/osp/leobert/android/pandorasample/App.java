package osp.leobert.android.pandorasample;

import android.app.Application;

import osp.leobert.android.pandora.Logger;

/**
 * <p><b>Package:</b> osp.leobert.android.pandorasample </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> App </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2019/1/14.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.DEBUG = true;
    }
}
