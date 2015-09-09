package com.hidetoolbar.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hidetoolbar.R;


/**
 * Created by wangzhiguo on 15/8/31.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private TextView mTextView;
    public RecyclerViewHolder(View itemView, int type) {
        super(itemView);
        if(type == ItemType.GENERAL) {
            mTextView = (TextView) itemView.findViewById(R.id.itemTextView);
        }
    }

    public void setText(String text) {
        if(mTextView != null) {
            mTextView.setText(text);
        }
    }


}
