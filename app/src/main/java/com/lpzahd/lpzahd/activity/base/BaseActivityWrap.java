package com.lpzahd.lpzahd.activity.base;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lpzahd.lpzahd.R;

/**
 * Created by Administrator on 2016/5/4.
 */
public class BaseActivityWrap extends TestBaseActivity {

    @Override
    public void setContentView() {

    }

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {
       TextView textView = (TextView) findViewById(R.id.textView1);
        option.optionView(textView, new SmartOptionWrapper() {

            @Override
            public void optionOnCreate(View view) {

            }

            @Override
            public void optionOnStart(View view) {

            }

            @Override
            public void optionOnStop(View view) {

            }
        });
    }
}
