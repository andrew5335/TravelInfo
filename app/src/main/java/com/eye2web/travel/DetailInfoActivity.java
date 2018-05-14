package com.eye2web.travel;

import android.content.Intent;
import android.os.Bundle;

import com.eye2web.travel.vo.ListItem;

/**
 * @File : DetailInfoActivity
 * @Date : 2018. 5. 14. PM 4:48
 * @Author : Andrew Kim
 * @Version : 1.0.0
 * @Description : 상세정보 페이지
**/
public class DetailInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);

        Intent detailInfoIntent = getIntent();
        ListItem item = (ListItem) detailInfoIntent.getSerializableExtra("item");

        //Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_LONG).show();
    }
}
