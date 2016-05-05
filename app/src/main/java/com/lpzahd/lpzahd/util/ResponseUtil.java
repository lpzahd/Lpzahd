package com.lpzahd.lpzahd.util;

/**
 * Created by mac-lpzahd on 16/4/7.
 *
 * 限定时间内只响应一次的工具类
 */
public class ResponseUtil {

    // 默认时间
    public static long DEFAULT_TIME = 500;

    // 响应截止
    protected static boolean isOver = true;

    // 计数器
    private static long counter = 0;

    private static long preTime = 0;
    private static long currTime = 0;

    /**
     * 检查是否可以响应
     * @return
     */
    public static boolean checkOver() {
        checkCounter();

        if(counter == 0) {
            isOver = true;
        } else {
            isOver = false;
        }

        return isOver;
    }

    /**
     * 检查counter
     *
     * 不精确检查了，没必要
     */
    static void checkCounter() {
//        currTime = System.nanoTime();
        currTime = System.currentTimeMillis();
        counter = currTime - preTime;
        if(counter >= DEFAULT_TIME) {
            counter = 0;
            preTime = currTime;
        } else if(counter == 0) {
            counter = DEFAULT_TIME;
        }
    }


}
