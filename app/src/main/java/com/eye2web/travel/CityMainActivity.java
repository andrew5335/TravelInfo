package com.eye2web.travel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eye2web.travel.service.GoogleApiService;
import com.eye2web.travel.vo.GooglePlaceItem;
import com.eye2web.travel.vo.GooglePlaceVO;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * @File : CityMainActivity
 * @Date : 2018. 6. 4. PM 2:25
 * @Author : Andrew Kim
 * @Version : 1.0.0
 * @Description : 초기화면에서 도시 선택 시 연결되는 도시 메인 Activity
**/
public class CityMainActivity extends BaseActivity {

    private GoogleApiService googleApiService;

    private String cityGu;    // 도시 구분값
    private String citySetting;    // 도시 구분값에 따른 도시 세팅
    private static String imageUrl;    // 이미지 파일 주소 url
    private String areaGu;    // 지역 구분값 (cityGu와 동일)
    private String cateGu;    // 카테고리 구분값 (관광, 숙박, 축제 등)
    private String cateName;    // 카테고리명
    private Intent cateIntent;

    // google place 관련 변수
    private boolean googleApiClientConYn = false;
    private static String googlePlacesApiNearbySearchUrl;    // 주변검색 요청 url
    private static String googlePlacesApiTextSearchUrl;    // 키워드검색 요청 url
    private static String googlePlacesApiDetailInfoUrl;    // 상세정보 요청 url
    private static String googlePlacesApiPhotoUrl;    // 사진정보 요청 url
    private static String googleApiKey;    // google api 호출용 key
    private static String googleRetType;
    private String googleSearchType;    // 검색 타입 - 관광, 맛집, 문화시설 등 구분값
    private double latitude = 0;    // 위도
    private double longitude = 0;    // 경도
    private String searchKeyword;    // 키워드 검색 시 사용할 기본 키워드 - 검색타입과 + 로 연결되어 키워드 검색에 사용됨 (ex. 서울+맛집)

    // wikipedia 관련 변수
    private static String wikipedia_api_summary;    // 위키피디어 요약정보 요청 url
    private static String wikipieda_api_detail;    // 위키피디어 세부정보 요청 url
    private String wikiSearchKeyword;
    private String cateCode = "";    // 지역코드
    private String gu = "1";    // 도시 메인 혹은 리스트 유무 구분값 - 1 : 도시메인 / 2 : 각 카테고리 리스트

    private ImageView imageView;
    private TextView city_summary_text;
    private ImageView city_main_photo;

    private Handler handler = null;

    private GooglePlaceVO googlePlaceVO = new GooglePlaceVO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
         **/

        setContentView(R.layout.activity_city_main);

        city_main_photo = (ImageView) findViewById(R.id.city_main_photo);
        city_summary_text = (TextView) findViewById(R.id.city_main_text);

        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().penaltyLog().build();
        //StrictMode.setThreadPolicy(policy);
        //StrictMode.VmPolicy vmPolicy = new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build();
        //StrictMode.setVmPolicy(vmPolicy);

        googlePlacesApiNearbySearchUrl = getResources().getString(R.string.google_places_api_nearby_search_url);
        googlePlacesApiTextSearchUrl = getResources().getString(R.string.google_places_api_text_search_url);
        googlePlacesApiDetailInfoUrl = getResources().getString(R.string.google_places_api_detail_info_url);
        googlePlacesApiPhotoUrl = getResources().getString(R.string.google_places_api_photo_url);
        googleApiKey = getResources().getString(R.string.google_maps_key);

        wikipedia_api_summary = getResources().getString(R.string.wikipedia_rest_api_summary_url);
        wikipieda_api_detail = getResources().getString(R.string.wikipedia_rest_api_detail_url);

        googleRetType = "json";
        googlePlacesApiNearbySearchUrl = googlePlacesApiNearbySearchUrl + googleRetType;
        googlePlacesApiTextSearchUrl = googlePlacesApiTextSearchUrl + googleRetType;
        googlePlacesApiDetailInfoUrl = googlePlacesApiDetailInfoUrl + googleRetType;

        imageUrl = getResources().getString(R.string.image_url);
        imageUrl = imageUrl + "/images/indexmenu/";
        imageView = (ImageView) findViewById(R.id.city_main_image);
        googleApiClientConYn = getGoogleApiClientConnect();
        Intent cityMainIntent = getIntent();
        cityGu = cityMainIntent.getStringExtra("cityGu");

        switch (cityGu) {
            case "1" :
                Picasso.get().load(imageUrl + "seoul.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 37.5652154;
                longitude = 126.9195108;
                searchKeyword = "서울";
                wikiSearchKeyword = "서울특별시";
                break;

            case "2" :
                Picasso.get().load(imageUrl + "incheon.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 37.4646656;
                longitude = 126.6043108;
                searchKeyword = "인천";
                wikiSearchKeyword = "인천광역시";
                break;

            case "3" :
                Picasso.get().load(imageUrl + "daejeon.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 36.373164;
                longitude = 127.3537807;
                searchKeyword = "대전";
                wikiSearchKeyword = "대전광역시";
                break;

            case "4" :
                Picasso.get().load(imageUrl + "daegu.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 35.8798145;
                longitude = 128.5316807;
                searchKeyword = "대구";
                wikiSearchKeyword = "대구광역시";
                break;

            case "5" :
                Picasso.get().load(imageUrl + "kwangju.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 35.1767651;
                longitude = 126.8087807;
                searchKeyword = "광주";
                wikiSearchKeyword = "광주광역시";
                break;

            case "6" :
                Picasso.get().load(imageUrl + "busan.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 35.1645702;
                longitude = 129.0017608;
                searchKeyword = "부산";
                wikiSearchKeyword = "부산광역시";
                break;

            case "7" :
                Picasso.get().load(imageUrl + "ulsan.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 35.5621694;
                longitude = 129.2814608;
                searchKeyword = "울산";
                wikiSearchKeyword = "울산광역시";
                break;

            case "8" :
                Picasso.get().load(imageUrl + "sejong.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 36.4801027;
                longitude = 127.2868467;
                searchKeyword = "세종";
                wikiSearchKeyword = "세종특별자치시청";
                break;

            case "31" :
                Picasso.get().load(imageUrl + "kyunggi2.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 37.5985327;
                longitude = 126.8157339;
                searchKeyword = "경기";
                wikiSearchKeyword = "경기도";
                break;

            case "32" :
                Picasso.get().load(imageUrl + "kangwon.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 37.8662752;
                longitude = 127.6873691;
                searchKeyword = "강원";
                wikiSearchKeyword = "강원도";
                break;

            case "33" :
                Picasso.get().load(imageUrl + "chungbook.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 36.6376267;
                longitude = 127.6867254;
                searchKeyword = "충북";
                wikiSearchKeyword = "충청북도";
                break;

            case "34" :
                Picasso.get().load(imageUrl + "chungnam.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 36.5320992;
                longitude = 125.6726602;
                searchKeyword = "충남";
                wikiSearchKeyword = "충청남도";
                break;

            case "35" :
                Picasso.get().load(imageUrl + "kyungbook.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 36.5594479;
                longitude = 129.2852767;
                searchKeyword = "경북";
                wikiSearchKeyword = "경상북도";
                break;

            case "36" :
                Picasso.get().load(imageUrl + "kyungnam.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 35.1813853;
                longitude = 127.8306548;
                searchKeyword = "경남";
                wikiSearchKeyword = "경상남도";
                break;

            case "37" :
                Picasso.get().load(imageUrl + "jeonbook.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 35.7278162;
                longitude = 126.6498459;
                searchKeyword = "전북";
                wikiSearchKeyword = "전라북도";
                break;

            case "38" :
                Picasso.get().load(imageUrl + "jeonnam.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 34.6959785;
                longitude = 125.9376253;
                searchKeyword = "전남";
                wikiSearchKeyword = "전라남도";
                break;

            case "39" :
                Picasso.get().load(imageUrl + "jeju.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                latitude = 33.5784808;
                longitude = 126.2928556;
                searchKeyword = "제주";
                wikiSearchKeyword = "제주도";
                break;
        }

        //GooglePlaceVO googlePlaceVO = new GooglePlaceVO();

        handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                getContent();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(null != googlePlaceVO) {
                            city_summary_text = (TextView) findViewById(R.id.city_main_text);
                            city_main_photo = (ImageView) findViewById(R.id.city_main_photo);

                            List<GooglePlaceItem> itemList = googlePlaceVO.getPlaceItem();

                            if(null != itemList && 0 < itemList.size()) {
                                for (int i = 0; i < itemList.size(); i++) {
                                    city_summary_text.setText(itemList.get(i).getSummary());
                                    Picasso.get().load(itemList.get(i).getGooglePhotoUrl()).placeholder(R.mipmap.logo_final).into(city_main_photo);
                                }
                            }
                        }
                    }
                });

            }
        }).start();


        //googlePlaceVO = getContent();

        //GooglePlaceVO googlePlaceVO = new GooglePlaceVO();
        //googlePlaceVO = getContent();

        /**
        if(null != googlePlaceVO) {
            city_summary_text = (TextView) findViewById(R.id.city_main_text);
            city_main_photo = (ImageView) findViewById(R.id.city_main_photo);

            List<GooglePlaceItem> itemList = googlePlaceVO.getPlaceItem();

            if(null != itemList && 0 < itemList.size()) {
                for (int i = 0; i < itemList.size(); i++) {
                    city_summary_text.setText(itemList.get(i).getSummary());
                    city_main_photo.setImageBitmap(itemList.get(i).getPhotoUrl());
                    //city_summary_text.setText(Html.fromHtml(itemList.get(i).getSummary(), new ImageGetter(), null));
                }
            }
        }
         **/
    }

    public void getContent() {
        //GooglePlaceVO googlePlaceVO = new GooglePlaceVO();
        googleApiService = new GoogleApiService();

        //city_summary_text = (TextView) findViewById(R.id.city_main_text);
        //city_main_photo = (ImageView) findViewById(R.id.city_main_photo);

        try {
            googlePlaceVO = googleApiService.getPlaceInfo(googlePlacesApiTextSearchUrl, googlePlacesApiPhotoUrl
                    , wikipedia_api_summary, wikipieda_api_detail, googleApiKey, "", latitude, longitude, searchKeyword, wikiSearchKeyword, gu, "");

            /**
            if(null != googlePlaceVO) {
                List<GooglePlaceItem> itemList = googlePlaceVO.getPlaceItem();

                if(null != itemList && 0 < itemList.size()) {
                    for (int i = 0; i < itemList.size(); i++) {
                        city_summary_text.setText(itemList.get(i).getSummary());
                        city_main_photo.setImageBitmap(itemList.get(i).getPhotoUrl());
                        //city_summary_text.setText(Html.fromHtml(itemList.get(i).getSummary(), new ImageGetter(), null));
                    }
                }
            }
             **/
        } catch (Exception e) {
            Log.e("Error", "Error : " + e.toString());
        }

        //return googlePlaceVO;
    }

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

                //d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
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

    public void travelBtnClicked(View v) {
        cateIntent = new Intent(this, SearchListActivity.class);
        cateIntent.putExtra("searchKeyword", searchKeyword);
        cateIntent.putExtra("searchApiUrl", googlePlacesApiTextSearchUrl);
        cateIntent.putExtra("photoApiUrl", googlePlacesApiPhotoUrl);
        cateIntent.putExtra("summaryApiUrl", wikipedia_api_summary);
        cateIntent.putExtra("wikiDetailApiUrl", wikipieda_api_detail);
        cateIntent.putExtra("googleKey", googleApiKey);
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
        cateIntent.putExtra("searchApiUrl", googlePlacesApiTextSearchUrl);
        cateIntent.putExtra("photoApiUrl", googlePlacesApiPhotoUrl);
        cateIntent.putExtra("summaryApiUrl", wikipedia_api_summary);
        cateIntent.putExtra("wikiDetailApiUrl", wikipieda_api_detail);
        cateIntent.putExtra("googleKey", googleApiKey);
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
        cateIntent.putExtra("searchApiUrl", googlePlacesApiTextSearchUrl);
        cateIntent.putExtra("photoApiUrl", googlePlacesApiPhotoUrl);
        cateIntent.putExtra("summaryApiUrl", wikipedia_api_summary);
        cateIntent.putExtra("wikiDetailApiUrl", wikipieda_api_detail);
        cateIntent.putExtra("googleKey", googleApiKey);
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
        cateIntent.putExtra("searchApiUrl", googlePlacesApiTextSearchUrl);
        cateIntent.putExtra("photoApiUrl", googlePlacesApiPhotoUrl);
        cateIntent.putExtra("summaryApiUrl", wikipedia_api_summary);
        cateIntent.putExtra("wikiDetailApiUrl", wikipieda_api_detail);
        cateIntent.putExtra("googleKey", googleApiKey);
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
        cateIntent.putExtra("searchApiUrl", googlePlacesApiTextSearchUrl);
        cateIntent.putExtra("photoApiUrl", googlePlacesApiPhotoUrl);
        cateIntent.putExtra("summaryApiUrl", wikipedia_api_summary);
        cateIntent.putExtra("wikiDetailApiUrl", wikipieda_api_detail);
        cateIntent.putExtra("googleKey", googleApiKey);
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
        cateIntent.putExtra("searchApiUrl", googlePlacesApiTextSearchUrl);
        cateIntent.putExtra("photoApiUrl", googlePlacesApiPhotoUrl);
        cateIntent.putExtra("summaryApiUrl", wikipedia_api_summary);
        cateIntent.putExtra("wikiDetailApiUrl", wikipieda_api_detail);
        cateIntent.putExtra("googleKey", googleApiKey);
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
        cateIntent.putExtra("searchApiUrl", googlePlacesApiTextSearchUrl);
        cateIntent.putExtra("photoApiUrl", googlePlacesApiPhotoUrl);
        cateIntent.putExtra("summaryApiUrl", wikipedia_api_summary);
        cateIntent.putExtra("wikiDetailApiUrl", wikipieda_api_detail);
        cateIntent.putExtra("googleKey", googleApiKey);
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
        cateIntent.putExtra("searchApiUrl", googlePlacesApiTextSearchUrl);
        cateIntent.putExtra("photoApiUrl", googlePlacesApiPhotoUrl);
        cateIntent.putExtra("summaryApiUrl", wikipedia_api_summary);
        cateIntent.putExtra("wikiDetailApiUrl", wikipieda_api_detail);
        cateIntent.putExtra("googleKey", googleApiKey);
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
}
