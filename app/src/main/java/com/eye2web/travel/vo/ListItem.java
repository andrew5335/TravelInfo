package com.eye2web.travel.vo;

/**
 * @File : ListItem
 * @Date : 2018. 5. 2. PM 4:48
 * @Author : Andrew Kim
 * @Version : 1.0.0
 * @Description : 리스트를 구성하는 구성요소
**/
public class ListItem {

    private String addr;
    private String firstImage;

    public String getAddr() {
        return addr;
    }

    public String getFirstImage() {
        return firstImage;
    }

    public ListItem(String addr, String firstImage) {
        this.addr = addr;
        this.firstImage = firstImage;
    }
}
