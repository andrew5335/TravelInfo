package com.eye2web.travel.service;

import android.util.Log;

import com.eye2web.travel.apivo.KakaoBlog;
import com.eye2web.travel.apivo.KakaoCafe;
import com.eye2web.travel.apivo.KakaoImage;
import com.eye2web.travel.apivo.KakaoLocal;
import com.eye2web.travel.apivo.KakaoVideo;
import com.eye2web.travel.apivo.KakaoWebDoc;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @File : KakaoApiService
 * @Date : 2018. 7. 5. PM 5:15
 * @Author : Andrew Kim
 * @Version : 1.0.0
 * @Description : 카카오 api 연동 서비스
**/
public class KakaoApiService {

    private static String kakaoApiHost = "https://dapi.kakao.com";
    private String kakaoHost = "https://dapi.kakao.com";

    private KakaoImage kakaoImage;
    private KakaoBlog kakaoBlog;
    //private KakaoBook kakaoBook;
    private KakaoCafe kakaoCafe;
    //private KakaoTip kakaoTip;
    private KakaoVideo kakaoVideo;
    private KakaoWebDoc kakaoWebDoc;
    private KakaoLocal kakaoLocal;

    private Retrofit client = new Retrofit.Builder().baseUrl(kakaoHost)
            .addConverterFactory(GsonConverterFactory.create()).build();

    /**
     * @parameter : page : 페이지 번호
     *              size : 한 페이지에 보여줄 컨텐츠 수
     *              searchKeyWord : 검색어
     *              sort : 정렬방식 - accuracy : 정확도순 / recency : 최신순
     * @Date : 2018. 7. 3. PM 1:57
     * @Author : Andrew Kim
     * @Description : 카카오 블로그 api 호출 결과를 가져온다.
    **/
    public KakaoBlog getKakaoBlog(int page, int size, String searchKeyWord, String sort) {

        try {
            KakaoBlog.KakaoBlogInterface service = client.create(KakaoBlog.KakaoBlogInterface.class);
            Call<KakaoBlog> call = service.get_kakao_blog(searchKeyWord, sort, page, size);

            kakaoBlog = call.execute().body();
            /**
            call.enqueue(new Callback<KakaoBlog>() {
                @Override
                public void onResponse(Call<KakaoBlog> call, Response<KakaoBlog> response) {
                    if(response.isSuccessful()) {
                        kakaoBlog = response.body();
                    }
                }

                @Override
                public void onFailure(Call<KakaoBlog> call, Throwable t) {
                    Log.e("Error", "Kakao Blog Api Call Error : " + t.getMessage().toString());
                }
            });
             **/
        } catch (Exception e) {
            Log.e("Error", "Kakao Blog Api Call Error : " + e.toString());
        }

        return kakaoBlog;
    }

    /**
     * @parameter : page : 페이지 번호
     *              size : 한 페이지에 보여줄 컨텐츠 수
     *              searchKeyWord : 검색어
     *              sort : 정렬방식 - accuracy : 정확도순 / recency : 최신순
     * @Date : 2018. 7. 3. PM 4:00
     * @Author : Andrew Kim
     * @Description : 카카오 이미지 api 호출 결과를 가져온다.
    **/
    public KakaoImage getKakaoImage(int page, int size, String searchKeyWord, String sort) {

        try {
            KakaoImage.KakaoImageInterface service = client.create(KakaoImage.KakaoImageInterface.class);
            Call<KakaoImage> call = service.get_kakao_image(searchKeyWord, sort, page, size);

            kakaoImage = call.execute().body();
            /**
            call.enqueue(new Callback<KakaoImage>() {
                @Override
                public void onResponse(Call<KakaoImage> call, Response<KakaoImage> response) {
                    if(response.isSuccessful()) {
                        kakaoImage = response.body();
                        Log.i("Info", "Kakao Image : " + kakaoImage.getDocuments().get(0).getImage_url() );
                    }
                }

                @Override
                public void onFailure(Call<KakaoImage> call, Throwable t) {
                    Log.e("Error", "Kakao Image Api Call Error : " + t.getMessage().toString());
                }
            });
             **/
        } catch (Exception e) {
            Log.e("Error", "Kakao Image Api Call Error : " + e.toString());
        }

        return kakaoImage;
    }

    /**
     * @parameter : page : 페이지 번호
     *              size : 한 페이지에 보여줄 컨텐츠 수
     *              searchKeyWord : 검색어
     *              sort : 정렬방식 - accuracy : 정확도순 / recency : 최신순
     * @Date : 2018. 7. 3. PM 4:02
     * @Author : Andrew Kim
     * @Description : 카카오 카페 api 호출 결과를 가져온다.
    **/
    public KakaoCafe getKakaoCafe(int page, int size, String searchKeyWord, String sort) {

        try {
            KakaoCafe.KakaoCafeInterface service = client.create(KakaoCafe.KakaoCafeInterface.class);
            Call<KakaoCafe> call = service.get_kakao_cafe(searchKeyWord, sort, page, size);

            kakaoCafe = call.execute().body();
            /**
            call.enqueue(new Callback<KakaoCafe>() {
                @Override
                public void onResponse(Call<KakaoCafe> call, Response<KakaoCafe> response) {
                    if(response.isSuccessful()) {
                        kakaoCafe = response.body();
                    }
                }

                @Override
                public void onFailure(Call<KakaoCafe> call, Throwable t) {
                    Log.e("Error", "Kakao Cafe Api Call Error : " + t.getMessage().toString());
                }
            });
             **/
        } catch (Exception e) {
            Log.e("Error", "Kakao Cafe Api Call Error : " + e.toString());
        }

        return kakaoCafe;
    }

    /**
     * @parameter : page : 페이지 번호
     *              size : 한 페이지에 보여줄 컨텐츠 수
     *              searchKeyWord : 검색어
     *              sort : 정렬방식 - accuracy : 정확도순 / recency : 최신순
     * @Date : 2018. 7. 3. PM 4:03
     * @Author : Andrew Kim
     * @Description : 카카오 비디오 api 호출 결과를 가져온다.
    **/
    public KakaoVideo getKakaoVideo(int page, int size, String searchKeyWord, String sort) {

        try {
            KakaoVideo.KakaoVideoInterface service = client.create(KakaoVideo.KakaoVideoInterface.class);
            Call<KakaoVideo> call = service.get_kakao_video(searchKeyWord, sort, page, size);

            kakaoVideo = call.execute().body();
            /**
            call.enqueue(new Callback<KakaoVideo>() {
                @Override
                public void onResponse(Call<KakaoVideo> call, Response<KakaoVideo> response) {
                    if(response.isSuccessful()) {
                        kakaoVideo = response.body();
                    }
                }

                @Override
                public void onFailure(Call<KakaoVideo> call, Throwable t) {
                    Log.e("Error", "Kakao Video Api Call Error : " + t.getMessage().toString());
                }
            });
             **/
        } catch (Exception e) {
            Log.e("Error", "Kakao Video Api Call Error : " + e.toString());
        }

        return kakaoVideo;
    }

    /**
     * @parameter : page : 페이지 번호
     *              size : 한 페이지에 보여줄 컨텐츠 수
     *              searchKeyWord : 검색어
     *              sort : 정렬방식 - accuracy : 정확도순 / recency : 최신순
     * @Date : 2018. 7. 3. PM 4:03
     * @Author : Andrew Kim
     * @Description : 카카오 웹 문서 api 호출 결과를 가져온다.
    **/
    public KakaoWebDoc getKakaoWebDoc(int page, int size, String searchKeyWord, String sort) {

        try {
            KakaoWebDoc.KakaoWebDocInterface service = client.create(KakaoWebDoc.KakaoWebDocInterface.class);
            Call<KakaoWebDoc> call = service.get_kakao_webdoc(searchKeyWord, sort, page, size);

            kakaoWebDoc = call.execute().body();
            /**
            call.enqueue(new Callback<KakaoWebDoc>() {
                @Override
                public void onResponse(Call<KakaoWebDoc> call, Response<KakaoWebDoc> response) {
                    if(response.isSuccessful()) {
                        kakaoWebDoc = response.body();
                    }
                }

                @Override
                public void onFailure(Call<KakaoWebDoc> call, Throwable t) {
                    Log.e("Error", "Kakao WebDoc Api Call Error : " + t.getMessage().toString());
                }
            });
             **/
        } catch (Exception e) {
            Log.e("Error", "Kakao WebDoc Api Call Error : " + e.toString());
        }

        return kakaoWebDoc;
    }

    /**
     * @parameter : page : 페이지 번호
     *              size : 한 페이지에 보여줄 컨텐츠 수
     *              searchKeyWord : 검색어
     *              sort : 정렬방식 - accuracy : 정확도순 / recency : 최신순
     *              categoryGroupCode : 카테고리 그룹 코드
     *              x : 중심좌표의 X값 (longitude)
     *              y : 중심좌표의 Y값 (latitude)
     *              radius : 중심좌표기준의 검색 반경 ( meter 기준 20000 이내)
     *              rect : 사각형 범위내 제한 검색을 위한 좌표값
     * @Date : 2018. 7. 4. AM 10:55
     * @Author : Andrew Kim
     * @Description :
    **/
    public KakaoLocal getKakaoLocal(int page, int size, String searchKeyWord, String sort
            , String categoryGroupCode, String x, String y, int radius, String rect) {

        try {
            KakaoLocal.KakaoLocalInterface service = client.create(KakaoLocal.KakaoLocalInterface.class);
            Call<KakaoLocal> call = service.get_kakao_local_search(searchKeyWord, categoryGroupCode, x, y, radius, rect, page, size, sort);

            kakaoLocal = call.execute().body();
        } catch (Exception e) {
            Log.e("Error", "Kakao Local Search Api Call Error : " + e.toString());
        }

        return kakaoLocal;
    }

    public static void main(String[] args) {
        try {
            Retrofit client = new Retrofit.Builder().baseUrl(kakaoApiHost)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            KakaoImage.KakaoImageInterface service = client.create(KakaoImage.KakaoImageInterface.class);
            KakaoLocal.KakaoLocalInterface service2 = client.create(KakaoLocal.KakaoLocalInterface.class);

            Call<KakaoImage> call = service.get_kakao_image("하나조노", "accuracy", 1, 10);
            Call<KakaoLocal> call2 = service2.get_kakao_local_search("맛집", ""
                    , "127.02751159667969",  "37.50479507446289", 3000, "", 1, 10, "distance");

            call.enqueue(new Callback<KakaoImage>() {
                @Override
                public void onResponse(Call<KakaoImage> call, Response<KakaoImage> response) {
                    if(response.isSuccessful()) {
                        KakaoImage image = response.body();
                        List<KakaoImage.Documents> imageList = image.getDocuments();
                        if(null != imageList && 0 < imageList.size()) {
                            for(int i=0; i < imageList.size(); i++) {
                                System.out.println("image : " + imageList.get(i).getImage_url());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<KakaoImage> call, Throwable t) {

                }
            });

            call2.enqueue(new Callback<KakaoLocal>() {
                @Override
                public void onResponse(Call<KakaoLocal> call, Response<KakaoLocal> response) {
                    if(response.isSuccessful()) {
                        KakaoLocal local = response.body();
                        System.out.println("response : " + response.raw());
                        System.out.println("result : " + local.getMeta().getTotal_count());
                        System.out.println("result : " + local.getMeta().getPageable_count());
                        System.out.println("result : " + local.getMeta().getIs_end());

                        List<KakaoLocal.Documents> itemList = new ArrayList<KakaoLocal.Documents>();
                        itemList = local.getDocuments();

                        if(null != itemList && 0 < itemList.size()) {
                            for(int i=0; i < itemList.size(); i++) {
                                System.out.println(itemList.get(i).getPlace_name());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<KakaoLocal> call, Throwable t) {
                    System.out.println("Fail : " + t.getMessage());
                }
            });
        } catch(Exception e) {
            e.toString();
        }
    }
}
