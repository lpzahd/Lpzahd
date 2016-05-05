package com.lpzahd.lpzahd.manager.cache;

import android.graphics.Bitmap;

import org.xutils.cache.LruCache;

/**
 * Created by 迪 on 2016/5/5.
 */
public class ImgCacheManager {

    private final static int maxSize = (int) (Runtime.getRuntime().maxMemory() / 8);
    private static LruCache<String, Bitmap> lruCache;

    static {
        lruCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    private ImgCacheManager(){}
    private static ImgCacheManager imgCacheManager;
    public static ImgCacheManager getImgCacheManager () {
        if(imgCacheManager == null) {
            imgCacheManager = new ImgCacheManager();
        }
        return imgCacheManager;
    }

    public void putBitmap(String fileName, Bitmap bitmap) {
        lruCache.put(fileName, bitmap);
    }

    public Bitmap getBitmap(String fileName) {
        return lruCache.get(fileName);
    }

}
