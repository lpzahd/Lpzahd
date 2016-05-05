package com.lpzahd.lpzahd.activity.loading;

import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import com.lpzahd.lpzahd.R;
import com.lpzahd.lpzahd.activity.base.AppBaseActivity;
import com.lpzahd.lpzahd.util.img.DrawableUtil;

public class LoadingActivity extends AppBaseActivity{

    private ImageView photos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        findView();
    }

    private void findView() {
        photos = (ImageView) findViewById(R.id.photos);

//        photos.setImageResource(R.mipmap.img_head_01);
        Drawable head = DrawableUtil.createCircleDrawable(BitmapFactory.decodeResource(getResources(), R.mipmap.img_head_01));
        Log.e("hit","head : " + head);
        photos.setImageDrawable(head);
    }

}
