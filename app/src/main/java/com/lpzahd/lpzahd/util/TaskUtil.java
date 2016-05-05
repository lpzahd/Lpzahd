package com.lpzahd.lpzahd.util;

import android.app.Application;

import com.lpzahd.lpzahd.app.App;

import org.xutils.common.task.AbsTask;
import org.xutils.x;

/**
 * Created by Administrator on 2016/5/6.
 */
public class TaskUtil {

    private static Application context;
    private static TaskUtil task = new TaskUtil();

    static {
        context = App.app;
    }

    private TaskUtil(){}

    public static TaskUtil task() {
        return task;
    }

    public void post(Runnable runnable) {
        x.task().post(runnable);
    }

    public void autoPost(Runnable runnable) {
        x.task().autoPost(runnable);
    }

    public void postDelayed(Runnable runnable, long delayMillis) {
        x.task().postDelayed(runnable, delayMillis);
    }

    public void run(Runnable runnable) {
        x.task().run(runnable);
    }

    public void removeCallbacks(Runnable runnable) {
        x.task().removeCallbacks(runnable);
    }


}
