package com.aliIoT.demo.util;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public class SmipleViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;
    private int viewType;

    public SmipleViewHolder(Context context, View itemView, ViewGroup parent, int viewType) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<View>();
        this.viewType = viewType;
    }

    public static SmipleViewHolder getHolder(Context mContext, ViewGroup parent, int mLayoutId, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(mLayoutId, parent,
                false);
        SmipleViewHolder holder = new SmipleViewHolder(mContext, itemView, parent, viewType);
        return holder;

    }

    public View getConvertView() {
        return mConvertView;
    }

    public int getViewType() {
        return viewType;
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }
}
