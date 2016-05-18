package com.lpzahd.lpzahd.activity.test;

import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lpzahd.lpzahd.R;

import java.util.ArrayList;
import java.util.List;

public class NoteActivity extends AppCompatActivity {

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void init(){
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setLogo(R.mipmap.ic_back);
        setSupportActionBar(mToolbar);

        initBar();
        initRecyclerView();
    }

    private void initBar(){
        mCollapsingToolbarLayout.setTitle("Lpzahd");
        mCollapsingToolbarLayout.setContentScrimResource(R.mipmap.ic_back);
        ;
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> data = new ArrayList<String>();
        for (int i=0;i<50;i++) {
            data.add("tab " + i);
        }
        SimpleRecyclerAdapter mRecyclerAdapter = new SimpleRecyclerAdapter(this, data);
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    public class SimpleRecyclerAdapter extends RecyclerView.Adapter<SimpleRecyclerViewHolder> {

        private Context mContext;
        private LayoutInflater mInflater;
        private List<String> mData;

        public SimpleRecyclerAdapter(Context context, List<String> data) {
            mContext = context;
            mInflater = LayoutInflater.from(mContext);
            mData = data;
        }

        @Override
        public SimpleRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = mInflater.inflate(R.layout.item_array,parent,false);
            return new SimpleRecyclerViewHolder(v);
        }

        @Override
        public void onBindViewHolder(SimpleRecyclerViewHolder holder, int position) {
            TextView tv = (TextView) holder.itemView;
            tv.setText(mData.get(position));
//            tv.setTextColor(Color.BLACK);
        }

        @Override
        public int getItemCount() {
            if(mData == null) {
                return 0;
            } else {
                return mData.size();
            }
        }
    }

    public class SimpleRecyclerViewHolder extends RecyclerView.ViewHolder {

        public SimpleRecyclerViewHolder(View itemView) {
            super(itemView);
        }
    }
}
