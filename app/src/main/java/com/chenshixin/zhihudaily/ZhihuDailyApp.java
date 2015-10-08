package com.chenshixin.zhihudaily;

import android.app.Application;

import com.chenshixin.zhihudaily.db.ZhihuDbHelper;
import com.facebook.stetho.Stetho;

/**
 * Application
 * Created by chenshixin on 15/9/20.
 */
public class ZhihuDailyApp extends Application {

    private ZhihuDbHelper mZhihuDbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        mZhihuDbHelper = new ZhihuDbHelper(ZhihuDailyApp.this);
        Stetho.initializeWithDefaults(this);
    }

    public ZhihuDbHelper getDbHelper() {
        return mZhihuDbHelper;
    }
}
