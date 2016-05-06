package com.lpzahd.lpzahd.help;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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

    public interface ImgCallBack {
        void s(Bitmap bitmap);
    }
}
