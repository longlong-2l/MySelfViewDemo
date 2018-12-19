package com.study.longl.myselfviewdemo.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.study.longl.myselfviewdemo.R;

/**
 * Created by longl on 2018/10/22.
 * 聊天页显示图片(传统)，带小三角形的
 */

public class MyIMImageView extends android.support.v7.widget.AppCompatImageView {

    private float mAngleLength = dp2px(20);      //圆弧半径
    private float mArrowTop = dp2px(40);         //箭头距离顶部的位置，有的需要箭头在正中间
    private float mArrowWidth = dp2px(20);       //箭头宽度
    private float mArrowHeight = dp2px(20);      //箭头高度

    private Paint mBitmapPaint;          //画Bitmap画笔
    private Paint mTextPaint;            //写进度的Bitmap
    private String direction;            //方向

    private Bitmap mBitmap;              //资源图片
    private int mBitmapHeight;
    private int mBitmapWidth;
    private BitmapShader mBitmapShader;  //bitmap着色器

    private RectF mBitmapRectF;
    private RectF mShadowRectF = new RectF();
    private Rect mTextRect = new Rect();
    private Path mPath;

    private boolean isShowText;    //是否显示文字
    private boolean isShowShadow;  //是否显示阴影

    private int i = 0;

    private int mProgressPercent = 69;

    public MyIMImageView(Context context) {
        super(context);
    }

    public MyIMImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(attrs);
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MyIMImageView);
        mArrowTop = typedArray.getDimension(R.styleable.MyIMImageView_arrowTop, mArrowTop);
        mArrowWidth = typedArray.getDimension(R.styleable.MyIMImageView_arrowWidth, mArrowWidth);
        mArrowHeight = typedArray.getDimension(R.styleable.MyIMImageView_arrowHeight, mArrowHeight);
        mAngleLength = typedArray.getDimension(R.styleable.MyIMImageView_circular, mAngleLength);
        direction = typedArray.getString(R.styleable.MyIMImageView_direction);
        isShowText = typedArray.getBoolean(R.styleable.MyIMImageView_showText, false);
        isShowShadow = typedArray.getBoolean(R.styleable.MyIMImageView_showShadow, false);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmapPaint != null) {
            canvas.drawPath(mPath, mBitmapPaint);
//            drawShadowAndProgress(canvas, mBitmapRectF);  //画阴影和进度
        }
    }

    private void drawShadowAndProgress(Canvas canvas, RectF rectF) {
        if (isShowShadow) {
            mTextPaint.setAntiAlias(true);
            mTextPaint.setStyle(Paint.Style.FILL);
            mTextPaint.setColor(Color.parseColor("#70000000"));
            if ("right".equals(direction)) {
                mShadowRectF.set(rectF.left, rectF.top, rectF.right - mArrowWidth, rectF.bottom);
            } else {
                mShadowRectF.set(rectF.left + mArrowWidth, rectF.top, rectF.right, rectF.bottom);
            }
            canvas.drawRoundRect(mShadowRectF, mAngleLength / 2, mAngleLength / 2, mTextPaint);
        }
        if (isShowText) {
            mTextPaint.setColor(Color.WHITE);
            mTextPaint.setTextSize(getResources().getDimension(R.dimen.shibadp));
            mTextPaint.getTextBounds(mProgressPercent + "%", 0, (mProgressPercent + "%").length(), mTextRect);
            if ("right".equals(direction)) {
                canvas.drawText(mProgressPercent + "%", (rectF.right - mArrowWidth - mTextRect.width()) / 2, rectF.bottom / 2, mTextPaint);
            } else {
                canvas.drawText(mProgressPercent + "%", (rectF.left + rectF.right - mTextRect.width()) / 2, rectF.bottom / 2, mTextPaint);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmapRectF = new RectF(getPaddingLeft(), getPaddingTop(), getRight()
                - getLeft() - getPaddingRight(), getBottom() - getTop()
                - getPaddingBottom());
        mPath = new Path();
        setBitmap();
        if ("right".equals(direction)) {
            rightPath(mBitmapRectF, mPath);
        } else if ("left".equals(direction)) {
            leftPath(mBitmapRectF, mPath);
        }
    }

    //三角形在有右边
    private void rightPath(RectF rectF, Path path) {
        path.moveTo(rectF.left + mAngleLength, rectF.top);   //path移动到上边直线左点
        path.lineTo(rectF.right - mAngleLength - mArrowWidth, rectF.top);  //划上边线
        //画右上角弧线
        path.arcTo(new RectF(rectF.right - mAngleLength - mArrowWidth, rectF.top, rectF.right - mArrowWidth, mAngleLength + rectF.top), 270, 90);
        path.lineTo(rectF.right - mArrowWidth, rectF.top + mArrowTop);                      //画弧线和三角之间的直线
        path.lineTo(rectF.right, rectF.top + mArrowTop + mArrowHeight / 2);                   //画三角的上边线
        path.lineTo(rectF.right - mArrowWidth, rectF.top + mArrowTop + mArrowHeight);      //画三角的下边线
        path.lineTo(rectF.right - mArrowWidth, rectF.bottom - mAngleLength);   //三角下边的线
        //画右下角弧线
        path.arcTo(new RectF(rectF.right - mAngleLength - mArrowWidth, rectF.bottom - mAngleLength, rectF.right - mArrowWidth, rectF.bottom), 0, 90);
        path.lineTo(rectF.left - mAngleLength, rectF.bottom);  //画底边线
        //画左下角弧线
        path.arcTo(new RectF(rectF.left, rectF.bottom - mAngleLength, mAngleLength + rectF.left, rectF.bottom), 90, 90);
        path.lineTo(rectF.left, rectF.top - mAngleLength);     //画左边线
        //画左上角弧线
        path.arcTo(new RectF(rectF.left, rectF.top, mAngleLength + rectF.left, mAngleLength + rectF.top), 180, 90);
        path.close();
    }

    //三角形在左边
    private void leftPath(RectF rectF, Path path) {
        path.moveTo(rectF.left + mAngleLength + mArrowWidth, rectF.top);   //path移动到上边直线左点
        path.lineTo(rectF.right - mAngleLength, rectF.top);  //划上边线
        //画右上角弧线
        path.arcTo(new RectF(rectF.right - mAngleLength, rectF.top, rectF.right, mAngleLength + rectF.top), 270, 90);
        path.lineTo(rectF.right, rectF.bottom - mAngleLength);
        //画右下角弧线
        path.arcTo(new RectF(rectF.right - mAngleLength, rectF.bottom - mAngleLength, rectF.right, rectF.bottom), 0, 90);
        path.lineTo(rectF.left - mAngleLength - mArrowWidth, rectF.bottom);  //画底边线
        //画左下角弧线
        path.arcTo(new RectF(rectF.left + mArrowWidth, rectF.bottom - mAngleLength, mAngleLength + rectF.left + mArrowWidth, rectF.bottom), 90, 90);
        path.lineTo(rectF.left + mArrowWidth, rectF.top + mArrowTop + mArrowHeight);       //三角下边的线
        path.lineTo(rectF.left, rectF.top + mArrowTop + mArrowHeight / 2);                   //画三角的下边线
        path.lineTo(rectF.left + mArrowWidth, rectF.top + mArrowTop);      //画三角的上边线
        path.lineTo(rectF.left + mArrowWidth, rectF.top + mArrowTop);   //三角上边的线
        //画左上角弧线
        path.arcTo(new RectF(rectF.left + mArrowWidth, rectF.top, mAngleLength + rectF.left + mArrowWidth, mAngleLength + rectF.top), 180, 90);
        path.close();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        mBitmap = bm;
//        setBitmap();
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        mBitmap = getBitmapFromDrawable(drawable);
//        setBitmap();
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        mBitmap = getBitmapFromDrawable(getDrawable());
//        setBitmap();
    }

    public void setProgressPercent(int percent) {
        this.mProgressPercent = percent;
        postInvalidate();
    }

    //从Drawable中获取bitmap
    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap;
        if (drawable instanceof ColorDrawable) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 设置着色器和Bitmap
     */
    private void setBitmap() {
        mTextPaint = new Paint();
        //设置着色器为拉伸
        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setShader(mBitmapShader);

        mBitmapHeight = mBitmap.getHeight();
        mBitmapWidth = mBitmap.getWidth();
        setMatrix();
//        invalidate();
    }

    /**
     * 设置矩阵，先缩放再位移
     */
    private void setMatrix() {
        float scaleX;
        float scaleY;
        float dx;
        float dy;

        Matrix mShaderMatrix = new Matrix();
        mShaderMatrix.set(null);
        scaleY = (mBitmapRectF.bottom - mBitmapRectF.top) / mBitmapHeight;
        dy = mBitmapRectF.top;
        scaleX = (mBitmapRectF.right - mBitmapRectF.left) / mBitmapWidth;
        dx = mBitmapRectF.left;
        mShaderMatrix.setScale(scaleX, scaleY); //按比例缩放，缩放点为view左上角
        mShaderMatrix.postTranslate(dx, dy);    //位移
        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }

    /**
     * 像素转换 dp转px
     *
     * @param dp dp数值
     * @return px
     */
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }

    public void start() {
        new Runnable() {
            @Override
            public void run() {
                i++;
                setProgressPercent(i);
                postDelayed(this, 1000);
            }
        }.run();
    }

    public void setIsShowText(boolean isShowText) {
        this.isShowText = isShowText;
    }

    public void setIsShowShadow(boolean isShowShadow) {
        this.isShowShadow = isShowShadow;
    }
}
