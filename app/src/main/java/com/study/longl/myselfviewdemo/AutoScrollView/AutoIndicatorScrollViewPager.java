package com.study.longl.myselfviewdemo.AutoScrollView;

import android.content.Context;
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

    public AutoIndicatorScrollViewPager(Context context) {
        this(context, null);
    }

    public AutoIndicatorScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AutoIndicatorScrollViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        mContext = context;
        mAutoScrollViewPager = new AutoScrollViewPager(context);
        addView(mAutoScrollViewPager);
    }

    public void initPointView(int size) {
        mLayout = new LinearLayout(mContext);
        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            params.leftMargin = 8;
            params.gravity = CENTER;
            imageView.setLayoutParams(params);
            if (i == 0) {
                imageView.setBackgroundResource(R.drawable.point_check);
            } else {
                imageView.setBackgroundResource(R.drawable.ponit_normal);
            }
            mLayout.addView(imageView);
        }

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(ALIGN_PARENT_RIGHT);
        layoutParams.setMargins(12, 20, 12, 20);
        mLayout.setLayoutParams(layoutParams);
        addView(mLayout);
    }

    public void updatePointView(int position) {
        int size = mLayout.getChildCount();
        for (int i = 0; i < size; i++) {
            ImageView imageView = (ImageView) mLayout.getChildAt(i);
            if (i == position) {
                imageView.setBackgroundResource(R.drawable.point_check);
            } else {
                imageView.setBackgroundResource(R.drawable.ponit_normal);
            }
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
