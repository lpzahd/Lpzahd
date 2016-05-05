package com.lpzahd.lpzahd.activity.base;

import android.util.Log;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/5/4.
 */
public class SimpleHolderAdapter extends HolderAdapter<TestSepatateActivity.SimpleHolder>{

    public SimpleHolderAdapter(SeparateBaseActivity.LifeSeparate eu) {
        super(eu);
    }

    @Override
    public void addAll(TestSepatateActivity.SimpleHolder holder) {
        eu.addItem(holder.t1, new SeparateBaseActivity.LifeSeparateWrap<TextView>() {

            @Override
            public void onCreate(TextView textView) {
                textView.setText("onCreate");
            }

        });

        eu.addItem(holder.t2, new SeparateBaseActivity.LifeSeparateWrap<TextView>() {

            @Override
            public void onStart(TextView textView) {
                textView.setText("onStart");
            }

        });

        eu.addItem(holder.t3, new SeparateBaseActivity.LifeSeparateWrap<TextView>() {

            @Override
            public void onResume(TextView textView) {
                textView.setText("onResume");
            }

        });

        eu.addItem(holder.s1, new SeparateBaseActivity.LifeSeparateWrap<String>() {

            @Override
            public void onResume(String str) {
                str = "onResume";
                Log.e("hit", "str : " + str);
            }

        });

        eu.addItem(holder.i1, new SeparateBaseActivity.LifeSeparateWrap<Integer>() {

            @Override
            public void onResume(Integer i) {
                i = 10;
                Log.e("hit", "i : " + i);
            }

        });
    }
}
