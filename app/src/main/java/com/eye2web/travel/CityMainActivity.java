package com.eye2web.travel;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * @File : CityMainActivity
 * @Date : 2018. 6. 4. PM 2:25
 * @Author : Andrew Kim
 * @Version : 1.0.0
 * @Description : 초기화면에서 도시 선택 시 연결되는 도시 메인 Activity
**/
public class CityMainActivity extends BaseActivity {

    private String cityGu;    // 도시 구분값
    private String citySetting;    // 도시 구분값에 따른 도시 세팅
    private String imageUrl;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        imageUrl = getResources().getString(R.string.image_url);
        imageUrl = imageUrl + "/images/indexmenu/";
        imageView = (ImageView) findViewById(R.id.city_main_image);
        Intent cityMainIntent = getIntent();
        cityGu = cityMainIntent.getStringExtra("cityGu");

        switch (cityGu) {
            case "1" :
                Picasso.get().load(imageUrl + "seoul.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case "2" :
                Picasso.get().load(imageUrl + "incheon.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case "3" :
                Picasso.get().load(imageUrl + "daejeon.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case "4" :
                Picasso.get().load(imageUrl + "daegu.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case "5" :
                Picasso.get().load(imageUrl + "kwangju.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case "6" :
                Picasso.get().load(imageUrl + "busan.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case "7" :
                Picasso.get().load(imageUrl + "ulsan.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case "8" :
                Picasso.get().load(imageUrl + "sejong.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case "31" :
                Picasso.get().load(imageUrl + "kyunggi2.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case "32" :
                Picasso.get().load(imageUrl + "kangwon.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case "33" :
                Picasso.get().load(imageUrl + "chungbook.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case "34" :
                Picasso.get().load(imageUrl + "chungnam.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case "35" :
                Picasso.get().load(imageUrl + "kyungbook.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case "36" :
                Picasso.get().load(imageUrl + "kyungnam.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case "37" :
                Picasso.get().load(imageUrl + "jeonbook.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case "38" :
                Picasso.get().load(imageUrl + "jeonnam.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case "39" :
                Picasso.get().load(imageUrl + "jeju.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;
        }
    }
}
