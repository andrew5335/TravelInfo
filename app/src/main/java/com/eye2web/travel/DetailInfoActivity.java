package com.eye2web.travel;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.eye2web.travel.adapter.DetailViewPagerAdapter;
import com.eye2web.travel.adapter.DetailViewTabPagerAdapger;
import com.eye2web.travel.service.DetailApiService;
import com.eye2web.travel.vo.DetailCommonItem;
import com.eye2web.travel.vo.DetailIntroItem;
import com.eye2web.travel.vo.ListItem;
import com.google.android.gms.maps.UiSettings;

import java.util.HashMap;
import java.util.Map;

/**
 * @File : DetailInfoActivity
 * @Date : 2018. 5. 14. PM 4:48
 * @Author : Andrew Kim
 * @Version : 1.0.0
 * @Description : 상세정보 페이지
**/
public class DetailInfoActivity extends BaseActivity {

    private DetailApiService detailApiService;
    private ViewPager detailContentPager;
    private DetailViewPagerAdapter detailViewPagerAdapter;
    private DetailViewTabPagerAdapger detailViewTabPagerAdapger;

    private TabLayout tabLayout;

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
        DetailCommonItem detailCommonItem = new DetailCommonItem();

        String contentId = "";
        String contentTypeId = "";
        String areaCode = "";

        contentId = item.getContentid();
        contentTypeId = item.getContenttypeid();
        areaCode = item.getAreacode();
        mapx = item.getMapx();
        mapy = item.getMapy();


        detailCommonItem = getContent(contentId, contentTypeId, areaCode, mapx, mapy);
        if(null != detailCommonItem) {
            detailCommonItem.setMapx(mapx);
            detailCommonItem.setMapy(mapy);
        }

        /**
        FragmentManager fragmentManager = getFragmentManager();

        detailContentPager = (ViewPager) findViewById(R.id.detail_content_viewpager);
        detailViewPagerAdapter = new DetailViewPagerAdapter(this, getLayoutInflater(), fragmentManager, detailCommonItem, mapx, mapy);
        detailContentPager.setAdapter(detailViewPagerAdapter);
         **/
        tabLayout = (TabLayout) findViewById(R.id.detail_tab);
        detailViewTabPagerAdapger = new DetailViewTabPagerAdapger(getSupportFragmentManager(), detailCommonItem, mapx, mapy);
        detailContentPager = (ViewPager) findViewById(R.id.detail_content_viewpager);
        detailContentPager.setAdapter(detailViewTabPagerAdapger);
        tabLayout.setupWithViewPager(detailContentPager);

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

    /**
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
    **/

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
    public DetailCommonItem getContent(String contentId, String contentTypeId, String areaCode, double mapx, double mapy) {
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
            /**
            title = (String) detailCommonItem.getTitle();

            TextView detailTitle = (TextView) findViewById(R.id.detailTitle);
            if(null != title && !"".equalsIgnoreCase(title)) {
                detailTitle.setText(title);
            }
             **/
        }

        return detailCommonItem;
    }
}
