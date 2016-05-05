package com.lpzahd.lpzahd.activity.base;

import android.widget.TextView;

/**
 * Created by Administrator on 2016/5/4.
 */
public abstract class HolderAdapter<T extends SeparateBaseActivity.LifeHolder> {

    protected SeparateBaseActivity.LifeSeparate eu;

    private HolderAdapter(){}

    public HolderAdapter(SeparateBaseActivity.LifeSeparate eu) {
        this.eu = eu;
    }

    public abstract void addAll(T holder);
}
