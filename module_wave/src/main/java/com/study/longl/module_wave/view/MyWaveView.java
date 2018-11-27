package com.study.longl.module_wave.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.study.longl.module_wave.R;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by longl on 2018/10/22.
 * 自定义波纹扩散view
 */

public class MyWaveView extends View {
    private int mInitialRadius;  //初始波纹大小
    private int mMaxRadius;      //最大波纹大小
    private int mOneWaveDuration = 5000;  //一个波纹从创建到消失时间为5秒
    private int mNewWaveSpeed = 750;      //新的波纹创建的速度为750毫秒
    private String text;

    private Rect textBound = new Rect();

    private ArrayList<Circle> mCircleList = new ArrayList<>();
    private Paint mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);  //画圆的画笔
    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);    //写字的画笔

    private Interpolator mInterpolator = new LinearInterpolator();

    private long mLastCreateTime;

    private int waveColor;

    private Runnable mCreateCircle = new Runnable() {
        @Override
        public void run() {
            newCircle();
            postDelayed(mCreateCircle, mNewWaveSpeed);
        }
    };

    public MyWaveView(Context context) {
        this(context, null);
    }

    public MyWaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MyWaveView);
        waveColor = typedArray.getColor(R.styleable.MyWaveView_waveColor, 0);
        text = typedArray.getString(R.styleable.MyWaveView_textContent);
        mOneWaveDuration = typedArray.getInteger(R.styleable.MyWaveView_oneWaveDuration, 5000);
        mNewWaveSpeed = typedArray.getInteger(R.styleable.MyWaveView_newWaveSpeed, 750);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Iterator<Circle> iterator = mCircleList.iterator();

        mTextPaint.setTextSize(getResources().getDimension(R.dimen.textsp));
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.getTextBounds(text, 0, text.length(), textBound);

        mCirclePaint.setColor(waveColor);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setAlpha((int) (255 * 0.3));

        mInitialRadius = getWidth() / 7;
        mMaxRadius = getWidth() / 2;
        while (iterator.hasNext()) {
            Circle circle = iterator.next();
            float radius = circle.getCurrentRadius();
            canvas.drawText(text, getWidth() / 2 - textBound.width() / 2, getHeight() / 2, mTextPaint);
            if (System.currentTimeMillis() - circle.mCreateTime < mOneWaveDuration) {
                mCirclePaint.setAlpha(circle.getAlpha());
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mCirclePaint);
            } else {
                iterator.remove();
            }
            if (mCircleList.size() > 0) {
                postInvalidateDelayed(10);
            }
        }
    }

    private void newCircle() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - mLastCreateTime >= mNewWaveSpeed) {
            Circle circle = new Circle();
            mCircleList.add(circle);
            invalidate();
            mLastCreateTime = currentTime;
        }
    }

    public void start() {
        mCreateCircle.run();
    }

    public class Circle {
        private long mCreateTime;

        Circle() {
            mCreateTime = System.currentTimeMillis();
        }

        public int getAlpha() {
            float percent = (getCurrentRadius() - mInitialRadius) / (mMaxRadius - mInitialRadius);
            return (int) ((int) (255 - mInterpolator.getInterpolation(percent) * 255) * 0.3);
        }

        public float getCurrentRadius() {
            float percent = (System.currentTimeMillis() - mCreateTime) * 1.0f / mOneWaveDuration;
            return mInitialRadius + mInterpolator.getInterpolation(percent) * (mMaxRadius - mInitialRadius);
        }
    }
}
