package com.eye2web.travel.apivo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class KakaoCafe implements Serializable {

    private static final long serialVersionUID = 7868815880970456456L;

    @SerializedName("meta")
    Meta meta;

    @SerializedName("documents")
    List<Documents> documents;

    public class Meta implements Serializable {

        private static final long serialVersionUID = 808536480893096314L;

        @SerializedName("total_count") int total_count;
        @SerializedName("pageable_count") int pageable_count;
        @SerializedName("is_end") boolean is_end;

        public int getTotal_count() { return total_count; }
        public int getPageable_count() { return pageable_count; }
        public boolean getIs_end() { return is_end; }
    }

    public class Documents implements Serializable {

        private static final long serialVersionUID = 6355561101044459063L;

        @SerializedName("title") String title;
        @SerializedName("contents") String contents;
        @SerializedName("url") String url;
        @SerializedName("cafename") String cafename;
        @SerializedName("thumbnail") String thumbnail;
        @SerializedName("datetime") String datetime;

        public String getTitle() { return title; }
        public String getContents() { return contents; }
        public String getUrl() { return url; }
        public String getCafename() { return cafename; }
        public String getThumbnail() { return thumbnail; }
        public String getDatetime() { return datetime; }
    }

    public interface KakaoCafeInterface {
        @Headers({"Authorization: KakaoAK aedd18491b704b456ffa5acf47e8fef7"})
        @GET("/v2/search/cafe")
        Call<KakaoCafe> get_kakao_cafe(
                @Query("query") String query
                , @Query("sort") String sort
                , @Query("page") int page
                , @Query("size") int size);
    }
}
