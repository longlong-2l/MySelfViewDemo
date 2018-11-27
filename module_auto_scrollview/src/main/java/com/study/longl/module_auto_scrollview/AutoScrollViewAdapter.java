package com.study.longl.module_auto_scrollview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by longl on 2018/11/5.
 * AutoScrollViewAdapter 自定义轮播图的Adapter
 * 主要是在原本view的两侧加两个view，分别为最后一个view和第一个view，
 * 当换到最后最后一个view时，接下来会滑到第一个view，然后我们在下一次
 * 轮播开始前更换view并快速无动画的滚动到第一个view
 */

public class AutoScrollViewAdapter extends PagerAdapter {
    private static final String TAG = "AutoScrollViewPager";
    private ArrayList<ImageView> arrayList = new ArrayList<>();

    public AutoScrollViewAdapter(Context context, int imgs[]) {
        if (imgs.length > 0) {
            for (int i = 0; i < imgs.length + 2; i++) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                if (i == 0) {
                    imageView.setImageResource(imgs[imgs.length - 1]);
                } else if (i == imgs.length + 1) {
                    imageView.setImageResource(imgs[0]);
                } else {
                    imageView.setImageResource(imgs[i - 1]);
                }
                arrayList.add(imageView);
            }
        }
    }

    @Override
    public int getCount() {
        return arrayList == null ? 0 : arrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        Log.i(TAG, "isViewFromObject: ");
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(arrayList.get(position));
//        arrayList.get(position).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //添加点击事件
//            }
//        });
        Log.i(TAG, "instantiateItem: ");
        return arrayList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(arrayList.get(position));
    }
}
