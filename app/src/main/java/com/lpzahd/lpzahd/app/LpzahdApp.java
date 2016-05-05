package com.lpzahd.lpzahd.app;

import android.app.Application;

import com.zhy.autolayout.config.AutoLayoutConifg;

import org.xutils.x;

/**
 * Created by mac-lpzahd on 16/4/12.
 */
public class LpzahdApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        App.init(this);

        x.Ext.init(this);

        AutoLayoutConifg.getInstance().useDeviceSize();
    }

}
