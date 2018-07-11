package com.eye2web.travel.service;

import android.util.Log;

import com.eye2web.travel.util.JsonParsingUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class WikiApiService {

    private JsonParsingUtil jsonParsingUtil;

    /**
     * @parameter : wikiUrl : Wikipedia Api 주소
     *              searchKeyWord : 검색어
     * @Date : 2018. 7. 3. PM 4:07
     * @Author : Andrew Kim
     * @Description : Wiki 요약 정보를 가져온다.
    **/
    public String getWikiDescription(String wikiSummaryUrl, String searchKeyWord) {

        jsonParsingUtil = new JsonParsingUtil();
        String result = "";
        HttpURLConnection urlConnection = null;
        URL wikiUrl = null;
        BufferedReader wikiBufferedReader = null;
        InputStreamReader wikiStreamReader = null;

        if(null != wikiSummaryUrl && !"".equalsIgnoreCase(wikiSummaryUrl) && 0 < wikiSummaryUrl.length()) {
            try {
                StringBuilder wikiDescriptionBuilder = new StringBuilder();
                wikiUrl = new URL(wikiSummaryUrl + URLEncoder.encode(searchKeyWord, "UTF-8"));

                urlConnection = (HttpURLConnection) wikiUrl.openConnection();
                wikiStreamReader = new InputStreamReader(urlConnection.getInputStream());

                wikiBufferedReader = new BufferedReader(wikiStreamReader);
                String wikiResultLine;

                while ((wikiResultLine = wikiBufferedReader.readLine()) != null) {
                    wikiDescriptionBuilder.append(wikiResultLine);
                }

                if(null != wikiDescriptionBuilder.toString() && !"".equalsIgnoreCase(wikiDescriptionBuilder.toString()) && 0 < wikiDescriptionBuilder.toString().length()) {
                    result = jsonParsingUtil.getSummary(wikiDescriptionBuilder.toString());
                }
            } catch (Exception e) {
                Log.e("Error", "Wiki Description Api Call Error : " + e.toString());
            }
        }

        return result;
    }
}
