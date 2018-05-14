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
public class SearchListViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<ListItem> data;
    private int layout;

    public SearchListViewAdapter(Context context, int layout, List<ListItem> data) {
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

        // 이미지가 있으면 imageview에 해당 이미지 세팅, 이미지가 없을 경우에는 기본 no image 세팅 처리
        ImageView firstImage = (ImageView) convertView.findViewById(R.id.listImg);
        if(null != listItem.getFirstimage() && "" != listItem.getFirstimage()) {
            Picasso.get().load(listItem.getFirstimage()).placeholder(R.mipmap.logo_small).into(firstImage);
        } else {
            if(null != listItem.getFirstimage2() && "" != listItem.getFirstimage2()) {
                Picasso.get().load(listItem.getFirstimage2()).placeholder(R.mipmap.logo_small).into(firstImage);
            } else {
                Picasso.get().cancelRequest(firstImage);
                firstImage.setImageResource(R.mipmap.noimage);
            }
        }

        TextView addr1 = (TextView) convertView.findViewById(R.id.listText);
        addr1.setText(listItem.getAddr1());

        TextView title = (TextView) convertView.findViewById(R.id.listTitle);
        title.setText(listItem.getTitle());

        return convertView;
    }
}