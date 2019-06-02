package com.example.slidelayout.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.slidelayout.slide.SlideLayout;

/**
 * @author gexinyu
 */
public class BaseActivity extends AppCompatActivity {

    //统一加入
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //设置样式
        super.onCreate(savedInstanceState);
        //绑定activity
//        new SlideLayout2(this).attachActivity(this);
        new SlideLayout(this).attachActivity(this);
    }

}
