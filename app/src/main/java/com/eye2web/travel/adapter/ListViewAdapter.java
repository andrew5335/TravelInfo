package com.eye2web.travel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eye2web.travel.R;
import com.eye2web.travel.vo.ListItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @File : ListViewAdapter
 * @Date : 2018. 5. 2. PM 4:48
 * @Author : Andrew Kim
 * @Version : 1.0.0
 * @Description : 리스트뷰 데이터 처리를 위한 adapter
**/
public class ListViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<ListItem> data;
    private int layout;

    public ListViewAdapter(Context context, int layout, List<ListItem> data) {
        this.inflater = (LayoutInflater) context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
        this.data = data;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }

        ListItem listItem = data.get(position);

        ImageView firstImage = (ImageView) convertView.findViewById(R.id.listImg);
        //firstImage.setImageResource(listItem.getFirstImage());
        Picasso.get().load(listItem.getFirstImage()).into(firstImage);

        TextView addr = (TextView) convertView.findViewById(R.id.listText);
        addr.setText(listItem.getAddr());

        return convertView;
    }
}
