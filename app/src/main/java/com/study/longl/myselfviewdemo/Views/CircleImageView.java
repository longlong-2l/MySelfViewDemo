package com.study.longl.myselfviewdemo.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;

import com.study.longl.myselfviewdemo.R;

/**
 * Created by longl on 2018/10/22.
 * 圆形图片
 * 1.获取Bitmap
 * 2.设置Bitmap着色器和矩阵，并设置到画笔中
 * 3.画出图片
 */

public class CircleImageView extends android.support.v7.widget.AppCompatImageView {
    private Bitmap mBitmap;
    private RectF mBitmapRect;

    private boolean isNormalImage;
    private boolean isColorImage;
    private boolean isSetText;
    private String textContent;

    private Paint mBitmapPaint;
    private Paint textPaint;
    private Paint colorImagePaint;

    private int colorImageColor;

    private Rect textBound;

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attr) {
        TypedArray array = getContext().obtainStyledAttributes(attr, R.styleable.CircleImageView);
        isSetText = array.getBoolean(R.styleable.CircleImageView_isText, false);
        isColorImage = array.getBoolean(R.styleable.CircleImageView_isColorImage, false);
        isNormalImage = array.getBoolean(R.styleable.CircleImageView_isNormalImage, true);
        textContent = array.getString(R.styleable.CircleImageView_textContent2);
        colorImageColor = array.getColor(R.styleable.CircleImageView_fillColor, Color.WHITE);
        array.recycle();
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        mBitmap = getBitmapFromDrawable(drawable);
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        mBitmap = getBitmapFromDrawable(getDrawable());
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        mBitmap = bm;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmapRect = new RectF(getPaddingLeft(), getPaddingTop(), getRight() - getLeft() - getPaddingRight(), getBottom() - getTop() - getPaddingBottom());
        if (isColorImage) {
            isNormalImage = false;
            if (colorImagePaint == null) {
                colorImagePaint = new Paint();
            }
            colorImagePaint.setAntiAlias(true);
            colorImagePaint.setStyle(Paint.Style.FILL);
            colorImagePaint.setColor(colorImageColor);
        }
        if (isSetText) {
            if (textPaint == null) {
                textPaint = new TextPaint();
            }
            textPaint.setTextSize(getResources().getDimension(R.dimen.text28sp));
            textPaint.setAntiAlias(true);
            textPaint.setStyle(Paint.Style.FILL);
            textPaint.setColor(Color.WHITE);
            textBound = new Rect();
        }
        if (isNormalImage) {
            setBitmap();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmapPaint != null && mBitmap != null) {
            canvas.drawCircle(mBitmapRect.centerX(), mBitmapRect.centerY(), mBitmapRect.width() / 2.0f, mBitmapPaint);
        }
        if (isColorImage) {
            canvas.drawCircle(mBitmapRect.centerX(), mBitmapRect.centerY(), mBitmapRect.width() / 2.0f, colorImagePaint);
        }
        if (isSetText) {
            textPaint.getTextBounds(textContent, 0, textContent.length(), textBound);
            canvas.drawText(textContent, mBitmapRect.centerX() - (textBound.width() / 2), mBitmapRect.centerY() + (textBound.height() / 2), textPaint);
        }
    }

    /**
     * 设置Bitmap图片
     */
    private void setBitmap() {
        if (mBitmap != null) {
            mBitmapPaint = new Paint();
            mBitmapPaint.setAntiAlias(true);
            //着色器设置缩放方式
            BitmapShader bitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mBitmapPaint.setShader(bitmapShader);
            bitmapShader.setLocalMatrix(setMatrix(mBitmap));
        }
    }

    /**
     * 矩阵变换，先缩放再位移
     * @param bitmap bitmap
     * @return Matrix
     */
    private Matrix setMatrix(Bitmap bitmap) {
        float scaleX;
        float scaleY;
        float dx;
        float dy;

        Matrix matrix = new Matrix();
        matrix.set(null);
        scaleX = (mBitmapRect.right - mBitmapRect.left) / bitmap.getWidth();  //width =345   1
        scaleY = (mBitmapRect.bottom - mBitmapRect.top) / bitmap.getHeight(); //height=568  0
        dx = mBitmapRect.left;
        dy = mBitmapRect.top;
        matrix.setScale(scaleX, scaleY); //缩放
        matrix.postTranslate(dx, dy);    //位移
        return matrix;
    }

    /**
     * 从Drawable中获取Bitmap
     *
     * @param drawable drawable
     * @return bitmap
     */
    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap;
        bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
