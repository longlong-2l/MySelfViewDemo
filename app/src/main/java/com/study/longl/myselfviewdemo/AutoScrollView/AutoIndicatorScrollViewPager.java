package com.study.longl.myselfviewdemo.AutoScrollView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.study.longl.myselfviewdemo.R;

import static android.view.Gravity.CENTER;

/**
 * Created by longl on 2018/11/8.
 * AutoIndicatorScrollViewPager
 * 带指示器的自动轮播图，
 * RelativeLayout里面包含自定义ViewPager和指示器布局
 */
public class AutoIndicatorScrollViewPager extends RelativeLayout {

    private AutoScrollViewPager mAutoScrollViewPager;
    private Context mContext;
    private LinearLayout mLayout;

    private boolean hasIndicator;               //是否有小圆点
    private int viewpagerChangeSpeed = 2000;   //viewPager轮播速度
    private float indicator_radius = 20;       //小圆点半径
    private float indicator_space = 12;        //小圆点间的间隙
    private int indicator_checked_color;       //小圆点选中时的颜色
    private int indicator_normal_color;        //小圆点正常时的颜色

    public AutoIndicatorScrollViewPager(Context context) {
        this(context, null);
    }

    public AutoIndicatorScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AutoIndicatorScrollViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoIndicatorScrollViewPager);
        hasIndicator = typedArray.getBoolean(R.styleable.AutoIndicatorScrollViewPager_hasIndicator, true);
        viewpagerChangeSpeed = typedArray.getInteger(R.styleable.AutoIndicatorScrollViewPager_viewpager_change_speed, 2000);
        indicator_radius = typedArray.getDimension(R.styleable.AutoIndicatorScrollViewPager_indicator_radius, 20);
        indicator_space = typedArray.getDimension(R.styleable.AutoIndicatorScrollViewPager_indicator_space, 8);
        indicator_checked_color = typedArray.getResourceId(R.styleable.AutoIndicatorScrollViewPager_indicator_checked_color, Color.WHITE);
        indicator_normal_color = typedArray.getResourceId(R.styleable.AutoIndicatorScrollViewPager_indicator_normal_color, Color.GRAY);
        typedArray.recycle();
        mContext = context;
        mAutoScrollViewPager = new AutoScrollViewPager(context);
        mAutoScrollViewPager.setPagerChangeSpeed(viewpagerChangeSpeed);
        addView(mAutoScrollViewPager);
    }

    public void initPointView(int size) {
        mLayout = new LinearLayout(mContext);
        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) indicator_radius, (int) indicator_radius);
            params.leftMargin = (int) indicator_space;
            params.gravity = CENTER;
            imageView.setLayoutParams(params);
            if (i == 0) {
                setIndicatorColor(imageView, 1);
            } else {
                setIndicatorColor(imageView, 0);
            }
            mLayout.addView(imageView);
        }
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(CENTER_HORIZONTAL);
        layoutParams.setMargins(12, 20, 12, 20);
        mLayout.setLayoutParams(layoutParams);
        addView(mLayout);
    }

    public void updatePointView(int position) {
        int size = mLayout.getChildCount();
        for (int i = 0; i < size; i++) {
            ImageView imageView = (ImageView) mLayout.getChildAt(i);
            if (i == position) {
                setIndicatorColor(imageView, 1);
            } else {
                setIndicatorColor(imageView, 0);
            }
        }
    }

    private void setIndicatorColor(ImageView imageView, int type) {
        switch (type) {
            case 1:
                GradientDrawable gradientDrawable = (GradientDrawable) getResources().getDrawable(R.drawable.point_check);
                gradientDrawable.setColor(getResources().getColor(indicator_checked_color));
                imageView.setBackgroundResource(R.drawable.point_check);
                break;
            case 0:
                GradientDrawable gradientDrawable2 = (GradientDrawable) getResources().getDrawable(R.drawable.ponit_normal);
                gradientDrawable2.setColor(getResources().getColor(indicator_normal_color));
                imageView.setBackgroundResource(R.drawable.ponit_normal);
                break;
        }
    }

    public void start() {
        mAutoScrollViewPager.start();
    }

    public void setAdapter(AutoScrollViewAdapter autoScrollViewAdapter) {
        mAutoScrollViewPager.setAdapter(autoScrollViewAdapter);
        mAutoScrollViewPager.init();
    }
}
