package com.chenshixin.zhihudaily.task;

import android.os.AsyncTask;

import com.chenshixin.zhihudaily.db.StoryTable;
import com.chenshixin.zhihudaily.db.ZhihuDbHelper;
import com.chenshixin.zhihudaily.model.Story;
import com.chenshixin.zhihudaily.network.ApiManager;
import com.chenshixin.zhihudaily.util.StoryUtil;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 离线下载任务
 * Created by chenshixin on 15/10/1.
 */
public class OfflineTask extends AsyncTask<Long, Integer, Boolean> {

    private ZhihuDbHelper mDbHelper;

    public void setDbHelper(ZhihuDbHelper dbHelper) {
        mDbHelper = dbHelper;
    }

    @Override
    protected Boolean doInBackground(Long... params) {
        if (params == null) {
            return true;
        }
        for (int i = 0; i < params.length; i++) {
            ApiManager.getService().getStory(params[i], new Callback<Story>() {
                @Override
                public void success(Story story, Response response) {
                    String onlineContent = StoryUtil.formatOnlineContent(story.getBody());
                    story.setBody(onlineContent);
                    new StoryTable(mDbHelper).insert(story);
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
        return false;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Boolean isSucceed) {
        super.onPostExecute(isSucceed);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

}
