package com.eye2web.travel.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.eye2web.travel.util.CommonUtil;
import com.eye2web.travel.util.JsonParsingUtil;
import com.eye2web.travel.vo.GooglePlaceItem;
import com.eye2web.travel.vo.GooglePlaceVO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @File : GoogleApiService
 * @Date : 2018. 6. 7. PM 1:09
 * @Author : Andrew Kim
 * @Version : 1.0.0
 * @Description : Google API 연동 서비스
**/
public class GoogleApiService {

    private JsonParsingUtil jsonParsingUtil;

    private CommonUtil commonUtil;

    /**
     * @parameter : searchApiUrl - 검색 api url (google)
     *              photoApiUtl - 사진 api url (google)
     *              summaryUrl - 요약정보 api url (wikipedia)
     *              lat - 위도
     *              lng - 경도
     *              searchKeyword - 검색 키워드
     * @Date : 2018. 6. 7. PM 1:09
     * @Author : Andrew Kim
     * @Description :
    **/
    public GooglePlaceVO getPlaceInfo(
            String searchApiUrl
            , String photoApiUrl
            , String summaryApiUrl
            , String wikiDetailApiUrl
            , String googleKey
            , double lat
            , double lng
            , String searchKeyword
            , String wikiSearchKeyword
            , String gu) throws Exception, IOException {

        GooglePlaceVO placeInfo = new GooglePlaceVO();

        BufferedReader searchResultStr = null;
        //BufferedReader photoResultStr = null;
        BufferedReader summaryResultStr = null;

        URL searchUrl = null;
        URL photoUrl = null;
        URL summaryUrl = null;

        InputStreamReader sris = null;
        InputStreamReader pris = null;
        InputStreamReader mris = null;

        try {
            if (null != searchApiUrl && !"".equalsIgnoreCase(searchApiUrl)) {
                jsonParsingUtil = new JsonParsingUtil();
                StringBuilder searchStrBuilder = new StringBuilder();

                String gSearchParam = "";
                if (null != gu && !"".equalsIgnoreCase(gu)) {
                    if ("1".equalsIgnoreCase(gu)) {
                        gSearchParam = gSearchParam + "?query=" + wikiSearchKeyword;
                    } else {
                        gSearchParam = gSearchParam + "?query=" + searchKeyword;
                    }
                    gSearchParam = gSearchParam + "&key=" + googleKey;
                    gSearchParam = gSearchParam + "&location=" + lat + "," + lng;
                }

                searchApiUrl = searchApiUrl + gSearchParam;
                searchUrl = new URL(searchApiUrl);

                sris = new InputStreamReader(searchUrl.openStream());

                searchResultStr = new BufferedReader(sris);
                String searchResultLine;

                while ((searchResultLine = searchResultStr.readLine()) != null) {
                    searchStrBuilder.append(searchResultLine);
                }

                if (null != searchStrBuilder.toString() && !"".equalsIgnoreCase(searchStrBuilder.toString())) {
                    //String searchRsltStr = "";
                    //searchRsltStr = searchStrBuilder.toString();
                    placeInfo = jsonParsingUtil.getGooglePlaceVO(searchStrBuilder.toString());

                    if (null != placeInfo) {
                        List<GooglePlaceItem> placeItems = new ArrayList<GooglePlaceItem>();
                        placeItems = placeInfo.getPlaceItem();

                        if (null != placeItems && 0 < placeItems.size()) {
                            if (null != photoApiUrl && !"".equalsIgnoreCase(photoApiUrl)) {
                                StringBuilder photoBuilder = new StringBuilder();

                                String gPhotoParam = "";
                                gPhotoParam = gPhotoParam + "?key=" + googleKey;
                                gPhotoParam = gPhotoParam + "&maxheight=240";

                                for (int i = 0; i < placeItems.size(); i++) {
                                    String photoReference = placeItems.get(i).getPhotoReference();
                                    if (null != photoReference && !"".equalsIgnoreCase(photoReference)) {
                                        gPhotoParam = gPhotoParam + "&photoreference=" + photoReference;

                                        photoApiUrl = photoApiUrl + gPhotoParam;
                                        photoUrl = new URL(photoApiUrl);

                                        Bitmap googlePhoto = BitmapFactory.decodeStream(photoUrl.openConnection().getInputStream());

                                        /**
                                         photoResultStr = new BufferedReader(new InputStreamReader(photoUrl.openStream()));
                                         String photoResultLine;

                                         while((photoResultLine = photoResultStr.readLine()) != null) {
                                         photoBuilder.append(photoResultLine);
                                         }
                                         **/

                                        if (null != googlePhoto) {
                                            placeItems.get(i).setPhotoUrl(googlePhoto);
                                        }
                                    }
                                }

                            }

                            if (null != summaryApiUrl && !"".equalsIgnoreCase(summaryApiUrl)) {
                                StringBuilder summaryBuilder = new StringBuilder();


                                if (null != placeItems && 0 < placeItems.size()) {
                                    for (int j = 0; j < placeItems.size(); j++) {
                                        if (null != gu && !"".equalsIgnoreCase(gu)) {

                                            // 기본은 각 카테고리 리스트를 위한 summary 조회를 위해 구글 place api를 통해 조회된 지역명으로 조회
                                            summaryUrl = new URL(summaryApiUrl + placeItems.get(j).getName());

                                            // 구분값이 1인 경우 도시 메인 페이지용 summary 조회가 필요하므로 위키피디어 조회용 키워드로 대체
                                            if ("1".equalsIgnoreCase(gu)) {
                                                summaryUrl = new URL(summaryApiUrl + wikiSearchKeyword);
                                            }

                                            mris = new InputStreamReader(summaryUrl.openStream());

                                            summaryResultStr = new BufferedReader(mris);
                                            String summaryResultLine;

                                            while ((summaryResultLine = summaryResultStr.readLine()) != null) {
                                                summaryBuilder.append(summaryResultLine);
                                            }

                                            if (null != summaryBuilder.toString() && !"".equalsIgnoreCase(summaryBuilder.toString())) {
                                                String summary = "";
                                                //if("1".equalsIgnoreCase(gu)) {
                                                //    summary = jsonParsingUtil.getCityMainSummary(summaryBuilder.toString());
                                                //} else {
                                                summary = jsonParsingUtil.getSummary(summaryBuilder.toString());
                                                //}
                                                placeItems.get(j).setSummary(summary);

                                            }
                                        }
                                    }
                                }

                            }
                        }
                    }
                }

                return placeInfo;
            }

        } catch(Exception e) {
            Log.e("Error", "Error : " + e.toString());
        } finally {
            if(null != searchUrl.openStream()) { searchUrl.openStream().close(); }
            if(null != photoUrl.openStream()) { photoUrl.openStream().close(); }
            if(null != summaryUrl.openStream()) { summaryUrl.openStream().close(); }
            if(null != summaryResultStr) { summaryResultStr.close(); }
            if(null != searchResultStr) { searchResultStr.close(); }
        }

        return null;
    }
}
