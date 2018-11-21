package com.study.longl.myselfviewdemo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.study.longl.myselfviewdemo.AutoScrollView.AutoIndicatorScrollViewPager;
import com.study.longl.myselfviewdemo.RefreshRecyclerView.MyRefreshRecyclerView;
import com.study.longl.myselfviewdemo.RefreshRecyclerView.MyRefreshRecyclerView2;
import com.study.longl.myselfviewdemo.RefreshRecyclerView.NormalAdapter;
import com.study.longl.myselfviewdemo.RefreshRecyclerView.RefreshableView;
import com.study.longl.myselfviewdemo.Views.MyFocusView;
import com.study.longl.myselfviewdemo.Views.PayPasswordView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private MyFocusView mFocusView;
    private AutoIndicatorScrollViewPager autoScrollViewPager;
    private int ima[] = {R.drawable.image1, R.drawable.image4, R.drawable.image5, R.drawable.image6};
    //    private int ima[] = {};
    String[] items = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R"};
    private boolean isNotBottom = false;                            //是否滑动到底部
    private static final String TAG = "MainActivity";
    private ArrayList<String> data = new ArrayList<>();
    private NormalAdapter normalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtil.setStatusColor(MainActivity.this, false, false, R.color.default_color_one);
        setContentView(R.layout.activity_main);

        /*侧滑RecyclerView*/
//        findViewById(R.id.bt_my_recycler_view).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, MyRecyclerViewActivity.class));
//            }
//        });
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
        /*水波纹扩散控件*/
//        MyWaveView myWaveView = findViewById(R.id.wv_my_test);
//        myWaveView.start();
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
//        WaterWaveView waterWaveView = findViewById(R.id.wwv_test);
//        waterWaveView.start();
        /*自定义轮播图*/
//        autoScrollViewPager = findViewById(R.id.asv_test);
//        AutoScrollViewAdapter autoScrollViewAdapter = new AutoScrollViewAdapter(this, ima);
//        autoScrollViewPager.setAdapter(autoScrollViewAdapter);
//        autoScrollViewPager.start();

        /*下拉刷新RecyclerView*/
        for (int i = 0; i < 20; i++) {
            data.add("content" + i);
        }
        final MyRefreshRecyclerView2 refreshableView = findViewById(R.id.refreshable_view);
        RecyclerView listView = findViewById(R.id.list_view);
        listView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        normalAdapter = new NormalAdapter(data);
        listView.setAdapter(normalAdapter);
        normalAdapter.setOnItemClickListener(new NormalAdapter.onItemClickListener() {
            @Override
            public void onItemClickListener(View view) {
                Toast.makeText(getApplicationContext(), "测试2", Toast.LENGTH_SHORT).show();
            }
        });
        listView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItemPosition;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!isNotBottom) {
                    if (data.size() < 30) {
                        normalAdapter.setCurrentStatus(NormalAdapter.STATUS_LOADING);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < 10; i++) {
                                    data.add("content" + i + 20);
                                }
                                normalAdapter.setCurrentStatus(NormalAdapter.STATUS_FINISH);
                            }
                        }, 1000);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isNotBottom = recyclerView.canScrollVertically(1);
//                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
            }
        });
    }
}
