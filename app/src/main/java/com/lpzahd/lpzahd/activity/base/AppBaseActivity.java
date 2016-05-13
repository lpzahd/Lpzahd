package com.lpzahd.lpzahd.activity.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.lpzahd.lpzahd.app.UserView;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by Administrator on 2016/5/4.
 */
public class AppBaseActivity extends AppCompatActivity {

    public String TAG = this.getClass().getName();

    private SystemBarTintManager mTintManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSystemBar();
    }

    public void setTintColor(int color) {
        mTintManager.setTintColor(color);
    }

    private void initSystemBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        mTintManager = new SystemBarTintManager(this);
        // enable status bar tint
        mTintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        mTintManager.setNavigationBarTintEnabled(true);

        // set a custom tint color for all system bars
        // tintManager.setTintColor(Color.RED);
        // set a custom navigation bar resource
        // tintManager.setNavigationBarTintResource(R.drawable.icon_1);
        // set a custom status bar drawable
        // tintManager.setStatusBarTintDrawable();

        mTintManager.setTintColor(Color.parseColor("#990000FF"));
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onStart() {
        UserView.getIt().show();
        super.onStart();
        Log.e("hit",TAG + " onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("hit",TAG + " onResume");
    }

    @Override
    protected void onPause() {
        UserView.getIt().hide();
        super.onPause();
        Log.e("hit",TAG + " onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("hit",TAG + " onStop");
    }

    public Context getContext() {
        return this;
    }
}
