package com.study.longl.myselfviewdemo.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.InputFilter;
import android.util.AttributeSet;

import com.study.longl.myselfviewdemo.R;

/**
 * Created by longl on 2018/10/22.
 * 小黑点密码输入框view，边框为弧线
 */
public class PayPasswordView extends android.support.v7.widget.AppCompatEditText {
    private int circleMount = 6;             //小圆点数量为6个
    private RectF storkRect = new RectF();   //边框矩形

    private Paint storkPaint;          //边框画笔
    private Paint circlePaint;         //小圆点画笔

    private Paint divideLinePaint;     //分割线画笔

    private int divideLineStartX;

    private int height;
    private int width;
    private int textLength;

    private int startX;
    private int startY;
    private boolean isFirst = true;

    private int radius = 20;

    public PayPasswordView(Context context) {
        super(context);
    }

    public PayPasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttr(attrs);
        initPaint();
        this.setBackgroundColor(Color.TRANSPARENT); //设置背景透明
        this.setCursorVisible(false);               //设置光标不可见
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(circleMount)});
    }

    public void getAttr(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PayPasswordInputView);
        typedArray.getInteger(R.styleable.PayPasswordInputView_maxCount, circleMount);
        typedArray.recycle();
    }

    public void initPaint() {
        storkPaint = getPaint(3f, Paint.Style.STROKE, Color.BLACK);
        circlePaint = getPaint(10, Paint.Style.FILL, Color.BLACK);
        divideLinePaint = getPaint(1.5f, Paint.Style.FILL, Color.BLACK);
    }

    private Paint getPaint(float storkWidth, Paint.Style style, int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(storkWidth);
        paint.setStyle(style);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(color);
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(storkRect, 20, 20, storkPaint);  //画边框
        for (int i = 1; i < circleMount; i++) {
            //画分割线
            canvas.drawLine(i * divideLineStartX, 0, i * divideLineStartX, height, divideLinePaint);
        }
        for (int i = 0; i < textLength; i++) {
            canvas.drawCircle(startX + i * 2 * startX, startY, radius, circlePaint);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        textLength = text.toString().length();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;
        divideLineStartX = w / circleMount;
        startX = w / circleMount / 2;
        startY = h / 2;
        storkRect.set(1.5f, 1.5f, width - 1.5f, height - 1.5f);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if (selEnd == selStart) {
            setSelection(getText().length());
        }
    }
}
