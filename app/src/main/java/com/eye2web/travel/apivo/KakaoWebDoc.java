package com.eye2web.travel.apivo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class KakaoWebDoc implements Serializable {

    private static final long serialVersionUID = 2299851643537295068L;

    @SerializedName("meta")
    Meta meta;

    @SerializedName("documents")
    List<Documents> documents;

    public class Meta implements Serializable {

        private static final long serialVersionUID = -7591992514854455020L;

        @SerializedName("total_count") int total_count;
        @SerializedName("pageable_count") int pageable_count;
        @SerializedName("is_end") boolean is_end;

        public int getTotal_count() { return total_count; }
        public int getPageable_count() { return pageable_count; }
        public boolean getIs_end() { return is_end; }
    }

    public class Documents implements Serializable {

        private static final long serialVersionUID = -8571418763384570921L;

        @SerializedName("datetime") String datetime;
        @SerializedName("contents") String contents;
        @SerializedName("title") String title;
        @SerializedName("url") String url;

        public String getDatetime() { return datetime; }
        public String getContents() { return contents; }
        public String getTitle() { return title; }
        public String getUrl() { return url; }
    }

    public Meta getMeta() { return meta; }

    public List<Documents> getDocuments() { return documents; }

    public interface KakaoWebDocInterface {
        @Headers({"Authorization: KakaoAK aedd18491b704b456ffa5acf47e8fef7"})
        @GET("/v2/search/web")
        Call<KakaoWebDoc> get_kakao_webdoc(
                @Query("query") String query
                , @Query("sort") String sort
                , @Query("page") int page
                , @Query("size") int size);
    }
}
