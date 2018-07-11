package com.eye2web.travel.apivo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class Eye2WebContent implements Serializable {

    private static final long serialVersionUID = 3943002341972242348L;

    @SerializedName("id") int id;
    @SerializedName("date") String date;
    @SerializedName("date_gmt") String date_gmt;
    @SerializedName("status") String status;
    @SerializedName("title") Title title;
    @SerializedName("content") Content content;
    @SerializedName("excerpt") Excerpt excerpt;
    @SerializedName("featured_media") int featured_media;

    public String feature_media_url;

    public class Title implements Serializable {

        private static final long serialVersionUID = -5748916382138034116L;

        @SerializedName("rendered") String rendered;

        public String getRendered() { return rendered; }
    }

    public class Content implements Serializable {

        private static final long serialVersionUID = 7206037230488626615L;

        @SerializedName("rendered") String rendered;

        public String getRendered() { return rendered; }
    }

    public class Excerpt implements Serializable {

        private static final long serialVersionUID = -7671940152719953870L;

        @SerializedName("rendered") String rendered;

        public String getRendered() { return rendered; }
    }

    public int getId() { return id; }
    public String getDate() { return date; }
    public String getDate_gmt() { return date_gmt; }
    public String getStatus() { return status; }
    public Title getTitle() { return title; }
    public Content getContent() { return content; }
    public Excerpt getExcerpt() { return excerpt; }
    public int getFeatured_media() { return featured_media; }

    public void setFeatured_media_url(String feature_media_url) {
        this.feature_media_url = feature_media_url;
    }

    public String getFeature_media_url() {
        return feature_media_url;
    }

    public interface Eye2WebContentInterface {
        @GET("/wp-json/wp/v2/posts")
        Call<List<Eye2WebContent>> get_content_list(
                @Query("categories") int categories
                , @Query("page") int page
                , @Query("per_page") int per_page);
    }

    public interface Eye2WebContentDetailInterface {
        @GET("/wp-json/wp/v2/posts/{id}")
        Call<Eye2WebContent> get_content_detail(@Path("id") int id);
    }

}
