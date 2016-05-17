package com.lpzahd.lpzahd.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.lpzahd.lpzahd.R;
import com.lpzahd.lpzahd.util.img.DrawableUtil;

/**
 * Created by 迪 on 2016/5/8.
 */
public class UserView {

    public static int WIDHT = App.SCREEN_WIDTH / 8;
    public static int HEIGHT = WindowManager.LayoutParams.WRAP_CONTENT;

    private static Application app = App.app;
    private static UserView userView;

    private UserView() {
    }

    private ImageView userHead;
    private Drawable drawable;
    private WindowManager windowManager;
    private View contentView;

    /**
     * contentview 是否显示
     */
    private boolean isShow;

    /**
     * contentview 是否加入窗口
     */
    private boolean isAdd;

    public static UserView getIt() {
        if (userView == null) {

            if (app == null) {
                throw new NullPointerException("appliction is null");
            }

            userView = new UserView();
            userView.initUsreView();
        }
        return userView;
    }

    /**
     * x
     * 创建一个windows层的视图
     *
     * @return
     */
    private void initUsreView() {
        if(windowManager == null) {
            windowManager = (WindowManager) app.getSystemService(Context.WINDOW_SERVICE);
        }

        WindowManager.LayoutParams wParams = new WindowManager.LayoutParams();
        // 在某些机型上需要悬浮窗权限设置，比如小米
//        wParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        wParams.type = WindowManager.LayoutParams.TYPE_TOAST;
//        WindowManager.LayoutParams.TYPE_APPLICATION
        wParams.format = PixelFormat.TRANSLUCENT; // 透明
        wParams.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wParams.width = WIDHT;
        wParams.height = HEIGHT;
        wParams.gravity = Gravity.TOP | Gravity.RIGHT;

        LayoutInflater inflater = (LayoutInflater) app.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.window_app, null);

        windowManager.addView(contentView, wParams);
        isShow = true;
        isAdd = true;

        userHead = (ImageView) contentView.findViewById(R.id.userHead);
        ViewGroup.LayoutParams headParams = userHead.getLayoutParams();
        headParams.width = WIDHT;
        headParams.height = WIDHT;
        userHead.setLayoutParams(headParams);
    }

    public boolean isShow() {
        return isShow;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void show() {
        if(!isShow) {
            isShow = true;
            contentView.setVisibility(View.VISIBLE);
        }
    }

    public void hide() {
        if(isShow) {
            isShow = false;
            contentView.setVisibility(View.GONE);
        }
    }

    public void addContentView() {
        if(!isAdd) {
            initUsreView();
        }
    }

    public void removeContentView() {
        if(isAdd) {
            windowManager.removeView(contentView);
        }
    }

    public void replaceHead(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }

        bitmap = checkHead(bitmap);

        drawable = DrawableUtil.createCircleDrawable(bitmap);

        ObjectAnimator anim1 = ObjectAnimator.ofFloat(userHead, "scaleX",
                1.0f, 0.1f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(userHead, "scaleY",
                1.0f, 0.1f);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(userHead, "alpha",
                1.0f, 0.1f);

        ObjectAnimator anim4 = ObjectAnimator.ofFloat(userHead, "scaleX",
                0.1f, 1.0f);
        ObjectAnimator anim5 = ObjectAnimator.ofFloat(userHead, "scaleY",
                0.1f, 1.0f);
        ObjectAnimator anim6 = ObjectAnimator.ofFloat(userHead, "alpha",
                0.1f, 1.0f);

        AnimatorSet animSet = new AnimatorSet();

        if(userHead.getDrawable() != null) {
            animSet.play(anim1).with(anim2);
            animSet.play(anim2).with(anim3);
            animSet.play(anim4).after(anim3);
            animSet.play(anim5).after(anim3);
            animSet.play(anim6).after(anim3);
            animSet.setDuration(1000);
        } else {
            // 总感觉别扭
            userHead.setImageDrawable(drawable);
            animSet.play(anim4).with(anim5);
            animSet.play(anim5).with(anim6);
            animSet.setDuration(500);
        }
        animSet.start();

        anim3.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                userHead.setImageDrawable(drawable);
            }
        });

    }

    /**
     * 检测头像是否小于额定尺寸，如果小于则创建一个合适大小的图片
     *
     * @param bm
     * @return
     */
    private Bitmap checkHead(Bitmap bm) {
        if (bm.getWidth() >= WIDHT &&
                bm.getHeight() >= WIDHT) {
        } else {
            bm = Bitmap.createScaledBitmap(bm, WIDHT, WIDHT, true);
        }
        return bm;
    }
}
