package com.study.longl.myselfviewdemo.AutoScrollView;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by longl on 2018/11/5.
 * 自定义轮播图
 */

public class AutoScrollViewPager extends ViewPager {
    private boolean isStart;
    private int currentItem = 0;
    private int pagerChangeSpeed = 2000;
    private int dataNum;

    private AutoIndicatorScrollViewPager autoIndicatorScrollViewPager;

    public AutoScrollViewPager(Context context) {
        this(context, null);
    }

    public AutoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        addOnPageChangeListener(pageChangeListener);
    }

    public void init() {
        dataNum = getAdapter().getCount() - 2;
        autoIndicatorScrollViewPager = (AutoIndicatorScrollViewPager) getParent();
        autoIndicatorScrollViewPager.initPointView(dataNum);
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (isStart) {
                currentItem = currentItem % (dataNum + 1) + 1;
                setCurrentItem(currentItem);
                postDelayed(mRunnable, pagerChangeSpeed);
            }
        }
    };

    /**
     * 对滑动进行处理
     *
     * @param ev 事件
     * @return boolean
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void start() {
        isStart = true;
        mRunnable.run();
    }

    public void stop() {
        isStart = false;
    }

    public void setPagerChangeSpeed(int pagerChangeSpeed) {
        this.pagerChangeSpeed = pagerChangeSpeed;
    }

    private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case ViewPager.SCROLL_STATE_IDLE:
                    // “偷梁换柱”
                    if (getCurrentItem() == dataNum + 1) {
                        setCurrentItem(1, false);
                    }
                    autoIndicatorScrollViewPager.updatePointView(getCurrentItem() - 1);
                    currentItem = getCurrentItem();
                    break;
            }
        }
    };
}
