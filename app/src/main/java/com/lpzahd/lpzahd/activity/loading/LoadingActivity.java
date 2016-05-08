package com.lpzahd.lpzahd.activity.loading;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lpzahd.lpzahd.R;
import com.lpzahd.lpzahd.activity.base.AppBaseActivity;
import com.lpzahd.lpzahd.anim.viewpage.ZoomOutTranformer;
import com.lpzahd.lpzahd.app.UserView;
import com.lpzahd.lpzahd.constance.Constances;
import com.lpzahd.lpzahd.help.ImgHelper;
import com.lpzahd.lpzahd.manager.cache.ImgCacheManager;
import com.lpzahd.lpzahd.util.ContextUtil;
import com.lpzahd.lpzahd.util.ToastUtil;
import com.lpzahd.lpzahd.util.img.BitmapUtil;
import com.lpzahd.lpzahd.util.img.DrawableUtil;

import java.io.IOException;
import java.io.InputStream;

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
        ButterKnife.bind(this);

        justDoViewPager();
        UserView.createInstance();
    }


    private void justDoViewPager() {
        asset = ContextUtil.getAssets();
        imgs = getHeadImgs(asset);

        if (imgs == null || imgs.length == 0) {
            ToastUtil.showToast("how can u do that to her!");
            return;
        }

        headViewPager.setOverScrollMode(ViewPager.OVER_SCROLL_NEVER);
        HeaderPagerAdapter headerPagerAdapter = new HeaderPagerAdapter(this);
        headViewPager.setAdapter(headerPagerAdapter);
        headViewPager.setPageTransformer(true, new ZoomOutTranformer());
    }

    /**
     * 获取assets下的所有头像
     *
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

        private Context context;

        public HeaderPagerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return imgs.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView headView = new ImageView(context);
            Bitmap bitmap = null;
            bitmap = ImgCacheManager.getImgCacheManager().getBitmap(this.getClass().getName() + "_" + imgs[position]);
            if(bitmap == null) {
                InputStream in = null;
                try {
                    in = asset.open(Constances.Assets.HEAD_IMG + "/" + imgs[position]);
                    BitmapFactory.Options options = ImgHelper.i().sampleOptions(in, new int[]{300, 300});
                    bitmap = BitmapFactory.decodeStream(in, null, options);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                Drawable drawable = null;
                if (bitmap != null) {
                    bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
                    drawable = DrawableUtil.createCircleDrawable(bitmap);
                    bitmap = BitmapUtil.drawableToBitmap(drawable);
                    ImgCacheManager.getImgCacheManager().putBitmap(this.getClass().getName() + "_" + imgs[position], bitmap);
                }
            }
            headView.setImageBitmap(bitmap);
            if (headView.getParent() == null) {
                container.addView(headView);
            }
            return headView;
        }

    }


}
