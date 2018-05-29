package com.eye2web.travel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eye2web.travel.adapter.SearchListViewAdapter;
import com.eye2web.travel.handler.BackPressCloseHandler;
import com.eye2web.travel.service.SearchApiService;
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

    private ListView contentList;
    private boolean lastItemVisibleFlag = false;
    private List<ListItem> itemList = new ArrayList<ListItem>();;
    private SearchListViewAdapter adapter;
    private int page = 1;
    private final int offset = 10;
    private ProgressBar progressBar;
    private boolean mLockListView = false;
    private String areaCodeStr = "";
    private String keywordStr = "";
    private String sort = "";

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

        Intent intent = getIntent();
        areaCodeStr = intent.getStringExtra("areaCode");
        keywordStr = intent.getStringExtra("keyword");
        sort = "O";    // 정렬방식 - A : 제목순 / B : 조회순 / C : 수정일순 / D : 생성일순 | 대표 이미지가 반드시 있는 정렬은 O/P/Q/R로 지정

        //itemList = new ArrayList<ListItem>();
        //itemList = getContentList(areaCodeStr, keywordStr, sort);


        //if(null != itemList && 0 < itemList.size()) {
            adapter = new SearchListViewAdapter(this, R.layout.listitem, itemList);
            contentList.setAdapter(adapter);
            contentList.setOnScrollListener(this);

        getContentList(areaCodeStr, keywordStr, sort);
        //}

        contentList.setOnItemClickListener(itemClickListener);
        //this.backPressCloseHandler = new BackPressCloseHandler(this);

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
            if(contentList.getLastVisiblePosition() >= contentList.getCount()-1) {
                getContentList(areaCodeStr, keywordStr, sort);
            }
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

        searchApiservice = new SearchApiService();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<ListItem> resultList = new ArrayList<ListItem>();

        String addr = getResources().getString(R.string.apiUrl) + "searchKeyword?serviceKey=";
        String serviceKey = getResources().getString(R.string.apiKey);
        String gu = "search";

        try {
            resultMap = searchApiservice.getContent(addr, serviceKey, code, keyword, sort, page, offset, gu, "", 0, 0);
        } catch(Exception e) {
            Log.e("Error", "==========Error : " + e.toString());
        }


        if(null != resultMap && 0 < resultMap.size()) {
            if("0000".equalsIgnoreCase((String)resultMap.get("resultCode"))) {
                resultList = (List<ListItem>) resultMap.get("resultList");

                if(null != resultList && 0 < resultList.size()) {
                    for(int i=0; i < resultList.size(); i++) {
                        itemList.add(resultList.get(i));
                    }
                }
            }
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                page++;
                adapter.notifyDataSetChanged();
                mLockListView = false;
            }
        }, 1000);

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
            TextView textView = (TextView) view.findViewById(R.id.listText);
            String text = textView.getText().toString();
            ListItem item = (ListItem) parent.getAdapter().getItem(position);

            Intent detailInfoIntent = new Intent(getApplicationContext(), DetailInfoActivity.class);
            detailInfoIntent.putExtra("item", item);
            startActivity(detailInfoIntent);
        }
    };

    /**
     * @parameter :
     * @Date : 2018. 5. 11. PM 2:47
     * @Author : Andrew Kim
     * @Description : 뒤로가기 버튼 클릭 시 처리
    **/
    /**
    @Override
    public void onBackPressed() {
        //this.backPressCloseHandler.onBackPressed();
        super.onBackPressed();    // index가 아닌 경우에는 그냥 이전 activity로 이동
    }
    **/
}
