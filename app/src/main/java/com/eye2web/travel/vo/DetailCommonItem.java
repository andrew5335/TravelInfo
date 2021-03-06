package com.eye2web.travel.vo;

import com.eye2web.travel.apivo.KakaoBlog;
import com.eye2web.travel.apivo.KakaoImage;
import com.eye2web.travel.apivo.KakaoLocal;

import java.io.Serializable;
import java.util.List;

public class DetailCommonItem implements Serializable {

    private static final long serialVersionUID = -3457366616186565141L;

    private String contentid;
    private String contenttypeid;
    private String booktour;
    private String createdtime;
    private String homepage;
    private String modifiedtime;
    private String tel;
    private String telname;
    private String firstimage;
    private String firstimage2;
    private String areacode;
    private String sigungucode;
    private String cat1;
    private String cat2;
    private String cat3;
    private String addr1;
    private String addr2;
    private double mapx;
    private double mapy;
    private String mlevel;
    private String overview;
    private String title;
    private List<String> imgUrlList;

    private KakaoImage kakaoImage;
    private KakaoBlog kakaoBlog;
    private KakaoLocal kakaoLocal;
    private KakaoLocal kakaoLocalGas;
    private KakaoLocal kakaoLocalPark;

    //private GooglePlaceDetailItem googlePlaceDetailItem;

    //private List<GooglePlaceItem> gasList;

    //private List<GooglePlaceItem> parkingList;

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

    public String getBooktour() {
        return booktour;
    }

    public void setBooktour(String booktour) {
        this.booktour = booktour;
    }

    public String getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(String createdtime) {
        this.createdtime = createdtime;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getModifiedtime() {
        return modifiedtime;
    }

    public void setModifiedtime(String modifiedtime) {
        this.modifiedtime = modifiedtime;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTelname() {
        return telname;
    }

    public void setTelname(String telname) {
        this.telname = telname;
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

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public String getSigungucode() {
        return sigungucode;
    }

    public void setSigungucode(String sigungucode) {
        this.sigungucode = sigungucode;
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

    public double getMapx() {
        return mapx;
    }

    public void setMapx(double mapx) {
        this.mapx = mapx;
    }

    public double getMapy() {
        return mapy;
    }

    public void setMapy(double mapy) {
        this.mapy = mapy;
    }

    public String getMlevel() {
        return mlevel;
    }

    public void setMlevel(String mlevel) {
        this.mlevel = mlevel;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImgUrlList() { return imgUrlList; }

    public void setImgUrlList(List<String> imgUrlList) { this.imgUrlList = imgUrlList; }

    public KakaoImage getKakaoImage() {
        return kakaoImage;
    }

    public void setKakaoImage(KakaoImage kakaoImage) {
        this.kakaoImage = kakaoImage;
    }

    public KakaoBlog getKakaoBlog() {
        return kakaoBlog;
    }

    public void setKakaoBlog(KakaoBlog kakaoBlog) {
        this.kakaoBlog = kakaoBlog;
    }

    public KakaoLocal getKakaoLocal() {
        return kakaoLocal;
    }

    public void setKakaoLocal(KakaoLocal kakaoLocal) {
        this.kakaoLocal = kakaoLocal;
    }

    public KakaoLocal getKakaoLocalGas() {
        return kakaoLocalGas;
    }

    public void setKakaoLocalGas(KakaoLocal kakaoLocalGas) {
        this.kakaoLocalGas = kakaoLocalGas;
    }

    public KakaoLocal getKakaoLocalPark() {
        return kakaoLocalPark;
    }

    public void setKakaoLocalPark(KakaoLocal kakaoLocalPark) {
        this.kakaoLocalPark = kakaoLocalPark;
    }

    /**
    public GooglePlaceDetailItem getGooglePlaceDetailItem() {
        return googlePlaceDetailItem;
    }

    public void setGooglePlaceDetailItem(GooglePlaceDetailItem googlePlaceDetailItem) {
        this.googlePlaceDetailItem = googlePlaceDetailItem;
    }

    public List<GooglePlaceItem> getGasList() {
        return gasList;
    }

    public void setGasList(List<GooglePlaceItem> gasList) {
        this.gasList = gasList;
    }

    public List<GooglePlaceItem> getParkingList() {
        return parkingList;
    }

    public void setParkingList(List<GooglePlaceItem> parkingList) {
        this.parkingList = parkingList;
    }
     **/
}
