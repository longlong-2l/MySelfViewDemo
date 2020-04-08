package com.study.longl.myselfviewdemo.Views.radiusview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * creator: li long on 2020/4/7 7:00 PM
 * description(please write):
 * participant:
 */
public class RadiusTextView extends TextView {
    private RadiusViewDelegate delegate;

    public RadiusTextView(Context context) {
        this(context, null);
    }

    public RadiusTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadiusTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.delegate = new RadiusViewDelegate(this, context, attrs);
    }

    public RadiusViewDelegate getDelegate() {
        return this.delegate;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        if (this.delegate.getWidthHeightEqualEnable() && this.getWidth() > 0 && this.getHeight() > 0) {
//            int max = Math.max(this.getWidth(), this.getHeight());
//            int measureSpec = MeasureSpec.makeMeasureSpec(max, MeasureSpec.UNSPECIFIED);
//            super.onMeasure(measureSpec, measureSpec);
//        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        }
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//        if (this.delegate.getRadiusHalfHeightEnable()) {
//            this.delegate.setRadius(this.getHeight() / 2);
//        } else {
            this.delegate.setBgSelector();
//        }
    }
}
