package com.chenshixin.zhihudaily.util;

import android.annotation.SuppressLint;
import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * Created by chenshixin on 15/10/8.
 */
@SuppressLint("SimpleDateFormat")
public class DateUtil {

    /**
     * 主页标题栏的日期格式
     */
    private static final String MAIN_TITLE_BAR_DATE = "yyyy-MM-dd";

    /**
     * 知乎通用的日期格式
     */
    private static final String COMMON_DATE_FORMAT = "yyyyMMdd";

    /**
     * 获取一天前的日期
     */
    public static String getBeforeDate(String targetDateString) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(COMMON_DATE_FORMAT);
            Date targetDate = simpleDateFormat.parse(targetDateString);
            Date beforeDate = new Date(targetDate.getTime() - DateUtils.DAY_IN_MILLIS);
            return simpleDateFormat.format(beforeDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getMainTitleBarDate(String targetDateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(COMMON_DATE_FORMAT);
        Date targetDate = null;
        try {
            targetDate = simpleDateFormat.parse(targetDateString);
            if(DateUtils.isToday(targetDate.getTime())) {
                return "今天";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        simpleDateFormat = new SimpleDateFormat(MAIN_TITLE_BAR_DATE);
        return simpleDateFormat.format(targetDate);
    }

}
