package com.cjj.mousepaint.utils;

/**
 * Created by Administrator on 2015/7/10.
 */
public class FastOnClickUtils {
    private static long lastClickTime;

    public static boolean isFastClick800() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static boolean isFastClick100() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 100) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

}
