package com.lpzahd.lpzahd.activity.rx;

import android.content.res.AssetManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lpzahd.lpzahd.R;
import com.lpzahd.lpzahd.util.AssetsUtil;
import com.lpzahd.lpzahd.util.ContextUtil;

public class FrescoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco);

        loadV1();
        loadV2();
        loadV3();
        loadV4();
        loadV5();
        loadV6();

    }

    private static final int[] ids = new int[] {
            R.id.v1,  R.id.v2, R.id.v3,
            R.id.v4,  R.id.v5, R.id.v6,
    };
    private SimpleDraweeView findSimpleDraweeView(int index) {
        return (SimpleDraweeView) findViewById(ids[index]);
    }

    private void loadV1() {
        Uri uri = Uri.parse("https://raw.githubusercontent.com/facebook/fresco/gh-pages/static/logo.png");
        findSimpleDraweeView(0).setImageURI(uri);
    }

    private void loadV2() {
        Uri uri = Uri.parse("asset://" + this.getApplication().getPackageName() + "/fro/fro_jpeg.jpeg");

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        findSimpleDraweeView(1).setController(controller);
//        findSimpleDraweeView(1).setImageURI(uri);
    }

    private void loadV3() {
        Uri uri = Uri.parse("asset://" + this.getApplication().getPackageName() + "/fro/fro_gif.gif");
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
        .build();
        findSimpleDraweeView(2).setController(controller);
    }

    private void loadV4() {
        Uri uri = Uri.parse("asset://" + this.getApplication().getPackageName() + "/fro/fro_webp.webp");
        findSimpleDraweeView(3).setImageURI(uri);
    }

    private void loadV5() {
        Uri uri = Uri.parse("https://raw.githubusercontent.com/facebook/fresco/gh-pages/static/logo.png");
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
        RoundingParams roundingParams = new RoundingParams();
//        RoundingParams roundingParams = RoundingParams.fromCornersRadius(7f);
        roundingParams.setRoundAsCircle(true);
        GenericDraweeHierarchy hierarchy =
            builder.setRoundingParams(roundingParams)
            .setProgressBarImage(new ProgressBarDrawable()).build();
        findSimpleDraweeView(4).setHierarchy(hierarchy);
        findSimpleDraweeView(4).setController(controller);
    }

    private void loadV6() {
        Uri uri = Uri.parse("www.baidu.com");
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
//                .setTapToRetryEnabled(true)
                .build();
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(7f);
        GenericDraweeHierarchy hierarchy =
                builder.setRoundingParams(roundingParams)
                        .setProgressBarImage(new ProgressBarDrawable())
                        .setFailureImage(getResources().getDrawable(R.mipmap.t2))
                        .build();
        findSimpleDraweeView(5).setHierarchy(hierarchy);
        findSimpleDraweeView(5).setController(controller);
    }
}
