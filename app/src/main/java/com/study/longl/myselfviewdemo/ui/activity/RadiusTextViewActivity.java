package com.study.longl.myselfviewdemo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.study.longl.myselfviewdemo.R;

/**
 * creator: li long on 2020/4/7 6:57 PM
 * description(please write):自定义圆角textView,还可以设置背景色
 * participant:
 */
public class RadiusTextViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radius_view);
        TextView view = findViewById(R.id.tv_test);
//        view.setBackground();
    }
}
