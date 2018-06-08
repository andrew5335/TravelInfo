package com.eye2web.travel.vo;

import java.io.Serializable;

public class GooglePlaceDetailPhoto implements Serializable {

    private static final long serialVersionUID = -414065366806162454L;

    private String width;

    private String height;

    private String photoReference;

    private String htmlAttributions;

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    public String getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(String htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }
}
