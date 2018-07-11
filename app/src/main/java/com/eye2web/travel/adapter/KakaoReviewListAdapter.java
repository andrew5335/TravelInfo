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
import com.eye2web.travel.apivo.KakaoBlog;
import com.eye2web.travel.holder.KakaoReviewListHolder;
import com.eye2web.travel.util.CommonUtil;

import java.util.List;

public class KakaoReviewListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<KakaoBlog.Documents> data;
    private int layout;
    private Context context;

    private CommonUtil commonUtil;

    public KakaoReviewListAdapter(Context context, int layout, List<KakaoBlog.Documents> data) {
        this.inflater = (LayoutInflater) context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
        this.context = context;
        this.layout = layout;
        this.data = data;
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
        KakaoReviewListHolder viewHolder;

        if(convertView == null) {
            viewHolder = new KakaoReviewListHolder();
            convertView = inflater.inflate(layout, parent, false);

            viewHolder.profilePhoto = (ImageView) convertView.findViewById(R.id.review_profile_photo);
            viewHolder.reviewAuthor = (TextView) convertView.findViewById(R.id.review_author_name);
            //viewHolder.reviewRating = (TextView) convertView.findViewById(R.id.review_rating);
            viewHolder.reviewText = (TextView) convertView.findViewById(R.id.review_text);
            viewHolder.reviewRelativeTime = (TextView) convertView.findViewById(R.id.review_relative_time);
            viewHolder.reviewTitle = (TextView) convertView.findViewById(R.id.review_title);
            viewHolder.reviewUrl = (TextView) convertView.findViewById(R.id.review_url);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (KakaoReviewListHolder) convertView.getTag();
        }

        commonUtil = new CommonUtil();

        //GooglePlaceDetailReviews googleReviewItem = data.get(position);
        KakaoBlog.Documents kakaoItem = data.get(position);

        //ImageView profilePhoto = (ImageView) convertView.findViewById(R.id.review_profile_photo);
        //TextView reviewAuthor = (TextView) convertView.findViewById(R.id.review_author_name);
        //TextView reviewRating = (TextView) convertView.findViewById(R.id.review_rating);
        //TextView reviewText = (TextView) convertView.findViewById(R.id.review_text);
        //TextView reviewRelativeTime = (TextView) convertView.findViewById(R.id.review_relative_time);

        if(null != kakaoItem) {
            if(null != kakaoItem.getThumbnail() && !"".equalsIgnoreCase(kakaoItem.getThumbnail())) {
                //Picasso.get().load(googleReviewItem.getProfilePhotoUrl()).placeholder(R.mipmap.logo_final).into(profilePhoto);
                Glide.with(context).load(kakaoItem.getThumbnail())
                        .apply(new RequestOptions().override(400, 300))
                        .into(viewHolder.profilePhoto);
            }

            if(null != kakaoItem.getBlogname() && !"".equalsIgnoreCase(kakaoItem.getBlogname())) {
                if(viewHolder.reviewAuthor.getVisibility() == View.GONE) {
                    viewHolder.reviewAuthor.setVisibility(View.VISIBLE);
                }
                viewHolder.reviewAuthor.setText(kakaoItem.getBlogname());
            } else {
                viewHolder.reviewAuthor.setVisibility(View.GONE);
            }

            /**
            if(0 < googleReviewItem.getRating()) {
                viewHolder.reviewRating.setText(String.valueOf(googleReviewItem.getRating()));
            }
             **/

            if(null != kakaoItem.getContents() && !"".equalsIgnoreCase(kakaoItem.getContents())) {
                if(viewHolder.reviewText.getVisibility() == View.GONE) {
                    viewHolder.reviewText.setVisibility(View.VISIBLE);
                }
                viewHolder.reviewText.setText(commonUtil.convertTxtToHtml(kakaoItem.getContents()));
            } else {
                viewHolder.reviewText.setVisibility(View.GONE);
            }

            if(null != kakaoItem.getDatetime() && !"".equalsIgnoreCase(kakaoItem.getDatetime())) {
                if(viewHolder.reviewRelativeTime.getVisibility() == View.GONE) {
                    viewHolder.reviewRelativeTime.setVisibility(View.VISIBLE);
                }
                viewHolder.reviewRelativeTime.setText(kakaoItem.getDatetime());
            } else {
                viewHolder.reviewRelativeTime.setVisibility(View.GONE);
            }

            if(null != kakaoItem.getTitle() && !"".equalsIgnoreCase(kakaoItem.getTitle())) {
                if(viewHolder.reviewTitle.getVisibility() == View.GONE) {
                    viewHolder.reviewTitle.setVisibility(View.VISIBLE);
                }
                viewHolder.reviewTitle.setText(commonUtil.convertTxtToHtml(kakaoItem.getTitle()));
            } else {
                viewHolder.reviewTitle.setVisibility(View.GONE);
            }

            if(null != kakaoItem.getUrl() && !"".equalsIgnoreCase(kakaoItem.getUrl())) {
                if(viewHolder.reviewUrl.getVisibility() == View.GONE) {
                    viewHolder.reviewUrl.setVisibility(View.VISIBLE);
                }
                viewHolder.reviewUrl.setText(kakaoItem.getUrl());
            } else {
                viewHolder.reviewUrl.setVisibility(View.GONE);
            }
        }

        return convertView;
    }
}
