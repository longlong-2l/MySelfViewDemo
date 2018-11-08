package com.study.longl.myselfviewdemo.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by longl on 2018/11/8.
 * 屏幕相关工具，如像素转换，屏幕宽高获取，分辨率等
 */

public class ScreenUtil {
    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
