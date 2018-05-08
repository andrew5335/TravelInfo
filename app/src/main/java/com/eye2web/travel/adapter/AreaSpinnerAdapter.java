package com.eye2web.travel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.eye2web.travel.R;
import com.eye2web.travel.vo.AreaListItem;

import java.util.ArrayList;

public class AreaSpinnerAdapter extends ArrayAdapter<AreaListItem> {

    private ArrayList<AreaListItem> data;
    private LayoutInflater inflater;
    private int layout;
    private Context context;

    public AreaSpinnerAdapter(Context context, int textViewResourceId, ArrayList<AreaListItem> data) {
        super(context, textViewResourceId, data);
        this.context = context;
        this.data = data;
        this.layout = textViewResourceId;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }

        AreaListItem item = data.get(position);

        ((TextView) convertView.findViewById(R.id.areaName)).setText(item.getName());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }

        AreaListItem item = data.get(position);

        ((TextView) convertView.findViewById(R.id.areaName)).setText(item.getName());

        return convertView;
    }
}
