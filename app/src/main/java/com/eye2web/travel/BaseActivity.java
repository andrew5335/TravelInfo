package com.eye2web.travel;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.eye2web.travel.handler.BackPressCloseHandler;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;

import java.util.HashMap;
import java.util.Map;

/**
 * @File : BaseActivity
 * @Date : 2018. 5. 14. PM 4:42
 * @Author : Andrew Kim
 * @Version : 1.0.0
 * @Description : 공통으로 사용되는 항목의 컨트롤을 위한 BaseActivity
**/
public class BaseActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private BackPressCloseHandler backPressCloseHandler;

    private Intent cityIntent;

    private GoogleApiClient googleApiClient;    // google api client

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();
    }

    public boolean getGoogleApiClientConnect() {
        boolean result = false;

        try {
            googleApiClient.connect();
        } catch(Exception e) {
            Log.e("Error", "Error : " + e.toString());
        }

        if(googleApiClient.isConnected()) {
            result = true;
        }

        return result;
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
        Intent homeIntent = new Intent(getApplicationContext(), AppInfoActivity.class);
        startActivity(homeIntent);
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
