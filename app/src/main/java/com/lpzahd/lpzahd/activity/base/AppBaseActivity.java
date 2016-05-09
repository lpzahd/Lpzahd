package com.lpzahd.lpzahd.activity.base;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.lpzahd.lpzahd.app.UserView;

/**
 * Created by Administrator on 2016/5/4.
 */
public class AppBaseActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        UserView.getIt().show();
        super.onStart();
        Log.e("hit","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserView.getIt().show();
        Log.e("hit","onResume");
    }

    @Override
    protected void onPause() {
        UserView.getIt().hide();
        super.onPause();
        Log.e("hit","onPause");
    }

    @Override
    protected void onStop() {
        UserView.getIt().hide();
        super.onStop();
        Log.e("hit","onStop");
    }
}
