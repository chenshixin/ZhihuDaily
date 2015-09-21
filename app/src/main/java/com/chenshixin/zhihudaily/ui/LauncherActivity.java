package com.chenshixin.zhihudaily.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chenshixin.zhihudaily.R;
import com.chenshixin.zhihudaily.model.StartImage;
import com.chenshixin.zhihudaily.network.ApiManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 启动页
 *
 */
public class LauncherActivity extends AppCompatActivity {

    public static final String TAG = LauncherActivity.class.getSimpleName();

    @Bind(R.id.iv_start_image)
    ImageView mStartImageIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        ButterKnife.bind(this);
        loadNetworkImage();
    }

    private void loadNetworkImage() {
        ApiManager.getService().getStartImage(StartImage.SIZE_XX_HIGH, new Callback<StartImage>() {
            @Override
            public void success(StartImage startImage, Response response) {
                Glide.with(LauncherActivity.this).load(startImage.getImg()).animate(1000).into(mStartImageIV);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

}
