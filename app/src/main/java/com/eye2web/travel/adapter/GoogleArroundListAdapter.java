package com.eye2web.travel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eye2web.travel.R;
import com.eye2web.travel.holder.GoogleArroundListHolder;
import com.eye2web.travel.util.CommonUtil;
import com.eye2web.travel.vo.GooglePlaceItem;

import java.util.List;

public class GoogleArroundListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<GooglePlaceItem> data;
    private int layout;
    private Context context;
    private String listGu;

    private CommonUtil commonUtil;

    public GoogleArroundListAdapter(Context context, int layout, List<GooglePlaceItem> data, String listGu) {
        this.inflater = (LayoutInflater) context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
        this.data = data;
        this.layout = layout;
        this.context = context;
        this.listGu = listGu;
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
        GoogleArroundListHolder viewHolder;

        if (convertView == null) {
            viewHolder = new GoogleArroundListHolder();
            convertView = inflater.inflate(layout, parent, false);

            viewHolder.googleImage = (ImageView) convertView.findViewById(R.id.arround_photo);
            viewHolder.googleTitle = (TextView) convertView.findViewById(R.id.arround_name);
            viewHolder.googleAddress = (TextView) convertView.findViewById(R.id.arround_address);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GoogleArroundListHolder) convertView.getTag();
        }

        //List<GooglePlaceItem> uniqueItem = new ArrayList<GooglePlaceItem>(new HashSet<GooglePlaceItem>());
        GooglePlaceItem item = data.get(position);

        //ImageView googleImage = (ImageView) convertView.findViewById(R.id.arround_photo);
        //TextView googleTitle = (TextView) convertView.findViewById(R.id.arround_name);
        //TextView googleAddress = (TextView) convertView.findViewById(R.id.arround_address);

        if(null != item.getGooglePhotoUrl() && !"".equalsIgnoreCase(item.getGooglePhotoUrl())) {
            //Picasso.get().load(item.getGooglePhotoUrl()).resize(400, 300).centerCrop().placeholder(R.mipmap.logo_final).into(viewHolder.googleImage);
            Glide.with(context).load(item.getGooglePhotoUrl())
                    .apply(new RequestOptions().override(400, 300))
                    .into(viewHolder.googleImage);
        } else {
            if("gas".equalsIgnoreCase(listGu)) {
                //viewHolder.googleImage.setImageBitmap(null);
                Glide.with(context).load(R.mipmap.gasstation).into(viewHolder.googleImage);
            } else {
                Glide.with(context).load(R.mipmap.parking).into(viewHolder.googleImage);
            }
        }

        if(null != item.getName() && !"".equalsIgnoreCase(item.getName())) {
            viewHolder.googleTitle.setText(item.getName());
        }

        if(null != item.getFormattedAddress() && !"".equalsIgnoreCase(item.getFormattedAddress())) {
            viewHolder.googleAddress.setText(item.getFormattedAddress());
        }

        return convertView;
    }
}
