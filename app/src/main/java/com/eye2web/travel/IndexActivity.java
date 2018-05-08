package com.eye2web.travel;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.eye2web.travel.adapter.AreaSpinnerAdapter;
import com.eye2web.travel.service.AreaApiService;
import com.eye2web.travel.vo.AreaListItem;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @File : IndexActivity
 * @Date : 2018. 5. 2. PM 4:43
 * @Author : Andrew Kim
 * @Version : 1.0.0
 * @Description : Splash 화면 구동 후 최초로 로딩되는 화면
**/
public class IndexActivity extends AppCompatActivity {

    private AreaApiService areaApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Spinner areaSpinner = (Spinner) findViewById(R.id.topArea);

        ArrayList<AreaListItem> areaList = new ArrayList<AreaListItem>();
        areaList = getAreaList();

        if(null != areaList && 0 < areaList.size()) {
            AreaSpinnerAdapter areaSpinnerAdapter = new AreaSpinnerAdapter(this, R.layout.areaitem, areaList);
            areaSpinner.setAdapter(areaSpinnerAdapter);
        }

        areaSpinner.setOnItemSelectedListener(itemSelectedListener);
    }

    public ArrayList<AreaListItem> getAreaList() {
        ArrayList<AreaListItem> resultList = new ArrayList<AreaListItem>();
        areaApiService = new AreaApiService();

        String addr = getResources().getString(R.string.apiUrl);
        String serviceKey = getResources().getString(R.string.apiKey);
        String serviceGu = "areaCode";

        try {
            resultList = areaApiService.getAreaCodeList(addr, serviceGu, serviceKey, "", "");
            AreaListItem initial = new AreaListItem("0", "지역선택", 0);
            resultList.add(initial);

            Collections.sort(resultList);
        } catch (Exception e) {
            Log.e("Error", "Error : " + e.toString());
        }

        return resultList;
    }

    public AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            AreaListItem item = (AreaListItem) parent.getAdapter().getItem(position);
            Toast.makeText(getApplicationContext(), item.getName() + "-" + item.getCode() + " selected !!!", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Spinner spinner = (Spinner) findViewById(R.id.topArea);
            spinner.setPrompt("지역선택");
        }
    };

}
