package com.eye2web.travel;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
public class ListActivity extends AppCompatActivity {

    private ApiService apiservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ListView testList = (ListView) findViewById(R.id.testList);

        List<ListItem> itemList = new ArrayList<ListItem>();
        itemList = getContentList();

        if(null != itemList && 0 < itemList.size()) {
            ListViewAdapter adapter = new ListViewAdapter(this, R.layout.listitem, itemList);
            testList.setAdapter(adapter);
        }

        testList.setOnItemClickListener(itemClickListener);

    }

    public List<ListItem> getContentList() {
        apiservice = new ApiService();
        List<ListItem> resultList = new ArrayList<ListItem>();

        String addr = getResources().getString(R.string.apiUrl) + "detailCommon?serviceKey=";
        String serviceKey = getResources().getString(R.string.apiKey);

        try {
            resultList = apiservice.getContent(addr, serviceKey);
        } catch(Exception e) {
            Log.e("Error", "==========Error : " + e.toString());
        }

        return resultList;
    }

    public AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView textView = (TextView) view.findViewById(R.id.listText);
            String text = textView.getText().toString();
            Toast.makeText(getApplicationContext(), "item " + text + "clicked !!!", Toast.LENGTH_LONG).show();
        }
    };
}
