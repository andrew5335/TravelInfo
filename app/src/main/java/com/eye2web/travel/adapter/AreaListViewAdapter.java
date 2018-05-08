package com.eye2web.travel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.eye2web.travel.vo.AreaListItem;

import java.util.List;

public class AreaListViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<AreaListItem> data;
    private int layout;

    public AreaListViewAdapter(Context context, int layout, List<AreaListItem> data) {
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

        AreaListItem areaListItem = data.get(position);



        return convertView;
    }
}
