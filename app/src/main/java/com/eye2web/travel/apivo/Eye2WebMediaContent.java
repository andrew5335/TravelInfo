package com.eye2web.travel.apivo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class Eye2WebMediaContent implements Serializable {

    private static final long serialVersionUID = -1281601787993539372L;

    @SerializedName("id") int id;
    @SerializedName("date") String date;
    @SerializedName("date_gmt") String date_gmt;
    @SerializedName("source_url") String source_url;

    public int getId() { return id; }
    public String getDate() { return date; }
    public String getDate_gmt() { return date_gmt; }
    public String getSource_url() { return source_url; }

    public interface Eye2WebMediaInterface {
        @GET("/wp-json/wp/v2/media/{id}")
        Call<Eye2WebMediaContent> get_media_url(@Path("id") int id);
    }
}
