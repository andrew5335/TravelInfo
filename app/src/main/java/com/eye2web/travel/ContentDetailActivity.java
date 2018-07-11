package com.eye2web.travel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import com.eye2web.travel.apivo.Eye2WebContent;
import com.eye2web.travel.handler.PicassoImageGetter;
import com.eye2web.travel.service.Eye2WebApiService;
import com.eye2web.travel.util.CommonUtil;
import com.eye2web.travel.util.JsonParsingUtil;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ContentDetailActivity extends BaseActivity {

    private Eye2WebApiService eye2WebApiService;
    private Eye2WebContent eye2WebContent;
    private int contentId = 0;
    private TextView contentTitle;
    private TextView contentDetail;

    private CommonUtil commonUtil;
    private JsonParsingUtil jsonParsingUtil;

    private Handler handler;

    private AdView mAdView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_detail);

        String admobId = getResources().getString(R.string.google_admob_id);
        MobileAds.initialize(this, admobId);    // admob 초기화

        // 배너 광고는 한 activity에 1개만 삽입 가능
        mAdView1 = findViewById(R.id.adView);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mAdView1.loadAd(adRequest1);

        commonUtil = new CommonUtil();
        jsonParsingUtil = new JsonParsingUtil();

        Intent contentIntent = getIntent();
        contentId = contentIntent.getIntExtra("contentId", 0);

        contentTitle = (TextView) findViewById(R.id.contentTitle);
        contentDetail = (TextView) findViewById(R.id.contentDetailTxt);

        if(0 < contentId) {

            handler = new Handler();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    eye2WebContent = new Eye2WebContent();
                    eye2WebContent = getEye2WebContent(contentId);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(null != eye2WebContent) {
                                if(20 < eye2WebContent.getTitle().getRendered().length()) {
                                    contentTitle.setText(eye2WebContent.getTitle().getRendered().substring(0, 20) + "...");
                                } else {
                                    contentTitle.setText(eye2WebContent.getTitle().getRendered());
                                }
                                //Spanned content = null;
                                //content = commonUtil.convertTxtToHtml(eye2WebContent.getContent().getRendered());
                                //ImageGetter imageGetter = new ImageGetter();
                                //Spanned content = Html.fromHtml(eye2WebContent.getContent().getRendered(), imageGetter, null);
                                Spanned content = Html.fromHtml(eye2WebContent.getContent().getRendered()
                                        , new PicassoImageGetter(contentDetail), null );
                                contentDetail.setText(content);

                            }
                        }
                    });
                }
            }).start();
        }
    }

    public Eye2WebContent getEye2WebContent(int id) {
        Eye2WebContent result = new Eye2WebContent();
        eye2WebApiService = new Eye2WebApiService();

        try {
            result = eye2WebApiService.getEye2WebContentDetail(id);
        } catch(Exception e) {
            Log.e("Error", "Eye2Web Content Api Call Error : " + e.toString());
        }

        return result;
    }

    private class ImageGetter implements Html.ImageGetter {

        @Override
        public Drawable getDrawable(String source) {

            Drawable d = null;
            URL imgUrl = null;
            URLConnection urlConnection = null;
            BufferedInputStream bufferedInputStream = null;

            try {

                imgUrl = new URL(source);
                urlConnection = imgUrl.openConnection();
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();

                bufferedInputStream = new BufferedInputStream(inputStream);

                Bitmap bm = BitmapFactory.decodeStream(bufferedInputStream);
                //bm = Bitmap.createScaledBitmap(bm, 640, 480, true);

                d = new BitmapDrawable(getResources(), bm);

                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);

                int width = size.x;
                int height = size.y;

                //if(source.contains("23px-Disambig_grey.svg.png") || source.contains("110px-Seal_of_Seoul.svg.png")
                //        || source.contains("110px-Slogan_of_Seoul_I.SEOUL.U.jpg") || source.contains("6px-Red_pog.svg.png")
                //        || source.contains("22px-Flag_of_South_Korea.svg.png")) {
                //    d.setBounds(0, 0, bm.getWidth(), bm.getHeight());
                //} else {
                    //d.setBounds(0, 0, width, (height * d.getIntrinsicHeight() / d.getIntrinsicWidth()));
                    d.setBounds(0, 0, bm.getWidth(), bm.getHeight());
                //}
            } catch (Exception e) {
                Log.e("Error", "Error : " + e.toString());
            }

            return d;
        }
    };

    public void onGobackBtnClicked(View v) {
        super.onBackPressed();
    }
}
