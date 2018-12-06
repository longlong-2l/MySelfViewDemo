package com.study.longl.myselfviewdemo.AuditProgress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.study.longl.myselfviewdemo.R;

import java.util.ArrayList;

/**
 * Created by longl on 2018/12/5.
 * 自定义进度流程展示控件
 * 重封装，易用性，可扩展性
 */

public class AuditProgressViewTwo extends View {

    private int allCount;           //所有进度数目，比如5个
    private int currentPosition;    //当前进度所在位置，比如在第3个进度
    private int lineCompleteColor;

    private TextPaint textPaint;    //写文字的画笔
    private Paint bitmapPaint;      //画图片的画笔
    private Paint linePaint;        //画线的画笔

    private Rect textRect;        //承接文字宽度的矩形
    private RectF viewRectF;        //整个view的矩形

    private int completeColor = Color.parseColor("#FF0000");      //完成颜色，主要用于文字
    private int notCompleteColor = Color.parseColor("#757575");   //未完成颜色，主要用于文字

    private ArrayList<Bitmap> completeBitmap = new ArrayList<>();        //完成的Bitmap
    private ArrayList<Bitmap> notCompleteBitmap = new ArrayList<>();     //未完成的Bitmap

    private int notCompleteImageArray[];
    private int completeImageArray[];
    private String text[];

    public AuditProgressViewTwo(Context context) {
        this(context, null);
    }

    public AuditProgressViewTwo(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AuditProgressViewTwo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AuditProgressViewTwo);
        allCount = typedArray.getInteger(R.styleable.AuditProgressViewTwo_all_count, 5);
        currentPosition = typedArray.getInteger(R.styleable.AuditProgressViewTwo_current_position, 4);
        completeColor = typedArray.getInteger(R.styleable.AuditProgressViewTwo_complete_color, Color.BLACK);
        notCompleteColor = typedArray.getInteger(R.styleable.AuditProgressViewTwo_not_complete_color, Color.GRAY);
        lineCompleteColor = typedArray.getInteger(R.styleable.AuditProgressViewTwo_line_complete_color, Color.BLACK);
        typedArray.recycle();
        if (completeImageArray != null) {
            if (allCount > completeImageArray.length) {
                throw new RuntimeException(getClass().getName() + "资源数组图标个数不能小于全部流程个数");
            }
        }
        textRect = new Rect();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewRectF = new RectF(getPaddingLeft(), getPaddingTop(), getRight() - getLeft() - getPaddingRight(), getBottom() - getTop() - getPaddingBottom());

        textPaint = new TextPaint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        textPaint.setColor(notCompleteColor);
        textPaint.setTextSize(30);

        bitmapPaint = new Paint();
        bitmapPaint.setStyle(Paint.Style.FILL);
        bitmapPaint.setAntiAlias(true);

        linePaint = new Paint();
        linePaint.setColor(completeColor);
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setStrokeWidth(2.0f);

        completeBitmap.clear();
        notCompleteBitmap.clear();
        if (completeImageArray != null && notCompleteImageArray != null) {
            for (int i = 0; i < completeImageArray.length; i++) {
                completeBitmap.add(i, BitmapFactory.decodeResource(getResources(), completeImageArray[i]));
            }
            for (int i = 0; i < notCompleteImageArray.length; i++) {
                notCompleteBitmap.add(i, BitmapFactory.decodeResource(getResources(), notCompleteImageArray[i]));
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (completeImageArray != null && notCompleteImageArray != null) {
            for (int i = 0; i < allCount; i++) {
                float smallRectWidth = viewRectF.width() / allCount;
                int bitmapWidthHalf;
                float startY = 10f;
                if (i < currentPosition) {
                    bitmapWidthHalf = completeBitmap.get(i).getWidth() / 2;
                    canvas.drawBitmap(completeBitmap.get(i), (i * smallRectWidth + smallRectWidth / 2) - bitmapWidthHalf, getPaddingTop(), bitmapPaint);
                    linePaint.setColor(lineCompleteColor);
                    textPaint.setColor(completeColor);
                    if (i < allCount - 1) {
                        startY = notCompleteBitmap.get(i).getHeight() / 2 + getPaddingTop();
                    }
                } else {
                    bitmapWidthHalf = notCompleteBitmap.get(i).getWidth() / 2;
                    canvas.drawBitmap(notCompleteBitmap.get(i), (i * smallRectWidth + smallRectWidth / 2) - bitmapWidthHalf, getPaddingTop(), bitmapPaint);
                    linePaint.setColor(notCompleteColor);
                    textPaint.setColor(notCompleteColor);
                    if (i < allCount - 1) {
                        startY = notCompleteBitmap.get(i).getHeight() / 2 + getPaddingTop();
                    }
                }

                if (i < allCount - 1) {
                    float startX = (i * smallRectWidth + smallRectWidth / 2) + bitmapWidthHalf;
                    float stopX = ((i + 1) * smallRectWidth + smallRectWidth / 2) - bitmapWidthHalf;
                    canvas.drawLine(startX, startY, stopX, startY, linePaint);
                }

                //写文字部分
                textPaint.getTextBounds(text[i], 0, text[i].length(), textRect);
                int textWidthHalf = textRect.width() / 2;
                float x = (i * smallRectWidth + smallRectWidth / 2) - textWidthHalf;
                float y = completeBitmap.get(i).getHeight() + getPaddingTop() + 70;
                canvas.drawText(text[i], x, y, textPaint);
            }
        }
    }

    /**
     * 设置完成进度的图片数组
     *
     * @param complete 数据源
     */
    public void setCompleteImageSource(int[] complete) {
        this.completeImageArray = complete;
    }

    /**
     * 设置未完成进度的图片数组
     *
     * @param notComplete 数据源
     */
    public void setNotCompleteImageSource(int[] notComplete) {
        this.notCompleteImageArray = notComplete;
    }

    /**
     * 设置文字描述数组
     *
     * @param textSource 数据源
     */
    public void setTextSource(String[] textSource) {
        this.text = textSource;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
        invalidate();
    }

    public void build() {
        invalidate();
    }
}
