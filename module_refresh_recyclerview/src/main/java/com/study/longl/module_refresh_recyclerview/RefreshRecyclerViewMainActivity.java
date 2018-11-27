package com.study.longl.module_refresh_recyclerview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.study.longl.module_refresh_recyclerview.RefreshRecyclerView.NormalAdapter;

import java.util.ArrayList;

/**
 * 带下拉刷新和上拉加载的recyclerView
 */
public class RefreshRecyclerViewMainActivity extends AppCompatActivity {

    String[] items = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R"};
    private boolean isNotBottom = false;                            //是否滑动到底部
    private static final String TAG = "MainActivity";
    private ArrayList<String> data = new ArrayList<>();
    private NormalAdapter normalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_recycler_view_main);
        setRecyclerView();
    }

    public void setRecyclerView() {
        for (int i = 0; i < 20; i++) {
            data.add("content" + i);
        }
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
                        }, 3000);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isNotBottom = recyclerView.canScrollVertically(1);
                //另一种判断到达底部的方法
//                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
            }
        });
    }
}
