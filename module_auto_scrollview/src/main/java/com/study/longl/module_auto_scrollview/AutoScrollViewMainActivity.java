package com.study.longl.module_auto_scrollview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * 自定义轮播图
 */
public class AutoScrollViewMainActivity extends AppCompatActivity {

    private AutoIndicatorScrollViewPager autoScrollViewPager;
    private int ima[] = {R.drawable.image1, R.drawable.image4, R.drawable.image5, R.drawable.image6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_scroll_view_main);
        autoScrollViewPager = findViewById(R.id.asv_test);
        AutoScrollViewAdapter autoScrollViewAdapter = new AutoScrollViewAdapter(this, ima);
        autoScrollViewPager.setAdapter(autoScrollViewAdapter);
        autoScrollViewPager.start();
    }
}
