package com.eye2web.travel.util;

import android.util.Log;

import com.eye2web.travel.vo.GooglePlaceItem;
import com.eye2web.travel.vo.GooglePlaceVO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @File : JsonParsingUtil
 * @Date : 2018. 6. 7. PM 2:11
 * @Author : Andrew Kim
 * @Version : 1.0.0
 * @Description : json string parsing util
**/
public class JsonParsingUtil {

    public GooglePlaceVO getGooglePlaceVO(String str) {
        GooglePlaceVO googlePlaceVO = new GooglePlaceVO();
        List<GooglePlaceItem> placeItem = new ArrayList<GooglePlaceItem>();

        if(null != str && !"".equalsIgnoreCase(str)) {
            try {
                JSONArray jsonArray;
                JSONObject jsonObject;

                String htmlAttributions = "";
                String nextPageToken = "";

                jsonObject = new JSONObject(str);

                if(jsonObject.has("html_attributions")) { htmlAttributions = jsonObject.getString("html_attributions"); }
                if(jsonObject.has("next_page_token")) { nextPageToken =  jsonObject.getString("next_page_token"); }

                googlePlaceVO.setHtmlAttributions(htmlAttributions);
                googlePlaceVO.setNextPageToken(nextPageToken);

                if(jsonObject.has("results")) {
                    jsonArray = jsonObject.getJSONArray("results");

                    if (null != jsonArray) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject result = jsonArray.getJSONObject(i);
                            JSONObject geometry;
                            JSONObject location;
                            JSONArray photos;

                            String lat = "";
                            String lng = "";
                            String photoReference = "";
                            String icon = "";
                            String id = "";
                            String name = "";
                            String openNow = "";
                            String placeId = "";
                            String scope = "";
                            int priceLevel = 0;
                            String rating = "";
                            String reference = "";
                            String formattedAddress = "";

                            if (result.has("geometry")) {
                                geometry = result.getJSONObject("geometry");

                                if (geometry.has("location")) {
                                    location = geometry.getJSONObject("location");
                                    lat = location.getString("lat");    // latitude 위도
                                    lng = location.getString("lng");    // longitude 경도
                                }
                            }

                            if (result.has("photos")) {
                                photos = result.getJSONArray("photos");

                                for(int j=0; j < photos.length(); j++) {
                                    JSONObject photoObj = photos.getJSONObject(j);

                                    if(photoObj.has("photo_reference")) {
                                        photoReference = photoObj.getString("photo_reference");
                                    }
                                }
                            }

                            if (result.has("icon")) {
                                icon = result.getString("icon");    // 지도에 결과를 표시할 경우 사용할 수 있는 권장 아이콘의 주소(url)
                            }

                            if (result.has("id")) {
                                id = result.getString("id");    // 장소를 표시하는 고유 식별자
                            }

                            if (result.has("name")) {
                                name = result.getString("name");    // 장소의 명칭
                            }

                            if (result.has("opennow")) {
                                openNow = result.getString("opennow");    // 현재 영업중 유무
                            }

                            if (result.has("place_id")) {
                                placeId = result.getString(("place_id"));    // 장소를 고유하게 식별하는 텍스트 식별자 - 장소에 대한 상세정보 요청 시 사용
                            }

                            if (result.has("scope")) {
                                scope = result.getString("scope");    // place_id의 범위 - APP : 장소 ID는 본인의 애플리케이션에서만 인식됨 / GOOGLE : 장소 ID를 다른 애플리케이션 및 구글 지도에서 사용가능
                            }

                            if (result.has("price_level")) {
                                priceLevel = result.getInt("price_level");    // 장소의 가격수준 - 0 : 무료 / 1 : 저렴 / 2 : 보통 / 3 : 비쌈 / 4 : 매우 비쌈
                            }

                            if (result.has("rating")) {
                                rating = result.getString("rating");    // 장소의 평점 (사용자 리뷰 기준으로 1.0 ~ 5.0)
                            }

                            if (result.has("formatted_address")) {
                                formattedAddress = result.getString("formatted_address");    // 장소의 주소 (우편주소)
                            }

                            GooglePlaceItem googlePlaceItem = new GooglePlaceItem();

                            if (null != lat && !"".equalsIgnoreCase(lat)) {
                                googlePlaceItem.setLat(Double.parseDouble(lat));
                            } else {
                                googlePlaceItem.setLat(0);
                            }

                            if (null != lng && !"".equalsIgnoreCase(lng)) {
                                googlePlaceItem.setLng(Double.parseDouble(lng));
                            } else {
                                googlePlaceItem.setLng(0);
                            }

                            googlePlaceItem.setIcon(icon);
                            googlePlaceItem.setId(id);
                            googlePlaceItem.setName(name);

                            if (null != openNow && !"".equalsIgnoreCase(openNow)) {
                                googlePlaceItem.setOpenNow(Boolean.valueOf(openNow));
                            } else {
                                googlePlaceItem.setOpenNow(false);
                            }

                            googlePlaceItem.setPhotoReference(photoReference);
                            googlePlaceItem.setPlaceId(placeId);
                            googlePlaceItem.setScope(scope);
                            googlePlaceItem.setPriceLevel(priceLevel);

                            if (null != rating && !"".equalsIgnoreCase(rating)) {
                                googlePlaceItem.setRating(Float.parseFloat(rating));
                            } else {
                                googlePlaceItem.setRating(0);
                            }

                            googlePlaceItem.setReference(reference);
                            googlePlaceItem.setFormattedAddress(formattedAddress);

                            placeItem.add(googlePlaceItem);
                        }

                        googlePlaceVO.setPlaceItem(placeItem);
                    }
                }
            } catch (Exception e) {
                Log.e("Error", "Error : " + e.toString());
            }
        } else {
            Log.i("Info", "Info : " + str);
        }

        return googlePlaceVO;
    }

    public String getSummary(String str) {
        String result = "";

        if(null != str && !"".equalsIgnoreCase(str)) {
            try {
                JSONObject jsonObject = new JSONObject(str);
                if(jsonObject.has("extract")) {
                    result = jsonObject.getString("extract");
                    result = removeTag(result);
                }
            } catch (Exception e) {
                Log.e("Error", "Error : " + e.toString());
            }
        }

        return result;
    }

    public String getCityMainSummary(String str) {
        String result = "";

        if(null != str && !"".equalsIgnoreCase(str)) {
            try {
                JSONObject jsonObject = new JSONObject(str);

                if(jsonObject.has("sections")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("sections");

                    if(null != jsonArray && 0 < jsonArray.length()) {
                        JSONObject resultObj = jsonArray.getJSONObject(0);
                        result = resultObj.getString("text");
                        //result = result.replace("\"//", "\"");
                        //result = removeTag(result);
                    }
                }
            } catch (Exception e) {
                Log.e("Error", "Error : " + e.toString());
            }
        }

        return result;
    }

    public String removeTag(String html) throws Exception {
        return html.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
    }
}
