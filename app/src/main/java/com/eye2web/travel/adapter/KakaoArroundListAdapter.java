package com.eye2web.travel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eye2web.travel.R;
import com.eye2web.travel.apivo.KakaoLocal;
import com.eye2web.travel.holder.KakaoArroundListHolder;
import com.eye2web.travel.util.CommonUtil;

import java.util.List;

public class KakaoArroundListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<KakaoLocal.Documents> data;
    private int layout;
    private Context context;
    private String listGu;

    private CommonUtil commonUtil;

    public KakaoArroundListAdapter(Context context, int layout, List<KakaoLocal.Documents> data, String listGu) {
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
        KakaoArroundListHolder viewHolder;

        if (convertView == null) {
            viewHolder = new KakaoArroundListHolder();
            convertView = inflater.inflate(layout, parent, false);

            viewHolder.arroundName = (TextView) convertView.findViewById(R.id.arround_name);
            viewHolder.arroundAddress = (TextView) convertView.findViewById(R.id.arround_address);
            viewHolder.arroundRoadAddress = (TextView) convertView.findViewById(R.id.arround_road_address);
            viewHolder.arroundPhone = (TextView) convertView.findViewById(R.id.arround_phone);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (KakaoArroundListHolder) convertView.getTag();
        }

        KakaoLocal.Documents kakaoItem = data.get(position);

        if(null != kakaoItem.getPlace_name() && !"".equalsIgnoreCase(kakaoItem.getPlace_name())) {
            if(viewHolder.arroundName.getVisibility() == View.GONE) {
                viewHolder.arroundName.setVisibility(View.VISIBLE);
            }
            viewHolder.arroundName.setText(kakaoItem.getPlace_name());
        } else {
            viewHolder.arroundName.setVisibility(View.GONE);
        }

        if(null != kakaoItem.getAddress_name() && !"".equalsIgnoreCase(kakaoItem.getAddress_name())) {
            if(viewHolder.arroundAddress.getVisibility() == View.GONE) {
                viewHolder.arroundAddress.setVisibility(View.VISIBLE);
            }
            viewHolder.arroundAddress.setText(kakaoItem.getAddress_name());
        } else {
            viewHolder.arroundAddress.setVisibility(View.GONE);
        }

        if(null != kakaoItem.getRoad_address_name() && !"".equalsIgnoreCase(kakaoItem.getRoad_address_name())) {
            if(viewHolder.arroundRoadAddress.getVisibility() == View.GONE) {
                viewHolder.arroundRoadAddress.setVisibility(View.VISIBLE);
            }
            viewHolder.arroundRoadAddress.setText(kakaoItem.getRoad_address_name());
        } else {
            viewHolder.arroundRoadAddress.setVisibility(View.GONE);
        }

        if(null != kakaoItem.getPhone() && !"".equalsIgnoreCase(kakaoItem.getPhone())) {
            if(viewHolder.arroundPhone.getVisibility() == View.GONE) {
                viewHolder.arroundPhone.setVisibility(View.VISIBLE);
            }
            viewHolder.arroundPhone.setText(kakaoItem.getPhone());
        } else {
            viewHolder.arroundPhone.setVisibility(View.GONE);
        }

        return convertView;
    }
}
