package com.hidetoolbar.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.ImageButton;


import com.hidetoolbar.R;
import com.hidetoolbar.adapter.RecyclerAdapter;
import com.hidetoolbar.mlistener.FabScrollListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ImageButton mFabButton;
    private RecyclerView mRecyclerView;

    private RecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        findView();
        initView();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle("上下滑动显示隐藏toolbar");
    }

    private void findView() {
        mFabButton = (ImageButton) findViewById(R.id.fabButton);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerAdapter(getList());
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
    }

    private void hide() {
        mToolbar.animate().translationY(-mToolbar.getHeight());//隐藏mToolbar的坐标为负
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)mFabButton.getLayoutParams();
        mFabButton.animate().translationY(mFabButton.getHeight() + params.bottomMargin);//隐藏mFabButton的坐标为正
    }

    private void show() {
        mToolbar.animate().translationY(0);//恢复到原来的位置0
        mFabButton.animate().translationY(0);
    }

    private List<String> getList() {
        List<String> list = new ArrayList<String>();
        for(int i = 0;i < 20;i++) {
            list.add("cardView" + i);
        }
        return list;

    }


}
