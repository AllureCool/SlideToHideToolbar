package com.hidetoolbar.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.hidetoolbar.R;

import java.util.List;

/**
 * Created by wangzhiguo on 15/8/31.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private List<String> mList;
    public RecyclerAdapter(List<String> list) {
        mList = list;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {

        if(type == ItemType.FIRSTITEM) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.empty_item,viewGroup,false);
            return new RecyclerViewHolder(view,type);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item,viewGroup,false);
            return new RecyclerViewHolder(view,type);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder recyclerViewHolder, int i) {
        if(i != 0) {
            recyclerViewHolder.setText(mList.get(i - 1));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) {
            return ItemType.FIRSTITEM;
        } else {
            return ItemType.GENERAL;
        }
    }
}
