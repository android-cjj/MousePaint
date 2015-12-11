package com.cjj.mousepaint.utils;

/**
 * Created by Administrator on 2015/10/10.
 */
public class VersionUtil {

    public static boolean checkVersionIntMoreThan14()
    {
        if (android.os.Build.VERSION.SDK_INT >= 14) {
            return true;
        }
        return false;
    }

    public static boolean checkVersionIntMoreThan17()
    {
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            return true;
        }
        return false;
    }

    public static boolean checkVersionIntMoreThan19()
    {
        if (android.os.Build.VERSION.SDK_INT > 19) {
            return true;
        }
        return false;
    }

}
