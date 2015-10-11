package com.chenshixin.zhihudaily.listener;

/**
 * 滑动到底部监听
 * Created by chenshixin on 15/10/8.
 */
public interface OnBottomListener {

    void onBottom();

    void onPositionChanged(int firstVisibleItemPosition);

}
