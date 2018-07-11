package com.eye2web.travel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eye2web.travel.adapter.DetailViewPagerAdapter;
import com.eye2web.travel.adapter.DetailViewTabPagerAdapger;
import com.eye2web.travel.apivo.KakaoBlog;
import com.eye2web.travel.apivo.KakaoImage;
import com.eye2web.travel.apivo.KakaoLocal;
import com.eye2web.travel.service.DetailApiService;
import com.eye2web.travel.service.GoogleApiService;
import com.eye2web.travel.service.KakaoApiService;
import com.eye2web.travel.vo.DetailCommonItem;
import com.eye2web.travel.vo.DetailIntroItem;
import com.eye2web.travel.vo.GooglePlaceItem;
import com.eye2web.travel.vo.ListItem;
import com.google.android.gms.maps.UiSettings;

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
public class DetailInfoActivity extends BaseActivity {

    private DetailApiService detailApiService;
    private KakaoApiService kakaoApiService;

    private KakaoImage kakaoImage;
    private KakaoBlog kakaoBlog;
    private KakaoLocal kakaoLocalGas;
    private KakaoLocal kakaoLocalPark;
    private String kakaoSearchKeyWord;
    private int kakaoPage = 1;
    private int kakaoSize = 10;
    private String kakaoSort = "";

    private ViewPager detailContentPager;
    private DetailViewPagerAdapter detailViewPagerAdapter;
    private DetailViewTabPagerAdapger detailViewTabPagerAdapger;
    private GoogleApiService googleApiService;

    private TabLayout tabLayout;

    private String title;
    private String addr1;
    private String addr2;
    private String mapAddr;
    private double mapx = 0;
    private double mapy = 0;

    private UiSettings uiSettings;

    private ProgressBar progressBar;
    private Handler handler = null;
    private LinearLayout progressbarLayout;

    private DetailCommonItem detailCommonItem;
    private ListItem item;
    private TextView titleText;
    private String contentId;
    private String contentTypeId;
    private String areaCode;
    private String contentsId;

    private Intent nearbyIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);

        progressBar = (ProgressBar) findViewById(R.id.detail_progressbar);
        progressbarLayout = (LinearLayout) findViewById(R.id.progressbar_layout);

        detailContentPager = (ViewPager) findViewById(R.id.detail_content_viewpager);
        detailContentPager.setVisibility(View.GONE);
        detailContentPager.requestFocus();

        Intent detailInfoIntent = getIntent();
        item = (ListItem) detailInfoIntent.getSerializableExtra("item");
        detailCommonItem = new DetailCommonItem();

        contentId = item.getContentid();
        contentTypeId = item.getContenttypeid();
        areaCode = item.getAreacode();
        mapx = item.getMapx();
        mapy = item.getMapy();
        title = item.getTitle();
        addr1 = item.getAddr1();
        addr2 = item.getAddr2();
        mapAddr = addr1 + " " + addr2;

        kakaoApiService = new KakaoApiService();

        handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                detailCommonItem = getContent(contentId, contentTypeId, areaCode, mapx, mapy, title);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(null != detailCommonItem) {
                            detailCommonItem.setMapx(mapx);
                            detailCommonItem.setMapy(mapy);
                            detailCommonItem.setContenttypeid(contentTypeId);
                        }

                        titleText = (TextView) findViewById(R.id.detailTitle);
                        titleText.setText(title);

                        tabLayout = (TabLayout) findViewById(R.id.detail_tab);
                        detailViewTabPagerAdapger = new DetailViewTabPagerAdapger(getSupportFragmentManager(), detailCommonItem, item, mapx, mapy);
                        detailContentPager.setAdapter(detailViewTabPagerAdapger);
                        tabLayout.setupWithViewPager(detailContentPager);
                        createTabIcon();
                        progressBar.setVisibility(View.GONE);
                        progressbarLayout.setVisibility(View.GONE);
                        detailContentPager.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).start();

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

        /**
        TextView tab2 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tab2.setText("상세정보");
        tab2.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.detail_info, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tab2);

        TextView tab3 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tab3.setText("위치정보");
        tab3.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.place_info, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tab3);
         **/

        TextView tab4 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tab4.setText("주변정보");
        tab4.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.place_around_info, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tab4);
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
    public DetailCommonItem getContent(String contentId, String contentTypeId, String areaCode, double mapx, double mapy, String title) {
        detailApiService = new DetailApiService();
        googleApiService = new GoogleApiService();
        DetailIntroItem detailIntroItem = new DetailIntroItem();
        DetailCommonItem detailCommonItem = new DetailCommonItem();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        String addr = getResources().getString(R.string.apiUrl);
        String serviceKey = getResources().getString(R.string.apiKey);
        String googleSearchAddr = getResources().getString(R.string.google_places_api_text_search_url);
        String googleNearbySearchAddr = getResources().getString(R.string.google_places_api_nearby_search_url);
        String googleDetailAddr = getResources().getString(R.string.google_places_api_detail_info_url);
        String googleServiceKey = getResources().getString(R.string.google_maps_key);
        String googleRetType = "json";

        googleSearchAddr = googleSearchAddr + googleRetType;
        googleNearbySearchAddr = googleNearbySearchAddr + googleRetType;
        googleDetailAddr = googleDetailAddr + googleRetType;

        try {
            resultMap = detailApiService.getDetailInfo(addr, serviceKey, contentId, contentTypeId, areaCode, mapx, mapy);
        } catch(Exception e) {
            Log.e("Error", "Error : " + e.toString());
        }

        int detailRadius = 1000;
        int arroundRadius = 3000;
        GooglePlaceItem googlePlaceItem = new GooglePlaceItem();
        List<GooglePlaceItem> gasPlaceList = new ArrayList<GooglePlaceItem>();
        List<GooglePlaceItem> parkingPlaceList = new ArrayList<GooglePlaceItem>();

        if(null != resultMap && 0 < resultMap.size() && !resultMap.isEmpty()) {
            detailCommonItem = (DetailCommonItem) resultMap.get("detailCommon");
            detailCommonItem.setMapx(mapx);
            detailCommonItem.setMapy(mapy);

            if(null != detailCommonItem.getTitle() &&
                    !"".equalsIgnoreCase(detailCommonItem.getTitle())) { } else { detailCommonItem.setTitle(title); }
            if(null != detailCommonItem.getAddr1()
                    && !"".equalsIgnoreCase(detailCommonItem.getAddr1())) { } else { detailCommonItem.setAddr1(addr1); }
            if(null != detailCommonItem.getAddr2()
                    && !"".equalsIgnoreCase(detailCommonItem.getAddr2())) { } else { detailCommonItem.setAddr2(addr2); }
            //Log.i("Info", "Place Info : " + detailCommonItem.getTitle() + "-" + mapx + "-" + mapy);

            // 결과값이 있을 경우 구글 검색 주소, 검색어(이 경우 타이틀/제목값, 구글 api key 로 구글 검색을 통해 place_id값을 가져온다.
            // 만약 구글 검색 결과 place_id가 없으면 정부 데이터의 소개정보를 조회한다.
            /**
            int detailRadius = 1000;
            int arroundRadius = 3000;
            GooglePlaceItem googlePlaceItem = new GooglePlaceItem();
            List<GooglePlaceItem> gasPlaceList = new ArrayList<GooglePlaceItem>();
            List<GooglePlaceItem> parkingPlaceList = new ArrayList<GooglePlaceItem>();
             **/

        } else {
            detailCommonItem = new DetailCommonItem();
            detailCommonItem.setTitle(title);
            detailCommonItem.setMapx(mapx);
            detailCommonItem.setMapy(mapy);
        }

        //googlePlaceItem = googleApiService.getSearchInfo(googleNearbySearchAddr, detailCommonItem.getTitle(), googleServiceKey, mapx, mapy, detailRadius);
        //gasPlaceList = googleApiService.getNearbyInfo(googleSearchAddr, "주유소", googleServiceKey, mapx, mapy, arroundRadius);
        //parkingPlaceList = googleApiService.getNearbyInfo(googleSearchAddr, "주차장", googleServiceKey, mapx, mapy, arroundRadius);

        /**
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

        if(null != gasPlaceList && 0 < gasPlaceList.size()) {
            detailCommonItem.setGasList(gasPlaceList);
        }

        if(null != parkingPlaceList && 0 < parkingPlaceList.size()) {
            detailCommonItem.setParkingList(parkingPlaceList);
        }
         **/

        getKakaoImage();
        getKakaoBlog();
        kakaoLocalGas = getKakaoLocal("주유소", "OL7");
        kakaoLocalPark = getKakaoLocal("주차장", "PK6");

        if(null != kakaoImage) { detailCommonItem.setKakaoImage(kakaoImage); }
        if(null != kakaoBlog) { detailCommonItem.setKakaoBlog(kakaoBlog); }
        if(null != kakaoLocalGas) { detailCommonItem.setKakaoLocalGas(kakaoLocalGas); }
        if(null != kakaoLocalPark) { detailCommonItem.setKakaoLocalPark(kakaoLocalPark); }

        return detailCommonItem;
    }

    public void onGobackBtnClicked(View v) {
        super.onBackPressed();
    }

    public void detailBtn01Clicked(View v) {
        //Toast.makeText(getApplicationContext(), "Item value : " + item.getMapx() + "-" + item.getMapy(), Toast.LENGTH_LONG).show();
        nearbyIntent = new Intent(getApplicationContext(), SearchListActivity.class);
        contentsId = "";
        if("12".equalsIgnoreCase(item.getContenttypeid())) {
            contentsId = "32";
        } else {
            contentsId = "12";
        }
        nearbyIntent.putExtra("areaCode", contentsId);
        nearbyIntent.putExtra("keyword", "");
        nearbyIntent.putExtra("searchGu", "nearby");
        nearbyIntent.putExtra("mapx", item.getMapx());
        nearbyIntent.putExtra("mapy", item.getMapy());
        startActivity(nearbyIntent);
    }

    public void detailBtn02Clicked(View v) {
        //Toast.makeText(getApplicationContext(), "Item value : " + item.getContenttypeid(), Toast.LENGTH_LONG).show();
        nearbyIntent = new Intent(getApplicationContext(), SearchListActivity.class);
        contentsId = "";
        if("32".equalsIgnoreCase(item.getContenttypeid()) || "12".equalsIgnoreCase(item.getContenttypeid())) {
            contentsId = "39";
        } else {
            contentsId = "32";
        }
        nearbyIntent.putExtra("areaCode", contentsId);
        nearbyIntent.putExtra("keyword", "");
        nearbyIntent.putExtra("searchGu", "nearby");
        nearbyIntent.putExtra("mapx", item.getMapx());
        nearbyIntent.putExtra("mapy", item.getMapy());
        startActivity(nearbyIntent);
    }

    public void detailBtn03Clicked(View v) {
        //Toast.makeText(getApplicationContext(), "Item value : " + item.getTitle(), Toast.LENGTH_LONG).show();
        nearbyIntent = new Intent(getApplicationContext(), SearchListActivity.class);
        nearbyIntent.putExtra("areaCode", "38");
        nearbyIntent.putExtra("keyword", "");
        nearbyIntent.putExtra("searchGu", "nearby");
        nearbyIntent.putExtra("mapx", item.getMapx());
        nearbyIntent.putExtra("mapy", item.getMapy());
        startActivity(nearbyIntent);
    }

    public void detailBtn04Clicked(View v) {
        //Toast.makeText(getApplicationContext(), "Item value : " + item.getAreacode(), Toast.LENGTH_LONG).show();
        nearbyIntent = new Intent(getApplicationContext(), SearchListActivity.class);
        contentsId = "";
        if("15".equalsIgnoreCase(item.getContenttypeid())) {
            contentsId = "14";
        } else {
            contentsId = "15";
        }
        nearbyIntent.putExtra("areaCode", contentsId);
        nearbyIntent.putExtra("keyword", "");
        nearbyIntent.putExtra("searchGu", "nearby");
        nearbyIntent.putExtra("mapx", item.getMapx());
        nearbyIntent.putExtra("mapy", item.getMapy());
        startActivity(nearbyIntent);
    }

    public void getKakaoImage() {
        kakaoSort = "accuracy";
        kakaoImage = new KakaoImage();
        kakaoSearchKeyWord = item.getTitle();

        try {
            kakaoImage = kakaoApiService.getKakaoImage(kakaoPage, kakaoSize, kakaoSearchKeyWord, kakaoSort);
        } catch (Exception e) {
            Log.e("Error", "Kakao Image Api Call Error : " + e.toString());
        }
    }

    public void getKakaoBlog() {
        kakaoSort = "accuracy";
        kakaoBlog = new KakaoBlog();
        kakaoSearchKeyWord = item.getTitle();

        try {
            kakaoBlog = kakaoApiService.getKakaoBlog(kakaoPage, kakaoSize, kakaoSearchKeyWord, kakaoSort);
        } catch (Exception e) {
            Log.e("Error", "Kakao Blog Api Call Error : " + e.toString());
        }
    }

    public KakaoLocal getKakaoLocal(String searchKeyWord, String categoryCode) {
        kakaoSort = "distance";
        int kakaoRadius = 3000;
        KakaoLocal kakaoLocal = new KakaoLocal();

        try {
            kakaoLocal = kakaoApiService.getKakaoLocal(kakaoPage, kakaoSize, searchKeyWord, kakaoSort, categoryCode
                    , String.valueOf(item.getMapx()), String.valueOf(item.getMapy()), kakaoRadius, "");
        } catch (Exception e) {
            Log.e("Error", "Kakao Local Search Api Call Error : " + e.toString());
        }

        return kakaoLocal;
    }
}
