package com.lpzahd.lpzahd.activity.loading;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lpzahd.lpzahd.R;
import com.lpzahd.lpzahd.activity.base.AppBaseActivity;
import com.lpzahd.lpzahd.constance.Constances;
import com.lpzahd.lpzahd.util.ContextUtil;
import com.lpzahd.lpzahd.util.TaskUtil;
import com.lpzahd.lpzahd.util.ToastUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadingActivity extends AppBaseActivity {

    @BindView(R.id.headViewPager)
    ViewPager headViewPager;

    private AssetManager asset;

    private String[] imgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);

        justDoViewPager();
    }


    private void justDoViewPager() {
        asset = ContextUtil.getAssets();
        imgs = getHeadImgs(asset);

        if(imgs == null || imgs.length == 0) {
            ToastUtil.showToast("how can u do that to her!");
            return ;
        }

        Log.e("hit","headViewPager : " + headViewPager);
        Log.e("hit","headViewPager : " + findViewById(R.id.headViewPager));

        HeaderPagerAdapter headerPagerAdapter = new HeaderPagerAdapter(this);
        headViewPager.setAdapter(headerPagerAdapter);
    }

    /**
     * 获取assets下的所有头像
     * @return
     */
    private String[] getHeadImgs(AssetManager manager) {
        String[] imgFiles = null;
        try {
            imgFiles = manager.list(Constances.Assets.HEAD_IMG);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgFiles;
    }

    public class HeaderPagerAdapter extends PagerAdapter {

        public int convertViewCount = 5;

        private View[] convertViews;

        private Context context;


        public HeaderPagerAdapter(Context context) {
            this.context = context;

            initConvertView();
        }

        private void initConvertView() {
            if(convertViews == null || convertViews.length < convertViewCount) {
                convertViews = new View[convertViewCount];
                for (int i = 0; i < convertViewCount; i++) {
                    convertViews[i] = new ImageView(context);
                }
            }
        }


        @Override
        public int getCount() {
            return imgs.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int showViewPosition = position % convertViewCount;
            ImageView headView = (ImageView) convertViews[showViewPosition];
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(asset.open(imgs[position]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            headView.setImageBitmap(bitmap);
            return headView;
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        asset.close();
    }

}
