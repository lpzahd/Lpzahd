package com.lpzahd.lpzahd.activity.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.andexert.library.RippleView;
import com.lpzahd.lpzahd.R;
import com.lpzahd.lpzahd.activity.base.AppBaseActivity;
import com.lpzahd.lpzahd.activity.test.DatabindActivity;
import com.lpzahd.lpzahd.activity.test.FrescoActivity;
import com.lpzahd.lpzahd.activity.test.RetrifitActivity;
import com.lpzahd.lpzahd.activity.test.RxActivity;
import com.lpzahd.lpzahd.widget.chiemy.CardAdapter;
import com.lpzahd.lpzahd.widget.chiemy.CardView;
import com.zhy.autolayout.utils.AutoUtils;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/12.
 *
 * 引导页面却不能返回, 做成换种概念
 *  1.选择当前是否最好？（怎么表述自己才能记住？）
 *  2.任务模式？（限制数量，每天给与固定数量的选择）
 */
public class GuideActivity extends AppBaseActivity implements View.OnClickListener {

    @BindView(R.id.cardview)
    CardView cardView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);

        initUI();
    }

    private void initUI() {
        initCardView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initFab();
    }

    private void initFab() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(fab, "alpha", 0f, 1.0f);
        anim.setDuration(2000);
        anim.start();
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                fab.setOnClickListener(GuideActivity.this);
            }
        });
    }

    private void initCardView() {
//        cardView.setOnCardClickListener(this);
//        cardView.setMaxVisibleCount(4);
        cardView.setItemSpace(Utils.convertDpToPixelInt(this, 20));

        List<GuideBean> lists = new ArrayList<GuideBean>();
        GuideBean rxAndroid = new GuideBean();
        rxAndroid.title = "RxAndroid Test";
        lists.add(rxAndroid);

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
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.fab) {
            cardView.next();
        }
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
            holder.ripple.setOnRippleCompleteListener(new CardClickListener(position));

            return convertView;
        }

    }

    public class CardClickListener implements RippleView.OnRippleCompleteListener {

        private int position;

        public CardClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onComplete(RippleView v) {
            if(position == 0) {
                startActivity(new Intent(GuideActivity.this, RxActivity.class));
            } else if(position == 1) {
                startActivity(new Intent(GuideActivity.this, RetrifitActivity.class));
            } else if(position == 2) {
                startActivity(new Intent(GuideActivity.this, FrescoActivity.class));
            } else if(position == 3) {
                startActivity(new Intent(GuideActivity.this, DatabindActivity.class));
            }
        }
    }

    public class ViewHolder {
        public TextView guideTitle;
        public RippleView ripple;

        public ViewHolder(View v) {
            guideTitle = (TextView) v.findViewById(R.id.guideTitle);
            ripple = (RippleView) v.findViewById(R.id.ripple);
        }
    }
}
