package com.eye2web.travel.vo;

import java.io.Serializable;
import java.util.List;

public class GooglePlaceVO implements Serializable {

    private static final long serialVersionUID = -4875072485971482614L;

    private String htmlAttributions;    // html내용

    private String nextPageToken;    // 다음 페이지 호출용 토큰

    private List<GooglePlaceItem> placeItem;    // 페이지 구성 내용 리스트

    public String getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(String htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public List<GooglePlaceItem> getPlaceItem() {
        return placeItem;
    }

    public void setPlaceItem(List<GooglePlaceItem> placeItem) {
        this.placeItem = placeItem;
    }
}
