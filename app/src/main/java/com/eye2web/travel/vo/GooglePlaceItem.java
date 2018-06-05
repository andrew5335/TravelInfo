package com.eye2web.travel.vo;

public class GooglePlaceItem {

    private double lat = 0;    // latitude 위도

    private double lng = 0;    // longitude 경도

    private String icon;    // 지도에 결과를 표시할 경우 사용할 수 있는 권장 아이콘의 주소(url)

    private String id;    // 장소를 표시하는 고유 식별자

    private String name;    // 장소의 명칭

    private boolean openNow;    // 현재 영업중 유무

    private int height;    // 이미지의 최대 높이

    private int width;    // 이미지의 최대 너비

    private String photoReference;    // 사진을 요청할 경우 사진을 식별하는데 사용되는 문자열

    private String placeId;    // 장소를 고유하게 식별하는 텍스트 식별자 - 장소에 대한 상세정보 요청 시 사용

    private String scope;    // place_id의 범위 - APP : 장소 ID는 본인의 애플리케이션에서만 인식됨 / GOOGLE : 장소 ID를 다른 애플리케이션 및 구글 지도에서 사용가능

    private int priceLevel;    // 장소의 가격수준 - 0 : 무료 / 1 : 저렴 / 2 : 보통 / 3 : 비쌈 / 4 : 매우 비쌈

    private float rating;    // 장소의 평점 (사용자 리뷰 기준으로 1.0 ~ 5.0)

    private String reference;    // 장소 세부정보 요청 시 사용되는 고유한 문자열 (현재는 place id로 대체되어 사용하지 않음)

    private String formattedAddress;    // 장소의 주소 (우편주소)
}
