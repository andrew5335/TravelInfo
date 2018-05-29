package com.eye2web.travel;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.eye2web.travel.adapter.IndexPagerAdapter;
import com.eye2web.travel.handler.BackPressCloseHandler;
import com.eye2web.travel.service.AreaApiService;
import com.eye2web.travel.vo.AreaListItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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

    private IndexPagerAdapter indexPagerAdapter;

    private BackPressCloseHandler backPressCloseHandler;

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

        if(Build.VERSION.SDK_INT >= 23
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }

        ViewPager indexMenuPager = (ViewPager) findViewById(R.id.index_menu_pager);
        indexPagerAdapter = new IndexPagerAdapter(this, getLayoutInflater());
        indexMenuPager.setAdapter(indexPagerAdapter);

        this.backPressCloseHandler = new BackPressCloseHandler(this);    // 뒤로가기 처리

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
            //resultList = areaApiService.getAreaCodeList(addr, serviceGu, serviceKey, "", "");

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

    public void onLocBtnClicked(View v) {
        double mapX = 0;
        double mapY = 0;
        Map<String, Object> gpsMap = new HashMap<String, Object>();
        gpsMap = getGpsInfo();

        if(null != gpsMap && 0 < gpsMap.size()) {
            mapX = (Double) gpsMap.get("mapX");
            mapY = (Double) gpsMap.get("mapY");

            //Log.i("info", "===============gps info : " + mapX + "=============" + mapY);
            Intent locIntent = new Intent(this, MenuListActivity.class);
            locIntent.putExtra("mapX", mapX);
            locIntent.putExtra("mapY", mapY);
            locIntent.putExtra("loc", "loc");
            locIntent.putExtra("cateName", "주변 검색 결과");
            startActivity(locIntent);
        }
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
