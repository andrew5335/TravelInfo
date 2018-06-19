package com.eye2web.travel.util;

import android.graphics.Bitmap;
import android.util.Log;

import com.eye2web.travel.vo.GooglePlaceDetailItem;
import com.eye2web.travel.vo.GooglePlaceDetailPhoto;
import com.eye2web.travel.vo.GooglePlaceDetailReviews;
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

    public GooglePlaceDetailItem getGooglePlaceDetailInfo(String str) {
        GooglePlaceDetailItem result = new GooglePlaceDetailItem();

        if(null != str && !"".equalsIgnoreCase(str)) {
            try {
                JSONArray jsonArray;
                JSONObject jsonObject;

                jsonObject = new JSONObject(str);

                if(jsonObject.has("result")) {
                    JSONObject resultObj = jsonObject.getJSONObject("result");

                    String formattedAddress = "";
                    String formattedPhoneNumber = "";
                    double lat = 0;
                    double lng = 0;
                    String icon = "";
                    String id = "";
                    String internationalPhoneNumber = "";
                    String name = "";
                    boolean openNow = false;
                    String[] weekdayText = null;
                    List<GooglePlaceDetailPhoto> photoList = null;
                    String placeId = "";
                    float rating = 0;
                    List<GooglePlaceDetailReviews> reviewsList = null;
                    String scope = "";
                    String url = "";
                    List<Bitmap> bitmapPhotoList = null;

                    if(resultObj.has("formatted_address")) { result.setFormattedAddress(resultObj.getString("formatted_address")); }
                    if(resultObj.has("formatted_phone_number")) { result.setFormattedPhoneNumber(resultObj.getString("formatted_phone_number")); }

                    if (resultObj.has("geometry")) {
                        JSONObject geometry;
                        geometry = resultObj.getJSONObject("geometry");

                        if (geometry.has("location")) {
                            JSONObject location;
                            location = geometry.getJSONObject("location");
                            lat = Double.parseDouble(location.getString("lat"));    // latitude 위도
                            lng = Double.parseDouble(location.getString("lng"));    // longitude 경도

                            result.setLat(lat);
                            result.setLng(lng);
                        }
                    }

                    if(resultObj.has("icon")) { result.setIcon(resultObj.getString("icon")); }
                    if(resultObj.has("id")) { result.setId(resultObj.getString("id")); }
                    if(resultObj.has("international_phone_number")) { result.setInternationalPhoneNumber(resultObj.getString("international_phone_number")); }
                    if(resultObj.has("name")) { result.setName(resultObj.getString("name")); }
                    if(resultObj.has("opening_horus")) {
                        JSONObject openingHours;
                        openingHours = resultObj.getJSONObject("opening_hours");

                        if(openingHours.has("open_now")) { result.setOpenNow(openingHours.getBoolean("open_now")); }
                    }

                    if(resultObj.has("weekday_text")) {
                        JSONArray weekday;
                        weekday = resultObj.getJSONArray("weekday_text");
                        weekdayText = new String[weekday.length()];

                        for(int i=0; i < weekday.length(); i++) {
                            weekdayText[i] = weekday.optString(i);
                        }
                        Log.i("Info", "WeekDay : " + weekdayText);
                        result.setWeekdayText(weekdayText);
                    }

                    if(resultObj.has("photos")) {
                        photoList = new ArrayList<GooglePlaceDetailPhoto>();
                        JSONArray photos;
                        photos = resultObj.getJSONArray(("photos"));
                        if(null != photos && 0 < photos.length()) {
                            for(int i=0; i < photos.length(); i++) {
                                GooglePlaceDetailPhoto tmpPhoto = new GooglePlaceDetailPhoto();
                                JSONObject photoObj = photos.getJSONObject(i);
                                String photoReference = "";

                                photoReference = photoObj.getString("photo_reference");
                                tmpPhoto.setPhotoReference(photoReference);
                                photoList.add(tmpPhoto);
                            }

                            result.setPhotoList(photoList);
                        }
                    }

                    if(resultObj.has("place_id")) { result.setPlaceId(resultObj.getString("place_id")); }
                    if(resultObj.has("rating")) { result.setRating(Float.parseFloat(resultObj.getString("rating"))); }

                    if(resultObj.has("reviews")) {
                        reviewsList = new ArrayList<GooglePlaceDetailReviews>();
                        JSONArray reviews;
                        reviews = resultObj.getJSONArray("reviews");
                        for(int i=0; i < reviews.length(); i++) {
                            GooglePlaceDetailReviews tmpReview = new GooglePlaceDetailReviews();
                            String authorName = "";
                            String authorUrl = "";
                            String language = "";
                            String profilePhotoUrl = "";
                            String reviewRating = "";
                            String relativeTimeDescription = "";
                            String text = "";
                            String reviewTime = "";

                            JSONObject reviewObj = reviews.getJSONObject(i);

                            authorName = reviewObj.getString("author_name");
                            authorUrl = reviewObj.getString("author_url");
                            language = reviewObj.getString("language");
                            profilePhotoUrl = reviewObj.getString("profile_photo_url");
                            reviewRating = reviewObj.getString("rating");
                            relativeTimeDescription = reviewObj.getString("relative_time_description");
                            text = reviewObj.getString("text");
                            reviewTime = reviewObj.getString("time");

                            tmpReview.setAuthorName(authorName);
                            tmpReview.setAuthorUrl(authorUrl);
                            tmpReview.setLanguage(language);
                            tmpReview.setProfilePhotoUrl(profilePhotoUrl);
                            tmpReview.setRating(Float.parseFloat(reviewRating));
                            tmpReview.setRelativeTimeDescription(relativeTimeDescription);
                            tmpReview.setText(text);
                            tmpReview.setTime(reviewTime);

                            reviewsList.add(tmpReview);
                        }

                        result.setReviewsList(reviewsList);
                    }

                    if(resultObj.has("scope")) { result.setScope(resultObj.getString("scope")); }
                    if(resultObj.has("url")) { result.setUrl(resultObj.getString("url")); }
                    if(resultObj.has("website")) { result.setWebsite(resultObj.getString("website")); }
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
