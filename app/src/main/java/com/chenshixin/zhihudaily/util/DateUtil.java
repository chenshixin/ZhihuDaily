package com.chenshixin.zhihudaily.util;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * Created by chenshixin on 15/10/8.
 */
public class DateUtil {

    public static String getBeforeDate(String targetDateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            Date targetDate = simpleDateFormat.parse(targetDateString);
            Date beforeDate = new Date(targetDate.getTime() - DateUtils.DAY_IN_MILLIS);
            return simpleDateFormat.format(beforeDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

}
