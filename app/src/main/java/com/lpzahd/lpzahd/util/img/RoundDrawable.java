package com.lpzahd.lpzahd.util.img;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Created by 迪 on 2016/5/4.
 */
public class RoundDrawable extends ShapeDrawable {

    private RectF rectF;
    private float round = 8;

    public RoundDrawable(Bitmap bitmap) {
        super(bitmap);
    }

    public RoundDrawable(Bitmap bitmap, float round) {
        super(bitmap);
        this.round = round;
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        rectF = new RectF(left, top, right, bottom);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRoundRect(rectF, round, round, mPaint);
    }
}
