package com.eye2web.travel.adapter;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eye2web.travel.R;
import com.eye2web.travel.holder.SearchListViewHolder;
import com.eye2web.travel.util.CommonUtil;
import com.eye2web.travel.vo.ListItem;
//import com.squareup.picasso.Picasso;

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
    //private int count = 10;
    private Context context;

    private CommonUtil commonUtil;

    public SearchListViewAdapter(Context context, int layout, List<ListItem> data) {
        this.inflater = (LayoutInflater) context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
        this.context = context;
        this.data = data;
        this.layout = layout;
        //this.count = data.size();
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
        SearchListViewHolder viewHolder;

        if(convertView == null) {
            viewHolder = new SearchListViewHolder();
            convertView = inflater.inflate(layout, parent, false);

            viewHolder.firstImage = (ImageView) convertView.findViewById(R.id.listImg);
            viewHolder.addr1 = (TextView) convertView.findViewById(R.id.listAddr);
            viewHolder.phone = (TextView) convertView.findViewById(R.id.listPhone);
            viewHolder.web = (TextView) convertView.findViewById(R.id.listWeb);
            viewHolder.title = (TextView) convertView.findViewById(R.id.listTitle);
            viewHolder.overView = (TextView) convertView.findViewById(R.id.listDetailTxt);
            viewHolder.homeImg = (ImageView) convertView.findViewById(R.id.list_home_img);
            viewHolder.phoneImg = (ImageView) convertView.findViewById(R.id.list_phone_img);
            viewHolder.webImg = (ImageView) convertView.findViewById(R.id.list_web_img);
            viewHolder.addrLayout = (LinearLayout) convertView.findViewById(R.id.list_addr_layout);
            viewHolder.phoneLayout = (LinearLayout) convertView.findViewById(R.id.list_phone_layout);
            viewHolder.webLayout = (LinearLayout) convertView.findViewById(R.id.list_web_layout);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (SearchListViewHolder) convertView.getTag();
        }

        commonUtil = new CommonUtil();
        ListItem listItem = data.get(position);

        // 이미지가 있으면 imageview에 해당 이미지 세팅, 이미지가 없을 경우에는 기본 no image 세팅 처리
        //ImageView firstImage = (ImageView) convertView.findViewById(R.id.listImg);
        if(null != listItem.getFirstimage() && "" != listItem.getFirstimage()) {
            if(viewHolder.firstImage.getVisibility() == View.GONE) {
                viewHolder.firstImage.setVisibility(View.VISIBLE);
            }
            //Picasso.get().load(listItem.getFirstimage()).resize(400, 300)
            //        .centerCrop().placeholder(R.mipmap.logo_final).into(viewHolder.firstImage);
            Glide.with(context).load(listItem.getFirstimage())
                    .apply(new RequestOptions().override(320, 240))
                    .into(viewHolder.firstImage);
            //Glide.with(context).asBitmap().load(listItem.getFirstimage()).into(viewHolder.firstImage);
        } else {
            /**
            if(null != listItem.getFirstimage2() && "" != listItem.getFirstimage2()) {
                if(viewHolder.firstImage.getVisibility() == View.GONE) {
                    viewHolder.firstImage.setVisibility(View.VISIBLE);
                }
                Picasso.get().load(listItem.getFirstimage2()).resize(400, 300).centerCrop().placeholder(R.mipmap.logo_final).into(viewHolder.firstImage);
            } else {
             **/
            if(viewHolder.firstImage.getVisibility() == View.GONE) {
                viewHolder.firstImage.setVisibility(View.VISIBLE);
            }
            viewHolder.firstImage.setVisibility(View.GONE);
            //Picasso.get().cancelRequest(viewHolder.firstImage);
            Glide.with(context).clear(viewHolder.firstImage);
            viewHolder.firstImage.setImageResource(R.mipmap.noimage);
            //}
        }

        //TextView addr1 = (TextView) convertView.findViewById(R.id.listText);
        if(viewHolder.addrLayout.getVisibility() == View.GONE) {
            viewHolder.addrLayout.setVisibility(View.VISIBLE);
        }
        if(null != listItem.getAddr1() && !"".equalsIgnoreCase(listItem.getAddr1())) {
            viewHolder.addr1.setText(listItem.getAddr1());
        } else {
            viewHolder.addrLayout.setVisibility(View.GONE);
        }

        if(viewHolder.phoneLayout.getVisibility() == View.GONE) {
            viewHolder.phoneLayout.setVisibility(View.VISIBLE);
        }
        if(null != listItem.getTel() && !"".equalsIgnoreCase(listItem.getTel())) {
            viewHolder.phone.setText(listItem.getTel());
        } else {
            viewHolder.phoneLayout.setVisibility(View.GONE);
        }

        if(viewHolder.webLayout.getVisibility() == View.GONE) {
            viewHolder.webLayout.setVisibility(View.VISIBLE);
        }
        if(null != listItem.getHomepage() && !"".equalsIgnoreCase(listItem.getHomepage())) {
            viewHolder.web.setText(listItem.getHomepage());
        } else {
            viewHolder.webLayout.setVisibility(View.GONE);
        }

        //TextView title = (TextView) convertView.findViewById(R.id.listTitle);
        viewHolder.title.setText(listItem.getTitle());

        //TextView overview = (TextView) convertView.findViewById(R.id.listDetailTxt);
        String overviewTxt = listItem.getOverview();
        SpannableStringBuilder overViewBuilder = new SpannableStringBuilder();
        if(null != overviewTxt && 0 < overviewTxt.length()) {
            overViewBuilder = commonUtil.convertTxtToLink(convertView.getContext(), overviewTxt);
        }

        if(null != overViewBuilder) {
            String overviewStr = overViewBuilder.toString();

            if(100 < overviewStr.length()) {
                overviewStr = overviewStr.substring(0, 100) + "...";
            }
            viewHolder.overView.setText(overviewStr);
        }

        //overview.setText(overviewTxt);

        return convertView;
    }

}
