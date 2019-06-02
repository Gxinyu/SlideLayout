package com.example.slidelayout;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.slidelayout.sample.DrawerActivity;
import com.example.slidelayout.sample.HScrollViewActivity;
import com.example.slidelayout.sample.InnerScrollActivity;
import com.example.slidelayout.sample.ListViewActivity;
import com.example.slidelayout.sample.RecyclerActivity;
import com.example.slidelayout.sample.RefreshActivity;
import com.example.slidelayout.sample.ScrollViewActivity;
import com.example.slidelayout.sample.TablayoutActivity;
import com.example.slidelayout.sample.ViewPagerActivity;
import com.example.slidelayout.sample.WebviewActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void jumpViewpager(View view) {
        Log.e("TAG", "跳转");
        startActivity(new Intent(this, ViewPagerActivity.class));
    }

    public void jumpListView(View view) {
        startActivity(new Intent(this, ListViewActivity.class));
    }

    public void jumpRecyclerView(View view) {
        startActivity(new Intent(this, RecyclerActivity.class));
    }

    public void jumpScrollView(View view) {
        startActivity(new Intent(this, ScrollViewActivity.class));
    }


    public void jumpHorizontalScrollView(View view) {
        startActivity(new Intent(this, HScrollViewActivity.class));
    }

    public void jumpWebview(View view) {
        startActivity(new Intent(this, WebviewActivity.class));
    }

    public void jumpRefresh(View view) {
        startActivity(new Intent(this, RefreshActivity.class));
    }

    public void jumpInner(View view) {
        startActivity(new Intent(this, InnerScrollActivity.class));
    }

    public void jumpTablayout(View view) {
        startActivity(new Intent(this, TablayoutActivity.class));
    }

    public void jumpDrawer(View view) {
        startActivity(new Intent(this, DrawerActivity.class));
    }

}
