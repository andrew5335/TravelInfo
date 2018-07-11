package com.eye2web.travel.apivo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class OpenWeatherMap implements Serializable {

    private static final long serialVersionUID = 6564208343452272829L;

    @SerializedName("coord") Coord coord;

    @SerializedName("weather") List<Weather> weather;

    @SerializedName("main") Main main;

    @SerializedName("wind") Wind wind;

    @SerializedName("clouds") Clouds clouds;

    @SerializedName("sys") Sys sys;

    @SerializedName("base") String base;

    @SerializedName("dt") String dt;

    @SerializedName("id") String id;

    @SerializedName("name") String name;

    @SerializedName("cod") String cod;

    public class Coord implements Serializable {

        private static final long serialVersionUID = -8996205467403872375L;

        @SerializedName("lon") String lon;
        @SerializedName("lat") String lat;

        public String getLon() { return lon; }
        public String getLat() { return lat; }
    }

    public class Weather implements Serializable {

        private static final long serialVersionUID = 7386935096658575248L;

        @SerializedName("id") String id;
        @SerializedName("main") String main;
        @SerializedName("description") String description;
        @SerializedName("icon") String icon;

        public String getId() { return id; }
        public String getMain() { return main; }
        public String getDescription() { return description; }
        public String getIcon() { return icon; }
    }

    public class Main implements Serializable {

        private static final long serialVersionUID = -1935902749363056912L;

        @SerializedName("temp") String temp;
        @SerializedName("pressure") String pressure;
        @SerializedName("humidity") String humidity;
        @SerializedName("temp_min") String temp_min;
        @SerializedName("temp_max") String temp_max;

        public String getTemp() { return temp; }
        public String getPressure() { return pressure; }
        public String getHumidity() { return humidity; }
        public String getTemp_min() { return temp_min; }
        public String getTemp_max() { return temp_max; }

    }

    public class Wind implements Serializable {

        private static final long serialVersionUID = -3711383331260229042L;

        @SerializedName("speed") String speed;
        @SerializedName("deg") String deg;

        public String getSpeed() { return speed; }
        public String getDeg() { return deg; }
    }

    public class Clouds implements Serializable {

        private static final long serialVersionUID = 7382748435198580535L;

        @SerializedName("all") int all;

        public int getAll() { return all; }
    }

    public class Sys implements Serializable {

        private static final long serialVersionUID = 7094198964175858997L;

        @SerializedName("type") String type;
        @SerializedName("id") String id;
        @SerializedName("message") String message;
        @SerializedName("country") String country;
        @SerializedName("sunrise") String sunrise;
        @SerializedName("sunset") String sunset;

        public String getType() { return type; }
        public String getId() { return id; }
        public String getMessage() { return message; }
        public String getCountry() { return country; }
        public String getSunrise() { return sunrise; }
        public String getSunset() { return sunset; }
    }

    public Coord getCoord() { return coord; }

    public List<Weather> getWeather() { return weather; }

    public Main getMain() { return main; }

    public Wind getWind() { return wind; }

    public Clouds getClouds() { return clouds; }

    public Sys getSys() { return sys; }

    public String getBase() { return base; }

    public String getDt() { return dt; }

    public String getId() { return id; }

    public String getName() { return name; }

    public String getCod() { return cod; }

    public interface WeatherMapInterface {
        @GET("/data/2.5/weather")
        Call<OpenWeatherMap> get_weather_info(
                @Query("lat") String lat
                , @Query("lon") String lon
                , @Query("appid") String appId
                , @Query("units") String units
                , @Query("lang") String lang);
    }

    public interface WeatherMapInterface2 {
        @GET("/data/2.5/weather")
        Call<OpenWeatherMap> get_weather_info(
                @Query("q") String q
                , @Query("appid") String appid
                , @Query("units") String units
                , @Query("lang") String lang);
    }
}
