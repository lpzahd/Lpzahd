package com.lpzahd.lpzahd.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/4.
 */
public abstract class TestBaseActivity extends AppBaseActivity{

    protected SplitViewOption option = new SplitViewOption();

    protected void beforeCreate() {

    }

    public abstract void setContentView();

    protected void onBaseCreate(Bundle savedInstanceState) {

    }

    @Override
    final protected void onCreate(@Nullable Bundle savedInstanceState) {
        beforeCreate();
        super.onCreate(savedInstanceState);
        setContentView();
        onBaseCreate(savedInstanceState);
        option.optionsOnCreate();
    }

    @Override
    protected void onStart() {
        option.optionsOnStart();
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        option.optionsOnStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        option.clearOption();
        super.onDestroy();
    }

    protected static class SplitViewOption {

        private Map<View, SmartOptionListener> options = new LinkedHashMap<View, SmartOptionListener>();

        public void optionView(@Nullable View view, @Nullable SmartOptionListener listener) {
            options.put(view, listener);
        }

        private void optionsOnCreate() {
          if(options.size() > 0) {
              Iterator<Map.Entry<View, SmartOptionListener>> it = options.entrySet().iterator();

              while (it.hasNext()) {

                  Map.Entry<View, SmartOptionListener> entry = it.next();

                  entry.getValue().optionOnCreate(entry.getKey());
              }
          }
        }

        private void optionsOnStart() {
            if(options.size() > 0) {
                Iterator<Map.Entry<View, SmartOptionListener>> it = options.entrySet().iterator();

                while (it.hasNext()) {

                    Map.Entry<View, SmartOptionListener> entry = it.next();

                    entry.getValue().optionOnStart(entry.getKey());
                }
            }
        }

        private void optionsOnStop() {
            if(options.size() > 0) {
                Iterator<Map.Entry<View, SmartOptionListener>> it = options.entrySet().iterator();

                while (it.hasNext()) {

                    Map.Entry<View, SmartOptionListener> entry = it.next();

                    entry.getValue().optionOnStop(entry.getKey());
                }
            }
        }

        private void clearOption() {
            options.clear();
        }
    }


    /**
     * 简单的实现类
     */
    protected class SmartOptionWrapper implements SmartOptionListener {

        @Override
        public void optionOnCreate(View view) {

        }

        @Override
        public void optionOnStart(View view) {

        }

        @Override
        public void optionOnStop(View view) {

        }
    }

    private interface SmartOptionListener {
        void optionOnCreate(View view);
        void optionOnStart(View view);
        void optionOnStop(View view);
//        void optionsOnStop(View view, String method, Object... argus);
    }

}
