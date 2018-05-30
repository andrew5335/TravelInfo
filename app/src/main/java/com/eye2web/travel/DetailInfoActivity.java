package com.eye2web.travel;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.widget.TextView;

import com.eye2web.travel.adapter.DetailImageViewPagerAdapter;
import com.eye2web.travel.service.DetailApiService;
import com.eye2web.travel.util.CommonUtil;
import com.eye2web.travel.vo.DetailCommonItem;
import com.eye2web.travel.vo.DetailIntroItem;
import com.eye2web.travel.vo.ListItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @File : DetailInfoActivity
 * @Date : 2018. 5. 14. PM 4:48
 * @Author : Andrew Kim
 * @Version : 1.0.0
 * @Description : 상세정보 페이지
**/
public class DetailInfoActivity extends BaseActivity implements OnMapReadyCallback {

    private DetailApiService detailApiService;
    private ViewPager imageViewPager;
    private DetailImageViewPagerAdapter detailImageViewPagerAdapter;

    private CommonUtil commonUtil;

    private String title;
    private String mapAddr;
    private double mapx = 0;
    private double mapy = 0;

    private UiSettings uiSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);

        Intent detailInfoIntent = getIntent();
        ListItem item = (ListItem) detailInfoIntent.getSerializableExtra("item");

        String contentId = "";
        String contentTypeId = "";
        String areaCode = "";

        contentId = item.getContentid();
        contentTypeId = item.getContenttypeid();
        areaCode = item.getAreacode();
        mapx = item.getMapx();
        mapy = item.getMapy();

        getContent(contentId, contentTypeId, areaCode, mapx, mapy);

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.map_info);
        mapFragment.getMapAsync(this);

        /** 네이버 지도 api 연동 완료 - 2018-05-30
        NaverFragment naverFragment = new NaverFragment();
        naverFragment.setArguments(new Bundle());
        naverFragment.setMenuVisibility(true);
        naverFragment.setPosition(mapx, mapy, title, mapAddr);
        android.support.v4.app.FragmentManager naverFragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction naverFragmentTransaction = naverFragmentManager.beginTransaction();
        naverFragmentTransaction.add(R.id.detail_naver_map, naverFragment);
        naverFragmentTransaction.commit();
         **/
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

    /**
     * @parameter : contentId - 컨텐츠 아이디값
     *              contentTypeId - 컨텐츠 타입 (관광, 숙박, 공연 등)
     *              areaCode - 지역코드
     *              mapx - GPS X좌표값
     *              mapy - GPS Y좌표값
     * @Date : 2018. 5. 15. AM 9:35
     * @Author : Andrew Kim
     * @Description : 상세정보 조회
    **/
    public void getContent(String contentId, String contentTypeId, String areaCode, double mapx, double mapy) {
        detailApiService = new DetailApiService();
        DetailIntroItem detailIntroItem = new DetailIntroItem();
        DetailCommonItem detailCommonItem = new DetailCommonItem();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        String addr = getResources().getString(R.string.apiUrl);
        String serviceKey = getResources().getString(R.string.apiKey);

        try {
            resultMap = detailApiService.getDetailInfo(addr, serviceKey, contentId, contentTypeId, areaCode, mapx, mapy);
        } catch(Exception e) {
            Log.e("Error", "Error : " + e.toString());
        }

        if(null != resultMap && 0 < resultMap.size()) {

            detailCommonItem = (DetailCommonItem) resultMap.get("detailCommon");

            String overView = "";
            String homepage = "";
            title = "";
            String addr1 = "";
            String addr2 = "";
            String firstImage = "";
            List<String> imgUrlList = new ArrayList<String>();
            //String firstImage2 = "";

            overView = detailCommonItem.getOverview();
            homepage = detailCommonItem.getHomepage();
            title = detailCommonItem.getTitle();
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
            TextView detailTitle = (TextView) findViewById(R.id.detailTitle);
            TextView detailOverView = (TextView) findViewById(R.id.detailOverView);
            TextView detailAddr1 = (TextView) findViewById(R.id.detailAddr1);
            TextView detailAddr2 = (TextView) findViewById(R.id.detailAddr2);
            TextView detailHomepage = (TextView) findViewById(R.id.detailHomepage);

            /**
            if(null != firstImage && !"".equalsIgnoreCase(firstImage)) {
                if(detailImg1.getVisibility() == View.GONE) {
                    detailImg1.setVisibility(View.VISIBLE);
                }
                Picasso.get().load(firstImage).placeholder(R.mipmap.logo_final).into(detailImg1);
            } else {
                detailImg1.setVisibility(View.GONE);
                Picasso.get().cancelRequest(detailImg1);
                //detailImg1.setImageResource(R.mipmap.noimage);
            }
             **/

            /**
            if(null != firstImage2 && !"".equalsIgnoreCase(firstImage2)) {
                if(detailImg2.getVisibility() == View.GONE) {
                    detailImg2.setVisibility(View.VISIBLE);
                }
                Picasso.get().load(firstImage2).placeholder(R.mipmap.logo_final).into(detailImg2);
            } else {
                detailImg2.setVisibility(View.GONE);
                Picasso.get().cancelRequest(detailImg2);
                //detailImg1.setImageResource(R.mipmap.noimage);
            }
             **/

            commonUtil = new CommonUtil();

            if(null != title && !"".equalsIgnoreCase(title)) {
                detailTitle.setText(title);
            }

            SpannableStringBuilder overViewBuilder = new SpannableStringBuilder();
            overViewBuilder = commonUtil.convertTxtToLink(getApplicationContext(), overView);
            if(null != overViewBuilder) { detailOverView.setText(overViewBuilder.toString()); }

            SpannableStringBuilder addr1Builder = new SpannableStringBuilder();
            addr1Builder = commonUtil.convertTxtToLink(getApplicationContext(), addr1);
            if(null != addr1Builder) { detailAddr1.setText(addr1Builder.toString()); }

            SpannableStringBuilder addr2Builder = new SpannableStringBuilder();
            addr2Builder = commonUtil.convertTxtToLink(getApplicationContext(), addr1);
            if(null != addr2Builder) { detailAddr2.setText(addr2Builder.toString()); }

            SpannableStringBuilder homePageBuilder = new SpannableStringBuilder();
            homePageBuilder = commonUtil.convertTxtToLink(getApplicationContext(), homepage);
            if(null != homePageBuilder) { detailHomepage.setText(homePageBuilder.toString()); }

            imageViewPager = (ViewPager) findViewById(R.id.detail_img_viewpager);
            detailImageViewPagerAdapter = new DetailImageViewPagerAdapter(this, getLayoutInflater(), imgUrlList, firstImage);
            imageViewPager.setAdapter(detailImageViewPagerAdapter);

            /**
            if(null != title && !"".equalsIgnoreCase(title)) { detailTitle.setText(title); }
            if(null != overView && !"".equalsIgnoreCase(overView)) { detailOverView.setText(Html.fromHtml(overView)); }
            if(null != addr1 && !"".equalsIgnoreCase(addr1)) { detailAddr1.setText(Html.fromHtml(addr1)); }
            if(null != addr2 && !"".equalsIgnoreCase(addr2)) { detailAddr2.setText(Html.fromHtml(addr2)); }
            //if(null != homepage && !"".equalsIgnoreCase(homepage)) { detailHomepage.setText(Html.fromHtml(homepage)); }
             **/
        }
    }
}
