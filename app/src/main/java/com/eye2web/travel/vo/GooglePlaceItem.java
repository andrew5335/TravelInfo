package com.eye2web.travel.vo;

import android.graphics.Bitmap;

import java.io.Serializable;

public class GooglePlaceItem implements Serializable{

    private static final long serialVersionUID = -5875642677087540479L;

    private double lat = 0;    // latitude 위도

    private double lng = 0;    // longitude 경도

    private String icon;    // 지도에 결과를 표시할 경우 사용할 수 있는 권장 아이콘의 주소(url)

    private String id;    // 장소를 표시하는 고유 식별자

    private String name;    // 장소의 명칭

    private boolean openNow;    // 현재 영업중 유무

    private int height;    // 이미지의 최대 높이

    private int width;    // 이미지의 최대 너비

    private String photoHtmlAttribution;

    private String photoReference;    // 사진을 요청할 경우 사진을 식별하는데 사용되는 문자열

    private String placeId;    // 장소를 고유하게 식별하는 텍스트 식별자 - 장소에 대한 상세정보 요청 시 사용

    private String scope = "GOOGLE";    // place_id의 범위 - APP : 장소 ID는 본인의 애플리케이션에서만 인식됨 / GOOGLE : 장소 ID를 다른 애플리케이션 및 구글 지도에서 사용가능

    private int priceLevel;    // 장소의 가격수준 - 0 : 무료 / 1 : 저렴 / 2 : 보통 / 3 : 비쌈 / 4 : 매우 비쌈

    private float rating;    // 장소의 평점 (사용자 리뷰 기준으로 1.0 ~ 5.0)

    private String reference;    // 장소 세부정보 요청 시 사용되는 고유한 문자열 (현재는 place id로 대체되어 사용하지 않음)

    private String formattedAddress;    // 장소의 주소 (우편주소)

    private Bitmap photoUrl;    // 사진 url

    private String summary;


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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getPhotoHtmlAttribution() {
        return photoHtmlAttribution;
    }

    public void setPhotoHtmlAttribution(String photoHtmlAttribution) {
        this.photoHtmlAttribution = photoHtmlAttribution;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public int getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(int priceLevel) {
        this.priceLevel = priceLevel;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public Bitmap getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(Bitmap photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
