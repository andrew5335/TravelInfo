package com.eye2web.travel.service;

import android.app.Application;
import android.util.Log;

import com.eye2web.travel.vo.DetailCommonItem;
import com.eye2web.travel.vo.DetailIntroItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @File : DetailApiService
 * @Date : 2018. 5. 15. AM 9:22
 * @Author : Andrew Kim
 * @Version : 1.0.0
 * @Description : 상세정보 API
**/
public class DetailApiService extends Application {

    /**
     * @parameter :
     * @Date : 2018. 5. 15. AM 9:23
     * @Author : Andrew Kim
     * @Description : 상세정보 확인 처리
    **/
    public Map<String, Object> getDetailInfo(String apiUrl, String serviceKey, String contentId, String contentTypeId
            , String areaCode, double mapx, double mapy) {
        DetailIntroItem detailIntroItem = new DetailIntroItem();
        DetailCommonItem detailCommonItem = new DetailCommonItem();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        boolean inOverview = false;
        boolean inHomepage = false;
        boolean inTitle = false;
        boolean inAddr1 = false;
        boolean inAddr2 = false;
        boolean inFirstImage = false;
        boolean inFirstImage2 = false;
        boolean inItem = false;
        boolean inOriginalImg = false;
        boolean inTel = false;

        String inOverviewStr = "";
        String inHomepageStr = "";
        String inTitleStr = "";
        String inAddr1Str = "";
        String inAddr2Str = "";
        String inFirstImageStr = "";
        String inFirstImage2Str = "";
        String inOriginalImgStr = "";
        String inTelStr = "";
        List<String> imgUrlList = new ArrayList<String>();

        //String introAddr = apiUrl + "detailIntro?serviceKey=" + serviceKey;
        String commonAddr = apiUrl + "detailCommon?serviceKey=" + serviceKey;
        String imageAddr = apiUrl + "detailImage?serviceKey=" + serviceKey;

        String introParameter = "";
        String detailCommonParameter = "";
        String imageParameter = "";

        detailCommonParameter = detailCommonParameter + "&numOfRows=1";
        detailCommonParameter = detailCommonParameter + "&pageNo=1";
        detailCommonParameter = detailCommonParameter + "&MobileOS=AND";
        detailCommonParameter = detailCommonParameter + "&MobileApp=TravelInfo";
        detailCommonParameter = detailCommonParameter + "&contentId=" + contentId;
        detailCommonParameter = detailCommonParameter + "&contentTypeId=" + contentTypeId;
        detailCommonParameter = detailCommonParameter + "&defaultYN=Y";
        detailCommonParameter = detailCommonParameter + "&firstImageYN=Y";
        detailCommonParameter = detailCommonParameter + "&areacodeYN=Y";
        detailCommonParameter = detailCommonParameter + "&catcodeYN=Y";
        detailCommonParameter = detailCommonParameter + "&addrinfoYN=Y";
        detailCommonParameter = detailCommonParameter + "&mapinfoYN=Y";
        detailCommonParameter = detailCommonParameter + "&overviewYN=Y";

        imageParameter = imageParameter + "&numOfRows=10";
        imageParameter = introParameter + "&pageNo=1";
        imageParameter = imageParameter + "&MobileOS=AND";
        imageParameter = imageParameter + "&MobileApp=TravelInfo";
        imageParameter = imageParameter + "&contentId=" + contentId;
        imageParameter = imageParameter + "&imageYN=Y";
        imageParameter = imageParameter + "&subImageYN=Y";

        //introAddr = introAddr + introParameter;
        commonAddr = commonAddr + detailCommonParameter;
        imageAddr = imageAddr + imageParameter;

        try {
            //URL introUrl = new URL(introAddr);
            URL commonUrl = new URL(commonAddr);
            URL imageUrl = new URL(imageAddr);
            Log.i("info", "image addr : " + imageUrl);

            XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
            //XmlPullParser introParser = parserFactory.newPullParser();
            XmlPullParser commonParser = parserFactory.newPullParser();
            XmlPullParser imageParser = parserFactory.newPullParser();

            //introParser.setInput(introUrl.openStream(), null);
            commonParser.setInput(commonUrl.openStream(), null);
            imageParser.setInput(imageUrl.openStream(), null);

            //int introParserEvent = introParser.getEventType();
            int commonParserEvent = commonParser.getEventType();
            int imageParserEvent = imageParser.getEventType();

            while(commonParserEvent != XmlPullParser.END_DOCUMENT) {
                switch(commonParserEvent) {
                    case XmlPullParser.START_TAG :
                        if(commonParser.getName().equalsIgnoreCase("overview")) {
                            inOverview = true;
                        }
                        else if(commonParser.getName().equalsIgnoreCase("homepage")) {
                            inHomepage = true;
                        }
                        else if(commonParser.getName().equalsIgnoreCase("title")) {
                            inTitle = true;
                        }
                        else if(commonParser.getName().equalsIgnoreCase("addr1")) {
                            inAddr1 = true;
                        }
                        else if(commonParser.getName().equalsIgnoreCase("addr2")) {
                            inAddr2 = true;
                        }
                        else if(commonParser.getName().equalsIgnoreCase("firstimage")) {
                            inFirstImage = true;
                        }
                        else if(commonParser.getName().equalsIgnoreCase("firstimage2")) {
                            inFirstImage2 = true;
                        }
                        else if(commonParser.getName().equalsIgnoreCase("tel")) {
                            inTel = true;
                        }
                        break;

                    case XmlPullParser.TEXT :
                        if(inOverview) {
                            inOverviewStr = commonParser.getText();
                            inOverview = false;
                        }
                        else if(inHomepage) {
                            inHomepageStr = commonParser.getText();
                            inHomepage = false;
                        }
                        else if(inTitle) {
                            inTitleStr = commonParser.getText();
                            inTitle = false;
                        }
                        else if(inAddr1) {
                            inAddr1Str = commonParser.getText();
                            inAddr1 = false;
                        }
                        else if(inAddr2) {
                            inAddr2Str = commonParser.getText();
                            inAddr2 = false;
                        }
                        else if(inFirstImage) {
                            inFirstImageStr = commonParser.getText();
                            inFirstImage = false;
                        }
                        else if(inFirstImage2) {
                            inFirstImage2Str = commonParser.getText();
                            inFirstImage2 = false;
                        }
                        else if(inTel) {
                            inTelStr = commonParser.getText();
                            inTel = false;
                        }
                        break;

                    case XmlPullParser.END_TAG :
                        if(commonParser.getName().equalsIgnoreCase("item")) {
                            Log.i("firstimage", "===============firstimage : " + inFirstImageStr);
                            detailCommonItem.setOverview(inOverviewStr);
                            detailCommonItem.setHomepage(inHomepageStr);
                            detailCommonItem.setTitle(inTitleStr);
                            detailCommonItem.setAddr1(inAddr1Str);
                            detailCommonItem.setAddr2(inAddr2Str);
                            detailCommonItem.setFirstimage(inFirstImageStr);
                            detailCommonItem.setFirstimage2(inFirstImage2Str);
                            detailCommonItem.setTel(inTelStr);
                        }
                        break;
                }
                commonParserEvent = commonParser.next();
            }

            while(imageParserEvent != XmlPullParser.END_DOCUMENT) {
                switch (imageParserEvent) {
                    case XmlPullParser.START_TAG :
                        if(imageParser.getName().equalsIgnoreCase("originimgurl")) {
                            inOriginalImg = true;
                        }
                        break;

                    case XmlPullParser.TEXT :
                        if(inOriginalImg) {
                            inOriginalImgStr = imageParser.getText();
                            inOriginalImg = false;
                        }
                        break;

                    case XmlPullParser.END_TAG :
                        if(imageParser.getName().equalsIgnoreCase("item")) {
                            imgUrlList.add(inOriginalImgStr);
                        }
                        break;
                }
                imageParserEvent = imageParser.next();
            }

            detailCommonItem.setImgUrlList(imgUrlList);

            resultMap.put("detailCommon", detailCommonItem);

        } catch(Exception e) {
            Log.e("Error", "Error : " + e.toString());
        }

        return resultMap;
    }
}
