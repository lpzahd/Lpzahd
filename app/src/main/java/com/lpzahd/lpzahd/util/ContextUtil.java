package com.lpzahd.lpzahd.util;

import android.app.Application;
import android.content.res.AssetManager;

import com.lpzahd.lpzahd.app.App;

/**
 * Created by Administrator on 2016/5/6.
 */
public class ContextUtil {

    private static Application context;

    static {
        context = App.app;
    }

    public static AssetManager getAssets() {
        return context.getAssets();
    }

}
