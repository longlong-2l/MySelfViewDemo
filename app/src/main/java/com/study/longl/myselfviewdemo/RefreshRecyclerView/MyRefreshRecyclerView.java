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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.study.longl.myselfviewdemo.R;

/**
 * Created by longl on 2018/11/13.
 * 自定义带下拉刷新的RecyclerView
 */

public class MyRefreshRecyclerView extends LinearLayout implements View.OnTouchListener {
    private static final String TAG = "MyRefreshRecyclerView";

    private RecyclerView recyclerView;
    private View headerView;                  //头部view
    private ProgressBar progressBar;          //进度条
    private ImageView headImageView;          //头部图片
    private TextView descriptionView;         //描述
    private int touchSlop;                    //最小滑动判断值，超过这个值才认为滑动了

    private int headerHeight;                 //头部控件的高度
    private MarginLayoutParams headerLayoutParams;
    private boolean isFirst = true;           //是否是第一次加载
    private boolean isCanPull = false;        //是否可以下拉，主要为了防止侵犯RecyclerView的下拉

    private float yDown;

    private final int WANT_TO_PULL = 0;                  //将要去刷新，但还没有，有可能会松手
    private final int CAN_TO_REFRESHING = 1;             //释放可以刷新
    private final int IS_REFRESHING = 2;                 //正在刷新
    private final int REFRESHING_FINISH = 3;             //刷新完成
    private int currentStatus = REFRESHING_FINISH;       //当前状态

    public MyRefreshRecyclerView(Context context) {
        this(context, null);
    }

    /**
     * 构造函数，初始化一些操作
     * 加入头部view，设置垂直属性等
     */
    public MyRefreshRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "MyRefreshRecyclerView: ");
        headerView = LayoutInflater.from(context).inflate(R.layout.view_refresh_header_normal, null, true);
        progressBar = headerView.findViewById(R.id.progress_bar);
//        headImageView = headerView.findViewById(R.id.arrow);
        descriptionView = headerView.findViewById(R.id.description);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setOrientation(VERTICAL);
        addView(headerView, 0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.i(TAG, "onLayout: ");
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
        recyclerView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float downY = 0;
        if (isAbleToPull()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Log.i(TAG, "ACTION_DOWN: ");
                    downY = event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.i(TAG, "ACTION_MOVE: ");
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
                        headerLayoutParams.topMargin = headerHeight;
                        headerView.setLayoutParams(headerLayoutParams);
                    } else if (currentStatus == CAN_TO_REFRESHING) {
                        descriptionView.setText("正在刷新中...");
                        postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                headerLayoutParams.topMargin = headerHeight;
                                headerView.setLayoutParams(headerLayoutParams);
                            }
                        }, 2000);
                    }
                    break;
            }
        }
        return false;
    }

    /**
     * 判断是否可以向下滚动
     *
     * @return 是否可以滚动，为了防止侵犯recyclerView
     * 自己的滚动
     */
    private boolean isAbleToPull() {
        View firstChild = recyclerView.getChildAt(0);
        isCanPull = firstChild != null && firstChild.getTop() == 0;
        Log.i(TAG, "isAbleToPull: " + firstChild.getTop());
//        if (firstChild != null && firstChild.getTop() < 0) {
////            if (layoutParams.topMargin != headerHeight) {
////                layoutParams.topMargin = headerHeight;
////                headerView.setLayoutParams(layoutParams);
////            }
//            isCanPull = false;
//        }
        return isCanPull;
    }
}
