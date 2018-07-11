package com.eye2web.travel.apivo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class NaverImageSearch implements Serializable {

    private static final long serialVersionUID = 2177756487379238220L;

    @SerializedName("items") List<Items> itemsList;
    @SerializedName("lastBuildDate") String lastBuildDate;
    @SerializedName("total") int total;
    @SerializedName("start") int start;
    @SerializedName("display") int display;

    public class Items implements Serializable {

        private static final long serialVersionUID = 2482324735126991908L;

        @SerializedName("title") String title;
        @SerializedName("link") String link;
        @SerializedName("thumbnail") String thumbnail;
        @SerializedName("sizeheight") String sizeheight;
        @SerializedName("sizewidth") String sizewidth;

        public String getTitle() { return title; }
        public String getLink() { return link; }
        public String getThumbnail() { return thumbnail; }
        public String getSizeheight() { return sizeheight; }
        public String getSizewidth() { return sizewidth; }
    }

    public List<Items> getItemsList() { return itemsList; }
    public String getLastBuildDate() { return lastBuildDate; }
    public int getTotal() { return total; }
    public int getStart() { return start; }
    public int getDisplay() { return display; }

    public interface NaverImageSearchInterface {
        @Headers({"X-Naver-Client-Id: eh2QuN2U0b9M6WNzMwwZ", "X-Naver-Client-Secret: U7sQVCWCnt"})
        @GET("/v1/search/image")
    Call<NaverImageSearch> get_image_info(
            @Query("query") String query
                , @Query("display") int display
                , @Query("start") int start
                , @Query("sort") String sort
                , @Query("filter") String filter);
    }
}
