package com.study.longl.myselfviewdemo.Views.ByteBeatView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longl on 2018/12/19.
 * 文本跳动自定义View
 */

public class ByteBeatTextView extends AppCompatTextView {

    protected int mHeight;
    protected int mWidth;

    protected CharSequence mNewText;
    protected CharSequence mOldText;

    protected TextPaint mPaint;
    protected TextPaint mOldPaint;

    protected List<Float> gapList = new ArrayList<>();    //新文本每一个字符宽度集合
    protected List<Float> oldGapList = new ArrayList<>(); //旧字符每一个文本宽度集合

    protected float progress;
    protected float mTextSize;

    protected float oldStartX = 0;

    private float charTime = 300;
    private int mostCount = 20;
    private int mTextHeight;

    private List<CharacterDiff> diffs = new ArrayList<>();
    private long duration;
    private ValueAnimator animator;

    public ByteBeatTextView(Context context) {
        this(context, null);
    }

    public ByteBeatTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mNewText = "";
        mOldText = getText();

        setMaxLines(1); //设置最多只能有一行
        setEllipsize(android.text.TextUtils.TruncateAt.END); //末尾多余省略...


        mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mOldPaint = new TextPaint(mPaint);
        mOldPaint.setAntiAlias(true);

        post(new Runnable() {
            @Override
            public void run() {
                mTextSize = getTextSize();
                mWidth = getWidth();
                mHeight = getHeight();
                oldStartX = 0;
                getOldStartX();
            }
        });

        prepareAnimate();

        animator = new ValueAnimator();
        animator.setInterpolator(new AccelerateDecelerateInterpolator()); //加速减速插值器
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                progress = (float) animation.getAnimatedValue();
                Log.i("bbbbbbbb", "onAnimationUpdate: " + progress);
                invalidate();
            }
        });

        int n = mNewText.length();
        n = n <= 0 ? 1 : n;
        duration = (long) (charTime + charTime / mostCount * (n - 1));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("bbbbbbbb", "onDraw: ");
        float startX = getLayout().getLineLeft(0);
        float startY = getBaseline();  //获取基线坐标

        float offset = startX;
        float oldOffset = startY;

        int maxLength = Math.max(mNewText.length(), mOldText.length());
        for (int i = 0; i < maxLength; i++) {
            //旧文本平移动画
            if (i < mOldText.length()) {
                float pp = progress;
                mOldPaint.setTextSize(mTextSize);
                int move = CharacterDiffUtil.needMove(i, diffs);
                if (move != -1) {
                    //如果字符需要移动
                    mOldPaint.setAlpha(255);
                    float p = pp * 2f;
                    p = p > 1 ? 1 : p;
                    float distX = CharacterDiffUtil.getOffset(i, move, p, startX, oldStartX, gapList, oldGapList);
                    canvas.drawText(mOldText.charAt(i) + "", 0, 1, distX, startY, mOldPaint);
                } else {
                    //透明度动画
                    mOldPaint.setAlpha((int) ((1 - pp) * 255));
                    float y = startY - pp * mTextHeight;
//                    float width = mOldPaint.measureText(mOldText.charAt(i) + "");
                    canvas.drawText(mOldText.charAt(i) + "", 0, 1, oldOffset, y, mOldPaint);
                }
                oldOffset += oldGapList.get(i);
            }
            //新文本平移动画
            if (i < mNewText.length()) {
                if (!CharacterDiffUtil.stayHere(i, diffs)) {
                    // 渐显效果 延迟
                    int alpha = (int) (255f / charTime * (progress * duration - charTime * i / mostCount));
                    alpha = alpha > 255 ? 255 : alpha;
                    alpha = alpha < 0 ? 0 : alpha;
                    mPaint.setAlpha(alpha);
                    mPaint.setTextSize(mTextSize);

                    //   float pp = progress * duration / (charTime + charTime / mostCount * (mText.length() - 1));
                    float pp = progress;
                    float y = mTextHeight + startY - pp * mTextHeight;

                    float width = mPaint.measureText(mNewText.charAt(i) + "");
                    canvas.drawText(mNewText.charAt(i) + "", 0, 1, offset + (gapList.get(i) - width) / 2, y, mPaint);

                }
                offset += gapList.get(i);
            }
        }
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    public void animateText(CharSequence text) {
        setText(text);
        mOldText = mNewText;
        mNewText = text;

        prepareAnimate();
        animatePrepare();
        animateStart();
        Log.i("bbbbbbbb", "animateText: ");
    }

    private void animateStart() {
        int n = mNewText.length();
        n = n <= 0 ? 1 : n;
        duration = (long) (charTime + charTime / mostCount * (n - 1));
        animator.cancel();
        animator.setFloatValues(0, 1);
        animator.setDuration(duration);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                getOldStartX();
            }
        });
        animator.start();
    }

    private void getOldStartX() {
        try {
            int layoutDirection = ViewCompat.getLayoutDirection(ByteBeatTextView.this);
            oldStartX = layoutDirection == LAYOUT_DIRECTION_LTR ? getLayout().getLineLeft(0) : getLayout().getLineRight(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void animatePrepare() {
        diffs.clear();
        diffs.addAll(CharacterDiffUtil.diff(mOldText, mNewText));

        Rect bounds = new Rect();
        mPaint.getTextBounds(mNewText.toString(), 0, mNewText.length(), bounds);
        mTextHeight = bounds.height();//获取文本高度
    }

    private void prepareAnimate() {
        //初始化相关数据
        mTextSize = getTextSize();
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(getCurrentTextColor());
        mPaint.setTypeface(getTypeface());

        gapList.clear();
        for (int i = 0; i < mNewText.length(); i++) {
            gapList.add(mPaint.measureText(String.valueOf(mNewText.charAt(i))));
        }
        mOldPaint.setTextSize(mTextSize);
        mOldPaint.setColor(getCurrentTextColor());
        mOldPaint.setTypeface(getTypeface());

        oldGapList.clear();
        for (int i = 0; i < mOldText.length(); i++) {
            oldGapList.add(mOldPaint.measureText(String.valueOf(mOldText.charAt(i))));
        }
    }
}
