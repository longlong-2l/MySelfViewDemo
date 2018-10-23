package com.study.longl.myselfviewdemo.MyRecyclerView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * Created by longl on 2018/10/22.
 * 自定义下拉刷新
 */

public class MySwipeRefreshLayout extends FrameLayout {
    private float mTouchY;
    onRefreshListener onRefreshListener;

    private boolean isFirstVisible = false;
    private float mCurrentY;
    private View mChildView;
    private float offsetY;

    public MySwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public MySwipeRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySwipeRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        mChildView = getChildAt(0);
        if (!isFirstVisible) {
            return false;
        }
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mTouchY = ev.getY();
                mCurrentY = mTouchY;
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                float currentY = ev.getY();
                float dy = currentY - mTouchY;
                if (dy > 0) {
                    return true;
                } else if (dy < 0) {
                    return super.onInterceptTouchEvent(ev);
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isFirstVisible) {
            return super.onTouchEvent(event);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mCurrentY = event.getY();
                float dy = mCurrentY - mTouchY;
                if (mChildView != null) {
                    if (dy > 0) {
                        offsetY = dy / 3;
                        if (dy < getScreenHeight(getContext())) {
                            mChildView.setTranslationY(offsetY);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (offsetY > 150) {
                    onRefreshListener.onRefreshing();
                }
                mChildView.setTranslationY(0);
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setIsFirstVisible(boolean isRefreshing) {
        this.isFirstVisible = isRefreshing;
    }

    public void setOnRefreshingListener(onRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public interface onRefreshListener {
        void onRefreshing();
    }

    public int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
}
