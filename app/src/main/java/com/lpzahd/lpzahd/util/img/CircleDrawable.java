package com.lpzahd.lpzahd.util.img;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

/**
 * Created by 迪 on 2016/5/4.
 */
public class CircleDrawable extends ShapeDrawable {

    private int minSize;

    public CircleDrawable(Bitmap bitmap) {
        super(bitmap);
        minSize = Math.min(bitmap.getWidth(), bitmap.getHeight());
        Log.e("hit","w : " + bitmap.getWidth() + " h : " + bitmap.getHeight());
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(minSize/2 , minSize/2 , minSize/2, mPaint);
    }
}
