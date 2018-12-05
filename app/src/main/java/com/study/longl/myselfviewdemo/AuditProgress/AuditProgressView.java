package com.study.longl.myselfviewdemo.AuditProgress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.study.longl.myselfviewdemo.R;

import static com.study.longl.myselfviewdemo.utils.ScreenUtil.dp2px;

/**
 * Created by longl on 2018/12/4.
 * 自定义流程进度
 * 可扩展性强，易用性差，原理简单
 * 合适学习
 */

public class AuditProgressView extends View {
    private boolean mIsCurrentComplete;
    private boolean mIsNextComplete;
    private boolean mIsFirstStep;
    private boolean mIsLastStep;
    private int stepCount;
    private String text;

    // 图片距离view顶部的距离
    private int paddingTop;
    private Paint paint;
    private TextPaint textPaint;

    private Bitmap audio_drawBitmap;

    private Point point;

    public AuditProgressView(Context context) {
        this(context, null);
    }

    public AuditProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AuditProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, context);
    }

    private void init(AttributeSet attrs, Context context) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AuditProgressView);
        mIsCurrentComplete = array.getBoolean(R.styleable.AuditProgressView_apv_isCurrentComplete, false);
        mIsNextComplete = array.getBoolean(R.styleable.AuditProgressView_apv_isNextComplete, false);
        mIsFirstStep = array.getBoolean(R.styleable.AuditProgressView_apv_isFirstStep, false);
        mIsLastStep = array.getBoolean(R.styleable.AuditProgressView_apv_isLastStep, false);
        stepCount = array.getInteger(R.styleable.AuditProgressView_apv_stepCount, 2);
        text = array.getString(R.styleable.AuditProgressView_apv_text);
        array.recycle();

        paddingTop = dp2px(getContext(), 22);
        paint = new Paint();
        textPaint = new TextPaint();
        point = new Point();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode != MeasureSpec.EXACTLY) {
            width = getContext().getResources().getDisplayMetrics().widthPixels / stepCount;
        }

        if (heightMode != MeasureSpec.EXACTLY) {
            height = dp2px(getContext(), 90);
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mIsCurrentComplete) {
            audio_drawBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.audit_complete);
        } else {
            audio_drawBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.audit_uncomplete);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        canvas.drawBitmap(audio_drawBitmap, width / 2 - audio_drawBitmap.getWidth() / 2, height / 2 - audio_drawBitmap.getHeight() / 2, paint);

        String mString = text;
        if (mIsCurrentComplete) {
            textPaint.setColor(Color.parseColor("#03A9F5"));
        } else {
            textPaint.setColor(Color.parseColor("#757575"));
        }

        // 绘制多行文字
        textPaint.setStyle(Paint.Style.FILL);

        point.x = width / 2;
        point.y = dp2px(getContext(), 70);
        textPaint.setTextSize(sp2px(getContext(), 14));
        textCenter(mString, textPaint, canvas, point, dp2px(getContext(), 57), Layout.Alignment.ALIGN_CENTER, 1, 0, false);

        // 绘制线条
        paint.setStrokeWidth(dp2px(getContext(), 2));

        // 根据是不是第一个步骤 确定是否有左边线条
        if (!mIsFirstStep) {
            // 左边
            // 根据当前步骤是否完成 来确定左边线条的颜色
            if (mIsCurrentComplete) {
                paint.setColor(Color.parseColor("#03A9F5"));
            } else {
                paint.setColor(Color.parseColor("#E4E4E4"));
            }
            canvas.drawLine(0, height / 2, width / 2 - audio_drawBitmap.getWidth() / 2 - dp2px(getContext(), 8), height / 2, paint);
        }

        // 根据是不是最后的步骤 确定是否有右边线条
        if (!mIsLastStep) {
            // 右边
            // 根据下一个步骤是否完成 来确定右边线条的颜色
            if (mIsNextComplete) {
                paint.setColor(Color.parseColor("#03A9F5"));
            } else {
                paint.setColor(Color.parseColor("#E4E4E4"));
            }
            canvas.drawLine(width / 2 + audio_drawBitmap.getWidth() / 2 + dp2px(getContext(), 8), height / 2, width, height / 2, paint);
        }
    }

    //绘制多行文字
    private void textCenter(String string, TextPaint textPaint, Canvas canvas, Point point, int width, Layout.Alignment align, float spacingmult, float spacingadd, boolean includepad) {
        StaticLayout staticLayout = new StaticLayout(string, textPaint, width, align, spacingmult, spacingadd, includepad);
        canvas.save();
        canvas.translate(-staticLayout.getWidth() / 2 + point.x, -staticLayout.getHeight() / 2 + point.y);
        staticLayout.draw(canvas);
        canvas.restore();
    }

    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px(像素)
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
