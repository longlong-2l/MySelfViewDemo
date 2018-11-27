package com.study.longl.module_slide_recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by longl on 2018/10/22.
 * RecyclerView的Adapter
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    private ArrayList<String> arrayList;
    private SlideLayout mSlideLayout;

    public MyRecyclerViewAdapter(ArrayList<String> dataList) {
        this.arrayList = dataList;
//        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, null));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int position) {
        myViewHolder.textView.setText(arrayList.get(position));
        myViewHolder.contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(, "item被点击", Toast.LENGTH_SHORT).show();
            }
        });

        myViewHolder.contentView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
//                int[] location = new int[2];
//                view.getLocationOnScreen(location);
//                View view1 = LayoutInflater.from(mContext).inflate(R.layout.item_background_popwindow, null);
//                PopupWindow popupWindow = new PopupWindow(view1, 300, 150);
//                popupWindow.setContentView(view1);
//                popupWindow.setOutsideTouchable(false);
//                popupWindow.setFocusable(true);
//                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, (view.getWidth() - popupWindow.getWidth()) / 2, location[1] - popupWindow.getHeight() - 5);
                return false;
            }
        });

        myViewHolder.to_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //position在remove后会变，所以先把内容取出来
                String content = arrayList.get(myViewHolder.getAdapterPosition());
                arrayList.remove(myViewHolder.getAdapterPosition());
                arrayList.add(0, content);
                notifyDataSetChanged();
            }
        });

        myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.remove(myViewHolder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });

        mSlideLayout = (SlideLayout) myViewHolder.itemView;
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
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private TextView to_first;
        private TextView delete;
        private RelativeLayout contentView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_test2);
            to_first = itemView.findViewById(R.id.tv_toFirst);
            delete = itemView.findViewById(R.id.tv_delete);
            contentView = itemView.findViewById(R.id.ll_content_view);
        }
    }
}
