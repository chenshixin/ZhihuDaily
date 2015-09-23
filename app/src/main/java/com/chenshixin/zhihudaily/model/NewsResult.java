package com.chenshixin.zhihudaily.model;

import java.util.List;

/**
 * 获取新闻的结果
 * Created by chenshixin on 15/9/22.
 */
public class NewsResult {

    /**
     * date 格式如"20150922"
     */
    private String date;

    private List<Story> stories;

    private List<Story> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public List<Story> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<Story> top_stories) {
        this.top_stories = top_stories;
    }

    @Override
    public String toString() {
        return "NewsResult{" +
                "date='" + date + '\'' +
                ", stories=" + stories +
                ", top_stories=" + top_stories +
                '}';
    }
}
