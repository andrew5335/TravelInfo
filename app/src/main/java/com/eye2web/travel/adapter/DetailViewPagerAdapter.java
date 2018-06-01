package com.eye2web.travel.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eye2web.travel.R;
import com.eye2web.travel.util.CommonUtil;
import com.eye2web.travel.vo.DetailCommonItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class DetailViewPagerAdapter extends PagerAdapter implements OnMapReadyCallback {

    private Context parentCtx;
    private LayoutInflater inflater;
    private DetailCommonItem detailCommonItem;
    private FragmentManager parentFragmentManager;

    private ViewPager imageViewPager;
    private DetailImageViewPagerAdapter detailImageViewPagerAdapter;
    private String title;
    private String mapAddr;
    private double mapx = 0;
    private double mapy = 0;
    private UiSettings uiSettings;

    private CommonUtil commonUtil;

    public DetailViewPagerAdapter(Context ctx, LayoutInflater inflater, FragmentManager fragmentManager
            , DetailCommonItem detailCommonItem, double mapx, double mapy) {
        this.parentCtx = ctx;
        this.inflater = inflater;
        this.parentFragmentManager = fragmentManager;
        this.detailCommonItem = detailCommonItem;
        this.mapx = mapx;
        this.mapy = mapy;
    }

    @Override
    public int getCount() {
        int result = 2;

        return result;
    }

    @Override
    public boolean isViewFromObject(View v, Object obj) {
        return v == ((LinearLayout) obj);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) parentCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;

        switch (position) {
            case 0 :
                view = inflater.inflate(R.layout.detail_content, null);
                //inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                String overView = "";
                String homepage = "";
                String addr1 = "";
                String addr2 = "";
                String firstImage = "";
                List<String> imgUrlList = new ArrayList<String>();
                //String firstImage2 = "";

                title = (String) detailCommonItem.getTitle();
                overView = detailCommonItem.getOverview();
                homepage = detailCommonItem.getHomepage();
                addr1 = detailCommonItem.getAddr1();
                addr2 = detailCommonItem.getAddr2();
                mapAddr = addr1 + " " + addr2;
                imgUrlList = (List<String>) detailCommonItem.getImgUrlList();
                firstImage = detailCommonItem.getFirstimage();
                //firstImage2 = detailCommonItem.getFirstimage2();

                Log.i("INFO", "================detail Info : " + overView);
                Log.i("INFO", "================firstimage Info : " + firstImage);

                //ImageView detailImg1 = (ImageView) findViewById(R.id.detailImg1);
                //ImageView detailImg2 = (ImageView) findViewById(R.id.detailImg2);

                TextView detailOverView = (TextView) view.findViewById(R.id.detailOverView);
                TextView detailAddr1 = (TextView) view.findViewById(R.id.detailAddr1);
                TextView detailAddr2 = (TextView) view.findViewById(R.id.detailAddr2);
                TextView detailHomepage = (TextView) view.findViewById(R.id.detailHomepage);
                TextView detailTitle = (TextView) view.findViewById(R.id.detailTitle);

                detailTitle.setText(title);

                commonUtil = new CommonUtil();
                //detailOverView.setText(overView);

                SpannableStringBuilder overViewBuilder = new SpannableStringBuilder();
                overViewBuilder = commonUtil.convertTxtToLink(parentCtx, overView);
                if(null != overViewBuilder) { detailOverView.setText(overViewBuilder.toString()); }

                SpannableStringBuilder addr1Builder = new SpannableStringBuilder();
                addr1Builder = commonUtil.convertTxtToLink(parentCtx, addr1);
                if(null != addr1Builder) { detailAddr1.setText(addr1Builder.toString()); }

                SpannableStringBuilder addr2Builder = new SpannableStringBuilder();
                addr2Builder = commonUtil.convertTxtToLink(parentCtx, addr1);
                if(null != addr2Builder) { detailAddr2.setText(addr2Builder.toString()); }

                SpannableStringBuilder homePageBuilder = new SpannableStringBuilder();
                homePageBuilder = commonUtil.convertTxtToLink(parentCtx, homepage);
                if(null != homePageBuilder) { detailHomepage.setText(homePageBuilder.toString()); }

                imageViewPager = (ViewPager) view.findViewById(R.id.detail_img_viewpager);
                detailImageViewPagerAdapter = new DetailImageViewPagerAdapter(parentCtx, inflater, imgUrlList, firstImage);
                imageViewPager.setAdapter(detailImageViewPagerAdapter);

                break;

            case 1 :
                view = inflater.inflate(R.layout.detail_map, null);
                //inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                title = (String) detailCommonItem.getTitle();
                TextView detailTitle2 = (TextView) view.findViewById(R.id.detailTitle);
                detailTitle2.setText(title);

                FragmentManager fragmentManager = parentFragmentManager;
                MapFragment mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.map_info);
                mapFragment.getMapAsync(this);

                break;
        }

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        ((ViewPager) container).removeView((View) view);
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        LatLng location = new LatLng(mapy, mapx);
        uiSettings = map.getUiSettings();

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location);
        markerOptions.title(title);
        markerOptions.snippet(mapAddr);
        map.addMarker(markerOptions);

        map.moveCamera(CameraUpdateFactory.newLatLng(location));
        map.animateCamera(CameraUpdateFactory.zoomTo(15));

        uiSettings.isZoomControlsEnabled();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
    }
}
