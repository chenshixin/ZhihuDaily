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

    private List<StoryItem> stories;

    private List<StoryItem> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoryItem> getStories() {
        return stories;
    }

    public void setStories(List<StoryItem> stories) {
        this.stories = stories;
    }

    public List<StoryItem> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<StoryItem> top_stories) {
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
