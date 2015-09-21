package com.chenshixin.zhihudaily.model;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 启动页面的图片
 * Created by chenshixin on 15/9/20.
 */
public class StartImage {

    @StringDef({SIZE_MIDDLE, SIZE_HIGH, SIZE_X_HIGH, SIZE_XX_HIGH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface StartImageSize {}

    // 当前支持的启动图尺寸
    public static final String SIZE_MIDDLE = "320*432";
    public static final String SIZE_HIGH = "480*728";
    public static final String SIZE_X_HIGH = "720*1184";
    public static final String SIZE_XX_HIGH = "1080*1776";

    /**
     * 启动图下方附加的文字，主要是一些版权信息
     */
    private String text;

    /**
     * 图片地址
     */
    private String img;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "StartImage{" +
                "text='" + text + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
