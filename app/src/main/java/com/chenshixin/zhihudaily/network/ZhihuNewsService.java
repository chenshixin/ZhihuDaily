package com.chenshixin.zhihudaily.network;

import android.support.annotation.NonNull;

import com.chenshixin.zhihudaily.model.NewsResult;
import com.chenshixin.zhihudaily.model.StartImage;
import com.chenshixin.zhihudaily.model.Story;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * 网络接口定义
 * Created by chenshixin on 15/9/20.
 */
public interface ZhihuNewsService {

    /**
     * 获取启动图
     *
     * @param size 启动图尺寸，可以在StartImage中找到
     * @param callback callback
     */
    @GET("/start-image/{size}")
    void getStartImage(@Path("size") @StartImage.StartImageSize String size, Callback<StartImage> callback);

    @GET("/stories/latest")
    void latestStories(Callback<NewsResult> callback);

    @GET("/stories/before/{date}")
    void beforeStories(@Path("date") @NonNull String date, Callback<NewsResult> callback);

    @GET("/story/{id}")
    void getStory(@Path("id") long id, Callback<Story> callback);

}