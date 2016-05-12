package com.lpzahd.lpzahd.activity.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lpzahd.lpzahd.R;
import com.lpzahd.lpzahd.activity.base.AppBaseActivity;
import com.lpzahd.lpzahd.widget.chiemy.CardAdapter;
import com.lpzahd.lpzahd.widget.chiemy.CardView;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2016/5/12.
 */
public class GuideActivity extends AppBaseActivity implements CardView.OnCardClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2main);
        initUI();
    }

    private void initUI() {
        CardView cardView = (CardView) findViewById(R.id.cardView1);
        cardView.setOnCardClickListener(this);
        cardView.setItemSpace(Utils.convertDpToPixelInt(this, 20));

        List<GuideBean> lists = new ArrayList<GuideBean>();
        for(int i = 0 ; i < 20 ;  i++) {
            GuideBean bean = new GuideBean();
            bean.title = "tab : " + i;
            lists.add(bean);
        }

        GuideCardAdapter adapter = new GuideCardAdapter(this);
        adapter.addAll(lists);
        cardView.setAdapter(adapter);

//        Main2Activity.MyCardAdapter adapter = new Main2Activity.MyCardAdapter(this);
//        adapter.addAll(initData());
//        cardView.setAdapter(adapter);
//
//        FragmentManager manager = getSupportFragmentManager();
//        frag = new TestFragment();
//        manager.beginTransaction().add(R.id.contentView, frag).commit();
    }

    @Override
    public void onCardClick(View view, int position) {

    }

    public class GuideBean {
        public String title;
        public String getTitle() {
            return title;
        }
    }

    public class GuideCardAdapter extends CardAdapter<GuideBean> {

        private LayoutInflater mInflater;

        public GuideCardAdapter(Context context) {
            this(context, null);
        }

        public GuideCardAdapter(Context context, List<GuideBean> items) {
            super(context, items);

            mInflater = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        protected View getCardView(int position, View convertView, ViewGroup parent) {
            position %= mData.size();

            ViewHolder holder;
            if(convertView == null) {
                convertView =  mInflater.inflate(R.layout.item_guide, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);

                //对于listview，注意添加这一行，即可在item上使用高度
                AutoUtils.autoSize(convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.guideTitle.setText(mData.get(position).title);

            return convertView;
        }
    }

    public class ViewHolder {
        public TextView guideTitle;

        public ViewHolder(View v) {
            guideTitle = (TextView) v.findViewById(R.id.guideTitle);
        }
    }
}
