package com.chenshixin.zhihudaily.util;

/**
 * 新闻工具
 * Created by chenshixin on 15/10/1.
 */
public class StoryUtil {

    /**
     * 格式化在线浏览的内容，主要是加上样式文件的引用，以及去掉顶部的图片
     * @param orgContent story的Body字符串
     * @return 格式化后的文件，供在线浏览
     */
    public static String formatOnlineContent(String orgContent) {
        return "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"/>"
                + orgContent.replace("<div class=\"img-place-holder\">", "");
    }

}
