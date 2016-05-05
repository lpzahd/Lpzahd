package com.lpzahd.lpzahd.util.img;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by 迪 on 2016/5/4.
 */
public class DrawableUtil {

    public static Drawable createRoundDrawable(Bitmap bitmap, float round) {
        return new RoundDrawable(bitmap, round);
    }

    public static Drawable createCircleDrawable(Bitmap bitmap) {
        return new CircleDrawable(bitmap);
    }


}
