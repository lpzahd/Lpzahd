package com.lpzahd.lpzahd.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by mac-lpzahd on 16/4/13.
 *
 * 应用全局参数
 */
public class App {

    public static Application app;

    /**
     * 设备屏幕宽高。默认手机设备竖屏：width < height, 平板设备横屏: width > height;
     */
    public static int SCREEN_WIDTH, SCREEN_HEIGHT;

    public static void init(Application application) {
        app = application;

        initDeviceInfo(app);
    }

    public static void initDeviceInfo(Context context){
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        if (!isTablet()) {
            SCREEN_WIDTH = Math.min(metrics.widthPixels, metrics.heightPixels);
            SCREEN_HEIGHT = Math.max(metrics.widthPixels, metrics.heightPixels);
        } else {
            SCREEN_WIDTH = Math.max(metrics.widthPixels, metrics.heightPixels);
            SCREEN_HEIGHT = Math.min(metrics.widthPixels, metrics.heightPixels);
        }
    }


    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isTablet() {
        return (app.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
