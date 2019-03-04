package com.study.longl.myselfviewdemo.Views.BitmapBomb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.study.longl.myselfviewdemo.R;

public class BitmapBombActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_bomb);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image3);
        int color = bitmap.getPixel(0, 0);
        TextView textView = findViewById(R.id.text);
        textView.setTextColor(color);
    }
}
