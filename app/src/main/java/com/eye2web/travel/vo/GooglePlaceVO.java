package com.eye2web.travel.vo;

import java.io.Serializable;
import java.util.List;

public class GooglePlaceVO implements Serializable {

    private String htmlAttributions;

    private String nextPageToken;

    private List<GooglePlaceItem> placeItem;

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
