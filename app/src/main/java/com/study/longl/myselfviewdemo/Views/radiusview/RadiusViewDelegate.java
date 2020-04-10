package com.study.longl.myselfviewdemo.Views.radiusview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.study.longl.myselfviewdemo.R;

/**
 * creator: li long on 2020/4/7 7:00 PM
 * description(please write):
 * participant:
 */
public class RadiusViewDelegate {
    private View view;
    private Context context;
    private GradientDrawable gdBackground = new GradientDrawable();
//    private GradientDrawable gdBackgroundPressed = new GradientDrawable();
//    private GradientDrawable gdBackgroundEnabled = new GradientDrawable();
    private int backgroundColor;
//    private int backgroundPressedColor;
//    private int backgroundEnabledColor;
    private int radius;
    private int topLeftRadius;
    private int topRightRadius;
    private int bottomLeftRadius;
    private int bottomRightRadius;
    private int strokeWidth;
    private int strokeColor;
//    private int strokePressedColor;
//    private int strokeEnabledColor;
    private int textColor;
    private int textPressedColor;
    private int textEnabledColor;
    private boolean isRadiusHalfHeight;
    private boolean isWidthHeightEqual;
    private boolean isRippleEnable;
    private float[] radiusArr = new float[8];

    public RadiusViewDelegate(View view, Context context, AttributeSet attrs) {
        this.view = view;
        this.context = context;
        this.obtainAttributes(context, attrs);
    }

    private void obtainAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.radiusTextView);
        this.backgroundColor = ta.getColor(R.styleable.radiusTextView_rv_backgroundColor, 0);
//        this.backgroundPressedColor = ta.getColor(R.styleable.radiusTextView_rv_backgroundPressedColor, 2147483647);
//        this.backgroundEnabledColor = ta.getColor(R.styleable.radiusTextView_rv_backgroundEnabledColor, 2147483647);
        this.radius = ta.getDimensionPixelSize(R.styleable.radiusTextView_rv_radius, 0);
        this.strokeWidth = ta.getDimensionPixelSize(R.styleable.radiusTextView_rv_strokeWidth, 0);
        this.strokeColor = ta.getColor(R.styleable.radiusTextView_rv_strokeColor, 0);
//        this.strokePressedColor = ta.getColor(R.styleable.radiusTextView_rv_strokePressedColor, 2147483647);
//        this.strokeEnabledColor = ta.getColor(R.styleable.radiusTextView_rv_strokeEnabledColor, 2147483647);
        this.textColor = ta.getColor(R.styleable.radiusTextView_rv_textColor, 2147483647);
        this.textPressedColor = ta.getColor(R.styleable.radiusTextView_rv_textPressedColor, 2147483647);
        this.textEnabledColor = ta.getColor(R.styleable.radiusTextView_rv_textEnabledColor, 2147483647);
//        this.isRadiusHalfHeight = ta.getBoolean(R.styleable.radiusTextView_rv_radiusHalfHeightEnable, false);
//        this.isWidthHeightEqual = ta.getBoolean(R.styleable.radiusTextView_rv_widthHeightEqualEnable, false);
        this.topLeftRadius = ta.getDimensionPixelSize(R.styleable.radiusTextView_rv_topLeftRadius, 0);
        this.topRightRadius = ta.getDimensionPixelSize(R.styleable.radiusTextView_rv_topRightRadius, 0);
        this.bottomLeftRadius = ta.getDimensionPixelSize(R.styleable.radiusTextView_rv_bottomLeftRadius, 0);
        this.bottomRightRadius = ta.getDimensionPixelSize(R.styleable.radiusTextView_rv_bottomRightRadius, 0);
        this.isRippleEnable = ta.getBoolean(R.styleable.radiusTextView_rv_rippleEnable, false);
        ta.recycle();
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        this.setBgSelector();
    }

    public void setRadius(int radius) {
        this.radius = this.dp2px((float)radius);
        this.setBgSelector();
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = this.dp2px((float)strokeWidth);
        this.setBgSelector();
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        this.setBgSelector();
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        this.setBgSelector();
    }

    public void setEadiusHalfHeightEnable(boolean isRadiusHalfHeight) {
        this.isRadiusHalfHeight = isRadiusHalfHeight;
        this.setBgSelector();
    }

    public void setWidthHeightEqualEnable(boolean isWidthHeightEqual) {
        this.isWidthHeightEqual = isWidthHeightEqual;
        this.setBgSelector();
    }

    public void setTopLeftRadius(int topLeftRadius) {
        this.topLeftRadius = topLeftRadius;
        this.setBgSelector();
    }

    public void setTopRightRadius(int topRightRadius) {
        this.topRightRadius = topRightRadius;
        this.setBgSelector();
    }

    public void setBottomLeftRadius(int bottomLeftRadius) {
        this.bottomLeftRadius = bottomLeftRadius;
        this.setBgSelector();
    }

    public void setBottomRightRadius(int bottomRightRadius) {
        this.bottomRightRadius = bottomRightRadius;
        this.setBgSelector();
    }

    public int getBackgroundColor() {
        return this.backgroundColor;
    }

    public int getRadius() {
        return this.radius;
    }

    public int getStrokeWidth() {
        return this.strokeWidth;
    }

    public int getStrokeColor() {
        return this.strokeColor;
    }

    public boolean getRadiusHalfHeightEnable() {
        return this.isRadiusHalfHeight;
    }

    public boolean getWidthHeightEqualEnable() {
        return this.isWidthHeightEqual;
    }

    public int gettopLeftRadius() {
        return this.topLeftRadius;
    }

    public int gettopRightRadius() {
        return this.topRightRadius;
    }

    public int getbottomLeftRadius() {
        return this.bottomLeftRadius;
    }

    public int getbottomRightRadius() {
        return this.bottomRightRadius;
    }

    protected int dp2px(float dp) {
        float scale = this.context.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5F);
    }

    protected int sp2px(float sp) {
        float scale = this.context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(sp * scale + 0.5F);
    }

    private void setDrawable(GradientDrawable gd, int color, int strokeColor) {
        gd.setColor(color);
        if (this.topLeftRadius <= 0 && this.topRightRadius <= 0 && this.bottomRightRadius <= 0 && this.bottomLeftRadius <= 0) {
            gd.setCornerRadius((float)this.radius);
        } else {
            this.radiusArr[0] = (float)this.topLeftRadius;
            this.radiusArr[1] = (float)this.topLeftRadius;
            this.radiusArr[2] = (float)this.topRightRadius;
            this.radiusArr[3] = (float)this.topRightRadius;
            this.radiusArr[4] = (float)this.bottomRightRadius;
            this.radiusArr[5] = (float)this.bottomRightRadius;
            this.radiusArr[6] = (float)this.bottomLeftRadius;
            this.radiusArr[7] = (float)this.bottomLeftRadius;
            gd.setCornerRadii(this.radiusArr);
        }

        gd.setStroke(this.strokeWidth, strokeColor);
    }

    public void setBgSelector() {
        StateListDrawable bg = new StateListDrawable();
        this.setDrawable(this.gdBackground, this.backgroundColor, this.strokeColor);
        if (Build.VERSION.SDK_INT >= 21 && this.isRippleEnable && this.view.isEnabled()) {
            RippleDrawable rippleDrawable = new RippleDrawable(this.getColorSelector(this.backgroundColor, this.backgroundColor, this.backgroundColor == 2147483647 ? this.backgroundColor : this.backgroundColor), this.gdBackground, null);
            this.view.setBackground(rippleDrawable);
        } else {
            if (this.view.isEnabled()) {
                bg.addState(new int[]{-16842919, -16842913}, this.gdBackground);
            }

//            if (this.backgroundPressedColor != 2147483647 || this.strokePressedColor != 2147483647) {
//                this.setDrawable(this.gdBackgroundPressed, this.backgroundPressedColor == 2147483647 ? this.backgroundColor : this.backgroundPressedColor, this.strokePressedColor == 2147483647 ? this.strokeColor : this.strokePressedColor);
//                bg.addState(new int[]{16842919, 16842919}, this.gdBackgroundPressed);
//            }
//
//            if (this.backgroundEnabledColor != 2147483647 || this.strokeEnabledColor != 2147483647) {
//                this.setDrawable(this.gdBackgroundEnabled, this.backgroundEnabledColor == 2147483647 ? this.backgroundColor : this.backgroundEnabledColor, this.strokeEnabledColor == 2147483647 ? this.strokeColor : this.strokeEnabledColor);
//                bg.addState(new int[]{-16842910}, this.gdBackgroundEnabled);
//            }

            if (Build.VERSION.SDK_INT >= 16) {
                this.view.setBackground(bg);
            } else {
                this.view.setBackgroundDrawable(bg);
            }
        }

//        if (this.view instanceof TextView || this.view instanceof EditText) {
//            TextView textView = (TextView)this.view;
//            if (this.textPressedColor != 2147483647) {
//                this.textColor = this.textColor == 2147483647 ? textView.getTextColors().getDefaultColor() : this.textColor;
//                if (this.textColor != 2147483647 || this.textPressedColor != 2147483647 || this.textEnabledColor != 2147483647) {
//                    ColorStateList colorStateList = this.getColorSelector(this.textColor, this.textPressedColor == 2147483647 ? this.textColor : this.textPressedColor, this.textEnabledColor == 2147483647 ? this.textColor : this.textEnabledColor);
//                    textView.setTextColor(colorStateList);
//                }
//            }
//        }
    }

    @TargetApi(11)
    private ColorStateList getColorSelector(int normalColor, int pressedColor, int enabledColor) {
        return new ColorStateList(new int[][]{{16842908}, {16843518}, {16842919}, {-16842910}, new int[0]}, new int[]{pressedColor, pressedColor, pressedColor, enabledColor, normalColor});
    }
}
