package com.chenshixin.zhihudaily.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.chenshixin.zhihudaily.R;
import com.chenshixin.zhihudaily.model.Story;
import com.chenshixin.zhihudaily.network.ApiManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class StoryActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "extra_id";
    private static final String EXTRA_IMAGE_URL = "extra_image_url";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        loadImage();
        loadContent();
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
                scheduleStartPostponedTransition(mStoryIV);
            }
        });
    }

    private void loadContent() {
        long id = getIntent().getLongExtra(EXTRA_ID, -1);
        ApiManager.getService().getStory(id, new Callback<Story>() {
            @Override
            public void success(Story story, Response response) {
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void scheduleStartPostponedTransition(final View sharedElement) {
        sharedElement.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onPreDraw() {
                sharedElement.getViewTreeObserver().removeOnPreDrawListener(this);
                startPostponedEnterTransition();
                return true;
            }
        });
    }

}
