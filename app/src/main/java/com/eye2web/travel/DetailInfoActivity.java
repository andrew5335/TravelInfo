package com.eye2web.travel;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.eye2web.travel.adapter.DetailViewPagerAdapter;
import com.eye2web.travel.adapter.DetailViewTabPagerAdapger;
import com.eye2web.travel.service.DetailApiService;
import com.eye2web.travel.service.GoogleApiService;
import com.eye2web.travel.vo.DetailCommonItem;
import com.eye2web.travel.vo.DetailIntroItem;
import com.eye2web.travel.vo.GooglePlaceDetailItem;
import com.eye2web.travel.vo.GooglePlaceItem;
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
    private GoogleApiService googleApiService;

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

        tabLayout = (TabLayout) findViewById(R.id.detail_tab);
        detailViewTabPagerAdapger = new DetailViewTabPagerAdapger(getSupportFragmentManager(), detailCommonItem, mapx, mapy);
        detailContentPager = (ViewPager) findViewById(R.id.detail_content_viewpager);
        detailContentPager.setAdapter(detailViewTabPagerAdapger);
        tabLayout.setupWithViewPager(detailContentPager);
        createTabIcon();

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

    public void createTabIcon() {
        TextView tab1 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tab1.setText("기본정보");
        tab1.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.basic_info, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tab1);

        TextView tab2 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tab2.setText("상세정보");
        tab2.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.detail_info, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tab2);

        TextView tab3 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tab3.setText("위치정보");
        tab3.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.place_info, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tab3);

        TextView tab4 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tab4.setText("주변정보");
        tab4.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.place_around_info, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tab4);
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
    public DetailCommonItem getContent(String contentId, String contentTypeId, String areaCode, double mapx, double mapy) {
        detailApiService = new DetailApiService();
        DetailIntroItem detailIntroItem = new DetailIntroItem();
        DetailCommonItem detailCommonItem = new DetailCommonItem();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        String addr = getResources().getString(R.string.apiUrl);
        String serviceKey = getResources().getString(R.string.apiKey);
        String googleSearchAddr = getResources().getString(R.string.google_places_api_text_search_url);
        String googleDetailAddr = getResources().getString(R.string.google_places_api_detail_info_url);
        String googleServiceKey = getResources().getString(R.string.google_maps_key);

        try {
            resultMap = detailApiService.getDetailInfo(addr, serviceKey, contentId, contentTypeId, areaCode, mapx, mapy);
        } catch(Exception e) {
            Log.e("Error", "Error : " + e.toString());
        }

        if(null != resultMap && 0 < resultMap.size()) {
            detailCommonItem = (DetailCommonItem) resultMap.get("detailCommon");
            detailCommonItem.setMapx(mapx);
            detailCommonItem.setMapy(mapy);
            //Log.i("Info", "Place Info : " + detailCommonItem.getTitle() + "-" + mapx + "-" + mapy);

            // 결과값이 있을 경우 구글 검색 주소, 검색어(이 경우 타이틀/제목값, 구글 api key 로 구글 검색을 통해 place_id값을 가져온다.
            // 만약 구글 검색 결과 place_id가 없으면 정부 데이터의 소개정보를 조회한다.
            GooglePlaceItem googlePlaceItem = new GooglePlaceItem();
            googlePlaceItem = googleApiService.getSearchInfo(googleSearchAddr, detailCommonItem.getTitle(), googleServiceKey, mapx, mapy);

            if(null != googlePlaceItem) {
                String placeId = googlePlaceItem.getPlaceId();

                if(null != placeId && !"".equalsIgnoreCase(placeId)) {
                    GooglePlaceDetailItem googlePlaceDetailItem = new GooglePlaceDetailItem();
                    googlePlaceDetailItem = googleApiService.getDetailInfo(googleDetailAddr, placeId, googleServiceKey);

                    if(null != googlePlaceDetailItem) {
                        detailCommonItem.setGooglePlaceDetailItem(googlePlaceDetailItem);
                    }
                }
            } else {
                // 구글 검색 결과가 없을 경우 정부 데이터의 소개 정보를 조회한다. (6/18날 추가 예정)
            }
        }

        return detailCommonItem;
    }
}
