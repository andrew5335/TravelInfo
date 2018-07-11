package com.eye2web.travel.apivo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class KakaoImage implements Serializable {

    private static final long serialVersionUID = -2269212845316653183L;

    @SerializedName("meta")
    Meta meta;

    @SerializedName("documents")
    List<Documents> documents;

    public class Meta implements Serializable {

        private static final long serialVersionUID = -503354890920576145L;

        @SerializedName("total_count") int total_count;
        @SerializedName("pageable_count") int pageable_count;
        @SerializedName("is_end") boolean is_end;

        public int getTotal_count() { return total_count; }
        public int getPageable_count() { return pageable_count; }
        public boolean getIs_end() { return is_end; }
    }

    public class Documents implements Serializable {

        private static final long serialVersionUID = 6722622964062552959L;

        @SerializedName("collection") String collection;
        @SerializedName("thumbnail_url") String thumbnail_url;
        @SerializedName("image_url") String image_url;
        @SerializedName("width") int width;
        @SerializedName("height") int height;
        @SerializedName("display_sitename") String display_sitename;
        @SerializedName("doc_url") String doc_url;
        @SerializedName("datetime") String datetime;

        public String getCollection() { return collection; }
        public String getThumbnail_url() { return thumbnail_url; }
        public String getImage_url() { return image_url; }
        public int getWidth() { return width; }
        public int getHeight() { return height; }
        public String getDisplay_sitename() { return display_sitename; }
        public String getDoc_url() { return doc_url; }
        public String getDatetime() { return datetime; }
    }

    public Meta getMeta() { return meta; }

    public List<Documents> getDocuments() { return documents; }

    public interface KakaoImageInterface {
        @Headers({"Authorization: KakaoAK aedd18491b704b456ffa5acf47e8fef7"})
        @GET("/v2/search/image")
        Call<KakaoImage> get_kakao_image(
                @Query("query") String query
                , @Query("sort") String sort
                , @Query("page") int page
                , @Query("size") int size);
    }
}
