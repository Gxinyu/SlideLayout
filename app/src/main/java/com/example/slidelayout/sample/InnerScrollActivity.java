package com.example.slidelayout.sample;

import android.os.Bundle;
import com.example.slidelayout.R;
import com.example.slidelayout.base.BaseActivity;

/**
 * 内部有可以滚动的view
 */
public class InnerScrollActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner_scroll);
    }
}
