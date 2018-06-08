package com.eye2web.travel.vo;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

public class GooglePlaceDetailItem implements Serializable {

    private static final long serialVersionUID = -2785182748060086164L;

    private String formattedAddress;

    private String formattedPhoneNumber;

    private double lat;

    private double lng;

    private String icon;

    private String id;

    private String internationalPhoneNumber;

    private String name;

    private boolean openNow;

    private String[] weekdayText;

    private List<GooglePlaceDetailPhoto> photoList;

    private String placeId;

    private float rating;

    private List<GooglePlaceDetailReviews> reviewsList;

    private String scope;

    private String url;

    private List<Bitmap> bitmapPhotoList;

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
}
