package com.eye2web.travel;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.eye2web.travel.adapter.AreaSpinnerAdapter;
import com.eye2web.travel.adapter.SearchListViewAdapter;
import com.eye2web.travel.service.DetailApiService;
import com.eye2web.travel.service.SearchApiService;
import com.eye2web.travel.util.CommonUtil;
import com.eye2web.travel.vo.AreaListItem;
import com.eye2web.travel.vo.DetailCommonItem;
import com.eye2web.travel.vo.ListItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuListActivity extends BaseActivity implements AbsListView.OnScrollListener {

    private SearchApiService searchApiservice;
    private DetailApiService detailApiService;
    private CommonUtil commonUtil;

    private String gu;
    private String areaGu;
    private String cateGu;
    private String cateName;
    private Intent cateIntent;

    private ListView contentList;
    private boolean lastItemVisibleFlag = false;
    private List<ListItem> itemList = new ArrayList<ListItem>();;
    private SearchListViewAdapter adapter;
    private int page = 1;
    private final int offset = 10;
    private ProgressBar progressBar;
    private boolean mLockListView = false;
    private String keyword = "";
    private String sort = "";
    private TextView btnText;

    private String loc = "";
    private double mapX = 0;
    private double mapY = 0;

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
        cateName = menuIntent.getStringExtra("cateName");
        sort = menuIntent.getStringExtra("sort");

        loc = menuIntent.getStringExtra("loc");
        mapX = menuIntent.getDoubleExtra("mapX", 0);
        mapY = menuIntent.getDoubleExtra("mapY", 0);
        //Log.i("info", "===============gps info : " + mapX + "=============" + mapY);
        if(null != sort && !"".equalsIgnoreCase(sort)) {} else { sort = "O"; }    // 정렬 기준이 없을 경우에는 기본 제목순 정렬
        commonUtil = new CommonUtil();

        contentList = (ListView) findViewById(R.id.cateSearchResultList);
        adapter = new SearchListViewAdapter(this, R.layout.listitem, itemList);
        contentList.setAdapter(adapter);
        contentList.setOnScrollListener(this);

        // 카테고리명
        TextView cateTitle = (TextView) findViewById(R.id.cate_name);
        cateTitle.setText(cateName);
        //Typeface typeface = Typeface.createFromFile("font/utogothic.otf");
        //cateTitle.setTypeface(typeface, Typeface.BOLD);

        // sorting spinner
        Spinner sortSpinner = (Spinner) findViewById(R.id.cate_spinner);
        ArrayList<AreaListItem> sortList = commonUtil.getSortList();
        AreaSpinnerAdapter sortSpinnerAdapter = new AreaSpinnerAdapter(this, R.layout.sortitem, sortList);
        sortSpinner.setAdapter(sortSpinnerAdapter);
        sortSpinner.setSelection(0, false);
        sortSpinner.setOnItemSelectedListener(itemSelectedListener);

        if(null != gu && !"".equalsIgnoreCase(gu)) {
            if(null != areaGu && !"".equalsIgnoreCase(areaGu)) {
                keyword = commonUtil.getKeyword(areaGu);

                getContentList(cateGu, areaGu, keyword, sort);
            } else {
                // 분류값(gu)은 있으나 지역분류값(areaGu)이 없을 경우에는 기본 서울의 리스트를 노출한다. (서울 + 분류값(ex. 레포츠 등))
                keyword = "서울";
                areaGu = "1";
                getContentList(cateGu, areaGu, keyword, sort);
            }
        } else {
            // 분류값(gu)이 없을 경우에는 오류를 발생시키지 않아야 하므로 기본 서울/
            cateGu = "12";
            keyword = "서울";
            areaGu = "1";
            getContentList(cateGu, areaGu, keyword, sort);
        }

        // 상단 메뉴 텍스트 색상 설정
        switch(areaGu) {
            case "1" :
                btnText = (TextView) findViewById(R.id.seoulBtn);
                btnText.setTextColor(Color.RED);
                break;

            case "2" :
                btnText = (TextView) findViewById(R.id.incheonBtn);
                btnText.setTextColor(Color.RED);
                break;

            case "3" :
                btnText = (TextView) findViewById(R.id.daejeonBtn);
                btnText.setTextColor(Color.RED);
                break;

            case "4" :
                btnText = (TextView) findViewById(R.id.daeguBtn);
                btnText.setTextColor(Color.RED);
                break;

            case "5" :
                btnText = (TextView) findViewById(R.id.kwangjuBtn);
                btnText.setTextColor(Color.RED);
                break;

            case "6" :
                btnText = (TextView) findViewById(R.id.busanBtn);
                btnText.setTextColor(Color.RED);
                break;

            case "7" :
                btnText = (TextView) findViewById(R.id.ulsanBtn);
                btnText.setTextColor(Color.RED);
                break;

            case "8" :
                btnText = (TextView) findViewById(R.id.sejongBtn);
                btnText.setTextColor(Color.RED);
                break;

            case "31" :
                btnText = (TextView) findViewById(R.id.kyunggiBtn);
                btnText.setTextColor(Color.RED);
                break;

            case "32" :
                btnText = (TextView) findViewById(R.id.kangwonBtn);
                btnText.setTextColor(Color.RED);
                break;

            case "33" :
                btnText = (TextView) findViewById(R.id.chungbookBtn);
                btnText.setTextColor(Color.RED);
                break;

            case "34" :
                btnText = (TextView) findViewById(R.id.chungnamBtn);
                btnText.setTextColor(Color.RED);
                break;

            case "35" :
                btnText = (TextView) findViewById(R.id.kyungbookBtn);
                btnText.setTextColor(Color.RED);
                break;

            case "36" :
                btnText = (TextView) findViewById(R.id.kyungnamBtn);
                btnText.setTextColor(Color.RED);
                break;

            case "37" :
                btnText = (TextView) findViewById(R.id.jeonbookBtn);
                btnText.setTextColor(Color.RED);
                break;

            case "38" :
                btnText = (TextView) findViewById(R.id.jeonnamBtn);
                btnText.setTextColor(Color.RED);
                break;

            case "39" :
                btnText = (TextView) findViewById(R.id.jejuBtn);
                btnText.setTextColor(Color.RED);
                break;
        }

        contentList.setOnItemClickListener(itemClickListener);
    }

    public AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            AreaListItem item = (AreaListItem) parent.getAdapter().getItem(position);
            Intent sortIntent = new Intent(getApplicationContext(), MenuListActivity.class);
            sortIntent.putExtra("gu", gu);
            sortIntent.putExtra("cateGu", cateGu);
            sortIntent.putExtra("cateName", cateName);
            sortIntent.putExtra("areaGu", areaGu);
            sortIntent.putExtra("sort", item.getCode());
            startActivity(sortIntent);
            finish();
            //Toast.makeText(getApplicationContext(), item.getName() + "-" + item.getCode() + " selected !!!", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Spinner spinner = (Spinner) findViewById(R.id.cate_spinner);
            spinner.setPrompt("선택");
        }
    };

    public void onSeoulBtnClicked(View v) {
        areaGu = "1";
        cateIntent = new Intent(getApplicationContext(), MenuListActivity.class);
        cateIntent.putExtra("gu", gu);
        cateIntent.putExtra("cateGu", cateGu);
        cateIntent.putExtra("cateName", cateName);
        cateIntent.putExtra("areaGu", areaGu);
        startActivity(cateIntent);
    }

    public void onIncheonBtnClicked(View v) {
        areaGu = "2";
        cateIntent = new Intent(getApplicationContext(), MenuListActivity.class);
        cateIntent.putExtra("gu", gu);
        cateIntent.putExtra("cateGu", cateGu);
        cateIntent.putExtra("cateName", cateName);
        cateIntent.putExtra("areaGu", areaGu);
        startActivity(cateIntent);
    }

    public void onDaejeonBtnClicked(View v) {
        areaGu = "3";
        cateIntent = new Intent(getApplicationContext(), MenuListActivity.class);
        cateIntent.putExtra("gu", gu);
        cateIntent.putExtra("cateGu", cateGu);
        cateIntent.putExtra("cateName", cateName);
        cateIntent.putExtra("areaGu", areaGu);
        startActivity(cateIntent);
    }

    public void onDaeguBtnClicked(View v) {
        areaGu = "4";
        cateIntent = new Intent(getApplicationContext(), MenuListActivity.class);
        cateIntent.putExtra("gu", gu);
        cateIntent.putExtra("cateGu", cateGu);
        cateIntent.putExtra("cateName", cateName);
        cateIntent.putExtra("areaGu", areaGu);
        startActivity(cateIntent);
    }

    public void onBusanBtnClicked(View v) {
        areaGu = "6";
        cateIntent = new Intent(getApplicationContext(), MenuListActivity.class);
        cateIntent.putExtra("gu", gu);
        cateIntent.putExtra("cateGu", cateGu);
        cateIntent.putExtra("cateName", cateName);
        cateIntent.putExtra("areaGu", areaGu);
        startActivity(cateIntent);
    }

    public void onKwangjuBtnClicked(View v) {
        areaGu = "5";
        cateIntent = new Intent(getApplicationContext(), MenuListActivity.class);
        cateIntent.putExtra("gu", gu);
        cateIntent.putExtra("cateGu", cateGu);
        cateIntent.putExtra("cateName", cateName);
        cateIntent.putExtra("areaGu", areaGu);
        startActivity(cateIntent);
    }

    public void onUlsanBtnClicked(View v) {
        areaGu = "7";
        cateIntent = new Intent(getApplicationContext(), MenuListActivity.class);
        cateIntent.putExtra("gu", gu);
        cateIntent.putExtra("cateGu", cateGu);
        cateIntent.putExtra("cateName", cateName);
        cateIntent.putExtra("areaGu", areaGu);
        startActivity(cateIntent);
    }

    public void onSejongBtnClicked(View v) {
        areaGu = "8";
        cateIntent = new Intent(getApplicationContext(), MenuListActivity.class);
        cateIntent.putExtra("gu", gu);
        cateIntent.putExtra("cateGu", cateGu);
        cateIntent.putExtra("cateName", cateName);
        cateIntent.putExtra("areaGu", areaGu);
        startActivity(cateIntent);
    }

    public void onKyunggiBtnClicked(View v) {
        areaGu = "31";
        cateIntent = new Intent(getApplicationContext(), MenuListActivity.class);
        cateIntent.putExtra("gu", gu);
        cateIntent.putExtra("cateGu", cateGu);
        cateIntent.putExtra("cateName", cateName);
        cateIntent.putExtra("areaGu", areaGu);
        startActivity(cateIntent);
    }

    public void onKwangwonBtnClicked(View v) {
        areaGu = "32";
        cateIntent = new Intent(getApplicationContext(), MenuListActivity.class);
        cateIntent.putExtra("gu", gu);
        cateIntent.putExtra("cateGu", cateGu);
        cateIntent.putExtra("cateName", cateName);
        cateIntent.putExtra("areaGu", areaGu);
        startActivity(cateIntent);
    }

    public void onChungbookBtnClicked(View v) {
        areaGu = "33";
        cateIntent = new Intent(getApplicationContext(), MenuListActivity.class);
        cateIntent.putExtra("gu", gu);
        cateIntent.putExtra("cateGu", cateGu);
        cateIntent.putExtra("cateName", cateName);
        cateIntent.putExtra("areaGu", areaGu);
        startActivity(cateIntent);
    }

    public void onChungnamBtnClicked(View v) {
        areaGu = "34";
        cateIntent = new Intent(getApplicationContext(), MenuListActivity.class);
        cateIntent.putExtra("gu", gu);
        cateIntent.putExtra("cateGu", cateGu);
        cateIntent.putExtra("cateName", cateName);
        cateIntent.putExtra("areaGu", areaGu);
        startActivity(cateIntent);
    }

    public void onJeonbookBtnClicked(View v) {
        areaGu = "37";
        cateIntent = new Intent(getApplicationContext(), MenuListActivity.class);
        cateIntent.putExtra("gu", gu);
        cateIntent.putExtra("cateGu", cateGu);
        cateIntent.putExtra("cateName", cateName);
        cateIntent.putExtra("areaGu", areaGu);
        startActivity(cateIntent);
    }

    public void onJeonnamBtnClicked(View v) {
        areaGu = "38";
        cateIntent = new Intent(getApplicationContext(), MenuListActivity.class);
        cateIntent.putExtra("gu", gu);
        cateIntent.putExtra("cateGu", cateGu);
        cateIntent.putExtra("cateName", cateName);
        cateIntent.putExtra("areaGu", areaGu);
        startActivity(cateIntent);
    }

    public void onKyungbookBtnClicked(View v) {
        areaGu = "35";
        cateIntent = new Intent(getApplicationContext(), MenuListActivity.class);
        cateIntent.putExtra("gu", gu);
        cateIntent.putExtra("cateGu", cateGu);
        cateIntent.putExtra("cateName", cateName);
        cateIntent.putExtra("areaGu", areaGu);
        startActivity(cateIntent);
    }

    public void onKyungnamBtnClicked(View v) {
        areaGu = "36";
        cateIntent = new Intent(getApplicationContext(), MenuListActivity.class);
        cateIntent.putExtra("gu", gu);
        cateIntent.putExtra("cateGu", cateGu);
        cateIntent.putExtra("cateName", cateName);
        cateIntent.putExtra("areaGu", areaGu);
        startActivity(cateIntent);
    }

    public void onJejuBtnClicked(View v) {
        areaGu = "39";
        cateIntent = new Intent(getApplicationContext(), MenuListActivity.class);
        cateIntent.putExtra("gu", gu);
        cateIntent.putExtra("cateGu", cateGu);
        cateIntent.putExtra("cateName", cateName);
        cateIntent.putExtra("areaGu", areaGu);
        startActivity(cateIntent);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag && mLockListView == false) {
            if(contentList.getLastVisiblePosition() >= contentList.getCount()-1) {
                getContentList(cateGu, areaGu, keyword, sort);
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
    private void getContentList(String code, String areaGu, String keyword, String sort) {
        mLockListView = true;

        searchApiservice = new SearchApiService();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<ListItem> resultList = new ArrayList<ListItem>();

        String addr = getResources().getString(R.string.apiUrl) + "areaBasedList?serviceKey=";
        String detailAddr = getResources().getString(R.string.apiUrl);
        String serviceKey = getResources().getString(R.string.apiKey);
        String gu = "area";

        if(null != loc && !"".equalsIgnoreCase(loc)) {
            if("loc".equalsIgnoreCase(loc)) {
                gu = "loc";
                addr = getResources().getString(R.string.apiUrl) + "locationBasedList?serviceKey=";
            }
        }
        //Log.i("info", "===============gps info : " + mapX + "=============" + mapY + "===================loc : " +loc);
        try {
            resultMap = searchApiservice.getContent(addr, serviceKey, code, keyword, sort, page, offset, gu, areaGu, mapX, mapY);
        } catch(Exception e) {
            Log.e("Error", "==========Error : " + e.toString());
        }

        LinearLayout category_layer = (LinearLayout) findViewById(R.id.category_layer);
        if(null != loc && !"".equalsIgnoreCase(loc)) {
            if("loc".equalsIgnoreCase(loc)) {
                category_layer.setVisibility(View.GONE);
            }
        } else {
            if(category_layer.getVisibility() == View.GONE) {
                category_layer.setVisibility(View.VISIBLE);
            }
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

                        if(null != detailMap && 0 < detailMap.size()) {
                            DetailCommonItem detailCommonItem = new DetailCommonItem();
                            detailCommonItem = (DetailCommonItem) detailMap.get("detailCommon");

                            if(null != detailCommonItem) {
                                String overviewTxt = detailCommonItem.getOverview();
                                resultList.get(i).setOverview(overviewTxt);
                            }
                        }

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
