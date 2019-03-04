package com.study.longl.myselfviewdemo.Views.BitmapBomb;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.study.longl.myselfviewdemo.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyBombView extends View {

    private Point mCoo = new Point(300, 400);//坐标系

    private Paint mPaint;//主画笔
    private Bitmap mBitmap;

    private int d = 3;//复刻的像素边长
    private List<Ball> mBalls = new ArrayList<>();//粒子集合
    private ValueAnimator mAnimator;//时间流
    private long mRunTime;//粒子运动时刻

    public MyBombView(Context context) {
        this(context, null);
    }

    public MyBombView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();//初始化
    }

    private void init() {
        //初始化主画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(5);

        //初始化时间流ValueAnimator
        mAnimator = ValueAnimator.ofFloat(0, 1);
        mAnimator.setRepeatCount(-1);
        mAnimator.setDuration(2000);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                MyBombView.this.updateBall();   //更新小球位置
                MyBombView.this.invalidate();
            }
        });

        //加载图片数组
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        bitmap2Ball(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(mCoo.x, mCoo.y);

        for (Ball ball : mBalls) {
            mPaint.setColor(ball.color);
            canvas.drawRect(ball.x - d / 2, ball.y - d / 2, ball.x + d / 2, ball.y + d / 2, mPaint);
//            canvas.drawCircle(ball.x, ball.y, d / 2, mPaint);
        }
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mRunTime = System.currentTimeMillis();//记录点击时间
                mAnimator.start();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }


    /**
     * 更新小球
     */
    private void updateBall() {
        for (int i = 0; i < mBalls.size(); i++) {
            Ball ball = mBalls.get(i);
            if (System.currentTimeMillis() - mRunTime > 2000) {
                mBalls.remove(i);
            }
            ball.x += ball.vX;
            ball.y += ball.vY;
            ball.vY += ball.aY;
            ball.vX += ball.aX;
        }
    }

    /**
     * 根像素初始化粒子
     *
     * @param bitmap bitmap
     */
    private void initBall(Bitmap bitmap) {
        for (int i = 0; i < bitmap.getWidth(); i++) {
            for (int j = 0; j < bitmap.getHeight(); j++) {
                Ball ball = new Ball();  //产生粒子-每个粒子拥有随机的一些属性信息
                ball.x = i * 4;
                ball.y = j * 4;
                ball.vX = (float) (Math.pow(-1, Math.ceil(Math.random() * 1001)) * 20 * Math.random());
                ball.vY = rangeInt(-15, 35);
                ball.aX = 0.98f;
                ball.color = bitmap.getPixel(i, j);
                ball.born = System.currentTimeMillis();
                mBalls.add(ball);
            }
        }
    }

    /**
     * 获取范围随机整数：如 rangeInt(1,9)
     *
     * @param s 前数(包括)
     * @param e 后数(包括)
     * @return 范围随机整数
     */
    public static int rangeInt(int s, int e) {
        int max = Math.max(s, e);
        int min = Math.min(s, e) - 1;
        return (int) (min + Math.ceil(Math.random() * (max - min)));
    }

    /**
     * 将一个图片粒子化
     *
     * @param bitmap bitmap
     */
    public void bitmap2Ball(Bitmap bitmap) {
        for (int i = 0; i < bitmap.getWidth(); i++) {
            for (int j = 0; j < bitmap.getHeight(); j++) {
                int pixel = bitmap.getPixel(i, j);
                if (pixel < 0) {//此处过滤掉其他颜色，避免全部产生粒子
                    Ball ball = new Ball();//产生粒子---每个粒子拥有随机的一些属性信息
                    ball.vX = (float) (Math.pow(-1, Math.ceil(Math.random() * 1000)) * 20 * Math.random());
                    ball.vY = rangeInt(-15, 35);
                    ball.aY = 0.98f;
                    ball.x = i * 4;
                    ball.y = j * 4;
                    ball.color = pixel;
                    ball.born = System.currentTimeMillis();
                    mBalls.add(ball);
                }
            }
        }
    }

    /**
     * 保存bitmap到本地
     *
     * @param path    路径
     * @param mBitmap 图片
     * @return 路径
     */
    public static String saveBitmap(String path, Bitmap mBitmap) {

        File file = new File(path);
        if (file.isDirectory()) {
            return null;
        }

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return file.getAbsolutePath();
    }
}
