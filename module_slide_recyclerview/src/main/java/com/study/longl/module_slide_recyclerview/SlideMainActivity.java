package com.study.longl.module_slide_recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class SlideMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_main);
        RecyclerView mRecyclerView = findViewById(R.id.rv_recycler);
        ArrayList<String> dataList = new ArrayList<>();
        for (int i = 0; i <= 30; i++) {
            dataList.add("好友名称" + i);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(SlideMainActivity.this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(new MyRecyclerViewAdapter(dataList));
    }
}
