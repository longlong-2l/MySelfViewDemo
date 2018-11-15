package com.study.longl.myselfviewdemo.RefreshRecyclerView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.longl.myselfviewdemo.R;

/**
 * Created by longl on 2018/11/13.
 * 自定义带下拉刷新的RecyclerView，
 * 另一种方式处理点击事件，拦截子View的事件
 */

public class MyRefreshRecyclerView2 extends LinearLayout {
    private static final String TAG = "MyRefreshRecyclerView2";
    private RecyclerView recyclerView;
    private View headerView;                  //头部view
    private TextView descriptionView;         //描述
    private int touchSlop;                    //最小滑动判断值，超过这个值才认为滑动了

    private int headerHeight;                 //头部控件的高度
    private MarginLayoutParams headerLayoutParams;
    private boolean isFirst = true;           //是否是第一次加载

    private float downY;

    private final int WANT_TO_PULL = 0;                  //将要去刷新，但还没有，有可能会松手
    private final int CAN_TO_REFRESHING = 1;             //释放可以刷新
    private final int IS_REFRESHING = 2;                 //正在刷新
    private final int REFRESHING_FINISH = 3;             //刷新完成
    private int currentStatus = REFRESHING_FINISH;       //当前状态

    public MyRefreshRecyclerView2(Context context) {
        this(context, null);
    }

    /**
     * 构造函数，初始化一些操作
     * 加入头部view，设置垂直属性等
     */
    public MyRefreshRecyclerView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        headerView = LayoutInflater.from(context).inflate(R.layout.view_refresh_header_normal, null, true);
        descriptionView = headerView.findViewById(R.id.description);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setOrientation(VERTICAL);
        addView(headerView, 0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (isFirst) {
            headerHeight = -headerView.getHeight();
            headerLayoutParams = (MarginLayoutParams) headerView.getLayoutParams();
            headerLayoutParams.topMargin = headerHeight;
            headerView.setLayoutParams(headerLayoutParams);
            isFirst = false;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        recyclerView = (RecyclerView) getChildAt(1);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean isIntercept = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float down_Y = event.getRawY();
                int dis = (int) (down_Y - downY);
                View firstChild = recyclerView.getChildAt(0);
                //当处于顶部位置并且是向下滑动时，拦截事件，不给子View
                if (firstChild != null && firstChild.getTop() == 0 && dis > 0) {
                    isIntercept = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float downY1 = event.getRawY();
                float dis = downY1 - downY;
                if (dis <= 0 && headerLayoutParams.topMargin <= headerHeight) {
                    return false;
                }
                if (dis < touchSlop) {
                    return false;
                }
                if (currentStatus != IS_REFRESHING) {
                    descriptionView.setText("释放立即刷新...");
                    headerLayoutParams.topMargin = (int) (dis / 3 + headerHeight);
                    headerView.setLayoutParams(headerLayoutParams);
                    if (headerLayoutParams.topMargin < 0) {
                        currentStatus = WANT_TO_PULL;
                    } else {
                        currentStatus = CAN_TO_REFRESHING;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (currentStatus == WANT_TO_PULL) {
                    resetHeader();
                } else if (currentStatus == CAN_TO_REFRESHING) {
                    refreshTask();
                }
                break;
        }
        return false;
    }

    /**
     * 重置Header位置，使其回到隐藏状态
     */
    private void resetHeader() {
        headerLayoutParams.topMargin = headerHeight;
        headerView.setLayoutParams(headerLayoutParams);
        currentStatus = REFRESHING_FINISH;
    }

    /**
     * 开始刷新任务，定时结束后重置header
     */
    private void refreshTask() {
        currentStatus = IS_REFRESHING;
        descriptionView.setText("正在刷新中...");
        postDelayed(new Runnable() {
            @Override
            public void run() {
                resetHeader();
            }
        }, 2000);
    }
}
