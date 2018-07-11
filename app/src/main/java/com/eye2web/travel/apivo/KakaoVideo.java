package com.eye2web.travel.apivo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class KakaoVideo implements Serializable {

    private static final long serialVersionUID = -6936330259728637071L;

    @SerializedName("meta")
    Meta meta;

    @SerializedName("documents")
    List<Documents> documents;

    public class Meta implements Serializable {

        private static final long serialVersionUID = 5334261727530092349L;

        @SerializedName("total_count") int total_count;
        @SerializedName("pageable_count") int pageable_count;
        @SerializedName("is_end") boolean is_end;

        public int getTotal_count() { return total_count; }
        public int getPageable_count() { return pageable_count; }
        public boolean getIs_end() { return is_end; }
    }

    public class Documents implements Serializable {

        private static final long serialVersionUID = -4660729114309530308L;

        @SerializedName("title") String title;
        @SerializedName("url") String url;
        @SerializedName("datetime") String datetime;
        @SerializedName("play_time") String play_time;
        @SerializedName("thumbnail") String thumbnail;
        @SerializedName("author") String author;

        public String getTitle() { return title; }
        public String getUrl() { return url; }
        public String getDatetime() { return datetime; }
        public String getPlay_time() { return play_time; }
        public String getThumbnail() { return thumbnail; }
        public String getAuthor() { return author; }
    }

    public Meta getMeta() { return meta; }

    public List<Documents> getDocuments() { return documents; }

    public interface KakaoVideoInterface {
        @Headers({"Authorization: KakaoAK aedd18491b704b456ffa5acf47e8fef7"})
        @GET("/v2/search/vclip")
        Call<KakaoVideo> get_kakao_video(
                @Query("query") String query
                , @Query("sort") String sort
                , @Query("page") int page
                , @Query("size") int size);
    }
}
