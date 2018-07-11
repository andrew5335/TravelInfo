package com.eye2web.travel.service;

import android.util.Log;

import com.eye2web.travel.apivo.Eye2WebContent;
import com.eye2web.travel.apivo.Eye2WebJson;
import com.eye2web.travel.apivo.Eye2WebMediaContent;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Eye2WebApiService {

    private String eye2webHost = "http://eye2web.co.kr";
    private Eye2WebContent eye2WebContent;
    private List<Eye2WebContent> eye2WebContentList;
    private Eye2WebMediaContent eye2WebMediaContent;
    private List<Eye2WebJson> eye2WebJsonList;

    private Retrofit client = new Retrofit.Builder().baseUrl(eye2webHost)
            .addConverterFactory(GsonConverterFactory.create()).build();

    public List<Eye2WebContent> getEye2WebContent(int category, int page, int per_page) {

        try {
            Eye2WebContent.Eye2WebContentInterface service = client.create(Eye2WebContent.Eye2WebContentInterface.class);
            Call<List<Eye2WebContent>> call = service.get_content_list(category, page, per_page);

            eye2WebContentList = call.execute().body();
        } catch(Exception e) {
            Log.e("Error", "Eye2Web Content Api Call Error : " + e.toString());
        }

        return eye2WebContentList;
    }

    public Eye2WebContent getEye2WebContentDetail(int id) {

        try {
            Eye2WebContent.Eye2WebContentDetailInterface service = client.create(Eye2WebContent.Eye2WebContentDetailInterface.class);
            Call<Eye2WebContent> call = service.get_content_detail(id);

            eye2WebContent = call.execute().body();
        } catch(Exception e) {
            Log.e("Error", "Eye2Web Content Api Call Error : " + e.toString());
        }

        return eye2WebContent;
    }

    public Eye2WebMediaContent getEye2WebMediaContent(int id) {

        try {
            Eye2WebMediaContent.Eye2WebMediaInterface service = client.create(Eye2WebMediaContent.Eye2WebMediaInterface.class);
            Call<Eye2WebMediaContent> call = service.get_media_url(id);

            eye2WebMediaContent = call.execute().body();
        } catch (Exception e) {
            Log.e("Error", "Eye2Web Media Content Api Call Error : " + e.toString());
        }

        return eye2WebMediaContent;
    }

    public List<Eye2WebJson> getEye2WebJsonData(int category, String auth_key, int page, int per_page) {

        try {
            Eye2WebJson.Eye2WebJsonInterface service = client.create(Eye2WebJson.Eye2WebJsonInterface.class);
            Call<List<Eye2WebJson>> call = service.get_json_data(category, auth_key, page, per_page);

            eye2WebJsonList = call.execute().body();
        } catch(Exception e) {
            Log.e("Error", "Eye2Web Json Data Api Call Error : " + e.toString());
        }

        return eye2WebJsonList;
    }
}
