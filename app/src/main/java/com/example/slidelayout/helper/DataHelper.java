package com.example.slidelayout.helper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.slidelayout.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gexinyu
 */
public class DataHelper {

    /**
     * 注入数据
     *
     * @return
     */
    public static List<ListBean> getBitmapList(Context context) {
        List<ListBean> list = new ArrayList();
        Resources resources = context.getResources();

        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.mipmap.image1);
        Bitmap bitmap2 = BitmapFactory.decodeResource(resources, R.mipmap.image2);
        Bitmap bitmap3 = BitmapFactory.decodeResource(resources, R.mipmap.image3);
        Bitmap bitmap4 = BitmapFactory.decodeResource(resources, R.mipmap.image4);
        Bitmap bitmap5 = BitmapFactory.decodeResource(resources, R.mipmap.image5);
        Bitmap bitmap6 = BitmapFactory.decodeResource(resources, R.mipmap.image6);
        Bitmap bitmap7 = BitmapFactory.decodeResource(resources, R.mipmap.image7);
        Bitmap bitmap8 = BitmapFactory.decodeResource(resources, R.mipmap.image8);
        Bitmap bitmap9 = BitmapFactory.decodeResource(resources, R.mipmap.image9);
        Bitmap bitmap10 = BitmapFactory.decodeResource(resources, R.mipmap.image10);

        for (int i = 0; i < 5; i++) {
            list.add(new ListBean(bitmap));
            list.add(new ListBean(bitmap2));
            list.add(new ListBean(bitmap3));
            list.add(new ListBean(bitmap4));
            list.add(new ListBean(bitmap5));
            list.add(new ListBean(bitmap6));
            list.add(new ListBean(bitmap7));
            list.add(new ListBean(bitmap8));
            list.add(new ListBean(bitmap9));
            list.add(new ListBean(bitmap10));
        }

        return list;
    }


    /**
     * 注入数据
     *
     * @return
     */
    public static Bitmap getBitmapIndex(Context context, int index) {
        List<ListBean> list = new ArrayList();
        Resources resources = context.getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.mipmap.image1);
        Bitmap bitmap2 = BitmapFactory.decodeResource(resources, R.mipmap.image2);
        Bitmap bitmap3 = BitmapFactory.decodeResource(resources, R.mipmap.image3);
        Bitmap bitmap4 = BitmapFactory.decodeResource(resources, R.mipmap.image4);
        Bitmap bitmap5 = BitmapFactory.decodeResource(resources, R.mipmap.image5);
        Bitmap bitmap6 = BitmapFactory.decodeResource(resources, R.mipmap.image6);
        Bitmap bitmap7 = BitmapFactory.decodeResource(resources, R.mipmap.image7);
        Bitmap bitmap8 = BitmapFactory.decodeResource(resources, R.mipmap.image8);
        Bitmap bitmap9 = BitmapFactory.decodeResource(resources, R.mipmap.image9);
        Bitmap bitmap10 = BitmapFactory.decodeResource(resources, R.mipmap.image10);
        list.add(new ListBean(bitmap));
        list.add(new ListBean(bitmap2));
        list.add(new ListBean(bitmap3));
        list.add(new ListBean(bitmap4));
        list.add(new ListBean(bitmap5));
        list.add(new ListBean(bitmap6));
        list.add(new ListBean(bitmap7));
        list.add(new ListBean(bitmap8));
        list.add(new ListBean(bitmap9));
        list.add(new ListBean(bitmap10));
        ListBean listBean = list.get(index);
        return listBean.bitmap;
    }
}
