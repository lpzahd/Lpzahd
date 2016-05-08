package com.lpzahd.lpzahd.app;

import android.app.Application;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.lpzahd.lpzahd.R;
import com.lpzahd.lpzahd.constance.Constances;

/**
 * Created by 迪 on 2016/5/8.
 */
public class UserView {

    private static Application app = App.app;
    private static UserView userView;
    private UserView(){}

    public static UserView createInstance() {
        if(userView == null) {

            if(app == null) {
                throw new NullPointerException("appliction is null");
            }

            userView = new UserView();
            userView.initUsreView();
        }
        return userView;
    }

    /**x
     * 创建一个windows层的视图
     * @return
     */
    private void initUsreView() {
        WindowManager windowManager = (WindowManager) app.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater inflater = (LayoutInflater) app.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View userView = inflater.inflate(R.layout.window_app, null);
        WindowManager.LayoutParams wParams = new WindowManager.LayoutParams();
        wParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        wParams.format = PixelFormat.TRANSLUCENT; // 透明
        wParams.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wParams.gravity = Gravity.TOP | Gravity.RIGHT;
        windowManager.addView(userView, wParams);
    }

}
