package com.chenshixin.zhihudaily.db;

import android.content.ContentValues;
import android.database.Cursor;
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

    public static final String COLUMN_NAME_DATE = "formatted_date";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_TITLE + ZhihuDbHelper.TEXT_TYPE + ZhihuDbHelper.COMMON_SEP +
                    COLUMN_NAME_IMAGE + ZhihuDbHelper.TEXT_TYPE + ZhihuDbHelper.COMMON_SEP +
                    COLUMN_NAME_BODY + ZhihuDbHelper.TEXT_TYPE + ZhihuDbHelper.COMMON_SEP +
                    COLUMN_NAME_DATE + ZhihuDbHelper.TEXT_TYPE +
                    " )";

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String WHERE_ID = COLUMN_NAME_ID + "=?";

    private SQLiteDatabase mDatabase;

    public StoryTable(ZhihuDbHelper dbHelper) {
        mDatabase = dbHelper.getWritableDatabase();
    }

    /**
     * 组装Story
     */
    public Story getStoryFromCursor(Cursor cursor) {
        Story story = new Story();
        story.setBody(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_BODY)));
        story.setDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_DATE)));
        story.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_NAME_ID)));
        story.setImage(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_IMAGE)));
        story.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_TITLE)));
        return story;
    }

    /**
     * 插入数据
     */
    public void insert(Story story) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_ID, story.getId());
        values.put(COLUMN_NAME_TITLE, story.getTitle());
        values.put(COLUMN_NAME_IMAGE, story.getImage());
        values.put(COLUMN_NAME_BODY, story.getBody());
        values.put(COLUMN_NAME_DATE, story.getDate());
        mDatabase.insert(TABLE_NAME, null, values);
    }

    /**
     * 根据id查询新闻
     *
     * @param id id
     * @return Story/null
     */
    public Story queryStoryById(long id) {
        Story story = null;
        String[] projection = {
                COLUMN_NAME_ID,
                COLUMN_NAME_TITLE,
                COLUMN_NAME_IMAGE,
                COLUMN_NAME_BODY,
                COLUMN_NAME_DATE
        };
        Cursor cursor = mDatabase.query(
                TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                WHERE_ID,                                // The columns for the WHERE clause
                new String[]{String.valueOf(id)},                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        if (cursor != null && cursor.moveToFirst() && cursor.getCount() == 1) {
            story = getStoryFromCursor(cursor);
        }
        if (cursor != null) {
            cursor.close();
        }
        return story;
    }

}
