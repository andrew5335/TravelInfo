package com.eye2web.travel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.eye2web.travel.adapter.AreaSpinnerAdapter;
import com.eye2web.travel.service.AreaApiService;
import com.eye2web.travel.vo.AreaListItem;

import java.util.ArrayList;
import java.util.Collections;

public class SearchActivity extends BaseActivity {

    private AreaApiService areaApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        Spinner areaSpinner = (Spinner) findViewById(R.id.topArea);
        Spinner sigunguSpinner = (Spinner) findViewById(R.id.sigungu);

        ArrayList<AreaListItem> areaList = new ArrayList<AreaListItem>();
        ArrayList<AreaListItem> sigunguList = new ArrayList<AreaListItem>();
        areaList = getAreaList();
        sigunguList = getSigunguList();

        if(null != areaList && 0 < areaList.size()) {
            AreaSpinnerAdapter areaSpinnerAdapter = new AreaSpinnerAdapter(this, R.layout.areaitem, areaList);
            //ArrayAdapter<AreaListItem> areaSpinnerAdapter = new ArrayAdapter<AreaListItem>(this, R.layout.support_simple_spinner_dropdown_item, areaList);
            //areaSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            areaSpinner.setAdapter(areaSpinnerAdapter);
        }

        if(null != sigunguList && 0 < sigunguList.size()) {
            AreaSpinnerAdapter sigunguSpinnerAdapter = new AreaSpinnerAdapter(this, R.layout.areaitem, sigunguList);
            sigunguSpinner.setAdapter(sigunguSpinnerAdapter);
        }

        areaSpinner.setOnItemSelectedListener(itemSelectedListener);
        sigunguSpinner.setOnItemSelectedListener(itemSelectedListener);


        final EditText keyword = (EditText) findViewById(R.id.keyword);
        keyword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    keyword.setText("");
                } else {
                    keyword.setText("검색어를 입력하세요.");
                }
            }
        });
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

    public ArrayList<AreaListItem> getSigunguList() {
        ArrayList<AreaListItem> resultList = new ArrayList<AreaListItem>();

        AreaListItem initial = new AreaListItem("0", "지역선택", 0);
        AreaListItem seoul = new AreaListItem("1", "서울", 1);
        AreaListItem incheon = new AreaListItem("2", "인천", 2);
        AreaListItem daejeon = new AreaListItem("3", "대전", 3);
        AreaListItem daegu = new AreaListItem("4", "대구", 4);
        AreaListItem busan = new AreaListItem("6", "부산", 5);
        AreaListItem kwangju = new AreaListItem("5", "광주", 6);
        AreaListItem ulsan = new AreaListItem("7", "울산", 7);
        AreaListItem sejong = new AreaListItem("8", "세종", 8);
        AreaListItem kyunggi = new AreaListItem("31", "경기도", 9);
        AreaListItem kangwon = new AreaListItem("32", "강원도", 10);
        AreaListItem chungbook = new AreaListItem("33", "충청북도", 11);
        AreaListItem chungnam = new AreaListItem("34", "충청남도", 12);
        AreaListItem kyungbook = new AreaListItem("35", "경상북도", 13);
        AreaListItem kyungnam = new AreaListItem("36", "경상남도", 14);
        AreaListItem jeonbook = new AreaListItem("37", "전라북도", 15);
        AreaListItem jeonnam = new AreaListItem("38", "전라남도", 16);
        AreaListItem jeju = new AreaListItem("39", "제주도", 17);

        resultList.add(initial);
        resultList.add(seoul);
        resultList.add(incheon);
        resultList.add(daejeon);
        resultList.add(daegu);
        resultList.add(busan);
        resultList.add(kwangju);
        resultList.add(ulsan);
        resultList.add(sejong);
        resultList.add(kyunggi);
        resultList.add(kangwon);
        resultList.add(chungbook);
        resultList.add(chungnam);
        resultList.add(kyungbook);
        resultList.add(kyungnam);
        resultList.add(jeonbook);
        resultList.add(jeonnam);
        resultList.add(jeju);

        Collections.sort(resultList);

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
        Spinner sigungu = (Spinner) findViewById(R.id.sigungu);
        EditText keyword = (EditText) findViewById(R.id.keyword);

        AreaListItem areaListItem = (AreaListItem) area.getSelectedItem();    // 선택된 spinner 객체값 받기
        AreaListItem sigunguItem = (AreaListItem) sigungu.getSelectedItem();

        String areaCodeStr = "";
        String keywordStr = "";
        String sigunguCode = "";

        areaCodeStr = areaListItem.getCode();
        sigunguCode = sigunguItem.getCode();
        keywordStr = keyword.getText().toString();

        //Toast.makeText(this, areaCodeStr + "-" + keywordStr, Toast.LENGTH_LONG).show();
        Intent searchIntent = new Intent(this, SearchListActivity.class);
        searchIntent.putExtra("areaCode", areaCodeStr);
        searchIntent.putExtra("sigunguCode", sigunguCode);
        searchIntent.putExtra("keyword", keywordStr);
        startActivity(searchIntent);
        finish();
    }

    public void onGobackBtnClicked(View v) {
        super.onBackPressed();
    }
}
