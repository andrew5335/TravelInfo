package com.eye2web.travel;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.eye2web.travel.adapter.IndexPagerAdapter;
import com.eye2web.travel.apivo.Eye2WebJson;
import com.eye2web.travel.handler.BackPressCloseHandler;
import com.eye2web.travel.service.AreaApiService;
import com.eye2web.travel.service.Eye2WebApiService;
import com.eye2web.travel.vo.AreaListItem;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @File : IndexActivity
 * @Date : 2018. 5. 2. PM 4:43
 * @Author : Andrew Kim
 * @Version : 1.0.0
 * @Description : Splash 화면 구동 후 최초로 로딩되는 화면
**/
public class IndexActivity extends BaseActivity {

    private AreaApiService areaApiService;

    private Eye2WebApiService eye2WebApiService;

    private IndexPagerAdapter indexPagerAdapter;

    private TabLayout tabLayout;

    private BackPressCloseHandler backPressCloseHandler;

    private String imageUrl;

    private AdView mAdView1;

    private AdView mAdView2;

    //private List<Eye2WebContent> eye2WebContentList1;
    //private List<Eye2WebContent> eye2webContentList2;
    //private Eye2WebMediaContent eye2WebMediaContent;
    private List<Eye2WebJson> eye2webJsonList;
    private Intent eye2webIntent;

    private ImageView theme_img;
    private TextView theme_title;
    private LinearLayout theme_travel_layout;

    private ImageView theme_img2;
    private ImageView theme_img3;
    private ImageView theme_img4;
    private ImageView theme_img5;

    private TextView theme_title2;
    private TextView theme_title3;
    private TextView theme_title4;
    private TextView theme_title5;

    private int mainContentId = 0;
    private int subContentId1 = 0;
    private int subContentId2 = 0;
    private int subContentId3 = 0;
    private int subContentId4 = 0;

    private String authKey;

    private Handler handler = null;

    /**
     * @parameter :
     * @Date : 2018. 5. 10. PM 12:21
     * @Author : Andrew Kim
     * @Description : 화면 구성
    **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // 배너 광고 삽입
        String admobId = getResources().getString(R.string.google_admob_id);
        MobileAds.initialize(this, admobId);    // admob 초기화

        // 배너 광고는 한 activity에 1개만 삽입 가능
        mAdView1 = findViewById(R.id.adView);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mAdView1.loadAd(adRequest1);

        imageUrl = getResources().getString(R.string.image_url);
        imageUrl = imageUrl + "/images/";

        authKey = getResources().getString(R.string.auth_key);

        if(Build.VERSION.SDK_INT >= 23
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }

        List<String> cityList = new ArrayList<String>();
        cityList = getCityList();
        ViewPager indexMenuPager = (ViewPager) findViewById(R.id.index_menu_pager);
        tabLayout = (TabLayout) findViewById(R.id.indicator_tab);
        indexPagerAdapter = new IndexPagerAdapter(this, getLayoutInflater(), cityList);
        tabLayout.setupWithViewPager(indexMenuPager, true);
        indexMenuPager.setAdapter(indexPagerAdapter);
        indexMenuPager.setFocusableInTouchMode(true);
        indexMenuPager.requestFocus();

        ImageView food_around = (ImageView) findViewById(R.id.search_around_food);
        ImageView hotel_around = (ImageView) findViewById(R.id.search_around_stay);
        ImageView travel_around = (ImageView) findViewById(R.id.search_around_travel);

        Picasso.get().load(imageUrl + "food.jpg").placeholder(R.mipmap.food_index).into(food_around);
        Picasso.get().load(imageUrl + "hotel.jpg").placeholder(R.mipmap.hotel_index).into(hotel_around);
        Picasso.get().load(imageUrl + "travel.jpg").placeholder(R.mipmap.travel_index).into(travel_around);

        eye2webJsonList = new ArrayList<Eye2WebJson>();
        //eye2webContentList2 = new ArrayList<Eye2WebContent>();
        theme_img = (ImageView) findViewById(R.id.theme_img);
        theme_title = (TextView) findViewById(R.id.theme_title);
        theme_travel_layout = (LinearLayout) findViewById(R.id.theme_travel_layout);

        theme_img2 = (ImageView) findViewById(R.id.theme_img2);
        theme_img3 = (ImageView) findViewById(R.id.theme_img3);
        theme_img4 = (ImageView) findViewById(R.id.theme_img4);
        theme_img5 = (ImageView) findViewById(R.id.theme_img5);

        theme_title2 = (TextView) findViewById(R.id.theme_title2);
        theme_title3 = (TextView) findViewById(R.id.theme_title3);
        theme_title4 = (TextView) findViewById(R.id.theme_title4);
        theme_title5 = (TextView) findViewById(R.id.theme_title5);

        theme_travel_layout.setVisibility(View.GONE);
        theme_img2.setVisibility(View.GONE);
        theme_img3.setVisibility(View.GONE);
        theme_img4.setVisibility(View.GONE);
        theme_img5.setVisibility(View.GONE);
        theme_title2.setVisibility(View.GONE);
        theme_title3.setVisibility(View.GONE);
        theme_title4.setVisibility(View.GONE);
        theme_title5.setVisibility(View.GONE);

        handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Eye2Web 컨텐츠 가져오기 1
                int themeCategory = Integer.parseInt(getResources().getString(R.string.travel_top_category));    // Travel 대 카테고리
                int themePage = Integer.parseInt(getResources().getString(R.string.eye2web_page1));
                int themePerPage = Integer.parseInt(getResources().getString(R.string.eye2web_per_page1));
                eye2webJsonList = getEye2WebContent(themeCategory, themePage, themePerPage);

                // Eye2Web 컨텐츠 가져오기 2
                //int eye2webCate = Integer.parseInt(getResources().getString(R.string.travel_main_category));
                //int eye2webPage = Integer.parseInt(getResources().getString(R.string.eye2web_page2));
                //int eye2webPer_page = Integer.parseInt(getResources().getString(R.string.eye2web_per_page2));
                //eye2webContentList2 = getEye2WebContent(eye2webCate, eye2webPage, eye2webPer_page);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(null != eye2webJsonList && 0 < eye2webJsonList.size()) {
                            if(theme_travel_layout.getVisibility() == View.GONE) {
                                theme_travel_layout.setVisibility(View.VISIBLE);
                            }
                            Picasso.get().load(eye2webJsonList.get(0).getImg()).placeholder(R.mipmap.logo_final).into(theme_img);
                            //Glide.with(getApplicationContext())
                            //        .load(eye2WebContentList1.get(0).getFeature_media_url())
                            //        .apply(new RequestOptions().override(960, 640))
                            //        .into(theme_img);
                            if(20 > eye2webJsonList.get(0).getTitle().length()) {
                                theme_title.setText(eye2webJsonList.get(0).getTitle());
                            } else {
                                theme_title.setText(eye2webJsonList.get(0).getTitle().substring(0, 20) + "...");
                            }
                            mainContentId = eye2webJsonList.get(0).getContent_id();

                            if(2 < eye2webJsonList.size() && 4 >= eye2webJsonList.size()) {
                                if(theme_img2.getVisibility() == View.GONE) {
                                    theme_img2.setVisibility(View.VISIBLE);
                                }
                                if(theme_title2.getVisibility() == View.GONE) {
                                    theme_title2.setVisibility(View.VISIBLE);
                                }
                                Picasso.get().load(eye2webJsonList.get(1).getImg()).placeholder(R.mipmap.logo_final).into(theme_img2);
                                if(20 > eye2webJsonList.get(1).getTitle().length()) {
                                    theme_title2.setText(eye2webJsonList.get(1).getTitle());
                                } else {
                                    theme_title2.setText(eye2webJsonList.get(1).getTitle().substring(0, 20) + "...");
                                }
                                subContentId1 = eye2webJsonList.get(1).getContent_id();

                                if(theme_img3.getVisibility() == View.GONE) {
                                    theme_img3.setVisibility(View.VISIBLE);
                                }
                                if(theme_title3.getVisibility() == View.GONE) {
                                    theme_title3.setVisibility(View.VISIBLE);
                                }
                                Picasso.get().load(eye2webJsonList.get(2).getImg()).placeholder(R.mipmap.logo_final).into(theme_img3);
                                if(20 > eye2webJsonList.get(2).getTitle().length()) {
                                    theme_title3.setText(eye2webJsonList.get(2).getTitle());
                                } else {
                                    theme_title3.setText(eye2webJsonList.get(2).getTitle().substring(0, 20) + "...");
                                }
                                subContentId2 = eye2webJsonList.get(2).getContent_id();
                            } else if(4 < eye2webJsonList.size()) {
                                if(theme_img2.getVisibility() == View.GONE) {
                                    theme_img2.setVisibility(View.VISIBLE);
                                }
                                if(theme_title2.getVisibility() == View.GONE) {
                                    theme_title2.setVisibility(View.VISIBLE);
                                }
                                Picasso.get().load(eye2webJsonList.get(1).getImg()).placeholder(R.mipmap.logo_final).into(theme_img2);
                                if(20 > eye2webJsonList.get(1).getTitle().length()) {
                                    theme_title2.setText(eye2webJsonList.get(1).getTitle());
                                } else {
                                    theme_title2.setText(eye2webJsonList.get(1).getTitle().substring(0, 20) + "...");
                                }
                                subContentId1 = eye2webJsonList.get(1).getContent_id();

                                if(theme_img3.getVisibility() == View.GONE) {
                                    theme_img3.setVisibility(View.VISIBLE);
                                }
                                if(theme_title3.getVisibility() == View.GONE) {
                                    theme_title3.setVisibility(View.VISIBLE);
                                }
                                Picasso.get().load(eye2webJsonList.get(2).getImg()).placeholder(R.mipmap.logo_final).into(theme_img3);
                                if(20 > eye2webJsonList.get(2).getTitle().length()) {
                                    theme_title3.setText(eye2webJsonList.get(2).getTitle());
                                } else {
                                    theme_title3.setText(eye2webJsonList.get(2).getTitle().substring(0, 20) + "...");
                                }
                                subContentId2 = eye2webJsonList.get(2).getContent_id();

                                if(theme_img4.getVisibility() == View.GONE) {
                                    theme_img4.setVisibility(View.VISIBLE);
                                }
                                if(theme_title4.getVisibility() == View.GONE) {
                                    theme_title4.setVisibility(View.VISIBLE);
                                }
                                Picasso.get().load(eye2webJsonList.get(3).getImg()).placeholder(R.mipmap.logo_final).into(theme_img4);
                                if(20 > eye2webJsonList.get(3).getTitle().length()) {
                                    theme_title4.setText(eye2webJsonList.get(3).getTitle());
                                } else {
                                    theme_title4.setText(eye2webJsonList.get(3).getTitle().substring(0, 20) + "...");
                                }
                                subContentId3 = eye2webJsonList.get(3).getContent_id();

                                if(theme_img5.getVisibility() == View.GONE) {
                                    theme_img5.setVisibility(View.VISIBLE);
                                }
                                if(theme_title5.getVisibility() == View.GONE) {
                                    theme_title5.setVisibility(View.VISIBLE);
                                }
                                Picasso.get().load(eye2webJsonList.get(4).getImg()).placeholder(R.mipmap.logo_final).into(theme_img5);
                                if(20 > eye2webJsonList.get(4).getTitle().length()) {
                                    theme_title5.setText(eye2webJsonList.get(4).getTitle());
                                } else {
                                    theme_title5.setText(eye2webJsonList.get(4).getTitle().substring(0, 20) + "...");
                                }
                                subContentId4 = eye2webJsonList.get(4).getContent_id();
                            }
                        } else {
                            theme_travel_layout.setVisibility(View.GONE);
                        }
                    }
                });

            }
        }).start();

        this.backPressCloseHandler = new BackPressCloseHandler(this);    // 뒤로가기 처리
    }

    public List<String> getCityList() {
        List<String> resultList = new ArrayList<String>();

        resultList.add("02");    // 서울
        resultList.add("032");   // 인천
        resultList.add("051");   // 부산
        resultList.add("053");   // 대구
        resultList.add("062");   // 광주
        resultList.add("042");   // 대전
        resultList.add("052");   // 울산
        resultList.add("044");   // 세종
        resultList.add("031");   // 경기
        resultList.add("033");   // 강원
        resultList.add("043");   // 충북
        resultList.add("041");   // 충남
        resultList.add("063");   // 전북
        resultList.add("061");   // 전남
        resultList.add("054");   // 경북
        resultList.add("055");   // 경남
        resultList.add("064");   // 제주

        return resultList;
    }

    public void onContentImgClicked(View v) {
        eye2webIntent = new Intent(getApplicationContext(), ContentDetailActivity.class);
        eye2webIntent.putExtra("contentId", mainContentId);
        startActivity(eye2webIntent);
    }

    public void onContentTextClicked(View v) {
        eye2webIntent = new Intent(getApplicationContext(), ContentDetailActivity.class);
        eye2webIntent.putExtra("contentId", mainContentId);
        startActivity(eye2webIntent);
    }

    /**
     * @parameter :
     * @Date : 2018. 5. 10. PM 12:22
     * @Author : Andrew Kim
     * @Description : 지역 리스트 가져오기
    **/
    public ArrayList<AreaListItem> getAreaList() {
        ArrayList<AreaListItem> resultList = new ArrayList<AreaListItem>();
        areaApiService = new AreaApiService();

        String addr = getResources().getString(R.string.apiUrl);
        String serviceKey = getResources().getString(R.string.apiKey);
        String serviceGu = "areaCode";

        try {
            // 키워드 검색으로 검색방식 변경 - 기존 지역 리스트 api 호출하여 지역 리스트 가져오는 방식에서 수동으로 카테고리 설정하는 방식으로 변경
            // spinner data 를 수동으로 생성
            AreaListItem initial = new AreaListItem("0", "선택", 0);
            // 검색용 카테고리 목록 생성
            AreaListItem travel = new AreaListItem("12", "관광지", 1);
            AreaListItem culture = new AreaListItem("14", "문화시설", 2);
            AreaListItem festival = new AreaListItem("15", "축제/공연", 3);
            AreaListItem course = new AreaListItem("25", "여행코스", 4);
            AreaListItem reports = new AreaListItem("28", "레포츠", 5);
            AreaListItem stay = new AreaListItem("32", "숙박", 6);
            AreaListItem shop = new AreaListItem("38", "쇼핑", 7);
            AreaListItem food = new AreaListItem("39", "맛집", 8);

            resultList.add(initial);
            resultList.add(travel);
            resultList.add(culture);
            resultList.add(festival);
            resultList.add(course);
            resultList.add(reports);
            resultList.add(stay);
            resultList.add(shop);
            resultList.add(food);

            Collections.sort(resultList);
        } catch (Exception e) {
            Log.e("Error", "Error : " + e.toString());
        }

        return resultList;
    }

    public List<Eye2WebJson> getEye2WebContent(int category, int page, int per_page) {

        eye2WebApiService = new Eye2WebApiService();
        //List<Eye2WebContent> resultList = new ArrayList<Eye2WebContent>();
        List<Eye2WebJson> resultList = new ArrayList<Eye2WebJson>();

        try {
            //resultList = eye2WebApiService.getEye2WebContent(category, page, per_page);
            resultList = eye2WebApiService.getEye2WebJsonData(category, authKey, page, per_page);

            /**
            if(null != resultList && 0 < resultList.size()) {
                if(1 == per_page) {
                    if(0 < resultList.get(0).getFeatured_media()) {
                        int id = 0;
                        eye2WebMediaContent = new Eye2WebMediaContent();
                        id = resultList.get(0).getFeatured_media();
                        eye2WebMediaContent = eye2WebApiService.getEye2WebMediaContent(id);

                        if(null != eye2WebMediaContent) {
                            resultList.get(0).setFeatured_media_url(eye2WebMediaContent.getSource_url());
                        }
                    }
                } else {
                    for(int i=0; i < resultList.size(); i++) {
                        int mediaId = 0;
                        mediaId = resultList.get(i).getFeatured_media();
                        eye2WebMediaContent = new Eye2WebMediaContent();

                        eye2WebMediaContent = eye2WebApiService.getEye2WebMediaContent(mediaId);

                        if(null != eye2WebMediaContent) {
                            resultList.get(i).setFeatured_media_url(eye2WebMediaContent.getSource_url());
                        }
                    }
                }
            }
             **/
        } catch (Exception e) {
            Log.e("Error", "Eye2Web Content Api Call Error : " + e.toString());
        }


        return resultList;
    }

    /**
     * @parameter :
     * @Date : 2018. 5. 10. PM 12:23
     * @Author : Andrew Kim
     * @Description : spinner 선택 시 선택된 값 확인용 itemSelectedListener
    **/
    public AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            AreaListItem item = (AreaListItem) parent.getAdapter().getItem(position);
            //Toast.makeText(getApplicationContext(), item.getName() + "-" + item.getCode() + " selected !!!", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Spinner spinner = (Spinner) findViewById(R.id.topArea);
            spinner.setPrompt("선택");
        }
    };

    /**
     * @parameter :
     * @Date : 2018. 5. 10. PM 12:24
     * @Author : Andrew Kim
     * @Description : 키워드 검색 처리
    **/
    public void onBtnSearchClicked(View v) {
        Spinner area = (Spinner) findViewById(R.id.topArea);
        EditText keyword = (EditText) findViewById(R.id.keyword);

        AreaListItem areaListItem = (AreaListItem) area.getSelectedItem();    // 선택된 spinner 객체값 받기

        String areaCodeStr = "";
        String keywordStr = "";

        areaCodeStr = areaListItem.getCode();
        keywordStr = keyword.getText().toString();

        //Toast.makeText(this, areaCodeStr + "-" + keywordStr, Toast.LENGTH_LONG).show();
        Intent searchIntent = new Intent(this, SearchListActivity.class);
        searchIntent.putExtra("areaCode", areaCodeStr);
        searchIntent.putExtra("keyword", keywordStr);
        startActivity(searchIntent);
        //finish();
    }

    public void onFoodAroundBtnClicked(View v) {
        double mapX = 0;
        double mapY = 0;
        Map<String, Object> gpsMap = new HashMap<String, Object>();
        gpsMap = getGpsInfo();

        if(null != gpsMap && 0 < gpsMap.size()) {
            mapX = (Double) gpsMap.get("mapX");
            mapY = (Double) gpsMap.get("mapY");

            Intent locIntent = new Intent(this, SearchListActivity.class);
            locIntent.putExtra("mapx", mapX);
            locIntent.putExtra("mapy", mapY);
            locIntent.putExtra("loc", "loc");
            locIntent.putExtra("aroundGu", "food");
            locIntent.putExtra("areaCode", "39");
            locIntent.putExtra("cateName", "맛집 검색 결과");
            locIntent.putExtra("searchGu", "nearby");
            startActivity(locIntent);
        }
    }

    public void onHotelAroundBtnClicked(View v) {
        double mapX = 0;
        double mapY = 0;
        Map<String, Object> gpsMap = new HashMap<String, Object>();
        gpsMap = getGpsInfo();

        if(null != gpsMap && 0 < gpsMap.size()) {
            mapX = (Double) gpsMap.get("mapX");
            mapY = (Double) gpsMap.get("mapY");

            Intent locIntent = new Intent(this, SearchListActivity.class);
            locIntent.putExtra("mapx", mapX);
            locIntent.putExtra("mapy", mapY);
            locIntent.putExtra("loc", "loc");
            locIntent.putExtra("aroundGu", "hotel");
            locIntent.putExtra("areaCode", "32");
            locIntent.putExtra("cateName", "호텔 검색 결과");
            locIntent.putExtra("searchGu", "nearby");
            startActivity(locIntent);
        }
    }

    public void onTravelAroundBtnClicked(View v) {
        double mapX = 0;
        double mapY = 0;
        Map<String, Object> gpsMap = new HashMap<String, Object>();
        gpsMap = getGpsInfo();

        if(null != gpsMap && 0 < gpsMap.size()) {
            mapX = (Double) gpsMap.get("mapX");
            mapY = (Double) gpsMap.get("mapY");

            //Log.i("info", "===============gps info : " + mapX + "=============" + mapY);
            Intent locIntent = new Intent(this, SearchListActivity.class);
            locIntent.putExtra("mapx", mapX);
            locIntent.putExtra("mapy", mapY);
            locIntent.putExtra("loc", "loc");
            locIntent.putExtra("aroundGu", "travel");
            locIntent.putExtra("areaCode", "12");
            locIntent.putExtra("cateName", "관광지 검색 결과");
            locIntent.putExtra("searchGu", "nearby");
            startActivity(locIntent);
        }
    }

    public void onSubContentImg1Clicked(View v) {
        eye2webIntent = new Intent(getApplicationContext(), ContentDetailActivity.class);
        eye2webIntent.putExtra("contentId", subContentId1);
        startActivity(eye2webIntent);
    }

    public void onSubContentText1Clicked(View v) {
        eye2webIntent = new Intent(getApplicationContext(), ContentDetailActivity.class);
        eye2webIntent.putExtra("contentId", subContentId1);
        startActivity(eye2webIntent);
    }

    public void onSubContentImg2Clicked(View v) {
        eye2webIntent = new Intent(getApplicationContext(), ContentDetailActivity.class);
        eye2webIntent.putExtra("contentId", subContentId2);
        startActivity(eye2webIntent);
    }

    public void onSubContentText2Clicked(View v) {
        eye2webIntent = new Intent(getApplicationContext(), ContentDetailActivity.class);
        eye2webIntent.putExtra("contentId", subContentId2);
        startActivity(eye2webIntent);
    }

    public void onSubContentImg3Clicked(View v) {
        eye2webIntent = new Intent(getApplicationContext(), ContentDetailActivity.class);
        eye2webIntent.putExtra("contentId", subContentId3);
        startActivity(eye2webIntent);
    }

    public void onSubContentText3Clicked(View v) {
        eye2webIntent = new Intent(getApplicationContext(), ContentDetailActivity.class);
        eye2webIntent.putExtra("contentId", subContentId3);
        startActivity(eye2webIntent);
    }

    public void onSubContentImg4Clicked(View v) {
        eye2webIntent = new Intent(getApplicationContext(), ContentDetailActivity.class);
        eye2webIntent.putExtra("contentId", subContentId4);
        startActivity(eye2webIntent);
    }

    public void onSubContentText4Clicked(View v) {
        eye2webIntent = new Intent(getApplicationContext(), ContentDetailActivity.class);
        eye2webIntent.putExtra("contentId", subContentId4);
        startActivity(eye2webIntent);
    }

    public void getRecomFood() {
        String addr = getResources().getString(R.string.apiUrl) + "searchKeyword?serviceKey=";
        String serviceKey = getResources().getString(R.string.apiKey);
    }

    /**
     * @parameter :
     * @Date : 2018. 5. 11. PM 2:42
     * @Author : Andrew Kim
     * @Description : 뒤로가기처리 - index 에서는 뒤로가기 버튼을 두 번 클릭 시 앱이 종료되도록 처리해야 하므로 별도의 핸들러로 처리
    **/
    public void onBackPressed() {
        this.backPressCloseHandler.onBackPressed();
    }
}
