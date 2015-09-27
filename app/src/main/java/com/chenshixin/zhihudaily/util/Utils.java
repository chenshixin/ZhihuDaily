package com.chenshixin.zhihudaily.util;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * 工具累
 * Created by chenshixin on 15/9/26.
 */
public class Utils {

    private static DisplayMetrics getDisplayMetrics(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric;
    }

    /**
     * 获取屏幕高度
     *
     * @param activity Activity对象
     * @return 屏幕高度
     */
    public static float getScreenHeight(Activity activity) {
        return getDisplayMetrics(activity).heightPixels;
    }


}
