package com.eye2web.travel.apivo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class NaverLocalSearch implements Serializable {

    private static final long serialVersionUID = -5308571122737255792L;

    @SerializedName("items") List<Items> itemsList;
    @SerializedName("lastBuildDate") String lastBuildDate;
    @SerializedName("total") int total;
    @SerializedName("start") int start;
    @SerializedName("display") int display;

    public class Items implements Serializable {

        private static final long serialVersionUID = -3680094030884504282L;

        @SerializedName("title") String title;
        @SerializedName("link") String link;
        @SerializedName("category") String category;
        @SerializedName("description") String description;
        @SerializedName("telephone") String telephone;
        @SerializedName("address") String address;
        @SerializedName("roadAddress") String roadAddress;
        @SerializedName("mapx") String mapx;
        @SerializedName("mapy") String mapy;

        public String getTitle() { return title; }
        public String getLink() { return link; }
        public String getCategory() { return category; }
        public String getDescription() { return description; }
        public String getTelephone() { return telephone; }
        public String getAddress() { return address; }
        public String getRoadAddress() { return roadAddress; }
        public String getMapx() { return mapx; }
        public String getMapy() { return mapy; }
    }

    public List<Items> getItemsList() { return itemsList; }
    public String getLastBuildDate() { return lastBuildDate; }
    public int getTotal() { return total; }
    public int getStart() { return start; }
    public int getDisplay() { return display; }

    public interface NaverLocalSearchInterface {
        @Headers({"X-Naver-Client-Id: eh2QuN2U0b9M6WNzMwwZ", "X-Naver-Client-Secret: U7sQVCWCnt"})
        @GET("/v1/search/local.json")
        Call<NaverLocalSearch> get_local_info(
                @Query("query") String query
                , @Query("display") int display
                , @Query("start") int start
                , @Query("sort") String sort);
    }
}