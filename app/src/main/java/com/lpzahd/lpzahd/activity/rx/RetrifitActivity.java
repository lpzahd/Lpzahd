package com.lpzahd.lpzahd.activity.rx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lpzahd.lpzahd.R;
import com.lpzahd.lpzahd.util.ToastUtil;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RetrifitActivity extends AppCompatActivity {

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
    private static GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrifit);
    }

    public void getInfo(View v) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://gank.io/api/")
                .addConverterFactory(gsonConverterFactory)
                .build();
        RetrofitInterface retrofitInterface =  retrofit.create(RetrofitInterface.class);

        Call<Bean> call = retrofitInterface.getGirl(10, 1);
        Log.e("hit","call : " + call);
        call.enqueue(new Callback<Bean>() {
            @Override
            public void onResponse(Call<Bean> call, Response<Bean> response) {
                Log.e("hit","response : " + response);
                Log.e("hit","response.message : " + response.message());
                Log.e("hit","response.body : " + response.body());
                Log.e("hit","response.body : " + response);
                Log.e("hit","response.code : " + response.code());
                ToastUtil.showNewToast(response.body().toString(), 5000);
            }

            @Override
            public void onFailure(Call<Bean> call, Throwable t) {
                Log.e("hit","onFailure : " + t);
            }
        });
    }

    private interface RetrofitInterface {

        @GET("data/福利/{number}/{page}")
        Call<Bean> getGirl(@Path("number") int number, @Path("page") int page);

    }

    private class Bean {
        public String error;
        public Object results;

        @Override
        public String toString() {
            return "Bean{" +
                    "error='" + error + '\'' +
                    ", results=" + results +
                    '}';
        }
    }

    public void getByRX(View v) {
        Retrofit retrofit = new Retrofit.Builder()
                // 已经默认使用okHttp了
                .client(okHttpClient)
                .baseUrl("http://gank.io/api/")
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        RxInterface rxInterface = retrofit.create(RxInterface.class);
        Observable<Bean> observable = rxInterface.getGirl(10,10);
        observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<Bean>() {
                @Override
                public void onCompleted() {
                    Log.e("hit","thread : " + Thread.currentThread().getName());
                    Log.e("hit","onCompleted : " );
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("hit","thread : " + Thread.currentThread().getName());
                    Log.e("hit","onError : " + e);
                }

                @Override
                public void onNext(Bean bean) {
                    ToastUtil.showToast(bean.toString());
                    Log.e("hit","thread : " + Thread.currentThread().getName());
                    Log.e("hit","onNext : " + bean);
                }
            });
    }

    private interface RxInterface {

        @GET("data/福利/{number}/{page}")
        Observable<Bean> getGirl(@Path("number")int number, @Path("page") int page);
    }
}
