package com.study.longl.myselfviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.study.longl.myselfviewdemo.AutoScrollView.AutoIndicatorScrollViewPager;
import com.study.longl.myselfviewdemo.RefreshRecyclerView.MyRefreshRecyclerView;
import com.study.longl.myselfviewdemo.RefreshRecyclerView.NormalAdapter;
import com.study.longl.myselfviewdemo.RefreshRecyclerView.RefreshableView;
import com.study.longl.myselfviewdemo.Views.MyFocusView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MyFocusView mFocusView;
    private AutoIndicatorScrollViewPager autoScrollViewPager;
    private int ima[] = {R.drawable.image1, R.drawable.image4, R.drawable.image5, R.drawable.image6};
    //    private int ima[] = {};
    String[] items = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R"};

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
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add("content" + i);
        }
        final MyRefreshRecyclerView refreshableView = findViewById(R.id.refreshable_view);
        RecyclerView listView = findViewById(R.id.list_view);
        listView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listView.setAdapter(new NormalAdapter(data));
//        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
//            @Override
//            public void onRefresh() {
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                refreshableView.finishRefreshing();
//            }
//        }, 0);
    }
}
