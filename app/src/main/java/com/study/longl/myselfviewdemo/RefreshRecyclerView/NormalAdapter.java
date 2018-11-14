package com.study.longl.myselfviewdemo.RefreshRecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.study.longl.myselfviewdemo.R;

import java.util.ArrayList;

/**
 * Created by longl on 2018/11/12.
 * 普通的Adapter，没有任何操作
 */

public class NormalAdapter extends RecyclerView.Adapter<NormalAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<String> mData;

    public NormalAdapter(ArrayList<String> data) {
        this.mData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(parent.getContext(), R.layout.item_refresh_view, null));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_refresh_content);
        }
    }
}
