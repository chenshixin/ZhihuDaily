package com.chenshixin.zhihudaily.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chenshixin.zhihudaily.R;
import com.chenshixin.zhihudaily.db.StoryTable;
import com.chenshixin.zhihudaily.db.ZhihuDbHelper;
import com.chenshixin.zhihudaily.model.Story;
import com.chenshixin.zhihudaily.model.StoryItem;
import com.chenshixin.zhihudaily.network.ApiManager;
import com.chenshixin.zhihudaily.ui.StoryActivity;
import com.chenshixin.zhihudaily.util.DeviceUtils;
import com.chenshixin.zhihudaily.util.StoryUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 新闻列表适配器
 * Created by chenshixin on 15/9/26.
 */
public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {

    private List<StoryItem> mStoryData;

    private Activity mActivity;

    private int lastAnimatedPosition = 0;

    private StoryTable mStoryTable;

    public StoryAdapter(Activity activity) {
        mActivity = activity;
    }

    public StoryAdapter setStoryData(List<StoryItem> storyData) {
        mStoryData = storyData;
        return this;
    }

    public void setDBHelper(ZhihuDbHelper dbHelper) {
        mStoryTable = new StoryTable(dbHelper);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_news_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final StoryItem storyItem = mStoryData.get(position);
        final List<String> imageUrl = storyItem.getImages();
        holder.mTitleTV.setText(storyItem.getTitle());
        Glide.with(mActivity).load(imageUrl.get(0)).into(holder.mImageIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoryActivity.start(mActivity, storyItem.getId(), holder.mImageIV, imageUrl.get(0));
            }
        });
        runEnterAnimation(holder.itemView, position);
        ApiManager.getService().getStory(storyItem.getId(), new Callback<Story>() {
            @Override
            public void success(final Story story, Response response) {
                String onlineContent = StoryUtil.formatOnlineContent(story.getBody());
                story.setBody(onlineContent);
                story.setDate(storyItem.getDate());
                if (mStoryTable.queryStoryById(story.getId()) == null) {
                    mStoryTable.insert(story);

                }
                Glide.with(mActivity).load(story.getImage()).crossFade(1000).into(holder.mImageIV);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StoryActivity.start(mActivity, story.getId(), holder.mImageIV, story.getImage());
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mStoryData == null ? 0 : mStoryData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_story_list_item_title)
        TextView mTitleTV;

        @Bind(R.id.iv_story_list_item_image)
        ImageView mImageIV;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void runEnterAnimation(View view, int position) {
        if (position >= 5) {
            return;
        }
        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(DeviceUtils.getScreenHeight(mActivity));
            view.animate()
                    .translationY(0)
                    .setStartDelay(100 * position)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(700)
                    .start();
        }
    }

}
