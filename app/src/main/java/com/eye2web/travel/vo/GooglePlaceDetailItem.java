package com.eye2web.travel.vo;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

public class GooglePlaceDetailItem implements Serializable {

    private static final long serialVersionUID = -2785182748060086164L;

    private String formattedAddress;    // 주소

    private String formattedPhoneNumber;    // 전화번호

    private double lat;    // 위도

    private double lng;    // 경도

    private String icon;    // 아이콘 이미지 url

    private String id;    // 고유 id값

    private String internationalPhoneNumber;    // 국제 전화번호

    private String name;    // 명칭

    private boolean openNow;    // 영업유무

    private String[] weekdayText;    // 영업일 정보

    private List<GooglePlaceDetailPhoto> photoList;    // 사진 이미지 리스트

    private String placeId;    // 장소 아이디값

    private float rating;    // 장소 평점

    private List<GooglePlaceDetailReviews> reviewsList;    // 리뷰 리스트

    private String scope;    // 범위

    private String url;    // 구글 공식 페이지 url

    private List<Bitmap> bitmapPhotoList;

    private String website;    // 웹 사이트 url

    private String utcOffSet;    // UTC 기준 시간대 표시

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getFormattedPhoneNumber() {
        return formattedPhoneNumber;
    }

    public void setFormattedPhoneNumber(String formattedPhoneNumber) {
        this.formattedPhoneNumber = formattedPhoneNumber;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInternationalPhoneNumber() {
        return internationalPhoneNumber;
    }

    public void setInternationalPhoneNumber(String internationalPhoneNumber) {
        this.internationalPhoneNumber = internationalPhoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpenNow() {
        return openNow;
    }

    public void setOpenNow(boolean openNow) {
        this.openNow = openNow;
    }

    public String[] getWeekdayText() {
        return weekdayText;
    }

    public void setWeekdayText(String[] weekdayText) {
        this.weekdayText = weekdayText;
    }

    public List<GooglePlaceDetailPhoto> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<GooglePlaceDetailPhoto> photoList) {
        this.photoList = photoList;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<GooglePlaceDetailReviews> getReviewsList() {
        return reviewsList;
    }

    public void setReviewsList(List<GooglePlaceDetailReviews> reviewsList) {
        this.reviewsList = reviewsList;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Bitmap> getBitmapPhotoList() {
        return bitmapPhotoList;
    }

    public void setBitmapPhotoList(List<Bitmap> bitmapPhotoList) {
        this.bitmapPhotoList = bitmapPhotoList;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getUtcOffSet() { return utcOffSet; }

    public void setUtcOffSet(String utcOffSet) { this.utcOffSet = utcOffSet; }
}
