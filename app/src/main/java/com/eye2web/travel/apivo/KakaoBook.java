package com.eye2web.travel.apivo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class KakaoBook implements Serializable {

    private static final long serialVersionUID = -8976503949160927060L;

    @SerializedName("meta")
    Meta meta;

    @SerializedName("documents")
    List<Documents> documents;

    public class Meta implements Serializable {

        private static final long serialVersionUID = -7649842065848033679L;

        @SerializedName("total_count") int total_count;
        @SerializedName("pageable_count") int pageable_count;
        @SerializedName("is_end") boolean is_end;

        public int getTotal_count() { return total_count; }
        public int getPageable_count() { return pageable_count; }
        public boolean getIs_end() { return is_end; }
    }

    public class Documents implements Serializable {

        private static final long serialVersionUID = 4599323836475343572L;

        @SerializedName("title") String title;
        @SerializedName("contents") String contents;
        @SerializedName("url") String url;
        @SerializedName("isbn") String isbn;
        @SerializedName("datetime") String datetime;
        @SerializedName("authors") List<String> authors;
        @SerializedName("publisher") String publisher;
        @SerializedName("translators") List<String> translators;
        @SerializedName("price") int price;
        @SerializedName("sale_price") int sale_price;
        @SerializedName("sale_yn") String sale_yn;
        @SerializedName("category") String category;
        @SerializedName("thumbnail") String thumbnail;
        @SerializedName("barcode") String barcode;
        @SerializedName("ebook_barcode") String ebook_barcode;
        @SerializedName("status") String status;

        public String getTitle() { return title; }
        public String getContents() { return contents; }
        public String getUrl() { return url; }
        public String getIsbn() { return isbn; }
        public String getDatetime() { return datetime; }
        public List<String> getAuthors() { return authors; }
        public String getPublisher() { return publisher; }
        public List<String> getTranslators() { return translators; }
        public int getPrice() { return price; }
        public int getSale_price() { return sale_price; }
        public String getSale_yn() { return sale_yn; }
        public String getCategory() { return category; }
        public String getThumbnail() { return thumbnail; }
        public String getBarcode() { return barcode; }
        public String getEbook_barcode() { return ebook_barcode; }
        public String getStatus() { return status; }
    }

    public interface KakaoBookInterface {
        @Headers({"Authorization: KakaoAK aedd18491b704b456ffa5acf47e8fef7"})
        @GET("/v2/search/book")
        Call<KakaoBook> get_kakao_book(
                @Query("query") String query
                , @Query("sort") String sort
                , @Query("page") int page
                , @Query("size") int size
                , @Query("target") String target
                , @Query("category") String category);
    }
}
