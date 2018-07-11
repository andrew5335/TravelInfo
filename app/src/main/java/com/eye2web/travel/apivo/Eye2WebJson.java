package com.eye2web.travel.apivo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class Eye2WebJson implements Serializable {

    private static final long serialVersionUID = -3580460372321784595L;

    @SerializedName("content_id") int content_id;
    @SerializedName("title") String title;
    @SerializedName("img") String img;

    public int getContent_id() { return content_id; }
    public String getTitle() { return title; }
    public String getImg() { return img; }

    public interface Eye2WebJsonInterface {
        @GET("/app_index_json.php")
        Call<List<Eye2WebJson>> get_json_data(
                @Query("category") int category
                , @Query("auth_key") String auth_key
                , @Query("page") int page
                , @Query("per_page") int per_page);
    }
}
