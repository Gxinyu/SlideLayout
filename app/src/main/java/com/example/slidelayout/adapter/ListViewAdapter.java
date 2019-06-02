package com.example.slidelayout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.slidelayout.R;
import com.example.slidelayout.helper.ListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gexinyu
 */
public class ListViewAdapter extends BaseAdapter {

    private Context context;
    private List<ListBean> list = new ArrayList();

    public ListViewAdapter(Context context, List<ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ListHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview, null);  //将布局转换成视图
            holder = new ListHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            //ViewHolder被复用
            holder = (ListHolder) convertView.getTag();
        }
        Glide.with(context)
                .load(list.get(position).bitmap)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(15)))
                .into(holder.imageView);
        return convertView;
    }

    private class ListHolder {
        ImageView imageView;
    }
}
