package com.chenshixin.zhihudaily.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.chenshixin.zhihudaily.R;
import com.chenshixin.zhihudaily.ZhihuDailyApp;
import com.chenshixin.zhihudaily.adapter.StoryAdapter;
import com.chenshixin.zhihudaily.listener.OnRecyclerViewScrollListener;
import com.chenshixin.zhihudaily.model.NewsResult;
import com.chenshixin.zhihudaily.model.StoryItem;
import com.chenshixin.zhihudaily.network.ApiManager;
import com.chenshixin.zhihudaily.util.DateUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.srl_main_news)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.rv_main_news)
    RecyclerView mNewsRV;

    private StoryAdapter mAdapter;

    /**
     * 最新数据的日期，格式为"20151008"
     */
    private String lastDate;

    private List<StoryItem> mStoryItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
        loadContent();
    }

    private void initViews() {
        initToolbar();
        initSwipeLayout();
        initNewsRV();
    }

    private void initNewsRV() {
        mAdapter = new StoryAdapter(this);
        mAdapter.setStoryData(mStoryItems);
        mAdapter.setDBHelper(((ZhihuDailyApp) getApplication()).getDbHelper());
        mNewsRV.setHasFixedSize(true);
        mNewsRV.setLayoutManager(new LinearLayoutManager(this));
        mNewsRV.setAdapter(mAdapter);
    }

    private void initSwipeLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mNewsRV.addOnScrollListener(new OnRecyclerViewScrollListener() {
            @Override
            public void onBottom() {
                loadMore();
            }

            @Override
            public void onPositionChanged(int firstVisibleItemPosition) {
                setTitle(DateUtil.getMainTitleBarDate(mStoryItems.get(firstVisibleItemPosition).getDate()));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        loadContent();
    }

    private void loadContent() {
        ApiManager.getService().latestStories(new Callback<NewsResult>() {
            @Override
            public void success(NewsResult newsResult, Response response) {
                Log.d(TAG, newsResult.toString());
                lastDate = newsResult.getDate();
                List<StoryItem> storyItems = newsResult.getStories();
                for (StoryItem storyItem : storyItems) {
                    storyItem.setDate(lastDate);
                }
                mStoryItems = storyItems;
                mAdapter.setStoryData(mStoryItems);
                mAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }


    private void loadMore() {
        lastDate = DateUtil.getBeforeDate(lastDate);
        ApiManager.getService().beforeStories(lastDate, new Callback<NewsResult>() {
            @Override
            public void success(NewsResult newsResult, Response response) {
                List<StoryItem> storyItems = newsResult.getStories();
                for (StoryItem storyItem : storyItems) {
                    storyItem.setDate(lastDate);
                }
                mStoryItems.addAll(storyItems);
                mAdapter.setStoryData(mStoryItems);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {

            }

        });
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

}
