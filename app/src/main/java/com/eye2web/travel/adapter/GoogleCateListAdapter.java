package com.eye2web.travel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eye2web.travel.R;
import com.eye2web.travel.util.CommonUtil;
import com.eye2web.travel.vo.GooglePlaceItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GoogleCateListAdapter  extends BaseAdapter {

    private LayoutInflater inflater;
    private List<GooglePlaceItem> data;
    private int layout;
    private Context context;

    private CommonUtil commonUtil;

    public GoogleCateListAdapter(Context context, int layout, List<GooglePlaceItem> data) {
        this.inflater = (LayoutInflater) context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
        this.data = data;
        this.layout = layout;
        this.context = context;
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
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }

        //List<GooglePlaceItem> uniqueItem = new ArrayList<GooglePlaceItem>(new HashSet<GooglePlaceItem>());
        GooglePlaceItem item = data.get(position);

        ImageView googleIcon = (ImageView) convertView.findViewById(R.id.google_icon);
        ImageView googleImage = (ImageView) convertView.findViewById(R.id.google_img);
        TextView googleTitle = (TextView) convertView.findViewById(R.id.google_title);
        TextView googleAddress = (TextView) convertView.findViewById(R.id.google_address);

        if(null != item.getIcon() && !"".equalsIgnoreCase(item.getIcon())) {
            Picasso.get().load(item.getIcon()).placeholder(R.mipmap.logo_final).into(googleIcon);
        }

        if(null != item.getGooglePhotoUrl() && !"".equalsIgnoreCase(item.getGooglePhotoUrl())) {
            Picasso.get().load(item.getGooglePhotoUrl()).placeholder(R.mipmap.logo_final).into(googleImage);
            //Glide.with(convertView.getContext()).load(item.getGooglePhotoUrl()).into(googleImage);
        } else {
            googleImage.setImageBitmap(null);
        }

        if(null != item.getName() && !"".equalsIgnoreCase(item.getName())) {
            googleTitle.setText(item.getName());
        }

        if(null != item.getFormattedAddress() && !"".equalsIgnoreCase(item.getFormattedAddress())) {
            googleAddress.setText(item.getFormattedAddress());
        }

        return convertView;
    }
}
