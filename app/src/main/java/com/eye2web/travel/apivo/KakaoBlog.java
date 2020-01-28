package com.eye2web.travel.apivo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class KakaoBlog implements Serializable {

    private static final long serialVersionUID = -1384487631955508591L;

    @SerializedName("meta")
    Meta meta;

    @SerializedName("documents")
    List<Documents> documents;

    public class Meta implements Serializable {

        private static final long serialVersionUID = 5955483512949034868L;

        @SerializedName("total_count") int total_count;
        @SerializedName("pageable_count") int pageable_count;
        @SerializedName("is_end") boolean is_end;

        public int getTotal_count() { return total_count; }
        public int getPageable_count() { return pageable_count; }
        public boolean getIs_end() { return is_end; }
    }

    public class Documents implements Serializable {

        private static final long serialVersionUID = -2568237699715067894L;

        @SerializedName("title") String title;
        @SerializedName("contents") String contents;
        @SerializedName("url") String url;
        @SerializedName("blogname") String blogname;
        @SerializedName("thumbnail") String thumbnail;
        @SerializedName("datetime") String datetime;

        public String getTitle() { return title; }
        public String getContents() { return contents; }
        public String getUrl() { return url; }
        public String getBlogname() { return blogname; }
        public String getThumbnail() { return thumbnail; }
        public String getDatetime() { return datetime; }
    }

    public Meta getMeta() { return meta; }

    public List<Documents> getDocuments() { return documents; }

    public interface KakaoBlogInterface {
        @Headers({"Authorization: KakaoAK aedd18491b704b456ffa5acf47e8fef7"})
        @GET("/v2/search/blog")
        Call<KakaoBlog> get_kakao_blog(
                @Query("query") String query
                , @Query("sort") String sort
                , @Query("page") int page
                , @Query("size") int size);
    }
}