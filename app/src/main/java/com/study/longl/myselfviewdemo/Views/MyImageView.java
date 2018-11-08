package com.study.longl.myselfviewdemo.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by longl on 2018/11/8.
 */

public class MyImageView extends android.support.v7.widget.AppCompatImageView {

    private Paint mCirclePaint;

    public MyImageView(Context context) {
        this(context, null);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(Color.RED);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCirclePaint.setColor(Color.WHITE);
        canvas.drawCircle(getWidth()/2, getHeight()/2, 35, mCirclePaint);
        mCirclePaint.setColor(Color.RED);
        canvas.drawCircle(getWidth()/2, getHeight()/2, 50, mCirclePaint);

    }
}
