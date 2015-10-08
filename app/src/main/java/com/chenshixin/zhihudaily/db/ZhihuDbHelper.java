package com.chenshixin.zhihudaily.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DbHelper
 * Created by chenshixin on 15/9/23.
 */
public class ZhihuDbHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "zhihu_daily.db";

    public static final String TEXT_TYPE = " TEXT";
    public static final String COMMON_SEP = ",";

    public ZhihuDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(StoryTable.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(StoryTable.SQL_DELETE_ENTRIES);
        onCreate(db);
    }


}
