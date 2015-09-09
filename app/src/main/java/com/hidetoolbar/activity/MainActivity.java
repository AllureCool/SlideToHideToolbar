package com.hidetoolbar.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;


import com.hidetoolbar.R;
import com.hidetoolbar.adapter.DividerItemDecoration;
import com.hidetoolbar.adapter.RecyclerAdapter;
import com.hidetoolbar.mlistener.FabScrollListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ImageButton mFabButton;
    private RecyclerView mRecyclerView;

    private RecyclerAdapter mAdapter;

    private ItemTouchHelper mItemTouchHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        findView();
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add:
                mAdapter.addItem(1);
                mRecyclerView.scrollToPosition(0);
                break;
            case R.id.action_delete:
                mAdapter.removeItem(1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle("RecycleView");
    }

    private void findView() {
        mFabButton = (ImageButton) findViewById(R.id.fabButton);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerAdapter(getList());
        mItemTouchHelper = new ItemTouchHelper(mAdapter.mCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(mAdapter);
        //监听滑动(上滑：出去，下滑：进来);
        mRecyclerView.addOnScrollListener(new FabScrollListener() {
            @Override
            public void onHide() {
                //出去
                hide();
            }

            @Override
            public void onShow() {
                //显示
                show();
            }
        });

        mAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "itemClick" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(MainActivity.this, "itemLongClick" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void hide() {
        mToolbar.animate().translationY(-mToolbar.getHeight());//隐藏mToolbar的坐标为负
        mToolbar.animate().alpha(0);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)mFabButton.getLayoutParams();
        mFabButton.animate().translationY(mFabButton.getHeight() + params.bottomMargin);//隐藏mFabButton的坐标为正
        mFabButton.animate().alpha(0);
    }

    private void show() {
        mToolbar.animate().translationY(0);//恢复到原来的位置0
        mToolbar.animate().alpha(1);
        mFabButton.animate().translationY(0);
        mFabButton.animate().alpha(1);
    }

    private List<String> getList() {
        List<String> list = new ArrayList<String>();
        for(int i = 0;i < 20;i++) {
            list.add("cardView" + i);
        }
        return list;

    }


}
