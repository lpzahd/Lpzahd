package com.lpzahd.lpzahd.activity.base;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.lpzahd.lpzahd.R;

/**
 * Created by Administrator on 2016/5/4.
 */
public class TestSepatateActivity extends SeparateBaseActivity{

    private SimpleHolder holder;

    @Nullable
    @Override
    public LifeHolder initHolder() {
        return new SimpleHolder();
    }

    @Override
    public void onBaseCreate() {
        setContentView(R.layout.activity_test);
        holder = getLifeHolder();
        holder.t1 = (TextView) findViewById(R.id.textView1);
        holder.t2 = (TextView) findViewById(R.id.textView2);
        holder.t3 = (TextView) findViewById(R.id.textView3);
        holder.s1 = "str";
        holder.i1 = 1;

        HolderAdapter adapter = new SimpleHolderAdapter(getLifeSepatate());
        adapter.addAll(holder);
    }

    class SimpleHolder extends LifeHolder {
        public TextView t1;
        public TextView t2;
        public TextView t3;
        public String s1;
        public int i1;


    }
}
