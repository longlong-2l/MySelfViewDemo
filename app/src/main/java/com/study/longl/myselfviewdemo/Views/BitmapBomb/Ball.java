package com.study.longl.myselfviewdemo.Views.BitmapBomb;

public class Ball implements Cloneable {
    public float aX;     //加速度x
    public float aY;     //加速度y
    public float vX;     //速度x
    public float vY;     //速度y
    public float x;      //点位x
    public float y;      //点位y
    public int color;    //颜色
    public float r;      //半径
    public long born;    //诞生时间

    public Ball clone() {
        Ball clone = null;
        try {
            clone = (Ball) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}
