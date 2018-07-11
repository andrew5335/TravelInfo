package com.eye2web.travel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eye2web.travel.adapter.GoogleCommonListAdapter;
import com.eye2web.travel.handler.BackPressCloseHandler;
import com.eye2web.travel.service.GoogleApiService;
import com.eye2web.travel.vo.GooglePlaceItem;
import com.eye2web.travel.vo.GooglePlaceVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoogleCateList extends BaseActivity implements AbsListView.OnScrollListener {

    private GoogleApiService googleApiService;
    private ListView googleCateList;
    private boolean lastItemVisibleFlag = false;
    private GooglePlaceVO googlePlaceVO;
    private List<GooglePlaceItem> itemList = new ArrayList<GooglePlaceItem>();
    private GoogleCommonListAdapter googleCateListAdapter;
    private int page = 1;
    private final int offset = 20;
    private ProgressBar progressBar;
    private boolean mLockListView = false;
    private Handler cateHandler = null;

    private String nextPageToken;
    private String searchApiUrl;
    private String photoApiUrl;
    private String summaryApiUrl;
    private String wikiDetailApiUrl;
    private String googleKey;
    private double lat;
    private double lng;
    private String searchKeyword;
    private String wikiSearchKeyword;
    private String gu;
    private String cateGu;

    private List<String> tokenList = new ArrayList<String>();
    Map<String, Object> tokenMap = new HashMap<String, Object>();

    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_cate_list);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        googleCateList = (ListView) findViewById(R.id.google_cate_list);

        Intent intent = getIntent();
        searchKeyword = intent.getStringExtra("searchKeyword");
        //nextPageToken = intent.getStringExtra("nextPageToken");
        searchApiUrl = intent.getStringExtra("searchApiUrl");
        photoApiUrl = intent.getStringExtra("photoApiUrl");
        summaryApiUrl = intent.getStringExtra("summaryApiUrl");
        wikiDetailApiUrl = intent.getStringExtra("wikiDetailApiUrl");
        googleKey = intent.getStringExtra("googleKey");
        lat = intent.getDoubleExtra("lat", 0);
        lng = intent.getDoubleExtra("lng", 0);
        wikiSearchKeyword = intent.getStringExtra("wikiSearchKeyWord");
        gu = intent.getStringExtra("gu");
        cateGu = intent.getStringExtra("cateGu");

        googleCateListAdapter = new GoogleCommonListAdapter(this, R.layout.google_list_item, itemList);
        googleCateList.setAdapter(googleCateListAdapter);
        googleCateList.setOnScrollListener(this);

        cateHandler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                getContentList(searchApiUrl, photoApiUrl, summaryApiUrl, wikiDetailApiUrl, googleKey
                        , nextPageToken, lat, lng, searchKeyword, wikiSearchKeyword, gu);
                Looper.loop();
            }
        }).start();

        googleCateList.setOnItemClickListener(itemClickListener);

    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag && mLockListView == false) {
            if(googleCateList.getLastVisiblePosition() >= googleCateList.getCount()-1) {
                //nextPageToken = googlePlaceVO.getNextPageToken();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String tempNextPageToken = "";
                        //tempNextPageToken = String.valueOf(tokenMap.get("nextPageToken"));
                        tempNextPageToken = googlePlaceVO.getNextPageToken();
                        getContentList(searchApiUrl, photoApiUrl, summaryApiUrl, wikiDetailApiUrl, googleKey, tempNextPageToken, lat, lng, searchKeyword, wikiSearchKeyword, gu);
                    }
                }).start();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        lastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
    }

    public void getContentList(
            String searchApiUrl
            , String photoApiUrl
            , String summaryApiUrl
            , String wikiDetailApiUrl
            , String googleKey
            , String tmpNextPageToken
            , double lat
            , double lng
            , String searchKeyword
            , String wikiSearchKeyword
            , String gu) {

        mLockListView = true;

        googleApiService = new GoogleApiService();
        List<GooglePlaceItem> resultList = new ArrayList<GooglePlaceItem>();

        try {
            googlePlaceVO = new GooglePlaceVO();
            googlePlaceVO = googleApiService.getPlaceInfo(searchApiUrl, photoApiUrl, summaryApiUrl, wikiDetailApiUrl
                    , googleKey, tmpNextPageToken, lat, lng, searchKeyword, wikiSearchKeyword, gu, cateGu);

            if(null != googlePlaceVO) {
                resultList = googlePlaceVO.getPlaceItem();
                nextPageToken = googlePlaceVO.getNextPageToken();
                tokenMap.put("nextPageToken", nextPageToken);

                for(int i=0; i < resultList.size(); i++) {
                    itemList.add(resultList.get(i));
                }
            }

            cateHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //page++;
                    //nextPageToken = googlePlaceVO.getNextPageToken();
                    googleCateListAdapter.notifyDataSetChanged();
                    mLockListView = false;
                }
            }, 1000);

        } catch (Exception e) {
            Log.e("Error", "Error : " + e.toString());
        }

    }

    public AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView textView = (TextView) view.findViewById(R.id.listText);
            //String text = textView.getText().toString();
            GooglePlaceItem item = (GooglePlaceItem) parent.getAdapter().getItem(position);

            Intent detailInfoIntent = new Intent(getApplicationContext(), DetailInfoActivity.class);
            detailInfoIntent.putExtra("item", item);
            startActivity(detailInfoIntent);
        }
    };
}
