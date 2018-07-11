package com.eye2web.travel.service;

import com.eye2web.travel.apivo.NaverLocalSearch;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NaverApiService {

    public static void main(String[] args) {
        String naverHost = "https://openapi.naver.com";
        int start = 1;

        Random rand = new Random();
        start = rand.nextInt(50) + 1;

        try {
            Retrofit client = new Retrofit.Builder().baseUrl(naverHost)
                    .addConverterFactory(GsonConverterFactory.create()).build();

            NaverLocalSearch.NaverLocalSearchInterface service = client.create(NaverLocalSearch.NaverLocalSearchInterface.class);
            Call<NaverLocalSearch> call = service.get_local_info("서울맛집", 10, start, "random");

            call.enqueue(new Callback<NaverLocalSearch>() {
                @Override
                public void onResponse(Call<NaverLocalSearch> call, Response<NaverLocalSearch> response) {
                    if(response.isSuccessful()) {
                        NaverLocalSearch local = response.body();

                        if(null != local) {
                            List<NaverLocalSearch.Items> itemsList = local.getItemsList();

                            if(null != itemsList && 0 < itemsList.size()) {
                                for(int i=0; i < itemsList.size(); i++) {
                                    System.out.println("Info : " + itemsList.get(i).getTitle());
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<NaverLocalSearch> call, Throwable t) {

                }
            });

        } catch (Exception e ) {

        }
    }
}
