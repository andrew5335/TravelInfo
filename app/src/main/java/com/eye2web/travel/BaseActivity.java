package com.eye2web.travel;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.eye2web.travel.handler.BackPressCloseHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @File : BaseActivity
 * @Date : 2018. 5. 14. PM 4:42
 * @Author : Andrew Kim
 * @Version : 1.0.0
 * @Description : 공통으로 사용되는 항목의 컨트롤을 위한 BaseActivity
**/
public class BaseActivity extends AppCompatActivity {

    private BackPressCloseHandler backPressCloseHandler;

    private Intent cityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    public Map<String, Object> getGpsInfo() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        boolean isGPSEnabled;
        boolean isNetworkEnabled;

        double lat = 0;
        double lng = 0;

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                //Toast.makeText(getApplicationContext(), "latitude : " + lat + ", longitude : " + lng, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                //Toast.makeText(getApplicationContext(), "status changed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onProviderEnabled(String provider) {
                //Toast.makeText(getApplicationContext(), "provider enabled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onProviderDisabled(String provider) {
                //Toast.makeText(getApplicationContext(), "provider disabled", Toast.LENGTH_LONG).show();
            }
        };

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        String locationProvider = LocationManager.GPS_PROVIDER;
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);

        if(null != lastKnownLocation) {
            lng = lastKnownLocation.getLongitude();
            lat = lastKnownLocation.getLatitude();
            //Toast.makeText(getApplicationContext(), "latitude2 : " + lat + ", longtitude2 : " + lng, Toast.LENGTH_LONG).show();
        }

        resultMap.put("mapX", lng);
        resultMap.put("mapY", lat);

        return resultMap;
    }

    /**
     * @parameter :
     * @Date : 2018. 5. 14. PM 4:42
     * @Author : Andrew Kim
     * @Description : 관광정보 버튼
    **/
    /**
    public void onBtnTravelClicked(View v) {
        //Toast.makeText(getApplicationContext(), "btn travel clicked", Toast.LENGTH_LONG).show();
        Intent travelIntent = new Intent(getApplicationContext(), MenuListActivity.class);
        travelIntent.putExtra("gu", "travel");
        travelIntent.putExtra("cateGu", "12");
        travelIntent.putExtra("cateName", "Travel Info");
        startActivity(travelIntent);
    }
     **/

    /**
     * @parameter :
     * @Date : 2018. 5. 14. PM 4:43
     * @Author : Andrew Kim
     * @Description : 공연정보 버튼
    **/
    /**
    public void onBtnFestClicked(View v) {
        //Toast.makeText(getApplicationContext(), "btn fest clicked", Toast.LENGTH_LONG).show();
        Intent festIntent = new Intent(getApplicationContext(), MenuListActivity.class);
        festIntent.putExtra("gu", "festival");
        festIntent.putExtra("cateGu", "15");
        festIntent.putExtra("cateName", "Festival Info");
        startActivity(festIntent);
    }
     **/

    /**
     * @parameter :
     * @Date : 2018. 5. 14. PM 4:43
     * @Author : Andrew Kim
     * @Description : 숙박정보 버튼
    **/
    /**
    public void onBtnStayClicked(View v) {
        //Toast.makeText(getApplicationContext(), "btn stay clicked", Toast.LENGTH_LONG).show();
        Intent stayIntent = new Intent(getApplicationContext(), MenuListActivity.class);
        stayIntent.putExtra("gu", "stay");
        stayIntent.putExtra("cateGu", "32");
        stayIntent.putExtra("cateName", "Stay Info");
        startActivity(stayIntent);
    }
     **/

    /**
     * @parameter :
     * @Date : 2018. 5. 14. PM 4:43
     * @Author : Andrew Kim
     * @Description : 맛집정보 버튼
    **/
    /**
    public void onBtnFoodClicked(View v) {
        //Toast.makeText(getApplicationContext(), "btn food clicked", Toast.LENGTH_LONG).show();
        Intent foodIntent = new Intent(getApplicationContext(), MenuListActivity.class);
        foodIntent.putExtra("gu", "food");
        foodIntent.putExtra("cateGu", "39");
        foodIntent.putExtra("cateName", "Food Info");
        startActivity(foodIntent);
    }
     **/

    /**
     * @parameter :
     * @Date : 2018. 5. 14. PM 4:43
     * @Author : Andrew Kim
     * @Description : 레포츠 버튼
    **/
    /**
    public void onBtnSportClicked(View v) {
        //Toast.makeText(getApplicationContext(), "btn sport clicked", Toast.LENGTH_LONG).show();
        Intent sportIntent = new Intent(getApplicationContext(), MenuListActivity.class);
        sportIntent.putExtra("gu", "sport");
        sportIntent.putExtra("cateGu", "28");
        sportIntent.putExtra("cateName", "Leports Info");
        startActivity(sportIntent);
    }
     **/

    /**
     * @parameter :
     * @Date : 2018. 5. 14. PM 4:44
     * @Author : Andrew Kim
     * @Description : 쇼핑 버튼
    **/
    /**
    public void onBtnShopClicked(View v) {
        //Toast.makeText(getApplicationContext(), "btn shop clicked", Toast.LENGTH_LONG).show();
        Intent shopIntent = new Intent(getApplicationContext(), MenuListActivity.class);
        shopIntent.putExtra("gu", "shop");
        shopIntent.putExtra("cateGu", "38");
        shopIntent.putExtra("cateName", "Shopping Info");
        startActivity(shopIntent);
    }
     **/

    /**
     * @parameter :
     * @Date : 2018. 5. 14. PM 4:44
     * @Author : Andrew Kim
     * @Description : 문화시설 버튼
    **/
    /**
    public void onBtnCultureClicked(View v) {
        //Toast.makeText(getApplicationContext(), "btn culture clicked", Toast.LENGTH_LONG).show();
        Intent cultureIntent = new Intent(getApplicationContext(), MenuListActivity.class);
        cultureIntent.putExtra("gu", "culture");
        cultureIntent.putExtra("cateGu", "14");
        cultureIntent.putExtra("cateName", "Cultural Facility Info");
        startActivity(cultureIntent);
    }
     **/

    /**
     * @parameter :
     * @Date : 2018. 5. 14. PM 4:44
     * @Author : Andrew Kim
     * @Description : 여행코스 버튼
    **/
    /**
    public void onBtnCourseClicked(View v) {
        //Toast.makeText(getApplicationContext(), "btn course clicked", Toast.LENGTH_LONG).show();
        Intent courseIntent = new Intent(getApplicationContext(), MenuListActivity.class);
        courseIntent.putExtra("gu", "course");
        courseIntent.putExtra("cateGu", "25");
        courseIntent.putExtra("cateName", "Travel Course Info");
        startActivity(courseIntent);
    }
     **/

    /**
     * @parameter :
     * @Date : 2018. 5. 18. PM 3:24
     * @Author : Andrew Kim
     * @Description : 검색버튼
    **/
    public void onSearchBtnClicked(View v) {
        //Toast.makeText(getApplicationContext(), "search btn clicked", Toast.LENGTH_LONG).show();
        Intent searchIntent = new Intent(getApplicationContext(), SearchActivity.class);
        startActivity(searchIntent);
    }

    /**
     * @parameter :
     * @Date : 2018. 5. 25. PM 2:04
     * @Author : Andrew Kim
     * @Description : 홈 버튼
    **/
    public void onHomeBtnClicked(View v) {
        Intent homeIntent = new Intent(getApplicationContext(), IndexActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
        finish();
    }

    /**
     * @parameter :
     * @Date : 2018. 5. 25. PM 2:04
     * @Author : Andrew Kim
     * @Description : 가이드 버튼
    **/
    public void onGuideBtnClicked(View v) {

    }

    /**
     * @parameter :
     * @Date : 2018. 5. 25. PM 2:05
     * @Author : Andrew Kim
     * @Description : 내 정보 버튼
    **/
    public void onInfoBtnClicked(View v) {

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

    /**
     *
     **/

    /**
     * @parameter :
     * @Date : 2018-06-04 오후 4:45
     * @Author : Andrew Kim
     * @Description : 서울버튼 클릭
    **/
    public void seoulBtnClicked(View v) {
        cityIntent = new Intent(getApplicationContext(), CityMainActivity.class);
        cityIntent.putExtra("cityGu", "1");
        startActivity(cityIntent);
    }

    /**
     * @parameter :
     * @Date : 2018-06-04 오후 4:45
     * @Author : Andrew Kim
     * @Description : 인천버튼 클릭
    **/
    public void incheonBtnClicked(View v) {
        cityIntent = new Intent(getApplicationContext(), CityMainActivity.class);
        cityIntent.putExtra("cityGu", "2");
        startActivity(cityIntent);
    }

    /**
     * @parameter :
     * @Date : 2018-06-04 오후 4:46
     * @Author : Andrew Kim
     * @Description : 대전버튼 클릭
    **/
    public void daejeonBtnClicked(View v) {
        cityIntent = new Intent(getApplicationContext(), CityMainActivity.class);
        cityIntent.putExtra("cityGu", "3");
        startActivity(cityIntent);
    }

    /**
     * @parameter :
     * @Date : 2018-06-04 오후 4:46
     * @Author : Andrew Kim
     * @Description : 대구버튼 클릭
    **/
    public void daeguBtnClicked(View v) {
        cityIntent = new Intent(getApplicationContext(), CityMainActivity.class);
        cityIntent.putExtra("cityGu", "4");
        startActivity(cityIntent);
    }

    /**
     * @parameter :
     * @Date : 2018-06-04 오후 4:46
     * @Author : Andrew Kim
     * @Description : 부산버튼 클릭
    **/
    public void busanBtnClicked(View v) {
        cityIntent = new Intent(getApplicationContext(), CityMainActivity.class);
        cityIntent.putExtra("cityGu", "6");
        startActivity(cityIntent);
    }

    /**
     * @parameter :
     * @Date : 2018-06-04 오후 4:46
     * @Author : Andrew Kim
     * @Description : 광주버튼 클릭
    **/
    public void kwangjuBtnClicked(View v) {
        cityIntent = new Intent(getApplicationContext(), CityMainActivity.class);
        cityIntent.putExtra("cityGu", "5");
        startActivity(cityIntent);
    }

    /**
     * @parameter :
     * @Date : 2018-06-04 오후 4:46
     * @Author : Andrew Kim
     * @Description : 울산버튼 클릭
    **/
    public void ulsanBtnClicked(View v) {
        cityIntent = new Intent(getApplicationContext(), CityMainActivity.class);
        cityIntent.putExtra("cityGu", "7");
        startActivity(cityIntent);
    }

    /**
     * @parameter :
     * @Date : 2018-06-04 오후 4:47
     * @Author : Andrew Kim
     * @Description : 세종버튼 클릭
    **/
    public void sejongBtnClicked(View v) {
        cityIntent = new Intent(getApplicationContext(), CityMainActivity.class);
        cityIntent.putExtra("cityGu", "8");
        startActivity(cityIntent);
    }

    /**
     * @parameter :
     * @Date : 2018-06-04 오후 4:47
     * @Author : Andrew Kim
     * @Description : 경기버튼 클릭
    **/
    public void kyunggiBtnClicked(View v) {
        cityIntent = new Intent(getApplicationContext(), CityMainActivity.class);
        cityIntent.putExtra("cityGu", "31");
        startActivity(cityIntent);
    }

    /**
     * @parameter :
     * @Date : 2018-06-04 오후 4:47
     * @Author : Andrew Kim
     * @Description : 강원버튼 클릭
    **/
    public void kangwonBtnClicked(View v) {
        cityIntent = new Intent(getApplicationContext(), CityMainActivity.class);
        cityIntent.putExtra("cityGu", "32");
        startActivity(cityIntent);
    }

    /**
     * @parameter :
     * @Date : 2018-06-04 오후 4:48
     * @Author : Andrew Kim
     * @Description : 충북버튼 클릭
    **/
    public void chungbookBtnClicked(View v) {
        cityIntent = new Intent(getApplicationContext(), CityMainActivity.class);
        cityIntent.putExtra("cityGu", "33");
        startActivity(cityIntent);
    }

    /**
     * @parameter :
     * @Date : 2018-06-04 오후 4:48
     * @Author : Andrew Kim
     * @Description : 충남버튼 클릭
    **/
    public void chungnamBtnClicked(View v) {
        cityIntent = new Intent(getApplicationContext(), CityMainActivity.class);
        cityIntent.putExtra("cityGu", "34");
        startActivity(cityIntent);
    }

    /**
     * @parameter :
     * @Date : 2018-06-04 오후 4:48
     * @Author : Andrew Kim
     * @Description : 전북버튼 클릭
    **/
    public void jeonbookBtnClicked(View v) {
        cityIntent = new Intent(getApplicationContext(), CityMainActivity.class);
        cityIntent.putExtra("cityGu", "37");
        startActivity(cityIntent);
    }

    /**
     * @parameter :
     * @Date : 2018-06-04 오후 4:48
     * @Author : Andrew Kim
     * @Description : 전남버튼 클릭
    **/
    public void jeonnamBtnClicked(View v) {
        cityIntent = new Intent(getApplicationContext(), CityMainActivity.class);
        cityIntent.putExtra("cityGu", "38");
        startActivity(cityIntent);
    }

    /**
     * @parameter :
     * @Date : 2018-06-04 오후 4:48
     * @Author : Andrew Kim
     * @Description : 경북버튼 클릭
    **/
    public void kyungbookBtnClicked(View v) {
        cityIntent = new Intent(getApplicationContext(), CityMainActivity.class);
        cityIntent.putExtra("cityGu", "35");
        startActivity(cityIntent);
    }

    /**
     * @parameter :
     * @Date : 2018-06-04 오후 4:48
     * @Author : Andrew Kim
     * @Description : 경남버튼 클릭
    **/
    public void kyungnamBtnClicked(View v) {
        cityIntent = new Intent(getApplicationContext(), CityMainActivity.class);
        cityIntent.putExtra("cityGu", "36");
        startActivity(cityIntent);
    }

    /**
     * @parameter :
     * @Date : 2018-06-04 오후 4:49
     * @Author : Andrew Kim
     * @Description : 제주버튼 클릭
    **/
    public void jejuBtnClicked(View v) {
        cityIntent = new Intent(getApplicationContext(), CityMainActivity.class);
        cityIntent.putExtra("cityGu", "39");
        startActivity(cityIntent);
    }

}
