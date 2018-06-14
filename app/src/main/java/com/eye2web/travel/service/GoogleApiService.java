package com.eye2web.travel.service;

import android.util.Log;

import com.eye2web.travel.util.CommonUtil;
import com.eye2web.travel.util.JsonParsingUtil;
import com.eye2web.travel.vo.GooglePlaceDetailItem;
import com.eye2web.travel.vo.GooglePlaceDetailPhoto;
import com.eye2web.travel.vo.GooglePlaceDetailReviews;
import com.eye2web.travel.vo.GooglePlaceItem;
import com.eye2web.travel.vo.GooglePlaceVO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
            , String nextPageToken
            , double lat
            , double lng
            , String searchKeyword
            , String wikiSearchKeyword
            , String gu
            , String cateGu) throws Exception, IOException {

        GooglePlaceVO placeInfo = new GooglePlaceVO();
        commonUtil = new CommonUtil();

        BufferedReader searchResultStr = null;
        //BufferedReader photoResultStr = null;
        BufferedReader summaryResultStr = null;

        HttpURLConnection urlConnection = null;

        URL searchUrl = null;
        URL photoUrl = null;
        URL summaryUrl = null;

        String googlePhotoUrl = "";

        InputStreamReader sris = null;
        InputStreamReader pris = null;
        InputStreamReader mris = null;

        String commonParam = "&language=ko";

        try {
            if (null != searchApiUrl && !"".equalsIgnoreCase(searchApiUrl)) {
                jsonParsingUtil = new JsonParsingUtil();
                StringBuilder searchStrBuilder = new StringBuilder();

                String gSearchParam = "";
                if (null != gu && !"".equalsIgnoreCase(gu)) {
                    if ("1".equalsIgnoreCase(gu)) {
                        gSearchParam = gSearchParam + "?query=" + wikiSearchKeyword + commonParam;
                    } else {
                        gSearchParam = gSearchParam + "?query=" + searchKeyword + "+" + cateGu + commonParam;
                    }
                    gSearchParam = gSearchParam + "&key=" + googleKey;

                    if(null != nextPageToken && !"".equalsIgnoreCase(nextPageToken)) {
                        gSearchParam = gSearchParam + "&pagetoken=" + nextPageToken;
                    }
                }

                Log.i("Info", "Search API URL : " + gSearchParam);
                searchUrl = new URL(searchApiUrl + gSearchParam);

                urlConnection = (HttpURLConnection) searchUrl.openConnection();

                sris = new InputStreamReader(urlConnection.getInputStream());

                searchResultStr = new BufferedReader(sris);
                String searchResultLine;

                while ((searchResultLine = searchResultStr.readLine()) != null) {
                    searchStrBuilder.append(searchResultLine);
                }

                if (null != searchStrBuilder.toString() && !"".equalsIgnoreCase(searchStrBuilder.toString())) {
                    placeInfo = jsonParsingUtil.getGooglePlaceVO(searchStrBuilder.toString());

                    if (null != placeInfo) {
                        List<GooglePlaceItem> placeItems = new ArrayList<GooglePlaceItem>();
                        placeItems = placeInfo.getPlaceItem();

                        if (null != placeItems && 0 < placeItems.size()) {
                            if (null != photoApiUrl && !"".equalsIgnoreCase(photoApiUrl)) {

                                for (int i = 0; i < placeItems.size(); i++) {
                                    String photoReference = "";
                                    photoReference = placeItems.get(i).getPhotoReference();

                                    String gPhotoParam = "";
                                    googlePhotoUrl = "";
                                    //placeItems.get(i).setGooglePhotoUrl(null);
                                    if (null != photoReference && !"".equalsIgnoreCase(photoReference)) {

                                        gPhotoParam = gPhotoParam + "?key=" + googleKey;
                                        gPhotoParam = gPhotoParam + "&maxheight=240";
                                        gPhotoParam = gPhotoParam + "&photoreference=" + photoReference  + commonParam;

                                        googlePhotoUrl = photoApiUrl + gPhotoParam;
                                        placeItems.get(i).setGooglePhotoUrl(googlePhotoUrl);
                                        Log.i("Info", "Photo Info : " + i + "-" + placeItems.get(i).getGooglePhotoUrl());
                                    } else {
                                        //placeItems.get(i).setPhotoUrl(null);
                                        placeItems.get(i).setGooglePhotoUrl(null);
                                        googlePhotoUrl = "";
                                        gPhotoParam = "";
                                    }
                                }
                            }

                            if("1".equalsIgnoreCase(gu)) {
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

                                                urlConnection = (HttpURLConnection) summaryUrl.openConnection();
                                                mris = new InputStreamReader(urlConnection.getInputStream());

                                                summaryResultStr = new BufferedReader(mris);
                                                String summaryResultLine;

                                                while ((summaryResultLine = summaryResultStr.readLine()) != null) {
                                                    summaryBuilder.append(summaryResultLine);
                                                }

                                                if (null != summaryBuilder.toString() && !"".equalsIgnoreCase(summaryBuilder.toString())) {
                                                    String summary = "";
                                                    summary = jsonParsingUtil.getSummary(summaryBuilder.toString());
                                                    placeItems.get(j).setSummary(summary);

                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            placeInfo.setPlaceItem(placeItems);
                        }
                    }
                }
            }

        } catch(Exception e) {
            Log.e("Error", "Error : " + e.toString());
        } finally {
            if(null != summaryResultStr) { summaryResultStr.close(); }
            if(null != searchResultStr) { searchResultStr.close(); }
        }

        return placeInfo;
    }

    public GooglePlaceDetailItem getDetailInfo() {
        GooglePlaceDetailItem detailItem = new GooglePlaceDetailItem();

        return detailItem;
    }

    public GooglePlaceDetailPhoto getDetailPhoto() {
        GooglePlaceDetailPhoto detailPhoto = new GooglePlaceDetailPhoto();

        return detailPhoto;
    }

    public GooglePlaceDetailReviews getDetailReview() {
        GooglePlaceDetailReviews detailReviews = new GooglePlaceDetailReviews();

        return detailReviews;
    }
}
