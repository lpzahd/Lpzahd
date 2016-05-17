package com.lpzahd.lpzahd.activity.test;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lpzahd.lpzahd.R;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);
    }

    public void hello(View v) {
        Observable<String> myObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Log.e("hit", "call : " + subscriber);
                subscriber.onNext("Hello, world!");
                subscriber.onNext("Hello, world2!");
                subscriber.onNext("Hello, world3!");
                subscriber.onCompleted();
            }
        });

        Subscriber<String> subscriber = new Subscriber<String>() {

            @Override
            public void onCompleted() {
                Log.e("hit", "onCompleted : ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("hit", "onError : " + e);
            }

            @Override
            public void onNext(String s) {
                Log.e("hit", "onNext : " + s);
            }
        };

        myObservable.subscribe(subscriber);
    }

    public void simpleHello(View v) {
        Observable<String> observable = Observable.just("hello world@");
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.e("hit", "onNext : " + s);
            }
        };
        observable.subscribe(subscriber);
    }

    public void moreSimpleHello(View v) {
        Observable.just("hello world hahaha").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e("hit", "s : " + s) ;
            }
        });
    }

    public void rxMap(View v) {
        Observable.just("hello hack").map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return s + " - end";
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e("hit", "s : " + s) ;
            }
        });
    }

    public void rxMap2(View v) {
        Observable.just("hello hack").map(new Func1<String, Integer>() {
            @Override
            public Integer call(String s) {
                return 1;
            }
        }).map(new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) {
                return integer + 2;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.e("hit", "int : " + integer);
            }
        });
    }

    public void fore(View v) {
        String[] strs =  new String[]{"a","b"};
        Observable.from(strs).subscribe(new Action1<String>() {
           @Override
           public void call(String s) {
               Log.e("hit", "s : " + s);
           }
        });
    }

    public void flatMap(View v) {
        Observable.just(new String[]{"a", "c", "e"}).flatMap(new Func1<String[], Observable<String>>() {
            @Override
            public Observable<String> call(String[] strings) {
                return Observable.from(strings);
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e("hit", "s : " + s);
            }
        });
    }

    public void thread(View v) {
        Observable.just(1)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        SystemClock.sleep(5000);
                        return 3 + "";
                    }
                })

                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e("hit", "thread : " + Thread.currentThread().getName()+"   s : " + s);
                    }
                });

    }


    public void threadControl(View v) {
        Observable.just("lpzahd")
                .observeOn(Schedulers.io())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        Log.e("hit", s + "   1 : " + Thread.currentThread().getName());
                        SystemClock.sleep(2000);
                        return s + "1";
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        Log.e("hit", s + "  2 : " + Thread.currentThread().getName());
                        return "2 : " + s;
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        Log.e("hit", s + "  3 : " + Thread.currentThread().getName());
                        return "3 : " + s;
                    }
                })
                .observeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("hit", "4 : " + Thread.currentThread().getName());
                    }
                })
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e("hit", "end    r : " + Thread.currentThread().getName());
                        Log.e("hit", "end    s : " + s);
                    }
                });
    }

    public void demo(View v) {
        Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> subscriber) {
                    Log.e("hit", "1 thread : " + Thread.currentThread().getName());
                    subscriber.onNext("Hello, world!");
                 }
                })
            .subscribeOn(Schedulers.io())
            .doOnSubscribe(new Action0() {
                @Override
                public void call() {
                    Log.e("hit", "2 thread : " + Thread.currentThread().getName());
                     // 需要在主线程执行
                }
            })
            .subscribeOn(AndroidSchedulers.mainThread())

            .observeOn(AndroidSchedulers.mainThread()) // 指定主线程
            .subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    Log.e("hit", "3 thread : " + Thread.currentThread().getName());
                }
            });
    }

   private void fun() {

   }
}
