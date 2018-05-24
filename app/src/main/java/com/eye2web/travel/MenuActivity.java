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
import com.eye2web.travel.service.SearchApiService;
import com.eye2web.travel.util.CommonUtil;
import com.eye2web.travel.vo.ListItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuActivity extends BaseActivity implements AbsListView.OnScrollListener {

    private SearchApiService searchApiservice;
    private CommonUtil commonUtil;

    private String gu;
    private String areaGu;
    private String cateGu;
    private Intent cateIntent;

    private ListView contentList;
    private boolean lastItemVisibleFlag = false;
    private List<ListItem> itemList = new ArrayList<ListItem>();;
    private SearchListViewAdapter adapter;
    private int page = 1;
    private final int offset = 20;
    private ProgressBar progressBar;
    private boolean mLockListView = false;
    private String keyword = "";
    private String sort = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catelist);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent menuIntent = getIntent();
        gu = menuIntent.getStringExtra("gu");
        areaGu = menuIntent.getStringExtra("areaGu");
        cateGu = menuIntent.getStringExtra("cateGu");
        sort = menuIntent.getStringExtra("sort");
        if(null != sort && !"".equalsIgnoreCase(sort)) {} else { sort = "A"; }    // 정렬 기준이 없을 경우에는 기본 제목순 정렬

        contentList = (ListView) findViewById(R.id.cateSearchResultList);
        adapter = new SearchListViewAdapter(this, R.layout.listitem, itemList);
        contentList.setAdapter(adapter);
        contentList.setOnScrollListener(this);

        if(null != gu && !"".equalsIgnoreCase(gu)) {
            if(null != areaGu && !"".equalsIgnoreCase(areaGu)) {
                commonUtil = new CommonUtil();
                keyword = commonUtil.getKeyword(areaGu);

                getContentList(cateGu, keyword, sort);
            } else {
                // 분류값(gu)은 있으나 지역분류값(areaGu)이 없을 경우에는 기본 서울의 리스트를 노출한다. (서울 + 분류값(ex. 레포츠 등))
                keyword = "서울";
                getContentList(cateGu, keyword, sort);
            }
        } else {
            // 분류값(gu)이 없을 경우에는 오류를 발생시키지 않아야 하므로 기본 서울/
            cateGu = "12";
            keyword = "서울";
            getContentList(cateGu, keyword, sort);
        }

        contentList.setOnItemClickListener(itemClickListener);
    }

    public void onSeoulBtnClicked(View v) {
        areaGu = "seoul";
        cateIntent = new Intent(getApplicationContext(), MenuActivity.class);
        cateIntent.putExtra("gu", gu);
        cateIntent.putExtra("cateGu", cateGu);
        cateIntent.putExtra("areaGu", areaGu);
        startActivity(cateIntent);
    }

    public void onDaejeonBtnClicked(View v) {
        areaGu = "daejeon";
        cateIntent = new Intent(getApplicationContext(), MenuActivity.class);
        cateIntent.putExtra("gu", gu);
        cateIntent.putExtra("cateGu", cateGu);
        cateIntent.putExtra("areaGu", areaGu);
        startActivity(cateIntent);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag && mLockListView == false) {
            if(contentList.getLastVisiblePosition() >= contentList.getCount()-1) {
                getContentList(cateGu, keyword, sort);
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

        try {
            resultMap = searchApiservice.getContent(addr, serviceKey, code, keyword, sort, page, offset);
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
}
