package com.example.slidelayout.sample;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import com.example.slidelayout.R;
import com.example.slidelayout.adapter.ImageAdapter;
import com.example.slidelayout.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends BaseActivity {

    private List<ImageView> mImageList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        ViewPager viewPager = findViewById(R.id.view_pager);

        ImageView imageView = new ImageView(this);
        ImageView imageView2 = new ImageView(this);
        ImageView imageView3 = new ImageView(this);
        ImageView imageView4 = new ImageView(this);
        ImageView imageView5 = new ImageView(this);
        ImageView imageView6 = new ImageView(this);
        ImageView imageView7 = new ImageView(this);
        ImageView imageView8 = new ImageView(this);
        ImageView imageView9 = new ImageView(this);
        ImageView imageView10 = new ImageView(this);
        imageView.setImageResource(R.mipmap.image1);
        imageView2.setImageResource(R.mipmap.image2);
        imageView3.setImageResource(R.mipmap.image3);
        imageView4.setImageResource(R.mipmap.image4);
        imageView5.setImageResource(R.mipmap.image5);
        imageView6.setImageResource(R.mipmap.image6);
        imageView7.setImageResource(R.mipmap.image7);
        imageView8.setImageResource(R.mipmap.image8);
        imageView9.setImageResource(R.mipmap.image9);
        imageView10.setImageResource(R.mipmap.image10);
        mImageList.add(imageView);
        mImageList.add(imageView2);
        mImageList.add(imageView3);
//        mImageList.add(imageView4);
//        mImageList.add(imageView5);
//        mImageList.add(imageView6);
//        mImageList.add(imageView7);
//        mImageList.add(imageView8);
//        mImageList.add(imageView9);
//        mImageList.add(imageView10);


        ImageAdapter imageAdapter = new ImageAdapter(mImageList);
        viewPager.setAdapter(imageAdapter);
    }
}
