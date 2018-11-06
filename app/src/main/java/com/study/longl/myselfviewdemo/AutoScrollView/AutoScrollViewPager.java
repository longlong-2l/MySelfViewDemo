package com.study.longl.myselfviewdemo.AutoScrollView;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by longl on 2018/11/5.
 * 自定义轮播图
 */

public class AutoScrollViewPager extends ViewPager {
    private boolean isStart;

    public AutoScrollViewPager(Context context) {
        super(context);
    }

    public AutoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (isStart) {
                int position = getCurrentItem();
                if (position >= getAdapter().getCount() - 1) {
                    setCurrentItem(0);
                } else {
                    setCurrentItem(position + 1);
                }
                postDelayed(mRunnable, 1000);
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
}
