package com.eye2web.travel.apivo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class KakaoTip implements Serializable {

    private static final long serialVersionUID = -8052794787677583526L;

    @SerializedName("meta")
    Meta meta;

    @SerializedName("documents")
    List<Documents> documents;

    public class Meta implements Serializable {

        private static final long serialVersionUID = -3517108809173501594L;

        @SerializedName("total_count") int total_count;
        @SerializedName("pageable_count") int pageable_count;
        @SerializedName("is_end") boolean is_end;

        public int getTotal_count() { return total_count; }
        public int getPageable_count() { return pageable_count; }
        public boolean getIs_end() { return is_end; }
    }

    public class Documents implements Serializable {

        private static final long serialVersionUID = 8678150006966065996L;

        @SerializedName("title") String title;
        @SerializedName("contents") String contents;
        @SerializedName("qu_url") String q_url;
        @SerializedName("a_url") String a_url;
        @SerializedName("thumbnails") List<String> thumbnails;
        @SerializedName("type") String type;
        @SerializedName("datetime") String datetime;

        public String getTitle() { return title; }
        public String getContents() { return contents; }
        public String getQ_url() { return q_url; }
        public String getA_url() { return a_url; }
        public List<String> getThumbnails() { return thumbnails; }
        public String getType() { return type; }
        public String getDatetime() { return datetime; }
    }

    public interface KakaoTipInterface {
        @Headers({"Authorization: KakaoAK aedd18491b704b456ffa5acf47e8fef7"})
        @GET("/v2/search/tip")
        Call<KakaoTip> get_kakao_tip(
                @Query("query") String query
                , @Query("sort") String sort
                , @Query("page") int page
                , @Query("size") int size);
    }
}