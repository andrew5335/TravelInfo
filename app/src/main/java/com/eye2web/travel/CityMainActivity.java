package com.eye2web.travel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eye2web.travel.apivo.Eye2WebJson;
import com.eye2web.travel.apivo.KakaoImage;
import com.eye2web.travel.apivo.OpenWeatherMap;
import com.eye2web.travel.service.Eye2WebApiService;
import com.eye2web.travel.service.KakaoApiService;
import com.eye2web.travel.service.WeatherApiService;
import com.eye2web.travel.service.WikiApiService;
import com.eye2web.travel.util.CommonUtil;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * @File : CityMainActivity
 * @Date : 2018. 6. 4. PM 2:25
 * @Author : Andrew Kim
 * @Version : 1.0.0
 * @Description : 초기화면에서 도시 선택 시 연결되는 도시 메인 Activity
**/
public class CityMainActivity extends BaseActivity {

    //private GoogleApiService googleApiService;
    private KakaoApiService kakaoApiService;
    private WikiApiService wikiApiService;
    private WeatherApiService weatherApiService;
    private Eye2WebApiService eye2WebApiService;

    private CommonUtil commonUtil;

    private KakaoImage kakaoImage;
    private String wikiSummaryStr;
    private String kakaoSearchKeyWord;
    private int kakaoPage = 1;
    private int kakaoSize = 10;
    private OpenWeatherMap openWeatherMap;
    private String weatherAppKey;
    private String weatherUnits;
    private String weatherIconUrl;
    private String weatherLang;

    //private List<Eye2WebContent> eye2WebContentList1;
    //private Eye2WebMediaContent eye2WebMediaContent;
    //private List<Eye2WebContent> eye2webContentList2;
    private List<Eye2WebJson> eye2webJsonList;

    private String cityGu;    // 도시 구분값
    private String citySetting;    // 도시 구분값에 따른 도시 세팅
    private static String imageUrl;    // 이미지 파일 주소 url
    private String areaGu;    // 지역 구분값 (cityGu와 동일)
    private String cateGu;    // 카테고리 구분값 (관광, 숙박, 축제 등)
    private String cateName;    // 카테고리명
    private Intent cateIntent;

    private Intent eye2webIntent;

    // google place 관련 변수
    /**
    private boolean googleApiClientConYn = false;
    private static String googlePlacesApiNearbySearchUrl;    // 주변검색 요청 url
    private static String googlePlacesApiTextSearchUrl;    // 키워드검색 요청 url
    private static String googlePlacesApiDetailInfoUrl;    // 상세정보 요청 url
    private static String googlePlacesApiPhotoUrl;    // 사진정보 요청 url
    private static String googleApiKey;    // google api 호출용 key
    private static String googleRetType;    // google return type - json / xml 중 설정 (기본 : json)
    private String googleSearchType;    // 검색 타입 - 관광, 맛집, 문화시설 등 구분값
     **/
    private double latitude = 0;    // 위도
    private double longitude = 0;    // 경도
    private String searchKeyword;    // 키워드 검색 시 사용할 기본 키워드 - 검색타입과 + 로 연결되어 키워드 검색에 사용됨 (ex. 서울+맛집)

    // wikipedia 관련 변수
    private static String wikipedia_api_summary;    // 위키피디어 요약정보 요청 url
    private static String wikipieda_api_detail;    // 위키피디어 세부정보 요청 url
    private String wikiSearchKeyword;    // wikipedia 연동을 위한 검색
    private String cateCode = "";    // 지역코드
    private String gu = "1";    // 도시 메인 혹은 리스트 유무 구분값 - 1 : 도시메인 / 2 : 각 카테고리 리스트

    // Eye2Web 관련 변수
    private int eye2webCate = 0;

    private ImageView imageView;
    private TextView city_summary_text;
    private ImageView city_main_photo;
    private ProgressBar progressBar;
    private LinearLayout weatherLayout;
    private ImageView weatherIcon;
    private TextView weatherDesc;
    private TextView weatherTemp;
    private ImageView theme_img;
    private ImageView theme_img2;
    private ImageView theme_img3;
    private ImageView theme_img4;
    private ImageView theme_img5;
    private TextView theme_title;
    private TextView theme_title2;
    private TextView theme_title3;
    private TextView theme_title4;
    private TextView theme_title5;
    private LinearLayout theme_layout;
    private int mainContentId = 0;
    private int subContentId1 = 0;
    private int subContentId2 = 0;
    private int subContentId3 = 0;
    private int subContentId4 = 0;

    private String authKey;

    private Handler handler = null;

    //private GooglePlaceVO googlePlaceVO = new GooglePlaceVO();

    private AdView mAdView1;

    /**
     * @parameter :
     * @Date : 2018. 7. 5. PM 5:26
     * @Author : Andrew Kim
     * @Description : 도시 메인 화면 생성
    **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_city_main);

        String admobId = getResources().getString(R.string.google_admob_id);
        MobileAds.initialize(this, admobId);    // admob 초기화

        // 배너 광고는 한 activity에 1개만 삽입 가능
        mAdView1 = findViewById(R.id.adView);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mAdView1.loadAd(adRequest1);

        city_main_photo = (ImageView) findViewById(R.id.city_main_photo);
        city_summary_text = (TextView) findViewById(R.id.city_main_text);

        city_summary_text = (TextView) findViewById(R.id.city_main_text);
        city_main_photo = (ImageView) findViewById(R.id.city_main_photo);

        weatherLayout = (LinearLayout) findViewById(R.id.weather_layout);
        weatherDesc = (TextView) findViewById(R.id.weather_desc);
        weatherIcon = (ImageView) findViewById(R.id.weather_icon);
        weatherTemp = (TextView) findViewById(R.id.weather_temp);

        theme_layout = (LinearLayout) findViewById(R.id.theme_travel_layout);
        theme_img = (ImageView) findViewById(R.id.theme_img);
        theme_title = (TextView) findViewById(R.id.theme_title);
        theme_img2 = (ImageView) findViewById(R.id.theme_img2);
        theme_title2 = (TextView) findViewById(R.id.theme_title2);
        theme_img3 = (ImageView) findViewById(R.id.theme_img3);
        theme_title3 = (TextView) findViewById(R.id.theme_title3);
        theme_img4 = (ImageView) findViewById(R.id.theme_img4);
        theme_title4 = (TextView) findViewById(R.id.theme_title4);
        theme_img5 = (ImageView) findViewById(R.id.theme_img5);
        theme_title5 = (TextView) findViewById(R.id.theme_title5);

        city_summary_text.setVisibility(View.GONE);
        city_main_photo.setVisibility(View.GONE);
        weatherLayout.setVisibility(View.GONE);
        weatherDesc.setVisibility(View.GONE);
        weatherIcon.setVisibility(View.GONE);
        weatherTemp.setVisibility(View.GONE);
        theme_layout.setVisibility(View.GONE);
        theme_img2.setVisibility(View.GONE);
        theme_title2.setVisibility(View.GONE);
        theme_img3.setVisibility(View.GONE);
        theme_title3.setVisibility(View.GONE);
        theme_img4.setVisibility(View.GONE);
        theme_title4.setVisibility(View.GONE);
        theme_img5.setVisibility(View.GONE);
        theme_title5.setVisibility(View.GONE);

        progressBar = (ProgressBar) findViewById(R.id.city_main_progressbar);

        authKey = getResources().getString(R.string.auth_key);

        //kakaoApiService = new KakaoApiService();
        //kakaoImage = new KakaoImage();

        /**
        googlePlacesApiNearbySearchUrl = getResources().getString(R.string.google_places_api_nearby_search_url);
        googlePlacesApiTextSearchUrl = getResources().getString(R.string.google_places_api_text_search_url);
        googlePlacesApiDetailInfoUrl = getResources().getString(R.string.google_places_api_detail_info_url);
        googlePlacesApiPhotoUrl = getResources().getString(R.string.google_places_api_photo_url);
        googleApiKey = getResources().getString(R.string.google_maps_key);
         **/

        // 위키피디어 텍스트 조회를 위한 관련값 세팅
        wikipedia_api_summary = getResources().getString(R.string.wikipedia_rest_api_summary_url);
        wikipieda_api_detail = getResources().getString(R.string.wikipedia_rest_api_detail_url);

        // 날씨정보 조회를 위한 관련값 세팅
        weatherAppKey = getResources().getString(R.string.weather_api_key);
        weatherIconUrl = getResources().getString(R.string.weather_icon_url);
        weatherLang = getResources().getString(R.string.weather_lang);
        weatherUnits = "metric";

        /**
        googleRetType = "json";
        googlePlacesApiNearbySearchUrl = googlePlacesApiNearbySearchUrl + googleRetType;
        googlePlacesApiTextSearchUrl = googlePlacesApiTextSearchUrl + googleRetType;
        googlePlacesApiDetailInfoUrl = googlePlacesApiDetailInfoUrl + googleRetType;
         **/

        // 이미지 정보 조회를 위한 관련값 세팅
        imageUrl = getResources().getString(R.string.image_url);
        imageUrl = imageUrl + "/images/indexmenu/";
        imageView = (ImageView) findViewById(R.id.city_main_image);
        //googleApiClientConYn = getGoogleApiClientConnect();
        Intent cityMainIntent = getIntent();
        cityGu = cityMainIntent.getStringExtra("cityGu");

        // 도시 구분값에 따른 이미지, 위도, 경도, 검색 키워드, 위키피디어 검색 키워드, 카카오 검색 키워드 설정
        switch (cityGu) {
            case "1" :
                Picasso.get().load(imageUrl + "seoul.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 37.5652154;
                longitude = 126.9195108;
                searchKeyword = "서울";
                wikiSearchKeyword = "서울특별시";
                kakaoSearchKeyWord = "서울특별시 명동";
                eye2webCate = 32;
                break;

            case "2" :
                Picasso.get().load(imageUrl + "incheon.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 37.4646656;
                longitude = 126.6043108;
                searchKeyword = "인천";
                wikiSearchKeyword = "인천광역시";
                kakaoSearchKeyWord = "인천광역시 야경";
                eye2webCate = 33;
                break;

            case "3" :
                Picasso.get().load(imageUrl + "daejeon.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 36.373164;
                longitude = 127.3537807;
                searchKeyword = "대전";
                wikiSearchKeyword = "대전광역시";
                kakaoSearchKeyWord = "대전광역시 유성온천";
                eye2webCate = 34;
                break;

            case "4" :
                Picasso.get().load(imageUrl + "daegu.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 35.8798145;
                longitude = 128.5316807;
                searchKeyword = "대구";
                wikiSearchKeyword = "대구광역시";
                kakaoSearchKeyWord = "대구 동성로";
                eye2webCate = 35;
                break;

            case "5" :
                Picasso.get().load(imageUrl + "kwangju.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 35.1767651;
                longitude = 126.8087807;
                searchKeyword = "광주";
                wikiSearchKeyword = "광주광역시";
                kakaoSearchKeyWord = "광주광역시 야경";
                eye2webCate = 37;
                break;

            case "6" :
                Picasso.get().load(imageUrl + "busan.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 35.1645702;
                longitude = 129.0017608;
                searchKeyword = "부산";
                wikiSearchKeyword = "부산광역시";
                kakaoSearchKeyWord = "부산광역시 야경";
                eye2webCate = 36;
                break;

            case "7" :
                Picasso.get().load(imageUrl + "ulsan.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 35.5621694;
                longitude = 129.2814608;
                searchKeyword = "울산";
                wikiSearchKeyword = "울산광역시";
                kakaoSearchKeyWord = "울산광역시 조선";
                eye2webCate = 38;
                break;

            case "8" :
                Picasso.get().load(imageUrl + "sejong.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 36.4801027;
                longitude = 127.2868467;
                searchKeyword = "세종";
                wikiSearchKeyword = "세종특별자치시청";
                kakaoSearchKeyWord = "세종시 야경";
                eye2webCate = 39;
                break;

            case "31" :
                Picasso.get().load(imageUrl + "kyunggi2.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 37.5985327;
                longitude = 126.8157339;
                searchKeyword = "경기";
                wikiSearchKeyword = "경기도";
                kakaoSearchKeyWord = "경기도 야경";
                eye2webCate = 40;
                break;

            case "32" :
                Picasso.get().load(imageUrl + "kangwon.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 37.8662752;
                longitude = 127.6873691;
                searchKeyword = "강원";
                wikiSearchKeyword = "강원도";
                kakaoSearchKeyWord = "강원도 항구";
                eye2webCate = 41;
                break;

            case "33" :
                Picasso.get().load(imageUrl + "chungbook.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 36.6376267;
                longitude = 127.6867254;
                searchKeyword = "충북";
                wikiSearchKeyword = "충청북도";
                kakaoSearchKeyWord = "충청북도 호암지";
                eye2webCate = 42;
                break;

            case "34" :
                Picasso.get().load(imageUrl + "chungnam.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 36.5320992;
                longitude = 125.6726602;
                searchKeyword = "충남";
                wikiSearchKeyword = "충청남도";
                kakaoSearchKeyWord = "충청남도 보령군 빙도";
                eye2webCate = 43;
                break;

            case "35" :
                Picasso.get().load(imageUrl + "kyungbook.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 36.5594479;
                longitude = 129.2852767;
                searchKeyword = "경북";
                wikiSearchKeyword = "경상북도";
                kakaoSearchKeyWord = "경상북도 경주 석굴암";
                eye2webCate = 46;
                break;

            case "36" :
                Picasso.get().load(imageUrl + "kyungnam.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 35.1813853;
                longitude = 127.8306548;
                searchKeyword = "경남";
                wikiSearchKeyword = "경상남도";
                kakaoSearchKeyWord = "경상남도 진해 군항제";
                eye2webCate = 47;
                break;

            case "37" :
                Picasso.get().load(imageUrl + "jeonbook.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 35.7278162;
                longitude = 126.6498459;
                searchKeyword = "전북";
                wikiSearchKeyword = "전라북도";
                kakaoSearchKeyWord = "전라북도 한옥마을";
                eye2webCate = 44;
                break;

            case "38" :
                Picasso.get().load(imageUrl + "jeonnam.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 34.6959785;
                longitude = 125.9376253;
                searchKeyword = "전남";
                wikiSearchKeyword = "전라남도";
                kakaoSearchKeyWord = "전라남도 순천만 자연생태공원";
                eye2webCate = 45;
                break;

            case "39" :
                Picasso.get().load(imageUrl + "jeju.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 33.5784808;
                longitude = 126.2928556;
                searchKeyword = "제주";
                wikiSearchKeyword = "제주도";
                kakaoSearchKeyWord = "제주도 야경";
                eye2webCate = 48;
                break;
        }

        //GooglePlaceVO googlePlaceVO = new GooglePlaceVO();

        //kakaoImage = getKakaoImage();

        handler = new Handler();    // main thread 내에서는 화면 구성을 처리할 수 없으므로 별도 handler 생성하여 해당 handler에서 화면 처리
        new Thread(new Runnable() {
            @Override
            public void run() {
                //getContent();
                getKakaoImage();    // 카카오 이미지 정보 가져오기
                getWikiSummary();    // 위키피디어 설명글 가져오기
                getWeather();    // 날씨정보 가져오기

                // Eye2Web 컨텐츠 가져오기 1
                int eye2webPage1 = Integer.parseInt(getResources().getString(R.string.eye2web_page1));
                int eye2webPer_page1 = Integer.parseInt(getResources().getString(R.string.eye2web_per_page1));
                eye2webJsonList = getEye2WebContent(eye2webCate, eye2webPage1, eye2webPer_page1);

                // Eye2Web 컨텐츠 가져오기 2
                //int eye2webPage2 = Integer.parseInt(getResources().getString(R.string.eye2web_page2));
                //int eye2webPer_page2 = Integer.parseInt(getResources().getString(R.string.eye2web_per_page2));
                //eye2webContentList2 = getEye2WebContent(eye2webCate, eye2webPage2, eye2webPer_page2);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //progressBar.setVisibility(View.VISIBLE);
                        //if(null != googlePlaceVO) {
                        commonUtil = new CommonUtil();

                            if(null != kakaoImage) {
                                if(city_main_photo.getVisibility() == View.GONE) {
                                    city_main_photo.setVisibility(View.VISIBLE);
                                }
                                //Picasso.get().load(kakaoImage.getDocuments().get(0).getImage_url())
                                //        .resize(400, 300).centerCrop()
                                //        .placeholder(R.mipmap.logo_final).into(city_main_photo);
                                Glide.with(getApplicationContext()).load(kakaoImage.getDocuments().get(0).getImage_url())
                                        .apply(new RequestOptions().override(400, 300))
                                        .into(city_main_photo);
                            } else {
                                city_main_photo.setVisibility(View.GONE);
                            }

                            if(null != wikiSummaryStr && !"".equalsIgnoreCase(wikiSummaryStr) && 0 < wikiSummaryStr.length()) {
                                if(city_summary_text.getVisibility() == View.GONE) {
                                    city_summary_text.setVisibility(View.VISIBLE);
                                }
                                city_summary_text.setText(wikiSummaryStr);
                            } else {
                                city_summary_text.setVisibility(View.GONE);
                            }

                            if(null != openWeatherMap) {
                                if(weatherLayout.getVisibility() == View.GONE) {
                                    weatherLayout.setVisibility(View.VISIBLE);
                                }

                                if(null != openWeatherMap.getWeather().get(0).getIcon()
                                        && !"".equalsIgnoreCase(openWeatherMap.getWeather().get(0).getIcon())) {
                                    if(weatherIcon.getVisibility() == View.GONE) {
                                        weatherIcon.setVisibility(View.VISIBLE);
                                    }
                                    Glide.with(getApplicationContext())
                                            .load(weatherIconUrl + openWeatherMap.getWeather().get(0).getIcon() + ".png")
                                            .apply(new RequestOptions().override(60, 60))
                                            .into(weatherIcon);
                                } else {
                                    weatherIcon.setVisibility(View.GONE);
                                }

                                if(null != openWeatherMap.getWeather().get(0).getDescription()
                                        && !"".equalsIgnoreCase(openWeatherMap.getWeather().get(0).getDescription())) {
                                    if(weatherDesc.getVisibility() == View.GONE) {
                                        weatherDesc.setVisibility(View.VISIBLE);
                                    }
                                    String weatherDescStr = openWeatherMap.getWeather().get(0).getDescription();

                                    String weatherCode = openWeatherMap.getWeather().get(0).getId();
                                    String codeStr = "";
                                    codeStr = commonUtil.getWeatherDesc(weatherCode);

                                    if(null != codeStr && !"".equalsIgnoreCase(codeStr)) {
                                        weatherDescStr = weatherDescStr + ", " + codeStr;
                                    }
                                    weatherDesc.setText(weatherDescStr);
                                } else {
                                    weatherDesc.setVisibility(View.GONE);
                                }

                                if(null != openWeatherMap.getMain().getTemp()
                                        && !"".equalsIgnoreCase(openWeatherMap.getMain().getTemp())) {
                                    if(weatherTemp.getVisibility() == View.GONE) {
                                        weatherTemp.setVisibility(View.VISIBLE);
                                    }
                                    String weatherTempStr = "현재 : " + openWeatherMap.getMain().getTemp() + "℃";
                                    String weatherMinTempStr = "";
                                    String weatherMaxTempStr = "";

                                    if(null != openWeatherMap.getMain().getTemp_min()
                                            && !"".equalsIgnoreCase(openWeatherMap.getMain().getTemp_min())) {
                                        weatherMinTempStr = openWeatherMap.getMain().getTemp_min();
                                    }

                                    if(null != openWeatherMap.getMain().getTemp_max()
                                            && !"".equalsIgnoreCase(openWeatherMap.getMain().getTemp_max())) {
                                        weatherMaxTempStr = openWeatherMap.getMain().getTemp_max();
                                    }

                                    if((null != weatherMinTempStr && !"".equalsIgnoreCase(weatherMinTempStr))
                                            && (null != weatherMaxTempStr && !"".equalsIgnoreCase(weatherMaxTempStr))) {
                                        weatherTempStr = weatherTempStr
                                                + " (최저 : " + weatherMinTempStr + "℃"
                                                + " / 최고 : " + weatherMaxTempStr + "℃"
                                                + ")";
                                    }
                                    weatherTemp.setText(weatherTempStr);
                                } else {
                                    weatherTemp.setVisibility(View.GONE);
                                }
                            } else {
                                weatherLayout.setVisibility(View.GONE);
                                weatherDesc.setVisibility(View.GONE);
                                weatherIcon.setVisibility(View.GONE);
                                weatherTemp.setVisibility(View.GONE);
                            }

                        if(null != eye2webJsonList && 0 < eye2webJsonList.size()) {
                            if(theme_layout.getVisibility() == View.GONE) {
                                theme_layout.setVisibility(View.VISIBLE);
                            }
                            Picasso.get().load(eye2webJsonList.get(0).getImg()).placeholder(R.mipmap.logo_final).into(theme_img);
                            //Glide.with(getApplicationContext())
                            //        .load(eye2WebContentList1.get(0).getFeature_media_url())
                            //        .apply(new RequestOptions().override(960, 640))
                            //        .into(theme_img);
                            theme_title.setText(eye2webJsonList.get(0).getTitle());
                            mainContentId = eye2webJsonList.get(0).getContent_id();

                            if(2 < eye2webJsonList.size() && 4 >= eye2webJsonList.size()) {
                                if(theme_img2.getVisibility() == View.GONE) {
                                    theme_img2.setVisibility(View.VISIBLE);
                                }
                                if(theme_title2.getVisibility() == View.GONE) {
                                    theme_title2.setVisibility(View.VISIBLE);
                                }
                                Picasso.get().load(eye2webJsonList.get(1).getImg()).placeholder(R.mipmap.logo_final).into(theme_img2);
                                theme_title2.setText(eye2webJsonList.get(1).getTitle());
                                subContentId1 = eye2webJsonList.get(1).getContent_id();

                                if(theme_img3.getVisibility() == View.GONE) {
                                    theme_img3.setVisibility(View.VISIBLE);
                                }
                                if(theme_title3.getVisibility() == View.GONE) {
                                    theme_title3.setVisibility(View.VISIBLE);
                                }
                                Picasso.get().load(eye2webJsonList.get(2).getImg()).placeholder(R.mipmap.logo_final).into(theme_img3);
                                theme_title3.setText(eye2webJsonList.get(2).getTitle());
                                subContentId2 = eye2webJsonList.get(2).getContent_id();
                            } else if(4 < eye2webJsonList.size()) {
                                if(theme_img2.getVisibility() == View.GONE) {
                                    theme_img2.setVisibility(View.VISIBLE);
                                }
                                if(theme_title2.getVisibility() == View.GONE) {
                                    theme_title2.setVisibility(View.VISIBLE);
                                }
                                Picasso.get().load(eye2webJsonList.get(1).getImg()).placeholder(R.mipmap.logo_final).into(theme_img2);
                                theme_title2.setText(eye2webJsonList.get(1).getTitle());
                                subContentId1 = eye2webJsonList.get(1).getContent_id();

                                if(theme_img3.getVisibility() == View.GONE) {
                                    theme_img3.setVisibility(View.VISIBLE);
                                }
                                if(theme_title3.getVisibility() == View.GONE) {
                                    theme_title3.setVisibility(View.VISIBLE);
                                }
                                Picasso.get().load(eye2webJsonList.get(2).getImg()).placeholder(R.mipmap.logo_final).into(theme_img3);
                                theme_title3.setText(eye2webJsonList.get(2).getTitle());
                                subContentId2 = eye2webJsonList.get(2).getContent_id();

                                if(theme_img4.getVisibility() == View.GONE) {
                                    theme_img4.setVisibility(View.VISIBLE);
                                }
                                if(theme_title4.getVisibility() == View.GONE) {
                                    theme_title4.setVisibility(View.VISIBLE);
                                }
                                Picasso.get().load(eye2webJsonList.get(3).getImg()).placeholder(R.mipmap.logo_final).into(theme_img4);
                                theme_title4.setText(eye2webJsonList.get(3).getTitle());
                                subContentId3 = eye2webJsonList.get(3).getContent_id();

                                if(theme_img5.getVisibility() == View.GONE) {
                                    theme_img5.setVisibility(View.VISIBLE);
                                }
                                if(theme_title5.getVisibility() == View.GONE) {
                                    theme_title5.setVisibility(View.VISIBLE);
                                }
                                Picasso.get().load(eye2webJsonList.get(4).getImg()).placeholder(R.mipmap.logo_final).into(theme_img5);
                                theme_title5.setText(eye2webJsonList.get(4).getTitle());
                                subContentId4 = eye2webJsonList.get(4).getContent_id();
                            }
                         } else {
                            theme_layout.setVisibility(View.GONE);
                        }

                        /**
                        if(null != eye2webContentList2 && 1 < eye2webContentList2.size()) {
                            if(theme_img2.getVisibility() == View.GONE) {
                                theme_img2.setVisibility(View.VISIBLE);
                            }
                            if(theme_title2.getVisibility() == View.GONE) {
                                theme_title2.setVisibility(View.VISIBLE);
                            }
                            Picasso.get().load(eye2webContentList2.get(0).getFeature_media_url()).placeholder(R.mipmap.logo_final).into(theme_img2);
                            theme_title2.setText(eye2webContentList2.get(0).getTitle().getRendered());
                            subContentId1 = eye2webContentList2.get(0).getId();

                            if(theme_img3.getVisibility() == View.GONE) {
                                theme_img3.setVisibility(View.VISIBLE);
                            }
                            if(theme_title3.getVisibility() == View.GONE) {
                                theme_title3.setVisibility(View.VISIBLE);
                            }
                            Picasso.get().load(eye2webContentList2.get(1).getFeature_media_url()).placeholder(R.mipmap.logo_final).into(theme_img3);
                            theme_title3.setText(eye2webContentList2.get(1).getTitle().getRendered());
                            subContentId2 = eye2webContentList2.get(1).getId();
                        }
                         **/

                        progressBar.setVisibility(View.GONE);

                            /** google api 연동 제거
                            List<GooglePlaceItem> itemList = googlePlaceVO.getPlaceItem();

                            if(null != itemList && 0 < itemList.size()) {
                                for (int i = 0; i < itemList.size(); i++) {
                                    city_summary_text.setText(itemList.get(i).getSummary());
                                    city_main_photo.setVisibility(View.VISIBLE);
                                    //Picasso.get().load(itemList.get(i).getGooglePhotoUrl()).resize(400, 300).centerCrop().placeholder(R.mipmap.logo_final).into(city_main_photo);
                                    Glide.with(getApplicationContext()).load(itemList.get(i).getGooglePhotoUrl())
                                            .apply(new RequestOptions().override(400, 300)).into(city_main_photo);
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                             **/
                        //}
                    }
                });

            }
        }).start();
    }

    /**
     * @parameter :
     * @Date : 2018. 7. 5. PM 5:30
     * @Author : Andrew Kim
     * @Description : 카카오 검색을 통한 이미지 리스트 가져오기
    **/
    public void getKakaoImage() {
        String kakaoSort = "accuracy";
        kakaoApiService = new KakaoApiService();
        kakaoImage = new KakaoImage();

        try {
            //kakaoImage = new KakaoImage();
            kakaoImage = kakaoApiService.getKakaoImage(kakaoPage, kakaoSize, kakaoSearchKeyWord, kakaoSort);
            //Log.i("info", "Kakao Image Url : " + kakaoImage.getDocuments().get(0).getImage_url());
        } catch(Exception e) {
            Log.e("Error", "Kakao Image Api Call Error : " + e.toString());
        }

        //return kakaoImage;
    }

    /**
     * @parameter :
     * @Date : 2018. 7. 5. PM 5:30
     * @Author : Andrew Kim
     * @Description : 위키피디어 간략 설명글 정보 가져오기
    **/
    public void getWikiSummary() {
        wikiApiService = new WikiApiService();

        try {
            wikiSummaryStr = wikiApiService.getWikiDescription(wikipedia_api_summary, wikiSearchKeyword);
        } catch (Exception e) {
            Log.e("Error", "Wiki Summary Api Call Error : " + e.toString());
        }
    }

    /**
     * @parameter :
     * @Date : 2018. 7. 5. PM 5:30
     * @Author : Andrew Kim
     * @Description : open weather map api를 통해 날씨정보 가져오기
    **/
    public void getWeather() {
        weatherApiService = new WeatherApiService();

        try {
            openWeatherMap = weatherApiService.getWeatherInfo(String.valueOf(latitude), String.valueOf(longitude), weatherAppKey, weatherUnits, weatherLang, cityGu);
        } catch(Exception e) {
            Log.e("Error", "Weather Api Call Error : " + e.toString());
        }
    }

    public List<Eye2WebJson> getEye2WebContent(int category, int page, int per_page) {
        eye2WebApiService = new Eye2WebApiService();
        //eye2WebContentList = new ArrayList<Eye2WebContent>();
        List<Eye2WebJson> resultList = new ArrayList<Eye2WebJson>();

        try {
            resultList = eye2WebApiService.getEye2WebJsonData(category, authKey, page, per_page);

            /**
            if(null != resultList && 0 < resultList.size()) {
                if(1 == per_page) {
                    int id = 0;
                    id = resultList.get(0).getFeatured_media();
                    if(0 < id) {
                        eye2WebMediaContent = new Eye2WebMediaContent();
                        eye2WebMediaContent = eye2WebApiService.getEye2WebMediaContent(id);

                        if(null != eye2WebMediaContent.getSource_url() && !"".equalsIgnoreCase(eye2WebMediaContent.getSource_url())) {
                            resultList.get(0).setFeatured_media_url(eye2WebMediaContent.getSource_url());
                        }
                    }
                } else {
                    for(int i = 0; i < resultList.size(); i++) {
                        int mediaId = 0;
                        eye2WebMediaContent = new Eye2WebMediaContent();
                        mediaId = resultList.get(i).getFeatured_media();

                        if(0 < mediaId) {
                            eye2WebMediaContent = eye2WebApiService.getEye2WebMediaContent(mediaId);
                            if(null != eye2WebMediaContent.getSource_url() && !"".equalsIgnoreCase(eye2WebMediaContent.getSource_url())) {
                                resultList.get(i).setFeatured_media_url(eye2WebMediaContent.getSource_url());
                            }
                        }
                    }
                }
            }
             **/
        } catch(Exception e) {
            Log.e("Error", "Eye2Web Content Api Call Error : " + e.toString());
        }

        return resultList;
    }

    /**
    public void getContent() {
        //GooglePlaceVO googlePlaceVO = new GooglePlaceVO();
        googleApiService = new GoogleApiService();

        try {
            googlePlaceVO = googleApiService.getPlaceInfo(googlePlacesApiTextSearchUrl, googlePlacesApiPhotoUrl
                    , wikipedia_api_summary, wikipieda_api_detail, googleApiKey, "", latitude, longitude
                    , searchKeyword, wikiSearchKeyword, gu, "");
        } catch (Exception e) {
            Log.e("Error", "Error : " + e.toString());
        }
    }
     **/

    /**
    private class ImageGetter implements Html.ImageGetter {
        @Override
        public Drawable getDrawable(String source) {

            if(source.startsWith("https://")) {

            } else {
                //Log.i("Info", "Info url : " + source);
                source = "https:" + source;
                //Log.i("Info", "Info url : " + source);
            }

            Drawable d = null;
            URL imgUrl = null;
            URLConnection urlConnection = null;
            BufferedInputStream bufferedInputStream = null;

            try {
                imgUrl = new URL(source);
                urlConnection = imgUrl.openConnection();
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                bufferedInputStream = new BufferedInputStream(inputStream);

                Bitmap bm = BitmapFactory.decodeStream(bufferedInputStream);

                d = new BitmapDrawable(getResources(), bm);

                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);

                int width = size.x;
                int height = size.y;

                if(source.contains("23px-Disambig_grey.svg.png") || source.contains("110px-Seal_of_Seoul.svg.png")
                        || source.contains("110px-Slogan_of_Seoul_I.SEOUL.U.jpg") || source.contains("6px-Red_pog.svg.png")
                        || source.contains("22px-Flag_of_South_Korea.svg.png")) {
                    d.setBounds(0, 0, bm.getWidth(), bm.getHeight());
                } else {
                    d.setBounds(0, 0, width, (height * d.getIntrinsicHeight() / d.getIntrinsicWidth()));
                }
            } catch (Exception e) {
                Log.e("Error", "Error : " + e.toString());
            }

            return d;
        }
    };
     **/

    public void travelBtnClicked(View v) {
        cateIntent = new Intent(this, SearchListActivity.class);
        cateIntent.putExtra("searchKeyword", searchKeyword);
        //cateIntent.putExtra("searchApiUrl", googlePlacesApiTextSearchUrl);
        //cateIntent.putExtra("photoApiUrl", googlePlacesApiPhotoUrl);
        cateIntent.putExtra("summaryApiUrl", wikipedia_api_summary);
        cateIntent.putExtra("wikiDetailApiUrl", wikipieda_api_detail);
        //cateIntent.putExtra("googleKey", googleApiKey);
        cateIntent.putExtra("nextPageToken", "");
        cateIntent.putExtra("lat", latitude);
        cateIntent.putExtra("lng", longitude);
        cateIntent.putExtra("wikiSearchKeyword", wikiSearchKeyword);
        cateIntent.putExtra("gu", "2");
        cateIntent.putExtra("cateGu", "관광지");
        cateIntent.putExtra("areaCode", "12");
        cateIntent.putExtra("keyword", searchKeyword);
        cateIntent.putExtra("cityGu", cityGu);
        cateIntent.putExtra("callType", "area");

        startActivity(cateIntent);
    }

    public void hotelBtnClicked(View v) {
        cateIntent = new Intent(this, SearchListActivity.class);
        cateIntent.putExtra("searchKeyword", searchKeyword);
        //cateIntent.putExtra("searchApiUrl", googlePlacesApiTextSearchUrl);
        //cateIntent.putExtra("photoApiUrl", googlePlacesApiPhotoUrl);
        cateIntent.putExtra("summaryApiUrl", wikipedia_api_summary);
        cateIntent.putExtra("wikiDetailApiUrl", wikipieda_api_detail);
        //cateIntent.putExtra("googleKey", googleApiKey);
        cateIntent.putExtra("nextPageToken", "");
        cateIntent.putExtra("lat", latitude);
        cateIntent.putExtra("lng", longitude);
        cateIntent.putExtra("wikiSearchKeyword", wikiSearchKeyword);
        cateIntent.putExtra("gu", "2");
        cateIntent.putExtra("cateGu", "호텔 모텔 여관 숙박");
        cateIntent.putExtra("areaCode", "32");
        cateIntent.putExtra("keyword", searchKeyword);
        cateIntent.putExtra("cityGu", cityGu);
        cateIntent.putExtra("callType", "area");


        startActivity(cateIntent);
    }

    public void foodBtnClicked(View v) {
        cateIntent = new Intent(this, SearchListActivity.class);
        cateIntent.putExtra("searchKeyword", searchKeyword);
        //cateIntent.putExtra("searchApiUrl", googlePlacesApiTextSearchUrl);
        //cateIntent.putExtra("photoApiUrl", googlePlacesApiPhotoUrl);
        cateIntent.putExtra("summaryApiUrl", wikipedia_api_summary);
        cateIntent.putExtra("wikiDetailApiUrl", wikipieda_api_detail);
        //cateIntent.putExtra("googleKey", googleApiKey);
        cateIntent.putExtra("nextPageToken", "");
        cateIntent.putExtra("lat", latitude);
        cateIntent.putExtra("lng", longitude);
        cateIntent.putExtra("wikiSearchKeyword", wikiSearchKeyword);
        cateIntent.putExtra("gu", "2");
        cateIntent.putExtra("cateGu", "restaurant 음식");
        cateIntent.putExtra("areaCode", "39");
        cateIntent.putExtra("keyword", searchKeyword);
        cateIntent.putExtra("cityGu", cityGu);
        cateIntent.putExtra("callType", "area");

        startActivity(cateIntent);
    }

    public void festivalBtnClicked(View v) {
        cateIntent = new Intent(this, SearchListActivity.class);
        cateIntent.putExtra("searchKeyword", searchKeyword);
        //cateIntent.putExtra("searchApiUrl", googlePlacesApiTextSearchUrl);
        //cateIntent.putExtra("photoApiUrl", googlePlacesApiPhotoUrl);
        cateIntent.putExtra("summaryApiUrl", wikipedia_api_summary);
        cateIntent.putExtra("wikiDetailApiUrl", wikipieda_api_detail);
        //cateIntent.putExtra("googleKey", googleApiKey);
        cateIntent.putExtra("nextPageToken", "");
        cateIntent.putExtra("lat", latitude);
        cateIntent.putExtra("lng", longitude);
        cateIntent.putExtra("wikiSearchKeyword", wikiSearchKeyword);
        cateIntent.putExtra("gu", "2");
        cateIntent.putExtra("cateGu", "festival 축제");
        cateIntent.putExtra("areaCode", "15");
        cateIntent.putExtra("keyword", searchKeyword);
        cateIntent.putExtra("cityGu", cityGu);
        cateIntent.putExtra("callType", "area");

        startActivity(cateIntent);
    }

    public void courseBtnClicked(View v) {
        cateIntent = new Intent(this, SearchListActivity.class);
        cateIntent.putExtra("searchKeyword", searchKeyword);
        //cateIntent.putExtra("searchApiUrl", googlePlacesApiTextSearchUrl);
        //cateIntent.putExtra("photoApiUrl", googlePlacesApiPhotoUrl);
        cateIntent.putExtra("summaryApiUrl", wikipedia_api_summary);
        cateIntent.putExtra("wikiDetailApiUrl", wikipieda_api_detail);
        //cateIntent.putExtra("googleKey", googleApiKey);
        cateIntent.putExtra("nextPageToken", "");
        cateIntent.putExtra("lat", latitude);
        cateIntent.putExtra("lng", longitude);
        cateIntent.putExtra("wikiSearchKeyword", wikiSearchKeyword);
        cateIntent.putExtra("gu", "2");
        cateIntent.putExtra("cateGu", "여행코스");
        cateIntent.putExtra("areaCode", "25");
        cateIntent.putExtra("keyword", searchKeyword);
        cateIntent.putExtra("cityGu", cityGu);
        cateIntent.putExtra("callType", "area");

        startActivity(cateIntent);
    }

    public void sportsBtnClicked(View v) {
        cateIntent = new Intent(this, SearchListActivity.class);
        cateIntent.putExtra("searchKeyword", searchKeyword);
        //cateIntent.putExtra("searchApiUrl", googlePlacesApiTextSearchUrl);
        //cateIntent.putExtra("photoApiUrl", googlePlacesApiPhotoUrl);
        cateIntent.putExtra("summaryApiUrl", wikipedia_api_summary);
        cateIntent.putExtra("wikiDetailApiUrl", wikipieda_api_detail);
        //cateIntent.putExtra("googleKey", googleApiKey);
        cateIntent.putExtra("nextPageToken", "");
        cateIntent.putExtra("lat", latitude);
        cateIntent.putExtra("lng", longitude);
        cateIntent.putExtra("wikiSearchKeyword", wikiSearchKeyword);
        cateIntent.putExtra("gu", "2");
        cateIntent.putExtra("cateGu", "체육+스포츠");
        cateIntent.putExtra("areaCode", "28");
        cateIntent.putExtra("keyword", searchKeyword);
        cateIntent.putExtra("cityGu", cityGu);
        cateIntent.putExtra("callType", "area");

        startActivity(cateIntent);
    }

    public void shopBtnClicked(View v) {
        cateIntent = new Intent(this, SearchListActivity.class);
        cateIntent.putExtra("searchKeyword", searchKeyword);
        //cateIntent.putExtra("searchApiUrl", googlePlacesApiTextSearchUrl);
        //cateIntent.putExtra("photoApiUrl", googlePlacesApiPhotoUrl);
        cateIntent.putExtra("summaryApiUrl", wikipedia_api_summary);
        cateIntent.putExtra("wikiDetailApiUrl", wikipieda_api_detail);
        //cateIntent.putExtra("googleKey", googleApiKey);
        cateIntent.putExtra("nextPageToken", "");
        cateIntent.putExtra("lat", latitude);
        cateIntent.putExtra("lng", longitude);
        cateIntent.putExtra("wikiSearchKeyword", wikiSearchKeyword);
        cateIntent.putExtra("gu", "2");
        cateIntent.putExtra("cateGu", "shopping 백화점");
        cateIntent.putExtra("areaCode", "38");
        cateIntent.putExtra("keyword", searchKeyword);
        cateIntent.putExtra("cityGu", cityGu);
        cateIntent.putExtra("callType", "area");

        startActivity(cateIntent);
    }

    public void cultureBtnClicked(View v) {
        cateIntent = new Intent(this, SearchListActivity.class);
        cateIntent.putExtra("searchKeyword", searchKeyword);
        //cateIntent.putExtra("searchApiUrl", googlePlacesApiTextSearchUrl);
        //cateIntent.putExtra("photoApiUrl", googlePlacesApiPhotoUrl);
        cateIntent.putExtra("summaryApiUrl", wikipedia_api_summary);
        cateIntent.putExtra("wikiDetailApiUrl", wikipieda_api_detail);
        //cateIntent.putExtra("googleKey", googleApiKey);
        cateIntent.putExtra("nextPageToken", "");
        cateIntent.putExtra("lat", latitude);
        cateIntent.putExtra("lng", longitude);
        cateIntent.putExtra("wikiSearchKeyword", wikiSearchKeyword);
        cateIntent.putExtra("gu", "2");
        cateIntent.putExtra("cateGu", "culture+박물관");
        cateIntent.putExtra("areaCode", "14");
        cateIntent.putExtra("keyword", searchKeyword);
        cateIntent.putExtra("cityGu", cityGu);
        cateIntent.putExtra("callType", "area");

        startActivity(cateIntent);
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
}
