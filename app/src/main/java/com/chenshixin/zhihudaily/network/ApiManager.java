package com.chenshixin.zhihudaily.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * 网络请求接口管理
 * Created by chenshixin on 15/9/20.
 */
public class ApiManager {

    public static final int TIMEOUT = 10;

    private static final String API_URL = "http://news-at.zhihu.com/api/4";

    final static Gson sGson =
            new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").serializeNulls().create();

    private static ZhihuNewsService sZhihuNewsService;

    static {
        OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(TIMEOUT, TimeUnit.SECONDS);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setClient(new OkClient(client))
                .setEndpoint(API_URL)
                .setConverter(new GsonConverter(sGson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        sZhihuNewsService = restAdapter.create(ZhihuNewsService.class);
    }

    public static ZhihuNewsService getService() {
        return sZhihuNewsService;
    }

}
