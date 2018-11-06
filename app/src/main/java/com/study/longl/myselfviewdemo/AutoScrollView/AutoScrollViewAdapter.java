package com.study.longl.myselfviewdemo.AutoScrollView;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longl on 2018/11/5.
 *
 */

public class AutoScrollViewAdapter<T> extends PagerAdapter implements ViewPager.OnPageChangeListener {

    private List<T> data = new ArrayList<>();

    private Context mContext;
    private AutoScrollView mView;

    private OnAutoViewPagerItemClickListener listener;

    public AutoScrollViewAdapter(Context context, List<T> data) {
        this.mContext = context;
        this.data = data;
    }

    public AutoScrollViewAdapter(Context context, List<T> data,OnAutoViewPagerItemClickListener listener) {
        this.mContext = context;
        this.data = data;
        this.listener = listener;
    }

    public void init(AutoScrollView viewPager,AutoScrollViewAdapter adapter){
        mView = viewPager;
        mView.setAdapter(this);
        mView.addOnPageChangeListener(this);

        if (data == null || data.size() == 0){
            return;
        }
        //设置初始为中间，这样一开始就能够往左滑动了
        int position = Integer.MAX_VALUE/2 - (Integer.MAX_VALUE/2) % getRealCount();
        mView.setCurrentItem(position);

        mView.start();
        mView.updatePointView(getRealCount());
    }

    public void setListener(OnAutoViewPagerItemClickListener listener) {
        this.listener = listener;
    }

    public void add(T t){
        data.add(t);
        notifyDataSetChanged();
        mView.updatePointView(getRealCount());
    }

    @Override
    public int getCount() {
        return 0;
    }

    public int getRealCount(){
        return data == null ? 0 : data.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public interface OnAutoViewPagerItemClickListener<T> {
        void onItemClick(int position,T t);
    }
}
