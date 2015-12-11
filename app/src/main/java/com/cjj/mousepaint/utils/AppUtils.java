package com.cjj.mousepaint.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * User: huangruimin
 * Date: 2014-12-12
 * Time: 10:34
 * Description:
 */
public class AppUtils {
    public static DisplayMetrics getScreenDisplayMetrics(Context context) {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        return localDisplayMetrics;
    }

    public static int getDrawableIdByName(Context context, String drawableName) {

        return context.getResources().getIdentifier(drawableName, "drawable", context.getPackageName());
    }


}
