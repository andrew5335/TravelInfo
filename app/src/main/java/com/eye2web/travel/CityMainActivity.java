package com.eye2web.travel;

import android.os.Bundle;

/**
 * @File : CityMainActivity
 * @Date : 2018. 6. 4. PM 2:25
 * @Author : Andrew Kim
 * @Version : 1.0.0
 * @Description : 초기화면에서 도시 선택 시 연결되는 도시 메인 Activity
**/
public class CityMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_main);
    }
}
