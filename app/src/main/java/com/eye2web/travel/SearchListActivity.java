package com.eye2web.travel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eye2web.travel.adapter.SearchListViewAdapter;
import com.eye2web.travel.handler.BackPressCloseHandler;
import com.eye2web.travel.service.DetailApiService;
import com.eye2web.travel.service.SearchApiService;
import com.eye2web.travel.util.CommonUtil;
import com.eye2web.travel.vo.DetailCommonItem;
import com.eye2web.travel.vo.ListItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @File : SearchListActivity
 * @Date : 2018. 5. 2. PM 4:44
 * @Author : Andrew Kim
 * @Version : 1.0.0
 * @Description : 초기화면에서 버튼 클릭에 따라 연결되는 화면
**/
public class SearchListActivity extends BaseActivity implements AbsListView.OnScrollListener {

    private SearchApiService searchApiservice;
    private DetailApiService detailApiService;

    private CommonUtil commonUtil;

    private ListView contentList;
    private boolean lastItemVisibleFlag = false;
    private List<ListItem> itemList = new ArrayList<ListItem>();
    private SearchListViewAdapter adapter;
    private int page = 1;
    private int offset = 10;
    private ProgressBar progressBar;
    private boolean mLockListView = false;
    private String areaCodeStr = "";
    private String keywordStr = "";
    private String sort = "";
    private String cityGu = "";
    private String callType = "";
    private String areaCode = "";
    private String searchGu = "";
    private String loc = "";
    private double mapx = 0;
    private double mapy = 0;
    private Handler searchHandler = null;

    private BackPressCloseHandler backPressCloseHandler;

    /**
     * @parameter :
     * @Date : 2018. 5. 11. PM 2:44
     * @Author : Andrew Kim
     * @Description : 리스트 화면 생성
    **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        contentList = (ListView) findViewById(R.id.searchResultList);
        LinearLayout nearbyLayout = findViewById(R.id.nearbyTitle);
        LinearLayout logoLayout = findViewById(R.id.logoLayout);

        Intent intent = getIntent();
        areaCodeStr = intent.getStringExtra("areaCode");
        keywordStr = intent.getStringExtra("keyword");
        sort = "P";    // 정렬방식 - A : 제목순 / B : 조회순 / C : 수정일순 / D : 생성일순 | 대표 이미지가 반드시 있는 정렬은 O/P/Q/R로 지정
        cityGu = intent.getStringExtra("cityGu");
        callType = intent.getStringExtra("callType");
        loc = intent.getStringExtra("loc");

        searchGu = intent.getStringExtra("searchGu");
        //arroundGu = intent.getStringExtra("arroundGu");
        if("nearby".equalsIgnoreCase(searchGu)) {
            logoLayout.setVisibility(View.GONE);
            TextView detailTitle = (TextView) findViewById(R.id.detailTitle);
            detailTitle.setText("주변찾기");
        } else {
            nearbyLayout.setVisibility(View.GONE);
        }

        mapx = intent.getDoubleExtra("mapx", 0);
        mapy = intent.getDoubleExtra("mapy", 0);

        progressBar = (ProgressBar) findViewById(R.id.search_list_progressbar);

        //itemList = new ArrayList<ListItem>();
        //itemList = getContentList(areaCodeStr, keywordStr, sort);


        //if(null != itemList && 0 < itemList.size()) {
        adapter = new SearchListViewAdapter(this, R.layout.listitem, itemList);
        contentList.setAdapter(adapter);

        //progressBar.setVisibility(View.GONE);

        contentList.setOnScrollListener(this);

        searchHandler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                getContentList(areaCodeStr, keywordStr, sort);
                //adapter.notifyDataSetChanged();
                Looper.loop();
            }
        }).start();

        contentList.setOnItemClickListener(itemClickListener);
    }

    /**
     * @parameter :
     * @Date : 2018. 5. 11. PM 2:44
     * @Author : Andrew Kim
     * @Description : 화면 스크롤 상태 변경 시 처리
    **/
    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {

        if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag && mLockListView == false) {
            progressBar.setVisibility(View.VISIBLE);
            contentList.setVisibility(View.GONE);
            //contentList.setVisibility(View.GONE);
            //if(contentList.getLastVisiblePosition() >= contentList.getCount()-1) {
                getContentList(areaCodeStr, keywordStr, sort);
            //}
        }
    }

    /**
     * @parameter :
     * @Date : 2018. 5. 11. PM 2:45
     * @Author : Andrew Kim
     * @Description : 화면 스크를 시 처리
    **/
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        lastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
        //progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * @parameter : code - 지역코드
     *              keyword - 검색어
     *              sort - 정렬기준
     * @Date : 2018. 5. 11. PM 2:45
     * @Author : Andrew Kim
     * @Description : 리스트 생성용 정보 가져오기
    **/
    private void getContentList(String code, String keyword, String sort) {
        mLockListView = true;
        //progressBar.setVisibility(View.VISIBLE);
        //contentList.setVisibility(View.GONE);

        searchApiservice = new SearchApiService();
        commonUtil = new CommonUtil();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<ListItem> resultList = new ArrayList<ListItem>();

        String detailAddr = getResources().getString(R.string.apiUrl);
        String serviceKey = getResources().getString(R.string.apiKey);
        String gu = "search";

        String addr = getResources().getString(R.string.apiUrl) + "searchKeyword?serviceKey=";
        if(null != searchGu && !"".equalsIgnoreCase(searchGu)) {
            if("nearby".equalsIgnoreCase(searchGu)) {
                addr = getResources().getString(R.string.apiUrl) + "locationBasedList?serviceKey=";
                gu = "nearby";

                if(null != loc && !"".equalsIgnoreCase(loc)) {
                    if("loc".equalsIgnoreCase(loc)) {
                        gu = "loc";
                    }
                }
            }
        }

        if(null != callType && !"".equalsIgnoreCase(callType)) {
            gu = "area";
            areaCode = cityGu;
            addr = getResources().getString(R.string.apiUrl) + "areaBasedList?serviceKey=";
        }

        try {
            resultMap = searchApiservice.getContent(addr, serviceKey, code, keyword, sort, page, offset, gu, areaCode, mapx, mapy);
        } catch(Exception e) {
            Log.e("Error", "==========Error : " + e.toString());
        }

        if(null != resultMap && 0 < resultMap.size()) {
            if("0000".equalsIgnoreCase((String)resultMap.get("resultCode"))) {
                resultList = (List<ListItem>) resultMap.get("resultList");

                if(null != resultList && 0 < resultList.size()) {
                    detailApiService = new DetailApiService();
                    for(int i=0; i < resultList.size(); i++) {
                        Map<String, Object> detailMap = new HashMap<String, Object>();
                        detailMap = detailApiService.getDetailInfo(detailAddr, serviceKey, resultList.get(i).getContentid()
                                , resultList.get(i).getContenttypeid(), resultList.get(i).getAreacode()
                                , resultList.get(i).getMapx(), resultList.get(i).getMapy());

                        //detailMap = null;
                        if(null != detailMap && 0 < detailMap.size()) {
                            DetailCommonItem detailCommonItem = new DetailCommonItem();
                            detailCommonItem = (DetailCommonItem) detailMap.get("detailCommon");

                            if(null != detailCommonItem) {
                                String overviewTxt = detailCommonItem.getOverview();
                                String phoneTxt = detailCommonItem.getTel();
                                String webTxt = detailCommonItem.getHomepage();
                                resultList.get(i).setOverview(overviewTxt);

                                SpannableStringBuilder phoneBuilder = new SpannableStringBuilder();
                                if(null != phoneTxt && !"".equalsIgnoreCase(phoneTxt)) {
                                    phoneBuilder = commonUtil.convertTxtToLink(getApplicationContext(), phoneTxt);
                                }
                                if(null != phoneBuilder.toString() && !"".equalsIgnoreCase(phoneBuilder.toString())
                                        && 0 < phoneBuilder.toString().length()) {
                                    resultList.get(i).setTel(phoneBuilder.toString().trim());
                                }

                                SpannableStringBuilder homePageBuilder = new SpannableStringBuilder();
                                if(null != webTxt && !"".equalsIgnoreCase(webTxt)) {
                                    homePageBuilder = commonUtil.convertTxtToLink(getApplicationContext(), webTxt);
                                }
                                if(null != homePageBuilder.toString() && !"".equalsIgnoreCase(homePageBuilder.toString())
                                        && 0 < homePageBuilder.toString().length()) {
                                    resultList.get(i).setHomepage(homePageBuilder.toString().trim());
                                }
                            }
                        }

                        itemList.add(resultList.get(i));
                        //adapter.notifyDataSetChanged();
                    }
                }
            }
        }

        searchHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page++;
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                contentList.setVisibility(View.VISIBLE);
                mLockListView = false;
            }
        }, 500);

    }

    /**
     * @parameter :
     * @Date : 2018. 5. 11. PM 2:46
     * @Author : Andrew Kim
     * @Description : 아이템 클릭 리스너 - 리스트내 아이템 선택 시 처리
    **/
    public AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //TextView textView = (TextView) view.findViewById(R.id.listAddr);
            //String text = textView.getText().toString();
            ListItem item = (ListItem) parent.getAdapter().getItem(position);

            Intent detailInfoIntent = new Intent(getApplicationContext(), DetailInfoActivity.class);
            detailInfoIntent.putExtra("item", item);
            startActivity(detailInfoIntent);
        }
    };

    public void onGobackBtnClicked(View v) {
        super.onBackPressed();
    }
}
