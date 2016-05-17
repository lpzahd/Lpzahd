package com.lpzahd.lpzahd.widget.chiemy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import com.lpzahd.lpzahd.R;

/**
 * 发现问题：
 *      1. 界面没有更新，在伟大的小米机型上
 *  暂时修改方式：
 *      不在复用view，直接重新生成view
 * @param <T>
 */
public abstract class CardAdapter<T> extends BaseAdapter {

    protected final Context mContext;

    protected ArrayList<T> mData;

    public CardAdapter(Context context) {
        this(context, null);
    }

    public CardAdapter(Context context, Collection<? extends T> items) {
        mContext = context;
        if(items != null) {
            mData = new ArrayList<T>(items);
        } else {
            mData = new ArrayList<T>();
        }
    }

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FrameLayout wrapper = (FrameLayout) convertView;
        View cardView;
        View convertedCardView;
        if (wrapper == null) {
            wrapper = new FrameLayout(mContext);
            wrapper.setBackgroundResource(R.drawable.card_background_shadow);
            cardView = getCardView(position, null, wrapper);
            wrapper.addView(cardView);
        } else {
            cardView = wrapper.getChildAt(0);
            convertedCardView = getCardView(position, null, wrapper);

            //要先删除，然后再添加，否则界面不更新
            if(cardView == convertedCardView) {

            } else {
                wrapper.removeView(cardView);
                wrapper.addView(convertedCardView);
            }

        }
        return wrapper;
    }

    protected abstract View getCardView(int position, View convertView, ViewGroup parent);

    public void addAll(List<T> items){
        mData.addAll(items);
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    public Context getContext() {
        return mContext;
    }

    public void clear(){
        if(mData != null){
            mData.clear();
        }
    }
}
