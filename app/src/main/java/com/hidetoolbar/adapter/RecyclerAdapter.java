package com.hidetoolbar.adapter;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.hidetoolbar.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by wangzhiguo on 15/8/31.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private List<String> mList;
    private OnItemClickListener mOnItemClickListener;
    public static final int LAST_POSITION = -1 ;

    /**
     * 1、dragDirs- 表示拖拽的方向，有六个类型的值：LEFT、RIGHT、START、END、UP、DOWN
     * 2、swipeDirs- 表示滑动的方向，有六个类型的值：LEFT、RIGHT、START、END、UP、DOWN
     *  如果为0，则表示不触发该操作（滑动or拖拽）
     */
    public ItemTouchHelper.Callback mCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        /**
         *
         * @param recyclerView
         * @param viewHolder 拖拽的viewHolder
         * @param target 目标位置的viewHolder
         * @return
         */
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
            int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position
            if(fromPosition < toPosition) {
                //分别把中间所有的item的位置进行交换
                for(int i = fromPosition;i < toPosition;i++) {
                    Collections.swap(mList,i - 1,i);
                }
            } else if(toPosition > 0) {
                for(int i = fromPosition;i > toPosition;i--) {
                    Collections.swap(mList,i - 1,i - 2);
                }
            }
            notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            mList.remove(position - 1);
            notifyItemRemoved(position);
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                //左右滑动时改变Item的透明度
                final float alpha = 1 - Math.abs(dX) / (float)viewHolder.itemView.getWidth();
                viewHolder.itemView.setAlpha(alpha);
                viewHolder.itemView.setTranslationX(dX);
            }
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            super.onSelectedChanged(viewHolder, actionState);
            //当选中Item时候会调用该方法，重写此方法可以实现选中时候的一些动画逻辑
            Log.v("zxy", "onSelectedChanged");
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            //当动画已经结束的时候调用该方法，重写此方法可以实现恢复Item的初始状态
            Log.v("zxy", "clearView");
        }
    };


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
    public void onBindViewHolder(final RecyclerViewHolder recyclerViewHolder, int position) {
        int layoutPosition = recyclerViewHolder.getLayoutPosition();
        if(layoutPosition != 0) {
            recyclerViewHolder.setText(mList.get(layoutPosition - 1));
            if(mOnItemClickListener != null) {
                recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int layoutPosition = recyclerViewHolder.getLayoutPosition();
                        mOnItemClickListener.onItemClick(recyclerViewHolder.itemView, layoutPosition);
                    }
                });

                recyclerViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int layoutPosition = recyclerViewHolder.getLayoutPosition();
                        mOnItemClickListener.onItemLongClick(recyclerViewHolder.itemView, layoutPosition);
                        return true;
                    }
                });
            }
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

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void addItem(int position) {
        position = position == LAST_POSITION ? getItemCount() - 1 : position - 1;
        Log.i("POsition：", position + "*********");
        mList.add(position, "添加一项数据");
        notifyItemInserted(position + 1);
    }

    public void removeItem(int position) {
        if (position == LAST_POSITION && getItemCount()>1)
            position = getItemCount() - 1;

        if (position > LAST_POSITION && position < getItemCount()) {
            Log.i("POsition：" , position + "---------");
            mList.remove(position - 1);
            notifyItemRemoved(position);
        }
    }
}
