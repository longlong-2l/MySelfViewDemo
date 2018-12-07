package com.study.longl.myselfviewdemo;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.study.longl.module_auto_scrollview.AutoScrollViewMainActivity;
import com.study.longl.module_refresh_recyclerview.RefreshRecyclerViewMainActivity;
import com.study.longl.module_slide_recyclerview.SlideMainActivity;
import com.study.longl.module_wave.ui.WaveMainActivity;
import com.study.longl.module_wave_water.WaveWaterMainActivity;
import com.study.longl.myselfviewdemo.AuditProgress.AuditProgressActivity;
import com.study.longl.myselfviewdemo.Views.MyFocusView;
import com.study.longl.myselfviewdemo.otherView.OtherOneActivity;
import com.study.longl.soundsendbutton.SoundSendMainActivity;

public class MainActivity extends AppCompatActivity {
    private MyFocusView mFocusView;
    String[] items = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtil.setStatusColor(MainActivity.this, false, false, R.color.default_color_one);
        setContentView(R.layout.activity_main);
        /*对焦控件代码*/
//        mFocusView = findViewById(R.id.fv_my);
//        RelativeLayout ll_root = findViewById(R.id.ll_root);
//        ll_root.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                switch (motionEvent.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        int width = mFocusView.getWidth();
//                        int height = mFocusView.getHeight();
//                        mFocusView.setX(motionEvent.getX() - (width / 2));
//                        mFocusView.setY(motionEvent.getY() - (height / 2));
//                        mFocusView.beginFocus();
//                        break;
//                }
//                return false;
//            }
//        });
        /*密码输入控件*/
//        PayPasswordView mPayPasswordView = findViewById(R.id.ppv_my);
//        mPayPasswordView.requestFocus();
//        mPayPasswordView.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
        /*聊天页图片展示*/
//        MyIMImageView myIMImageView = findViewById(R.id.miv_test);
//        myIMImageView.start();

         /*水波纹加载进度展示*/
        findViewById(R.id.bt_water_wave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WaveWaterMainActivity.class));
            }
        });

        /*下拉刷新RecyclerView*/
        findViewById(R.id.bt_refresh_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RefreshRecyclerViewMainActivity.class));
            }
        });

        /*侧滑RecyclerView*/
        findViewById(R.id.bt_slide_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SlideMainActivity.class));
            }
        });

         /*语音发送按钮*/
        findViewById(R.id.bt_sound_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SoundSendMainActivity.class));
            }
        });

          /*水波纹扩散展示*/
        findViewById(R.id.bt_water_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WaveMainActivity.class));
            }
        });

         /*自定义轮播图*/
        findViewById(R.id.bt_scroll_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AutoScrollViewMainActivity.class));
            }
        });

         /*流程进度展示*/
        findViewById(R.id.bt_audio_progress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AuditProgressActivity.class));
            }
        });

         /*其他一*/
        findViewById(R.id.bt_other_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OtherOneActivity.class));
            }
        });
    }
}
