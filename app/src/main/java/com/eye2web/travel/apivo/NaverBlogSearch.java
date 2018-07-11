package com.eye2web.travel.apivo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class NaverBlogSearch implements Serializable {

    private static final long serialVersionUID = -4067214898696912954L;

    @SerializedName("items") List<Items> itemList;
    @SerializedName("lastBuildDate") String lastBuildDate;
    @SerializedName("total") int total;
    @SerializedName("start") int start;
    @SerializedName("display") int display;

    public class Items implements Serializable {

        private static final long serialVersionUID = 7288447563057873880L;

        @SerializedName("title") String title;
        @SerializedName("link") String link;
        @SerializedName("description") String description;
        @SerializedName("bloggername") String bloggername;
        @SerializedName("bloggerlink") String bloggerlink;
        @SerializedName("postdate") String postdate;

        public String getTitle() { return title; }
        public String getLink() { return link; }
        public String getDescription() { return description; }
        public String getBloggername() { return bloggername; }
        public String getBloggerlink() { return bloggerlink; }
        public String getPostdate() { return postdate; }
    }

    public List<Items> getItemList() { return itemList; }
    public String getLastBuildDate() { return lastBuildDate; }
    public int getTotal() { return total; }
    public int getStart() { return start; }
    public int getDisplay() { return display; }

    public interface NaverBlogSearchInterface {
        @Headers({"X-Naver-Client-Id: eh2QuN2U0b9M6WNzMwwZ", "X-Naver-Client-Secret: U7sQVCWCnt"})
        @GET("/v1/search/blog.json")
        Call<NaverBlogSearch> get_blog_info(@Query("query") String query
                , @Query("display") int display
                , @Query("start") int start
                , @Query("sort") String sort);
    }
}
