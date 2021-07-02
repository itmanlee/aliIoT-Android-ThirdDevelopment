package com.aliIoT.demo.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SmipleViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;

    public View getConvertView() {
        return mConvertView;
    }

    private View mConvertView;
    private Context mContext;

    public int getViewType() {
        return viewType;
    }

    private int viewType;

    public SmipleViewHolder(Context context, View itemView, ViewGroup parent, int viewType) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<View>();
        this.viewType = viewType;
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

    public static SmipleViewHolder getHolder(Context mContext, ViewGroup parent, int mLayoutId, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(mLayoutId, parent,
                false);
        SmipleViewHolder holder = new SmipleViewHolder(mContext, itemView, parent, viewType);
        return holder;

    }
}
