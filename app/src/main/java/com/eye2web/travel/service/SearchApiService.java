package com.eye2web.travel.service;

import android.app.Application;
import android.util.Log;

import com.eye2web.travel.vo.ListItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @File : ApiService
 * @Date : 2018. 5. 2. PM 4:50
 * @Author : Andrew Kim
 * @Version : 1.0.0
 * @Description : 외부 API 연동 처리
**/
public class SearchApiService extends Application {

    static String testAddr = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?serviceKey=";
    static String testServiceKey = "ZjtS%2F7q9SORXFBybZ%2FhYciDyQKRNeP3r0tc8r%2BQLOv97shkq%2FNDa6a7Fp4m9T2lhT5fSjOiB6XR4aD33p7ljvA%3D%3D";
    static String testParameter = "";

    public Map<String, Object> getContent(String addr, String serviceKey, String code, String keyword
            , String sort, int page, int offset, String gu, String areaCode, double mapX, double mapY) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<ListItem> resultList = new ArrayList<ListItem>();
        boolean inAddr = false;
        boolean inFirstImage = false;
        boolean inFirstImage2 = false;
        boolean inTitle = false;
        boolean inContentId = false;
        boolean inContentTypeId = false;
        boolean inCat1 = false;
        boolean inCat2 = false;
        boolean inCat3 = false;
        boolean inMapx = false;
        boolean inMapy = false;
        boolean inItem = false;
        boolean inResultCode = false;

        String inAddrStr = "";
        String inFirstImageStr = "";
        String inFirstImage2Str = "";
        String inTitleStr = "";
        String inContentIdStr = "";
        String inContentTypeIdStr = "";
        String inCat1Str = "";
        String inCat2Str = "";
        String inCat3Str = "";
        String inReultCodeStr = "";
        float inMapxNum = 0;
        float inMapyNum = 0;

        String parameter = "";
        Log.i("info", "===============gps info : " + mapX + "=============" + mapY
                + "===================gu : " + gu + "=================url : " + addr);
        // 구분값 (검색인지, 지역기반 조회인지 등) 이 있을 경우에는 해당 구분값에 맞도록 parameter 구성
        // 구분값이 없으면 지역 기반의 서울을 기준으로 parameter 구성
        if(null != gu && !"".equalsIgnoreCase(gu)) {
            if("search".equalsIgnoreCase(gu)) {
                parameter = parameter + "&MobileOS=AND";
                parameter = parameter + "&MobileApp=TravelInfo";
                parameter = parameter + "&numOfRows=" + offset;
                parameter = parameter + "&pageNo=" + page;
                parameter = parameter + "&listYN=Y";
                parameter = parameter + "&arrange=" + sort;
                parameter = parameter + "&contentTypeId=" + code;
                parameter = parameter + "&keyword=" + keyword;
            } else if("area".equalsIgnoreCase(gu)) {
                parameter = parameter + "&MobileOS=AND";
                parameter = parameter + "&MobileApp=TravelInfo";
                parameter = parameter + "&numOfRows=" + offset;
                parameter = parameter + "&pageNo=" + page;
                parameter = parameter + "&listYN=Y";
                parameter = parameter + "&arrange=" + sort;
                parameter = parameter + "&contentTypeId=" + code;
                parameter = parameter + "&areaCode=" + areaCode;
            } else if("loc".equalsIgnoreCase(gu)) {
                parameter = parameter + "&MobileOS=AND";
                parameter = parameter + "&MobileApp=TravelInfo";
                parameter = parameter + "&numOfRows=" + offset;
                parameter = parameter + "&pageNo=" + page;
                parameter = parameter + "&listYN=Y";
                parameter = parameter + "&arrange=" + sort;
                parameter = parameter + "&contentTypeId=" + code;
                parameter = parameter + "&radius=5000";
                parameter = parameter + "&mapX=" + mapX;
                parameter = parameter + "&mapY=" + mapY;
                //Toast.makeText(getApplicationContext(), "parameter : " + parameter, Toast.LENGTH_LONG).show();
                Log.i("info", "=============parameter : " + parameter);
            }
        } else {
            parameter = parameter + "&MobileOS=AND";
            parameter = parameter + "&MobileApp=TravelInfo";
            parameter = parameter + "&numOfRows=" + offset;
            parameter = parameter + "&pageNo=" + page;
            parameter = parameter + "&listYN=Y";
            parameter = parameter + "&arrange=" + sort;
            parameter = parameter + "&contentTypeId=" + code;
            parameter = parameter + "&areaCode=" + areaCode;
        }

        addr = addr + serviceKey + parameter;
        System.out.println(addr);

        URL url = new URL(addr);

        XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = parserFactory.newPullParser();
        parser.setInput(url.openStream(), null);

        int parserEvent = parser.getEventType();

        while(parserEvent != XmlPullParser.END_DOCUMENT) {
            switch(parserEvent) {
                case XmlPullParser.START_TAG :
                    if(parser.getName().equalsIgnoreCase("addr1")) {
                        inAddr = true;
                    }
                    else if(parser.getName().equalsIgnoreCase("firstimage")) {
                        inFirstImage = true;
                    }
                    else if(parser.getName().equalsIgnoreCase("firstimage2")) {
                        inFirstImage2 = true;
                    }
                    //Log.i("firstimage", "===================firstimage : " + inFirstImage);
                    else if(parser.getName().equalsIgnoreCase("title")) {
                        inTitle = true;
                    }
                    else if(parser.getName().equalsIgnoreCase("contentid")) {
                        inContentId = true;
                    }
                    else if(parser.getName().equalsIgnoreCase("contenttypeid")) {
                        inContentTypeId = true;
                    }
                    else if(parser.getName().equalsIgnoreCase("cat1")) {
                        inCat1 = true;
                    }
                    else if(parser.getName().equalsIgnoreCase("cat2")) {
                        inCat2 = true;
                    }
                    else if(parser.getName().equalsIgnoreCase("cat3")) {
                        inCat3 = true;
                    }
                    else if(parser.getName().equalsIgnoreCase("mapx")) {
                        inMapx = true;
                    }
                    else if(parser.getName().equalsIgnoreCase("mapy")) {
                        inMapy = true;
                    }
                    else if(parser.getName().equalsIgnoreCase("resultCode")) {
                        inResultCode = true;
                    }
                    break;

                case XmlPullParser.TEXT :
                    if(inAddr) {
                        inAddrStr = parser.getText();
                        inAddr = false;
                    }
                    else if(inFirstImage) {
                        inFirstImageStr = parser.getText();
                        inFirstImage = false;
                    }
                    else if(inFirstImage2) {
                        inFirstImage2Str = parser.getText();
                        inFirstImage2 = false;
                    }
                    else if(inTitle) {
                        inTitleStr = parser.getText();
                        inTitle = false;
                    }
                    else if(inContentId) {
                        inContentIdStr = parser.getText();
                        inContentId = false;
                    }
                    else if(inContentTypeId) {
                        inContentTypeIdStr = parser.getText();
                        inContentTypeId = false;
                    }
                    else if(inCat1) {
                        inCat1Str = parser.getText();
                        inCat1 = false;
                    }
                    else if(inCat2) {
                        inCat2Str = parser.getText();
                        inCat2 = false;
                    }
                    else if(inCat3) {
                        inCat3Str = parser.getText();
                        inCat3 = false;
                    }
                    else if(inMapx) {
                        inMapxNum = Float.parseFloat(parser.getText());
                        inMapx = false;
                    }
                    else if(inMapy) {
                        inMapyNum = Float.parseFloat(parser.getText());
                        inMapy = false;
                    }
                    else if(inResultCode) {
                        inReultCodeStr = parser.getText();
                        inResultCode = false;
                    }
                    break;

                case XmlPullParser.END_TAG :
                    if(parser.getName().equalsIgnoreCase("item")) {
                        Log.i("firstimage", "===============firstimage : " + inFirstImageStr);
                        ListItem item = new ListItem(inAddrStr, inFirstImageStr, inFirstImage2Str, inTitleStr, inContentIdStr
                                , inContentTypeIdStr, inCat1Str, inCat2Str, inCat3Str, inMapxNum, inMapyNum, "");

                        resultList.add(item);

                        inAddrStr = "";
                        inFirstImageStr = "";
                        inFirstImage2Str = "";
                        inTitleStr = "";
                        inContentIdStr = "";
                        inContentTypeIdStr = "";
                        inCat1Str = "";
                        inCat2Str = "";
                        inCat3Str = "";
                        inMapxNum = 0;
                        inMapyNum = 0;
                    }
                    break;
            }
            parserEvent = parser.next();
        }

        resultMap.put("resultList", resultList);
        resultMap.put("resultCode", inReultCodeStr);
        inReultCodeStr = "";

        return resultMap;
    }

    public static void main(String[] args) throws Exception {
        List<ListItem> resultList = new ArrayList<ListItem>();

        //testParameter = testParameter + "&type=xml";
        testParameter = testParameter + "&MobileOS=AND";
        testParameter = testParameter + "&MobileApp=TravelInfo";
        testParameter = testParameter + "&contentId=2392105";
        testParameter = testParameter + "&numOfRows=4";
        testParameter = testParameter + "&defaultYN=Y";
        testParameter = testParameter + "&firstImageYN=Y";
        testParameter = testParameter + "&pageNo=1";
        testParameter = testParameter + "&areacodeYN=Y";
        testParameter = testParameter + "&catcodeYN=Y";
        testParameter = testParameter + "&addrinfoYN=Y";
        testParameter = testParameter + "&mapinfoYN=Y";
        testParameter = testParameter + "&overviewYN=Y";

        testAddr = testAddr + testServiceKey + testParameter;
        System.out.println(testAddr);

        URL url = new URL(testAddr);
        InputStream in = url.openStream();
        BufferedInputStream bis = new BufferedInputStream(in);

        byte[] b = new byte[bis.available()];
        System.out.println(b.length);

        while(bis.read(b) != -1) {

        }

        System.out.println("result : " + new String(b));
    }
}
