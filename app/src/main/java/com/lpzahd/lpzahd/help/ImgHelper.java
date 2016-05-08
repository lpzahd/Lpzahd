package com.lpzahd.lpzahd.help;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.lpzahd.lpzahd.manager.cache.ImgCacheManager;

import org.xutils.x;

import java.io.InputStream;

/**
 * Created by Administrator on 2016/5/6.
 */
public class ImgHelper {

    private static ImgHelper helper = new ImgHelper();
    private static ImgCacheManager cacheManager;

    static {
        cacheManager = ImgCacheManager.getImgCacheManager();
    }

    private ImgHelper() {
    }

    public static ImgHelper i() {
        return helper;
    }

    public void porterImg(String fileName, ImgCallBack cb) {
        Bitmap bitmap = null;
        // 缓存读取
        bitmap = cacheManager.getBitmap(fileName);

        if(bitmap != null) {
            cb.s(bitmap);
            return;
        }


    }

    public BitmapFactory.Options sampleOptions(InputStream is, @NonNull  int[] size) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;

        BitmapFactory.decodeStream(is, null, options);

        options.inSampleSize = (options.outWidth / size[0]  + options.outHeight / size[1])/2;
        options.inSampleSize = options.inSampleSize == 0 ? 1 : options.inSampleSize;

        options.inJustDecodeBounds = false;

        options.inDither=false;    /*不进行图片抖动处理*/
        options.inPreferredConfig=null;  /*设置让解码器以最佳方式解码*/

        /* 下面两个字段需要组合使用 */

        options.inPurgeable = true;

        options.inInputShareable = true;

        return options;
    }

    public interface ImgCallBack {
        void s(Bitmap bitmap);
    }
}
