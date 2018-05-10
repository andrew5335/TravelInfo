package com.eye2web.travel.vo;

/**
 * @File : ListItem
 * @Date : 2018. 5. 2. PM 4:48
 * @Author : Andrew Kim
 * @Version : 1.0.0
 * @Description : 리스트를 구성하는 구성요소
**/
public class ListItem {

    private String addr1;
    private String addr2;
    private String areacode;
    private String booktour;
    private String cat1;
    private String cat2;
    private String cat3;
    private String contentid;
    private String contenttypeid;
    private String firstimage;
    private String firstimage2;
    private float mapx;
    private float mapy;
    private int mlevel;
    private String modifiedtime;
    private int readcount;
    private String siguguncode;
    private String tel;
    private String title;

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public String getBooktour() {
        return booktour;
    }

    public void setBooktour(String booktour) {
        this.booktour = booktour;
    }

    public String getCat1() {
        return cat1;
    }

    public void setCat1(String cat1) {
        this.cat1 = cat1;
    }

    public String getCat2() {
        return cat2;
    }

    public void setCat2(String cat2) {
        this.cat2 = cat2;
    }

    public String getCat3() {
        return cat3;
    }

    public void setCat3(String cat3) {
        this.cat3 = cat3;
    }

    public String getContentid() {
        return contentid;
    }

    public void setContentid(String contentid) {
        this.contentid = contentid;
    }

    public String getContenttypeid() {
        return contenttypeid;
    }

    public void setContenttypeid(String contenttypeid) {
        this.contenttypeid = contenttypeid;
    }

    public String getFirstimage() {
        return firstimage;
    }

    public void setFirstimage(String firstimage) {
        this.firstimage = firstimage;
    }

    public String getFirstimage2() {
        return firstimage2;
    }

    public void setFirstimage2(String firstimage2) {
        this.firstimage2 = firstimage2;
    }

    public float getMapx() {
        return mapx;
    }

    public void setMapx(float mapx) {
        this.mapx = mapx;
    }

    public float getMapy() {
        return mapy;
    }

    public void setMapy(float mapy) {
        this.mapy = mapy;
    }

    public int getMlevel() {
        return mlevel;
    }

    public void setMlevel(int mlevel) {
        this.mlevel = mlevel;
    }

    public String getModifiedtime() {
        return modifiedtime;
    }

    public void setModifiedtime(String modifiedtime) {
        this.modifiedtime = modifiedtime;
    }

    public int getReadcount() {
        return readcount;
    }

    public void setReadcount(int readcount) {
        this.readcount = readcount;
    }

    public String getSiguguncode() {
        return siguguncode;
    }

    public void setSiguguncode(String siguguncode) {
        this.siguguncode = siguguncode;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
    public ListItem(String addr1, String addr2, String areacode, String booktour,
                    String cat1, String cat2, String cat3, String contentid, String contenttypeid,
                    String firstimage, String firstimage2, float mapx, float mapy, int mlevel,
                    String modifiedtime, int readcount, String siguguncode, String tel, String title) {

        this.addr1 = addr1;
        this.addr2 = addr2;
        this.areacode = areacode;
        this.booktour = booktour;
        this.cat1 = cat1;
        this.cat2 = cat2;
        this.cat3 = cat3;
        this.contentid = contentid;
        this.contenttypeid = contenttypeid;
        this.firstimage = firstimage;
        this.firstimage2 = firstimage2;
        this.mapx = mapx;
        this.mapy = mapy;
        this.mlevel = mlevel;
        this.modifiedtime = modifiedtime;
        this.readcount = readcount;
        this.siguguncode = siguguncode;
        this.tel = tel;
        this.title = title;
    }
     **/
    public ListItem(String addr1, String firstimage, String title) {
        this.addr1 = addr1;
        this.firstimage = firstimage;
        this.title = title;
    }
}
