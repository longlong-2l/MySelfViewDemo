package com.study.longl.myselfviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.study.longl.myselfviewdemo.MyRecyclerView.MyRecyclerViewAdapter;
import com.study.longl.myselfviewdemo.MyRecyclerView.MySwipeRefreshLayout;

import java.util.ArrayList;

public class MyRecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recycler_view);
        RecyclerView mRecyclerView = findViewById(R.id.rv_2);
        MySwipeRefreshLayout mMySwipeRefreshLayout = findViewById(R.id.msrl_test);
        ArrayList<String> dataList = new ArrayList<>();
        for (int i = 0; i <= 30; i++) {
            dataList.add("好友名称" + i);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MyRecyclerViewActivity.this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(new MyRecyclerViewAdapter(this, dataList));
        mMySwipeRefreshLayout.setIsFirstVisible(true);
        mMySwipeRefreshLayout.setOnRefreshingListener(new MySwipeRefreshLayout.onRefreshListener() {
            @Override
            public void onRefreshing() {

            }
        });
    }
}
