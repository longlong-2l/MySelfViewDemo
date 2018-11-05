package com.study.longl.myselfviewdemo.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by longl on 2018/10/29.
 * 自定义水波流动view
 */

public class WaterWaveView extends View {
    private int width;
    private int height;
    private Point startPoint;
    private Path path;
    private Path circlePath;
    private Paint paint;
    private Paint circlePaint;
    private Paint textPaint;

    private int waveHeight = 340;
    private int cycle = (int) (waveHeight / (1.7));

    private int mNewWaveSpeed = 100;
    private int translateX = 40;
    private int progress = 0;

    private boolean isStart;

    public WaterWaveView(Context context) {
        super(context);
    }

    public WaterWaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WaterWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("ffff", "onMeasure: ");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getViewSize(400, widthMeasureSpec);
        height = getViewSize(400, widthMeasureSpec);
    }

    private int getViewSize(int defaultSize, int measureSpec) {
        int viewSize = defaultSize;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                viewSize = defaultSize;
                break;
            case MeasureSpec.AT_MOST:
                viewSize = size;
                break;
            case MeasureSpec.EXACTLY:
                viewSize = size;
                break;
        }
        return viewSize;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i("ffff", "onSizeChanged: ");
//        startPoint = new Point(-cycle * 3, height / 2);
        startPoint = new Point(0, height / 2);
        path = new Path();
        circlePath = new Path();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);

        circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.parseColor("#FF4081"));

        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(32);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        circlePath.addCircle(width / 2, height / 2, width / 2, Path.Direction.CW);
        canvas.clipPath(circlePath);
        canvas.drawPath(circlePath, circlePaint);
        circlePath.reset();

        startPoint.y = (int) (height - (progress / 100.0 * height));
        path.moveTo(startPoint.x, startPoint.y);
        int j = 1;
        for (int i = 1; i <= 8; i++) {
            if (i % 2 == 0) {
                path.quadTo(startPoint.x + (cycle * j), startPoint.y + waveHeight, startPoint.x + (cycle * 2) * i, startPoint.y);
            } else {
                path.quadTo(startPoint.x + (cycle * j), startPoint.y - waveHeight, startPoint.x + (cycle * 2) * i, startPoint.y);
            }
            j += 2;
        }

//        for (int i = 1; i < 3; i++) {
//            path.cubicTo(startPoint.x + (cycle * (4 * i - 3)), startPoint.y - waveHeight, startPoint.x + (cycle * (4 * i - 1)), startPoint.y + waveHeight, startPoint.x + (cycle * (4 * i)), startPoint.y);
//        path.cubicTo(startPoint.x + (cycle), startPoint.y - waveHeight, startPoint.x + (cycle * 2), startPoint.y + waveHeight, startPoint.x + (cycle * 3), startPoint.y);
//        path.cubicTo(startPoint.x + cycle, startPoint.y - waveHeight, startPoint.x + (cycle), startPoint.y + waveHeight, startPoint.x + (cycle * 2), startPoint.y);
//        }

        path.lineTo(width, height);
        path.lineTo(startPoint.x, height);
        path.lineTo(startPoint.x, startPoint.y);
        path.close();
        canvas.drawPath(path, paint);

        canvas.drawText(progress + "%", width / 2, height / 2, textPaint);

        if (progress == 100) {
            progress = 0;
        } else {
            progress++;
        }

        if (startPoint.x + translateX >= 0) {
            startPoint.x = -cycle * 4;
        }
        startPoint.x += translateX;
        path.reset();
//        if (isStart) {
            postInvalidateDelayed(mNewWaveSpeed);
//        }
    }

    public void setProgress(int progress) {
        if (progress > 100 || progress < 0) {
            throw new RuntimeException(getClass().getName() + "进度值须在[0,100]");
        }
        this.progress = progress;
        invalidate();
    }

    //开始动画
    public void start() {
        isStart = true;
    }

    //停止动画
    public void stop() {
        isStart = false;
    }
}
