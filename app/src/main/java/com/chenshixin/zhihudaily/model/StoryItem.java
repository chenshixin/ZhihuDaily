package com.chenshixin.zhihudaily.model;

import java.util.Date;
import java.util.List;

/**
 * 新闻列表中的项目
 * Created by chenshixin on 15/9/22.
 */
public class StoryItem {

    /**
     * 图片地址(eg."images": ["http:\/\/pic2.zhimg.com\/6abe5713b16af112138461a139db693d.jpg"])
     */
    private List<String> images;

    private int type;

    private long id;

    private String ga_prefix;

    private String title;

    /**
     * 是否包含多图
     */
    private Boolean multipic;

    private String date;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getMultipic() {
        return multipic;
    }

    public void setMultipic(Boolean multipic) {
        this.multipic = multipic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Story{" +
                "images=" + images +
                ", type=" + type +
                ", id=" + id +
                ", ga_prefix='" + ga_prefix + '\'' +
                ", title='" + title + '\'' +
                ", multipic=" + multipic +
                '}';
    }
}
