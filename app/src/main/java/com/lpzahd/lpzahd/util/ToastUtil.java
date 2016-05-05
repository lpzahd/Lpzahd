package com.lpzahd.lpzahd.util;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import com.github.johnpersano.supertoasts.SuperToast;
import com.lpzahd.lpzahd.app.App;

/**
 * Created by mac-lpzahd on 16/4/13.
 */
public class ToastUtil {

    static Context context;
    static SuperToast toast;

    static int duration = SuperToast.Duration.MEDIUM;

    static {
        context = App.app;
        toast = new SuperToast(context);
        toast.setDuration(duration);
    }

    /**
     * 重复响应时，只会修改文字，不创建新的toast
     * @param text
     */
    public static void showToast(String text) {
        showToast(text, -1, -1);
    }

    public static void showToast(@ColorInt int textColor) {
        showToast("", textColor, -1);
    }

    public static void showToast(String text, @ColorInt int textColor) {
        showToast(text, textColor, -1);
    }

    public static void showToast(String text, @ColorInt int textColor, int duration) {
        if(!TextUtils.isEmpty(text) && !TextUtils.equals(toast.getText(), text)) {
            toast.setText(text);
        }
        if(textColor <= Color.TRANSPARENT && textColor >= Color.BLACK && !(toast.getTextColor() == textColor)) {
            toast.setTextColor(textColor);
        }
        if(duration >= 0 && duration <= 4500) {
            toast.setDuration(duration);
        }
        if(!toast.isShowing()) {
            toast.show();
        }
    }

    /**
     * 创建新的toast
     * @param text
     * @param duration
     */
    public static void showNewToast(String text, int duration) {
        SuperToast toast = new SuperToast(context);
        toast.setDuration(duration);
        toast.setText(text);
        toast.show();
    }
}
