package com.eye2web.travel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * @File : SplashActivity
 * @Date : 2018. 5. 2. PM 4:47
 * @Author : Andrew Kim
 * @Version : 1.0.0
 * @Description : 앱 실행 초기 화면
**/
public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1500;

    private static final int requestReadPhoneState = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView splashImg = (ImageView) findViewById(R.id.splash_img);
        Picasso.get().load("http://www.eye2web.co.kr/images/logo_final.png").placeholder(R.mipmap.logo_final).into(splashImg);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this, IndexActivity.class);
                startActivity(mainIntent);

                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
