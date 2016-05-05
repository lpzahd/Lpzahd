package com.lpzahd.lpzahd.app;

import android.app.Application;

/**
 * Created by mac-lpzahd on 16/4/13.
 *
 * 应用全局参数
 */
public class App {

    public static Application app;

    public static void init(Application application) {
        app = application;
    }
}
