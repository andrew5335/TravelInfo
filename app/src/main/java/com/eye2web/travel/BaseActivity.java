package com.eye2web.travel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        Intent travelIntent = new Intent(getApplicationContext(), MenuActivity.class);
        travelIntent.putExtra("gu", "travel");
        startActivity(travelIntent);
    }

    /**
     * @parameter :
     * @Date : 2018. 5. 14. PM 4:43
     * @Author : Andrew Kim
     * @Description : 공연정보 버튼
    **/
    public void onBtnFestClicked(View v) {
        Toast.makeText(getApplicationContext(), "btn fest clicked", Toast.LENGTH_LONG).show();
        Intent festIntent = new Intent(getApplicationContext(), MenuActivity.class);
        festIntent.putExtra("gu", "festival");
        startActivity(festIntent);
    }

    /**
     * @parameter :
     * @Date : 2018. 5. 14. PM 4:43
     * @Author : Andrew Kim
     * @Description : 숙박정보 버튼
    **/
    public void onBtnStayClicked(View v) {
        Toast.makeText(getApplicationContext(), "btn stay clicked", Toast.LENGTH_LONG).show();
        Intent stayIntent = new Intent(getApplicationContext(), MenuActivity.class);
        stayIntent.putExtra("gu", "stay");
        startActivity(stayIntent);
    }

    /**
     * @parameter :
     * @Date : 2018. 5. 14. PM 4:43
     * @Author : Andrew Kim
     * @Description : 맛집정보 버튼
    **/
    public void onBtnFoodClicked(View v) {
        Toast.makeText(getApplicationContext(), "btn food clicked", Toast.LENGTH_LONG).show();
        Intent foodIntent = new Intent(getApplicationContext(), MenuActivity.class);
        foodIntent.putExtra("gu", "food");
        startActivity(foodIntent);
    }

    /**
     * @parameter :
     * @Date : 2018. 5. 14. PM 4:43
     * @Author : Andrew Kim
     * @Description : 레포츠 버튼
    **/
    public void onBtnSportClicked(View v) {
        Toast.makeText(getApplicationContext(), "btn sport clicked", Toast.LENGTH_LONG).show();
        Intent sportIntent = new Intent(getApplicationContext(), MenuActivity.class);
        sportIntent.putExtra("gu", "sport");
        startActivity(sportIntent);
    }

    /**
     * @parameter :
     * @Date : 2018. 5. 14. PM 4:44
     * @Author : Andrew Kim
     * @Description : 쇼핑 버튼
    **/
    public void onBtnShopClicked(View v) {
        Toast.makeText(getApplicationContext(), "btn shop clicked", Toast.LENGTH_LONG).show();
        Intent shopIntent = new Intent(getApplicationContext(), MenuActivity.class);
        shopIntent.putExtra("gu", "shop");
        startActivity(shopIntent);
    }

    /**
     * @parameter :
     * @Date : 2018. 5. 14. PM 4:44
     * @Author : Andrew Kim
     * @Description : 문화시설 버튼
    **/
    public void onBtnCultureClicked(View v) {
        Toast.makeText(getApplicationContext(), "btn culture clicked", Toast.LENGTH_LONG).show();
        Intent cultureIntent = new Intent(getApplicationContext(), MenuActivity.class);
        cultureIntent.putExtra("gu", "culture");
        startActivity(cultureIntent);
    }

    /**
     * @parameter :
     * @Date : 2018. 5. 14. PM 4:44
     * @Author : Andrew Kim
     * @Description : 여행코스 버튼
    **/
    public void onBtnCourseClicked(View v) {
        Toast.makeText(getApplicationContext(), "btn course clicked", Toast.LENGTH_LONG).show();
        Intent courseIntent = new Intent(getApplicationContext(), MenuActivity.class);
        courseIntent.putExtra("gu", "course");
        startActivity(courseIntent);
    }

    /**
     * @parameter :
     * @Date : 2018. 5. 18. PM 3:24
     * @Author : Andrew Kim
     * @Description : 검색버튼
    **/
    public void onSearchBtnClicked(View v) {
        Toast.makeText(getApplicationContext(), "search btn clicked", Toast.LENGTH_LONG).show();
        Intent searchIntent = new Intent(getApplicationContext(), SearchActivity.class);
        startActivity(searchIntent);
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
