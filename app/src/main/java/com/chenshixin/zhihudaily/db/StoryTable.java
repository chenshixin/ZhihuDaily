package com.chenshixin.zhihudaily.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.chenshixin.zhihudaily.model.Story;

/**
 * 新闻表
 * Created by chenshixin on 15/10/1.
 */
public class StoryTable extends BasicTable {

    public static final String TABLE_NAME = "stories";

    public static final String COLUMN_NAME_ID = "id";

    public static final String COLUMN_NAME_TITLE = "title";

    public static final String COLUMN_NAME_IMAGE = "image";

    public static final String COLUMN_NAME_BODY = "body";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_TITLE + ZhihuDbHelper.TEXT_TYPE + ZhihuDbHelper.COMMON_SEP +
                    COLUMN_NAME_IMAGE + ZhihuDbHelper.TEXT_TYPE + ZhihuDbHelper.COMMON_SEP +
                    COLUMN_NAME_BODY + ZhihuDbHelper.TEXT_TYPE +
                    " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    private SQLiteDatabase mDatabase;

    public StoryTable(ZhihuDbHelper dbHelper) {
        mDatabase = dbHelper.getWritableDatabase();
    }

    public void insert(Story story) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_ID, story.getId());
        values.put(COLUMN_NAME_TITLE, story.getTitle());
        values.put(COLUMN_NAME_IMAGE, story.getImage());
        values.put(COLUMN_NAME_BODY, story.getBody());
        mDatabase.insert(TABLE_NAME, null, values);
    }

}
