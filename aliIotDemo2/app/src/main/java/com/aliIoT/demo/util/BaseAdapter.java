package com.aliIoT.demo.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseAdapter<T, V> extends RecyclerView.Adapter<SmipleViewHolder> {
    public List<T> getList() {
        return list;
    }

    List<T> list;

    public void setData(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setListener(V listener) {
        this.listener = listener;
    }

    V listener;

    public abstract int getLayoutId(int viewType);

    @NonNull
    @Override
    public SmipleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SmipleViewHolder viewHolder = SmipleViewHolder.getHolder(MyApplication.getInstance(), parent, getLayoutId(viewType),viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SmipleViewHolder holder, int position) {
        setBindViewHolder(holder, position);
    }

    protected abstract void setBindViewHolder(SmipleViewHolder holder, int position);

    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

}
