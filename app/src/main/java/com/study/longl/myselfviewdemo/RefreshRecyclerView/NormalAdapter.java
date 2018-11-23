package com.study.longl.myselfviewdemo.RefreshRecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.study.longl.myselfviewdemo.R;

import java.util.ArrayList;

/**
 * Created by longl on 2018/11/12.
 * 普通的Adapter，没有任何操作
 */

public class NormalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<String> mData;
    private onItemClickListener myOnItemClickListener;
    private final int NORMAL_ITEM = 1;
    private final int FOOT_ITEM = 2;
    private int currentStatus = 3;
    public static final int STATUS_LOADING = 1;
    public static final int STATUS_FINISH = 2;
    public static final int STATUS_DEFAULT = 3;


    public NormalAdapter(ArrayList<String> data) {
        this.mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOT_ITEM) {
            return new FootViewHolder(View.inflate(parent.getContext(), R.layout.foot_layout, null));
        } else {
            return new MyViewHolder(View.inflate(parent.getContext(), R.layout.item_refresh_view, null));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.textView.setText(mData.get(position));
            myViewHolder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myOnItemClickListener.onItemClickListener(v);
                }
            });
        } else if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            switch (currentStatus) {
                case STATUS_LOADING:
                    footViewHolder.ll_root.setVisibility(View.VISIBLE);
                    break;
                case STATUS_FINISH:
                    footViewHolder.ll_root.setVisibility(View.VISIBLE);
                    footViewHolder.progressBar.setVisibility(View.GONE);
                    footViewHolder.tv_content.setText("我是有底线的");
                    break;
                case STATUS_DEFAULT:
                    footViewHolder.ll_root.setVisibility(View.GONE);
                    break;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return FOOT_ITEM;
        } else {
            return NORMAL_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    /**
     * 普通的viewHolder
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_refresh_content);
        }
    }

    /**
     * 底部的ViewHolder
     */
    public class FootViewHolder extends RecyclerView.ViewHolder {
        private View line1;
        private ProgressBar progressBar;
        private TextView tv_content;
        private View line2;
        private RelativeLayout ll_root;

        public FootViewHolder(View itemView) {
            super(itemView);
            line1 = itemView.findViewById(R.id.tv_line1);
            line2 = itemView.findViewById(R.id.tv_line2);
            progressBar = itemView.findViewById(R.id.progressbar);
            tv_content = itemView.findViewById(R.id.foot_view_item_tv);
            ll_root = itemView.findViewById(R.id.ll_foot_root);
        }
    }

    /**
     * 外部改变状态
     *
     * @param status 状态
     */
    public void setCurrentStatus(int status) {
        this.currentStatus = status;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.myOnItemClickListener = onItemClickListener;
    }

    public interface onItemClickListener {
        void onItemClickListener(View view);
    }
}
