package com.study.longl.myselfviewdemo.MyRecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.study.longl.myselfviewdemo.R;

import java.util.ArrayList;

/**
 * Created by longl on 2018/10/22.
 * ListView的Adapter
 */

public class MyListViewAdapter extends BaseAdapter {
    private static final String TAG = "MyListViewAdapter";

    private ArrayList<String> mDataList;
    private Context mContext;
    private SlideLayout mSlideLayout;

    public MyListViewAdapter(Context context, ArrayList<String> dataList) {
        Log.i(TAG, "MyListViewAdapter: ");
        this.mDataList = dataList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        Log.i(TAG, "getCount: ");
        return mDataList.size();
    }

    @Override
    public Object getItem(int i) {
        Log.i(TAG, "getItem: ");
        return mDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        String content = mDataList.get(position);
        Log.i(TAG, "getView: ");
        final MyViewHolder myViewHolder;
        if (view == null) {
            myViewHolder = new MyViewHolder();
            view = View.inflate(mContext, R.layout.recycler_view_2_item, null);
            myViewHolder.text = view.findViewById(R.id.tv_test2);
            myViewHolder.ll_content_view = view.findViewById(R.id.ll_content_view);
            myViewHolder.to_first = view.findViewById(R.id.tv_toFirst);
            myViewHolder.delete = view.findViewById(R.id.tv_delete);
            myViewHolder.text.setText(content);
            view.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) view.getTag();
            myViewHolder.text.setText(content);
        }
        mSlideLayout = (SlideLayout) view;
        myViewHolder.ll_content_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "item被点击", Toast.LENGTH_SHORT).show();
            }
        });

        myViewHolder.ll_content_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                View view1 = LayoutInflater.from(mContext).inflate(R.layout.item_background_popwindow, null);
                PopupWindow popupWindow = new PopupWindow(view1, 300, 150);
                popupWindow.setContentView(view1);
                popupWindow.setOutsideTouchable(false);
                popupWindow.setFocusable(true);
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, (view.getWidth() - popupWindow.getWidth()) / 2, location[1] - popupWindow.getHeight() - 5);
                return false;
            }
        });

        myViewHolder.to_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //position在remove后会变，所以先把内容取出来
                String content = mDataList.get(position);
                mDataList.remove(position);
                mDataList.add(0, content);
                notifyDataSetInvalidated();
            }
        });

        myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataList.remove(position);
                notifyDataSetInvalidated();
            }
        });
        mSlideLayout.setOnSlideChangeListener(new SlideLayout.onSlideChangeListener() {
            @Override
            public void onMenuOpen(SlideLayout slideLayout) {
                mSlideLayout = slideLayout;
            }

            @Override
            public void onMenuClose(SlideLayout slideLayout) {
                if (mSlideLayout != null) {
                    mSlideLayout = null;
                }
            }

            @Override
            public void onClick(SlideLayout slideLayout) {
                if (mSlideLayout != null) {
                    mSlideLayout.closeMenu();
                }
            }
        });
        return view;
    }

    class MyViewHolder {
        TextView text;
        TextView to_first;
        TextView delete;
        RelativeLayout ll_content_view;
    }
}
