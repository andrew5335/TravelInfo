package com.eye2web.travel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * @File : BaseActivity
 * @Date : 2018. 5. 14. PM 4:42
 * @Author : Andrew Kim
 * @Version : 1.0.0
 * @Description : 공통으로 사용되는 항목의 컨트롤을 위한 BaseActivity
**/
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * @parameter :
     * @Date : 2018. 5. 14. PM 4:42
     * @Author : Andrew Kim
     * @Description : 관광정보 버튼
    **/
    public void onBtnTravelClicked(View v) {
        Toast.makeText(getApplicationContext(), "btn travel clicked", Toast.LENGTH_LONG).show();
    }

    /**
     * @parameter :
     * @Date : 2018. 5. 14. PM 4:43
     * @Author : Andrew Kim
     * @Description : 공연정보 버튼
    **/
    public void onBtnFestClicked(View v) {
        Toast.makeText(getApplicationContext(), "btn fest clicked", Toast.LENGTH_LONG).show();
    }

    /**
     * @parameter :
     * @Date : 2018. 5. 14. PM 4:43
     * @Author : Andrew Kim
     * @Description : 숙박정보 버튼
    **/
    public void onBtnStayClicked(View v) {
        Toast.makeText(getApplicationContext(), "btn stay clicked", Toast.LENGTH_LONG).show();
    }

    /**
     * @parameter :
     * @Date : 2018. 5. 14. PM 4:43
     * @Author : Andrew Kim
     * @Description : 맛집정보 버튼
    **/
    public void onBtnFoodClicked(View v) {
        Toast.makeText(getApplicationContext(), "btn food clicked", Toast.LENGTH_LONG).show();
    }

    /**
     * @parameter :
     * @Date : 2018. 5. 14. PM 4:43
     * @Author : Andrew Kim
     * @Description : 레포츠 버튼
    **/
    public void onBtnSportClicked(View v) {
        Toast.makeText(getApplicationContext(), "btn sport clicked", Toast.LENGTH_LONG).show();
    }

    /**
     * @parameter :
     * @Date : 2018. 5. 14. PM 4:44
     * @Author : Andrew Kim
     * @Description : 쇼핑 버튼
    **/
    public void onBtnShopClicked(View v) {
        Toast.makeText(getApplicationContext(), "btn shop clicked", Toast.LENGTH_LONG).show();
    }

    /**
     * @parameter :
     * @Date : 2018. 5. 14. PM 4:44
     * @Author : Andrew Kim
     * @Description : 문화시설 버튼
    **/
    public void onBtnCultureClicked(View v) {
        Toast.makeText(getApplicationContext(), "btn culture clicked", Toast.LENGTH_LONG).show();
    }

    /**
     * @parameter :
     * @Date : 2018. 5. 14. PM 4:44
     * @Author : Andrew Kim
     * @Description : 여행코스 버튼
    **/
    public void onBtnCourseClicked(View v) {
        Toast.makeText(getApplicationContext(), "btn course clicked", Toast.LENGTH_LONG).show();
    }

    /**
     * @parameter :
     * @Date : 2018. 5. 14. PM 4:50
     * @Author : Andrew Kim
     * @Description : 뒤로가기 버튼
    **/
    @Override
    public void onBackPressed() {
        //this.backPressCloseHandler.onBackPressed();
        super.onBackPressed();    // index가 아닌 경우에는 그냥 이전 activity로 이동
    }
}