package com.chenshixin.zhihudaily.model;

/**
 * 新闻详情
 * Created by chenshixin on 15/9/26.
 */
public class Story {

    /**
     * 新闻内容，是HTML字符串
     */
    private String body;

    /**
     * 新闻标题
     */
    private String title;

    /**
     * 新闻顶部的图片
     */
    private String image;

    /**
     * id
     */
    private long id;

    private String date;

    public String getBody() {
        return body;
    }

    public Story setBody(String body) {
        this.body = body;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Story setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Story setImage(String image) {
        this.image = image;
        return this;
    }

    public long getId() {
        return id;
    }

    public Story setId(long id) {
        this.id = id;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Story setDate(String date) {
        this.date = date;
        return this;
    }

    @Override
    public String toString() {
        return "Story{" +
                "body='" + body + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", id=" + id +
                '}';
    }
}
