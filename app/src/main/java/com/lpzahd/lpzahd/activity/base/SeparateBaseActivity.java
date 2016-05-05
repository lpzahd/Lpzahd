package com.lpzahd.lpzahd.activity.base;


import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/4.
 */
public abstract class SeparateBaseActivity extends AppBaseActivity {

    protected LifeSeparate mEu = new LifeSeparate();
    protected LifeHolder mHolder;

    @Nullable
    public abstract LifeHolder initHolder();

    public void beforeOnCreate(){}

    public void onBaseCreate(){}

    protected LifeSeparate getLifeSepatate() {
        return mEu;
    }

    protected <T extends LifeHolder> T getLifeHolder() {
        return (T) mHolder;
    }


    @Override
    final protected void onCreate(@Nullable Bundle savedInstanceState) {
        beforeOnCreate();
        super.onCreate(savedInstanceState);
        mEu.initHolder(mHolder = initHolder());
        onBaseCreate();
        mEu.onCreate();
    }

    @Override
    protected void onStart() {
        mEu.onStart();
        super.onStart();
    }

    @Override
    protected void onResume() {
        mEu.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mEu.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        mEu.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mEu.onDestroy();
        super.onDestroy();
    }

    public class LifeSeparate<T extends LifeHolder> {

        private T holder;

        public void initHolder(T holder) {
            this.holder = holder;
        }

        public void addItem(Object t, LifeSeparateListener<?> listener) {
            holder.add(t, listener);
        }

        public void onCreate() {
            if(holder.size() > 0) {
                Iterator<Map.Entry<Object, LifeSeparateListener>> it = holder.getMap().entrySet().iterator();

                while (it.hasNext()) {

                    Map.Entry<Object, LifeSeparateListener> entry = it.next();

                    entry.getValue().onCreate(entry.getKey());
                }
            }
        }

        public void onStart() {
            if(holder.size() > 0) {
                Iterator<Map.Entry<Object, LifeSeparateListener>> it = holder.getMap().entrySet().iterator();

                while (it.hasNext()) {

                    Map.Entry<Object, LifeSeparateListener> entry = it.next();

                    entry.getValue().onStart(entry.getKey());
                }
            }
        }

        public void onResume() {
            if(holder.size() > 0) {
                Iterator<Map.Entry<Object, LifeSeparateListener>> it = holder.getMap().entrySet().iterator();

                while (it.hasNext()) {

                    Map.Entry<Object, LifeSeparateListener> entry = it.next();

                    entry.getValue().onResume(entry.getKey());
                }
            }
        }

        public void onPause() {
            if(holder.size() > 0) {
                Iterator<Map.Entry<Object, LifeSeparateListener>> it = holder.getMap().entrySet().iterator();

                while (it.hasNext()) {

                    Map.Entry<Object, LifeSeparateListener> entry = it.next();

                    entry.getValue().onPause(entry.getKey());
                }
            }
        }

        public void onStop() {
            if(holder.size() > 0) {
                Iterator<Map.Entry<Object, LifeSeparateListener>> it = holder.getMap().entrySet().iterator();

                while (it.hasNext()) {

                    Map.Entry<Object, LifeSeparateListener> entry = it.next();

                    entry.getValue().onStop(entry.getKey());
                }
            }
        }

        public void onDestroy() {
            if(holder.size() > 0) {
                Iterator<Map.Entry<Object, LifeSeparateListener>> it = holder.getMap().entrySet().iterator();

                while (it.hasNext()) {

                    Map.Entry<Object, LifeSeparateListener> entry = it.next();

                    entry.getValue().onDestroy(entry.getKey());
                }
            }
        }

    }

    public static class LifeHolder {

        private Map<Object, LifeSeparateListener> objs = new LinkedHashMap<Object, LifeSeparateListener>();

        public int size() {
            return objs.size();
        }

        public Map<Object, LifeSeparateListener> getMap() {
            return objs;
        }

        public void add(Object obj, LifeSeparateListener listener) {
            objs.put(obj, listener);
        }

        public synchronized void remove(Object obj) {
            objs.remove(obj);
        }

        public synchronized void clear() {
            objs.clear();
        }
    }

    public interface LifeSeparateListener<T> {
        void onCreate(T t);
        void onStart(T t);
        void onResume(T t);
        void onPause(T t);
        void onStop(T t);
        void onDestroy(T t);
    }

    public static class LifeSeparateWrap<T> implements LifeSeparateListener<T> {

        @Override
        public void onCreate(T t) {

        }

        @Override
        public void onStart(T t) {

        }

        @Override
        public void onResume(T t) {

        }

        @Override
        public void onPause(T t) {

        }

        @Override
        public void onStop(T t) {

        }

        @Override
        public void onDestroy(T t) {

        }
    }


}
