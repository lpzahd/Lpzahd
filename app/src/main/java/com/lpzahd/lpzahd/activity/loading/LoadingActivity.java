package com.lpzahd.lpzahd.activity.loading;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
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
import com.lpzahd.lpzahd.help.StringHelper;
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

    @BindView(R.id.parent)
    View parent;

    @BindView(R.id.headViewPager)
    ViewPager headViewPager;

    private HeaderPagerAdapter headerPagerAdapter;

    private AssetManager asset;

    private String[] imgs;

    private UserView it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        ButterKnife.bind(this);

        justDoViewPager();
        it = UserView.getIt();
    }


    private void justDoViewPager() {
        asset = ContextUtil.getAssets();
        imgs = getHeadImgs(asset);

        if (imgs == null || imgs.length == 0) {
            ToastUtil.showToast("how can u do that to her!");
            return;
        }

        headViewPager.setOverScrollMode(ViewPager.OVER_SCROLL_NEVER);
        headerPagerAdapter = new HeaderPagerAdapter(this);
        headViewPager.setAdapter(headerPagerAdapter);
        headViewPager.setPageTransformer(true, new ZoomOutTranformer());


        headViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                String imgPath = StringHelper.stitchStr(headerPagerAdapter.TAG, imgs[position]);
                Bitmap bitmap = ImgCacheManager.getImgCacheManager().getBitmap(imgPath);

                if(bitmap != null) {
                    Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {
                            int lightColor = getResources().getColor(android.R.color.holo_blue_light);
                            int darkColor = getResources().getColor(android.R.color.background_dark);
                            int color = palette.getLightMutedColor(lightColor);

                            if(color == lightColor) {
                                color = palette.getDarkMutedColor(darkColor);
                            }
                            parent.setBackgroundColor(color);
                        }
                    });
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

        public final String TAG = this.getClass().getName();

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
            final String imgPath = StringHelper.stitchStr(TAG, imgs[position]);
            bitmap = ImgCacheManager.getImgCacheManager().getBitmap(imgPath);
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
                    ImgCacheManager.getImgCacheManager().putBitmap(imgPath, bitmap);
                }
            }

            headView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    it.replaceHead(ImgCacheManager.getImgCacheManager().getBitmap(imgPath));
                }
            });

            headView.setImageBitmap(bitmap);
            if (headView.getParent() == null) {
                container.addView(headView);
            }
            return headView;
        }

    }

}
