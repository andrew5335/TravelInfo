package com.eye2web.travel.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eye2web.travel.R;

import java.util.List;

public class DetailImageViewPagerAdapter extends PagerAdapter {

    private Context ctx;
    private LayoutInflater inflater;
    private List<String> imgUrlList;
    private String firstImage;

    private View view;

    public DetailImageViewPagerAdapter(Context ctx, LayoutInflater inflater, List<String> imgUrlList, String firstImage) {
    //Log.i("info", "first-image : " + firstImage);
        this.ctx = ctx;
        this.inflater = inflater;
        this.imgUrlList = imgUrlList;
        this.firstImage = firstImage;
    }

    @Override
    public int getCount() {
        int result = 0;
        if(null != imgUrlList && 0 < imgUrlList.size()) {
            result = imgUrlList.size();
            if(3 < result) {
                //result = 3;
            }
        } else {
            if(null != firstImage && !"".equalsIgnoreCase(firstImage)) {
                result = 1;
            } else {
                result = 0;
            }
        }

        return result;
    }

    @Override
    public boolean isViewFromObject(View v, Object obj) {
        return v == ((LinearLayout) obj);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.detail_img, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.detail_img);

        if(null != imgUrlList && 0 < imgUrlList.size()) {
            if(imageView.getVisibility() == View.GONE) {
                imageView.setVisibility(View.VISIBLE);
            }
            //Picasso.get().load((String)imgUrlList.get(position)).resize(400, 300).centerCrop().placeholder(R.mipmap.logo_final).into(imageView);
            Glide.with(view.getContext()).load((String) imgUrlList.get(position))
                    .apply(new RequestOptions().override(400, 300))
                    .into(imageView);

            container.addView(view);
        } else {
            Log.i("info", "first-image : " + firstImage);
            if(null != firstImage && !"".equalsIgnoreCase(firstImage)) {
                //Log.i("info", "first-image : " + firstImage);
                if(imageView.getVisibility() == View.GONE) {
                    imageView.setVisibility(View.VISIBLE);
                }
                //Picasso.get().load(firstImage).resize(400, 300).centerCrop().placeholder(R.mipmap.logo_final).into(imageView);
                Glide.with(view.getContext()).load(firstImage)
                        .apply(new RequestOptions().override(400, 300))
                        .into(imageView);
            } else {
                imageView.setVisibility(View.GONE);
                //Picasso.get().cancelRequest(imageView);
                Glide.with(view.getContext()).clear(imageView);
            }
            container.addView(view);
        }

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object obj) {
        container.invalidate();
    }
}
