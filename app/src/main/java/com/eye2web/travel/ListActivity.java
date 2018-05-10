package com.eye2web.travel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eye2web.travel.adapter.ListViewAdapter;
import com.eye2web.travel.service.ApiService;
import com.eye2web.travel.vo.ListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @File : ListActivity
 * @Date : 2018. 5. 2. PM 4:44
 * @Author : Andrew Kim
 * @Version : 1.0.0
 * @Description : 초기화면에서 버튼 클릭에 따라 연결되는 화면
**/
public class ListActivity extends AppCompatActivity implements AbsListView.OnScrollListener {

    private ApiService apiservice;

    private ListView contentList;
    private boolean lastItemVisibleFlag = false;
    private List<ListItem> itemList;
    private ListViewAdapter adapter;
    private int page = 1;
    private final int offset = 20;
    private ProgressBar progressBar;
    private boolean mLockListView = false;
    private String areaCodeStr = "";
    private String keywordStr = "";
    private String sort = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        contentList = (ListView) findViewById(R.id.testList);

        Intent intent = getIntent();
        areaCodeStr = intent.getStringExtra("areaCode");
        keywordStr = intent.getStringExtra("keyword");
        sort = "A";    // 정렬방식 - A : 제목순 / B : 조회순 / C : 수정일순 / D : 생성일순 | 대표 이미지가 반드시 있는 정렬은 O/P/Q/R로 지정

        itemList = new ArrayList<ListItem>();
        //itemList = getContentList(areaCodeStr, keywordStr, sort);


        //if(null != itemList && 0 < itemList.size()) {
            adapter = new ListViewAdapter(this, R.layout.listitem, itemList);
            contentList.setAdapter(adapter);
            contentList.setOnScrollListener(this);

        getContentList(areaCodeStr, keywordStr, sort);
        //}

        //contentList.setOnItemClickListener(itemClickListener);

    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag && mLockListView == false) {
            getContentList(areaCodeStr, keywordStr, sort);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        lastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
    }

    private void getContentList(String code, String keyword, String sort) {
        mLockListView = true;

        apiservice = new ApiService();
        List<ListItem> resultList = new ArrayList<ListItem>();

        String addr = getResources().getString(R.string.apiUrl) + "searchKeyword?serviceKey=";
        String serviceKey = getResources().getString(R.string.apiKey);

        try {
            resultList = apiservice.getContent(addr, serviceKey, code, keyword, sort, page, offset);
        } catch(Exception e) {
            Log.e("Error", "==========Error : " + e.toString());
        }

        //return resultList;
        if(null != resultList && 0 < resultList.size()) {
            for(int i=0; i < resultList.size(); i++) {
                itemList.add(resultList.get(i));
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

    public AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView textView = (TextView) view.findViewById(R.id.listText);
            String text = textView.getText().toString();
            ListItem item = (ListItem) parent.getAdapter().getItem(position);
            Toast.makeText(getApplicationContext(), "item " + item.getAddr1() + "clicked !!!", Toast.LENGTH_LONG).show();
        }
    };
}
