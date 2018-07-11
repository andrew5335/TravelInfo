package com.eye2web.travel.service;

import android.util.Log;

import com.eye2web.travel.apivo.OpenWeatherMap;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @File : WeatherApiService
 * @Date : 2018. 7. 5. PM 5:15
 * @Author : Andrew Kim
 * @Version : 1.0.0
 * @Description : 날씨정보 확인을 위한 open weather map api 연동 서비스
**/
public class WeatherApiService {

    private String openWeatherMapHost = "http://api.openweathermap.org";

    private OpenWeatherMap openWeatherMap;

    private Retrofit client = new Retrofit.Builder().baseUrl(openWeatherMapHost)
            .addConverterFactory(GsonConverterFactory.create()).build();

    /**
     * @parameter : lat - 위도
     *              lng - 경도
     *              appKey - api 연동을 위한 키값
     *              units - 결과값 유형 (metric - 섭씨로 표시)
     *              lang - 사용 언어 설정 (kr 세팅 시 한국어로 결과값 return)
     *              cityGu - 도시 구분 코드
     *                       일부 도시의 경우 도시의 위도/경도 값으로 날씨정보 확인 불가하여
     *                       해당 지역의 경우 기준이 되는 도시 세팅이 필요
     * @Date : 2018. 7. 5. PM 5:16
     * @Author : Andrew Kim
     * @Description :
    **/
    public OpenWeatherMap getWeatherInfo(String lat, String lng, String appKey, String units, String lang, String cityGu) {

        try {

            // 도시 구분값이 있을 경우 도시 구분값을 확인하여 특정 도시의 경우 날씨 정보 확인을 위한 기준 도시 설정
            // 이 경우 사용하는 query parameter 형태가 달라짐.
            // 위도/경도로 확인이 가능한 경우에는 lat, lng 값으로 날씨정보 확인
            // 위도/경도로 확인이 불가능한 경우에는 별도로 설정된 도시명으로 날씨정보 확인
            if(null != cityGu && !"".equalsIgnoreCase(cityGu)) {
                if("32".equalsIgnoreCase(cityGu) || "34".equalsIgnoreCase(cityGu)
                        || "35".equalsIgnoreCase(cityGu) || "36".equalsIgnoreCase(cityGu)) {
                    String city = "";

                    switch (cityGu) {
                        case "32" :
                            city = "wonju";
                            break;

                        case "34" :
                            city = "gongju";
                            break;

                        case "35" :
                            city = "daegu";
                            break;

                        case "36" :
                            city = "changwon";
                            break;
                    }

                    // 도시명으로 날씨정보 확인을 진행하기 위한 인터페이스 세팅
                    OpenWeatherMap.WeatherMapInterface2 service = client.create(OpenWeatherMap.WeatherMapInterface2.class);
                    Call<OpenWeatherMap> call = service.get_weather_info(city + ",KR", appKey, units, lang);

                    openWeatherMap = call.execute().body();
                } else {
                    // 위도/경도로 날씨정보 확인을 진행하기 위한 인터페이스 세팅
                    OpenWeatherMap.WeatherMapInterface service = client.create(OpenWeatherMap.WeatherMapInterface.class);
                    Call<OpenWeatherMap> call = service.get_weather_info(lat, lng, appKey, units, lang);

                    openWeatherMap = call.execute().body();
                }
            } else {
                // 도시 구분값이 없을 경우에는 위도/경도로 날씨정보 확인을 진행
                OpenWeatherMap.WeatherMapInterface service = client.create(OpenWeatherMap.WeatherMapInterface.class);
                Call<OpenWeatherMap> call = service.get_weather_info(lat, lng, appKey, units, lang);

                openWeatherMap = call.execute().body();
            }


        } catch(Exception e) {
            Log.e("Error", "Open Weather Map Api Call Error : " + e.toString());
        }

        return openWeatherMap;
    }
}
