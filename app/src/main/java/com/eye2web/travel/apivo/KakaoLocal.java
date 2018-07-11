package com.eye2web.travel.apivo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class KakaoLocal implements Serializable {

    private static final long serialVersionUID = 1073685575204532192L;

    @SerializedName("meta")
    Meta meta;

    @SerializedName("documents")
    List<Documents> documents;

    public class Meta implements Serializable {

        private static final long serialVersionUID = 1011325580358583246L;

        @SerializedName("total_count") int total_count;
        @SerializedName("pageable_count") int pageable_count;
        @SerializedName("is_end") boolean is_end;
        @SerializedName("same_name") SameName sameName;

        public int getTotal_count() { return total_count; }
        public int getPageable_count() { return pageable_count; }
        public boolean getIs_end() { return is_end; }
        public SameName getSameName() { return sameName; }
    }

    public class SameName implements Serializable {

        private static final long serialVersionUID = -6352807996467575343L;

        @SerializedName("region") List<String> region;
        @SerializedName("keyword") String keyword;
        @SerializedName("selected_region") String selected_region;

        public List<String> getRegion() { return region; }
        public String getKeyword() { return keyword; }
        public String getSelected_region() { return selected_region; }
    }

    public class Documents implements Serializable {

        private static final long serialVersionUID = 7024000909921896698L;

        @SerializedName("id") String id;
        @SerializedName("place_name") String place_name;
        @SerializedName("category_name") String category_name;
        @SerializedName("category_group_code") String category_group_code;
        @SerializedName("category_group_name") String category_group_name;
        @SerializedName("phone") String phone;
        @SerializedName("address_name") String address_name;
        @SerializedName("road_address_name") String road_address_name;
        @SerializedName("x") String x;
        @SerializedName("y") String y;
        @SerializedName("place_url") String place_url;
        @SerializedName("distance") String distance;

        public String getId() { return id; }
        public String getPlace_name() { return place_name; }
        public String getCategory_name() { return category_name; }
        public String getCategory_group_code() { return category_group_code; }
        public String getCategory_group_name() { return category_group_name; }
        public String getPhone() { return phone; }
        public String getAddress_name() { return address_name; }
        public String getRoad_address_name() { return road_address_name; }
        public String getX() { return x; }
        public String getY() { return y; }
        public String getPlace_url() { return place_url; }
        public String getDistance() { return distance; }
    }

    public Meta getMeta() { return meta; }

    public List<Documents> getDocuments() { return documents; }

    public interface KakaoLocalInterface {
        @Headers({"Authorization: KakaoAK aedd18491b704b456ffa5acf47e8fef7"})
        @GET("/v2/local/search/keyword.json")
        Call<KakaoLocal> get_kakao_local_search(
                @Query("query") String query
                , @Query("category_group_code") String category_group_code
                , @Query("x") String x
                , @Query("y") String y
                , @Query("radius") int radius
                , @Query("rect") String rect
                , @Query("page") int page
                , @Query("size") int size
                , @Query("sort") String sort);
    }
}
