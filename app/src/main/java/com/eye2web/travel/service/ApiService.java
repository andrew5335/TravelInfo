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
import java.util.List;

/**
 * @File : ApiService
 * @Date : 2018. 5. 2. PM 4:50
 * @Author : Andrew Kim
 * @Version : 1.0.0
 * @Description : 외부 API 연동 처리
**/
public class ApiService extends Application {

    static String testAddr = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?serviceKey=";
    static String testServiceKey = "ZjtS%2F7q9SORXFBybZ%2FhYciDyQKRNeP3r0tc8r%2BQLOv97shkq%2FNDa6a7Fp4m9T2lhT5fSjOiB6XR4aD33p7ljvA%3D%3D";
    static String testParameter = "";

    public List<ListItem> getContent(String addr, String serviceKey, String code, String keyword, String sort, int page, int offset) throws Exception {
        List<ListItem> resultList = new ArrayList<ListItem>();
        boolean inAddr = false;
        boolean inFirstImage = false;
        boolean inTitle = false;
        boolean inItem = false;
        String inAddrStr = "";
        String inFirstImageStr = "";
        String inTitleStr = "";

        String parameter = "";

        parameter = parameter + "&MobileOS=AND";
        parameter = parameter + "&MobileApp=TravelInfo";
        parameter = parameter + "&numOfRows=" + offset;
        parameter = parameter + "&pageNo=" + page;
        parameter = parameter + "&listYN=Y";
        parameter = parameter + "&arrange=" + sort;
        parameter = parameter + "&contentTypeId=" + code;
        parameter = parameter + "&keyword=" + keyword;

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
                    //Log.i("firstimage", "===================firstimage : " + inFirstImage);
                    else if(parser.getName().equalsIgnoreCase("title")) {
                        inTitle = true;
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
                    else if(inTitle) {
                        inTitleStr = parser.getText();
                        inTitle = false;
                    }
                    break;

                case XmlPullParser.END_TAG :
                    if(parser.getName().equalsIgnoreCase("item")) {
                        Log.i("firstimage", "===============firstimage : " + inFirstImageStr);
                        ListItem item = new ListItem(inAddrStr, inFirstImageStr, inTitleStr);
                        resultList.add(item);
                        inAddrStr = "";
                        inFirstImageStr = "";
                        inTitleStr = "";
                    }
                    break;
            }
            parserEvent = parser.next();
        }

        return resultList;
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
