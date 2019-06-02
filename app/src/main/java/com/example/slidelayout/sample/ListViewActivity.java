package com.example.slidelayout.sample;

import android.os.Bundle;
import android.widget.ListView;

import com.example.slidelayout.R;
import com.example.slidelayout.adapter.ListViewAdapter;
import com.example.slidelayout.base.BaseActivity;
import com.example.slidelayout.helper.DataHelper;
import com.example.slidelayout.helper.ListBean;

import java.util.List;


public class ListViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        ListView listView = findViewById(R.id.listView);
        List<ListBean> bitmapList = DataHelper.getBitmapList(this);
        ListViewAdapter listAdapter = new ListViewAdapter(this, bitmapList);
        listView.setAdapter(listAdapter);
    }
}
