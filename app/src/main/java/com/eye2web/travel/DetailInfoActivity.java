package com.eye2web.travel;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eye2web.travel.service.DetailApiService;
import com.eye2web.travel.util.CommonUtil;
import com.eye2web.travel.vo.DetailCommonItem;
import com.eye2web.travel.vo.DetailIntroItem;
import com.eye2web.travel.vo.ListItem;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

/**
 * @File : DetailInfoActivity
 * @Date : 2018. 5. 14. PM 4:48
 * @Author : Andrew Kim
 * @Version : 1.0.0
 * @Description : 상세정보 페이지
**/
public class DetailInfoActivity extends BaseActivity {

    private DetailApiService detailApiService;

    private CommonUtil commonUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);

        Intent detailInfoIntent = getIntent();
        ListItem item = (ListItem) detailInfoIntent.getSerializableExtra("item");

        String contentId = "";
        String contentTypeId = "";
        String areaCode = "";
        float mapx = 0;
        float mapy = 0;

        contentId = item.getContentid();
        contentTypeId = item.getContenttypeid();
        areaCode = item.getAreacode();
        mapx = item.getMapx();
        mapy = item.getMapy();

        getContent(contentId, contentTypeId, areaCode, mapx, mapy);
    }

    /**
     * @parameter : contentId - 컨텐츠 아이디값
     *              contentTypeId - 컨텐츠 타입 (관광, 숙박, 공연 등)
     *              areaCode - 지역코드
     *              mapx - GPS X좌표값
     *              mapy - GPS Y좌표값
     * @Date : 2018. 5. 15. AM 9:35
     * @Author : Andrew Kim
     * @Description : 상세정보 조회
    **/
    public void getContent(String contentId, String contentTypeId, String areaCode, float mapx, float mapy) {
        detailApiService = new DetailApiService();
        DetailIntroItem detailIntroItem = new DetailIntroItem();
        DetailCommonItem detailCommonItem = new DetailCommonItem();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        String addr = getResources().getString(R.string.apiUrl);
        String serviceKey = getResources().getString(R.string.apiKey);

        try {
            resultMap = detailApiService.getDetailInfo(addr, serviceKey, contentId, contentTypeId, areaCode, mapx, mapy);
        } catch(Exception e) {
            Log.e("Error", "Error : " + e.toString());
        }

        if(null != resultMap && 0 < resultMap.size()) {

            detailCommonItem = (DetailCommonItem) resultMap.get("detailCommon");

            String overView = "";
            String homepage = "";
            String title = "";
            String addr1 = "";
            String addr2 = "";
            String firstImage = "";
            String firstImage2 = "";

            overView = detailCommonItem.getOverview();
            homepage = detailCommonItem.getHomepage();
            title = detailCommonItem.getTitle();
            addr1 = detailCommonItem.getAddr1();
            addr2 = detailCommonItem.getAddr2();
            firstImage = detailCommonItem.getFirstimage();
            firstImage2 = detailCommonItem.getFirstimage2();

            Log.i("INFO", "================detail Info : " + overView);

            ImageView detailImg1 = (ImageView) findViewById(R.id.detailImg1);
            ImageView detailImg2 = (ImageView) findViewById(R.id.detailImg2);
            TextView detailTitle = (TextView) findViewById(R.id.detailTitle);
            TextView detailOverView = (TextView) findViewById(R.id.detailOverView);
            TextView detailAddr1 = (TextView) findViewById(R.id.detailAddr1);
            TextView detailAddr2 = (TextView) findViewById(R.id.detailAddr2);
            TextView detailHomepage = (TextView) findViewById(R.id.detailHomepage);

            if(null != firstImage && !"".equalsIgnoreCase(firstImage)) {
                if(detailImg1.getVisibility() == View.GONE) {
                    detailImg1.setVisibility(View.VISIBLE);
                }
                Picasso.get().load(firstImage).placeholder(R.mipmap.logo_final).into(detailImg1);
            } else {
                detailImg1.setVisibility(View.GONE);
                Picasso.get().cancelRequest(detailImg1);
                //detailImg1.setImageResource(R.mipmap.noimage);
            }

            if(null != firstImage2 && !"".equalsIgnoreCase(firstImage2)) {
                if(detailImg2.getVisibility() == View.GONE) {
                    detailImg2.setVisibility(View.VISIBLE);
                }
                Picasso.get().load(firstImage2).placeholder(R.mipmap.logo_final).into(detailImg2);
            } else {
                detailImg2.setVisibility(View.GONE);
                Picasso.get().cancelRequest(detailImg2);
                //detailImg1.setImageResource(R.mipmap.noimage);
            }

            commonUtil = new CommonUtil();

            SpannableStringBuilder overViewBuilder = new SpannableStringBuilder();
            overViewBuilder = commonUtil.convertTxtToLink(getApplicationContext(), overView);
            if(null != overViewBuilder) { detailOverView.setText(overViewBuilder.toString()); }

            SpannableStringBuilder addr1Builder = new SpannableStringBuilder();
            addr1Builder = commonUtil.convertTxtToLink(getApplicationContext(), addr1);
            if(null != addr1Builder) { detailAddr1.setText(addr1Builder.toString()); }

            SpannableStringBuilder addr2Builder = new SpannableStringBuilder();
            addr2Builder = commonUtil.convertTxtToLink(getApplicationContext(), addr1);
            if(null != addr2Builder) { detailAddr2.setText(addr2Builder.toString()); }

            SpannableStringBuilder homePageBuilder = new SpannableStringBuilder();
            homePageBuilder = commonUtil.convertTxtToLink(getApplicationContext(), homepage);
            if(null != homePageBuilder) { detailHomepage.setText(homePageBuilder.toString()); }

            /**
            if(null != title && !"".equalsIgnoreCase(title)) { detailTitle.setText(title); }
            if(null != overView && !"".equalsIgnoreCase(overView)) { detailOverView.setText(Html.fromHtml(overView)); }
            if(null != addr1 && !"".equalsIgnoreCase(addr1)) { detailAddr1.setText(Html.fromHtml(addr1)); }
            if(null != addr2 && !"".equalsIgnoreCase(addr2)) { detailAddr2.setText(Html.fromHtml(addr2)); }
            //if(null != homepage && !"".equalsIgnoreCase(homepage)) { detailHomepage.setText(Html.fromHtml(homepage)); }
             **/
        }
    }
}
