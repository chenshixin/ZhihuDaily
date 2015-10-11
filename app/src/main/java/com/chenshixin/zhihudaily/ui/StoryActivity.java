package com.chenshixin.zhihudaily.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.chenshixin.zhihudaily.R;
import com.chenshixin.zhihudaily.ZhihuDailyApp;
import com.chenshixin.zhihudaily.db.StoryTable;
import com.chenshixin.zhihudaily.model.Story;
import com.chenshixin.zhihudaily.network.ApiManager;
import com.chenshixin.zhihudaily.util.StoryUtil;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class StoryActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {

    public static final String EXTRA_ID = "extra_id";
    public static final String EXTRA_IMAGE_URL = "extra_image_url";

    public static void start(Activity activity, long id, ImageView sourceImageView, String imageUrl) {
        Intent intent = new Intent(activity, StoryActivity.class);
        intent.putExtra(EXTRA_ID, id);
        intent.putExtra(EXTRA_IMAGE_URL, imageUrl);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                sourceImageView, activity.getString(R.string.transition_storyImage));
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Bind(R.id.iv_story_image)
    ImageView mStoryIV;

    @Bind(R.id.osv_story_container)
    ObservableScrollView mContainerOSV;

    @Bind(R.id.wv_story_content)
    WebView mWebView;

    @Bind(R.id.view_story_overlay)
    View mOverlayView;

    private int mImageHeight;

    private StoryTable mStoryTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        ButterKnife.bind(this);
        initViews();
        initZhihuDbHelper();
        initData();
    }

    private void initZhihuDbHelper() {
        mStoryTable = new StoryTable(((ZhihuDailyApp) getApplication()).getDbHelper());
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int minOverlayTransitionY = 0 - mOverlayView.getHeight();
        ViewCompat.setTranslationY(mOverlayView, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
        ViewCompat.setTranslationY(mStoryIV, ScrollUtils.getFloat(-scrollY / 2, minOverlayTransitionY, 0));
        ViewCompat.setAlpha(mOverlayView, ScrollUtils.getFloat((float) scrollY / mImageHeight, 0, 1));
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    private void initViews() {
        mImageHeight = getResources().getDimensionPixelSize(R.dimen.story_image_height);
        mContainerOSV.setScrollViewCallbacks(this);
        ScrollUtils.addOnGlobalLayoutListener(mWebView, new Runnable() {
            @Override
            public void run() {
                mWebView.scrollTo(0, mImageHeight);
            }
        });
        initWebView();
    }

    private void initData() {
        loadImage();
        loadContent();
    }

    private void initWebView() {
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
    }

    private void loadImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition();
        }
        String imageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
        Glide.with(StoryActivity.this).load(imageUrl).into(new GlideDrawableImageViewTarget(mStoryIV) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                super.onResourceReady(resource, animation);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    scheduleStartPostponedTransition(mStoryIV);
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void scheduleStartPostponedTransition(final View sharedElement) {
        sharedElement.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                sharedElement.getViewTreeObserver().removeOnPreDrawListener(this);
                startPostponedEnterTransition();
                return true;
            }
        });
    }

    private void loadContent() {
        long id = getIntent().getLongExtra(EXTRA_ID, -1);
        Story story = mStoryTable.queryStoryById(id);
        if (story == null) {
            ApiManager.getService().getStory(id, new Callback<Story>() {
                @Override
                public void success(Story story, Response response) {
                    showContent(story);
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        } else {
            showContent(story);
        }
    }


    private void showContent(Story story) {
        String mNewsContent = StoryUtil.formatOnlineContent(story.getBody());
        mWebView.loadDataWithBaseURL("file:///android_asset/", mNewsContent, "text/html", "UTF-8", null);
    }

}
