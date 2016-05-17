package com.lpzahd.lpzahd.app;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpNetworkFetcher;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import org.xutils.x;

import okhttp3.OkHttpClient;

/**
 * Created by mac-lpzahd on 16/4/12.
 */
public class LpzahdApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        App.init(this);

        x.Ext.init(this);

        Context context = this;
        OkHttpClient okHttpClient = new OkHttpClient(); // build on your own
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
                .newBuilder(context, okHttpClient)
//              .other setters
//              .setNetworkFetcher is already called for you
                .build();
        Fresco.initialize(context, config);
//        AutoLayoutConifg.getInstance().useDeviceSize();
    }

}
