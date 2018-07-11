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
import com.eye2web.travel.holder.GoogleReviewListHolder;
import com.eye2web.travel.util.CommonUtil;
import com.eye2web.travel.vo.GooglePlaceDetailReviews;

import java.util.List;

public class GoogleListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<GooglePlaceDetailReviews> data;
    private int layout;
    private Context context;

    private CommonUtil commonUtil;

    public GoogleListAdapter(Context context, int layout, List<GooglePlaceDetailReviews> data) {
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
        GoogleReviewListHolder viewHolder;

        if(convertView == null) {
            viewHolder = new GoogleReviewListHolder();
            convertView = inflater.inflate(layout, parent, false);

            viewHolder.profilePhoto = (ImageView) convertView.findViewById(R.id.review_profile_photo);
            viewHolder.reviewAuthor = (TextView) convertView.findViewById(R.id.review_author_name);
            //viewHolder.reviewRating = (TextView) convertView.findViewById(R.id.review_rating);
            viewHolder.reviewText = (TextView) convertView.findViewById(R.id.review_text);
            viewHolder.reviewRelativeTime = (TextView) convertView.findViewById(R.id.review_relative_time);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GoogleReviewListHolder) convertView.getTag();
        }

        commonUtil = new CommonUtil();

        GooglePlaceDetailReviews googleReviewItem = data.get(position);

        //ImageView profilePhoto = (ImageView) convertView.findViewById(R.id.review_profile_photo);
        //TextView reviewAuthor = (TextView) convertView.findViewById(R.id.review_author_name);
        //TextView reviewRating = (TextView) convertView.findViewById(R.id.review_rating);
        //TextView reviewText = (TextView) convertView.findViewById(R.id.review_text);
        //TextView reviewRelativeTime = (TextView) convertView.findViewById(R.id.review_relative_time);

        if(null != googleReviewItem) {
            if(null != googleReviewItem.getProfilePhotoUrl() && !"".equalsIgnoreCase(googleReviewItem.getProfilePhotoUrl())) {
                //Picasso.get().load(googleReviewItem.getProfilePhotoUrl()).placeholder(R.mipmap.logo_final).into(profilePhoto);
                Glide.with(context).load(googleReviewItem.getProfilePhotoUrl())
                        .apply(new RequestOptions().override(400, 300))
                        .into(viewHolder.profilePhoto);
            }

            if(null != googleReviewItem.getAuthorName() && !"".equalsIgnoreCase(googleReviewItem.getAuthorName())) {
                viewHolder.reviewAuthor.setText(googleReviewItem.getAuthorName());
            }

            if(0 < googleReviewItem.getRating()) {
                viewHolder.reviewRating.setText(String.valueOf(googleReviewItem.getRating()));
            }

            if(null != googleReviewItem.getText() && !"".equalsIgnoreCase(googleReviewItem.getText())) {
                viewHolder.reviewText.setText(googleReviewItem.getText());
            }

            if(null != googleReviewItem.getRelativeTimeDescription() && !"".equalsIgnoreCase(googleReviewItem.getRelativeTimeDescription())) {
                viewHolder.reviewRelativeTime.setText(googleReviewItem.getRelativeTimeDescription());
            }
        }

        return convertView;
    }
}
