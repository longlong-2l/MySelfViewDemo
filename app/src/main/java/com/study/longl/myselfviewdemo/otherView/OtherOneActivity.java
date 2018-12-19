package com.study.longl.myselfviewdemo.otherView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.study.longl.myselfviewdemo.R;
import com.study.longl.myselfviewdemo.Views.BubbleImageView;
import com.study.longl.myselfviewdemo.Views.BubbleVideoView;
import com.study.longl.myselfviewdemo.Views.MyIMImageView;

public class OtherOneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_one);
        MyIMImageView imageView = findViewById(R.id.miv_test);
        BubbleImageView bubbleImageView = findViewById(R.id.id_biv_chatPic);
        BubbleVideoView bubbleVideoView = findViewById(R.id.id_bvv_chatVideo);
        Glide.with(OtherOneActivity.this)
                .load(R.drawable.image3)
                .placeholder(R.drawable.image2)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .dontAnimate()
                .override(100, 100)
                .into(imageView);

        Glide.with(OtherOneActivity.this)
                .load(R.drawable.image2)
                .placeholder(R.drawable.image3)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(100, 100)
                .into(bubbleImageView);

        Glide.with(OtherOneActivity.this)
                .load(R.drawable.image2)
                .placeholder(R.drawable.image3)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(100, 100)
                .into(bubbleVideoView);
    }
}
