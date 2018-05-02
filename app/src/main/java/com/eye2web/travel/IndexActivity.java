package com.eye2web.travel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * @File : IndexActivity
 * @Date : 2018. 5. 2. PM 4:43
 * @Author : Andrew Kim
 * @Version : 1.0.0
 * @Description : Splash 화면 구동 후 최초로 로딩되는 화면
**/
public class IndexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
    }
}
