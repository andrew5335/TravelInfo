package com.eye2web.travel.service;

import android.app.Application;

import com.eye2web.travel.vo.AreaListItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.ArrayList;

/**
 * @File : AreaApiService
 * @Date : 2018. 5. 8. AM 11:01
 * @Author : Andrew Kim
 * @Version : 1.0.0
 * @Description : 지역코드조회
**/
public class AreaApiService extends Application {

    /**
     * @parameter : serviceGu - 서비스구분값
     *              addr - api 호출주소
     *              serviceKey - api 호출 인증키
     *              contentId - 컨텐츠 아이디값
     *              code - 지역코드값 (옵션)
     * @Date : 2018. 5. 8. AM 11:10
     * @Author : Andrew Kim
     * @Description :
    **/
    public ArrayList<AreaListItem> getAreaCodeList(String addr, String serviceGu, String serviceKey, String contentId, String code) throws Exception {
        ArrayList<AreaListItem> resultList = new ArrayList<AreaListItem>();
        boolean inCode = false;
        boolean inName = false;
        boolean inRNum = false;

        String inCodeStr = "";
        String inNameStr = "";
        int inRNumCnt = 0;

        String parameter = "";

        parameter = parameter + "&numOfRows=10";
        parameter = parameter + "&pageNo=1";
        parameter = parameter + "&MobileOS=AND";
        parameter = parameter + "&MobileApp=TravelInfo";
        parameter = parameter + "&areaCode=";

        addr = addr + serviceGu + "?serviceKey=" +  serviceKey + parameter;
        System.out.println(addr);
        URL url = new URL(addr);

        XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = parserFactory.newPullParser();
        parser.setInput(url.openStream(), null);

        int parserEvent = parser.getEventType();

        while(parserEvent != XmlPullParser.END_DOCUMENT) {
            switch(parserEvent) {
                case XmlPullParser.START_TAG :
                    if(parser.getName().equalsIgnoreCase("code")) {
                        inCode = true;
                    }
                    if(parser.getName().equalsIgnoreCase("name")) {
                        inName = true;
                    }
                    if(parser.getName().equalsIgnoreCase("rnum")) {
                        inRNum = true;
                    }
                    break;

                case XmlPullParser.TEXT :
                    if(inCode) {
                        inCodeStr = parser.getText();
                        inCode = false;
                    }
                    if(inName) {
                        inNameStr = parser.getText();
                        inName = false;
                    }
                    if(inRNum) {
                        inRNumCnt = Integer.parseInt(parser.getText());
                        inRNum = false;
                    }
                    break;

                case XmlPullParser.END_TAG :
                    if(parser.getName().equalsIgnoreCase("item")) {
                        AreaListItem areaListItem = new AreaListItem(inCodeStr, inNameStr, inRNumCnt);
                        resultList.add(areaListItem);
                    }
                    break;
            }
            parserEvent = parser.next();
        }

        return resultList;
    }

}
