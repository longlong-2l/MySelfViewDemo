package com.study.longl.myselfviewdemo.RefreshRecyclerView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by longl on 2018/11/13.
 * RecyclerView下拉刷新，头部View
 */

public class CustomHeaderView extends View {

    private Paint textPaint;
    private Paint drawPaint;

    public CustomHeaderView(Context context) {
        super(context);
    }

    public CustomHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);

        drawPaint = new Paint();
        drawPaint.setColor(Color.WHITE);
        drawPaint.setAntiAlias(true);
        drawPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, 0, 200, drawPaint);
        canvas.drawText("头部信息", getWidth() / 2, getHeight() / 2, textPaint);
    }
}
