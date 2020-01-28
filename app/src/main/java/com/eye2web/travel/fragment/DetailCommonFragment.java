package com.eye2web.travel.fragment;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.eye2web.travel.R;
import com.eye2web.travel.ReviewWebViewActivity;
import com.eye2web.travel.adapter.DetailImageViewPagerAdapter;
import com.eye2web.travel.adapter.KakaoReviewListAdapter;
import com.eye2web.travel.apivo.KakaoBlog;
import com.eye2web.travel.apivo.KakaoImage;
import com.eye2web.travel.util.CommonUtil;
import com.eye2web.travel.vo.DetailCommonItem;
import com.eye2web.travel.vo.ListItem;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailCommonFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailCommonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailCommonFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View view;
    private DetailCommonItem detailCommonItem;
    private ListItem item;
    private CommonUtil commonUtil;
    //private String googlePhotoUrl;
    //private String googleKey;

    private ViewPager imageViewPager;
    private ViewPager detailImageViewPager;
    private DetailImageViewPagerAdapter detailImageViewPagerAdapter;
    private ListView reviewList;
    //private GoogleListAdapter googleReviewListAdapter;
    private LinearLayout reviewListLayout;

    private TabLayout tabLayout1;
    private TabLayout tabLayout2;

    private MapView mapView;
    private double mapx = 0;
    private double mapy = 0;
    private String title = "";
    private String addr1 = "";
    private String addr2 = "";
    private String googleAddr = "";
    private String mapAddr = "";
    private String contentTypeId = "";
    private UiSettings uiSettings;
    private FragmentManager parentFragmentManager;

    private Button button01;
    private Button button02;
    private Button button03;
    private Button button04;

    private AdView mAdView1;    // ad banner

    private KakaoImage kakaoImage;
    private KakaoBlog kakaoBlog;
    private KakaoReviewListAdapter kakaoReviewListAdapter;

    public DetailCommonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailCommonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailCommonFragment newInstance(String param1, String param2) {
        DetailCommonFragment fragment = new DetailCommonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
     **/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view != null) {
            ViewGroup viewGroupParent = (ViewGroup) view.getParent();
            if(viewGroupParent != null) {
                viewGroupParent.removeView(view);
            }
        }

        // view 세팅
        view = inflater.inflate(R.layout.fragment_detail_common, container, false);

        String admobId = getResources().getString(R.string.google_admob_id);
        MobileAds.initialize(getContext(), admobId);    // admob 초기화

        mAdView1 = view.findViewById(R.id.adView);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mAdView1.loadAd(adRequest1);

        // data 가져오기 (bundle에 들어있는 detailCommonItem 객체 가져오기)
        Bundle bundle = getArguments();
        detailCommonItem = (DetailCommonItem) bundle.getSerializable("detailCommonItem");
        item = (ListItem) bundle.getSerializable("item");

        LinearLayout addressLayout = (LinearLayout) view.findViewById(R.id.address_layout);

        LinearLayout detailImageViewPagerLayout = view.findViewById(R.id.detailImageViewPagerLayout);
        detailImageViewPagerLayout.requestFocus();

        tabLayout1 = (TabLayout) view.findViewById(R.id.indicator_tab);
        tabLayout2 = (TabLayout) view.findViewById(R.id.indicator_tab2);

        // 구글 이미지 노출을 위한 구글 이미지 api 정보 세팅
        //googlePhotoUrl = view.getResources().getString(R.string.google_places_api_photo_url);
        //googleKey = view.getResources().getString(R.string.google_maps_key);
        //googlePhotoUrl = googlePhotoUrl + "?key=" + googleKey;

        // 구글 정보를 담기 위한 객체 생성
        //GooglePlaceDetailItem googleItem = new GooglePlaceDetailItem();
        //List<GooglePlaceDetailPhoto> googlePhotoList = new ArrayList<GooglePlaceDetailPhoto>();    // 구글 이미지 리스트 객체 생성
        //List<GooglePlaceDetailReviews> googleReviewList = new ArrayList<GooglePlaceDetailReviews>();    // 구글 리뷰 리스트 객체 생성

        // 각 데이터값을 담을 객체 생성
        String overView = "";
        String homepage = "";
        String phoneNo = "";
        String firstImage = "";
        List<String> imgUrlList = new ArrayList<String>();
        //String firstImage2 = "";

        // detailCommonItem 객체에서 필요한 값 가져오기
        title = (String) detailCommonItem.getTitle();
        overView = detailCommonItem.getOverview();
        homepage = detailCommonItem.getHomepage();
        addr1 = detailCommonItem.getAddr1();
        addr2 = detailCommonItem.getAddr2();
        if(null != addr2 && !"".equalsIgnoreCase(addr2) && 0 < addr2.length()) {
            mapAddr = addr1 + " " + addr2;
        } else {
            mapAddr = addr1;
        }
        phoneNo = detailCommonItem.getTel();
        imgUrlList = (List<String>) detailCommonItem.getImgUrlList();
        firstImage = detailCommonItem.getFirstimage();

        // 지도 표시를 위한 위도, 경도 값 가져오기
        mapx = detailCommonItem.getMapx();
        mapy = detailCommonItem.getMapy();
        Log.i("Info", "Map info : " + mapx + " " + mapy);

        // 지도의 마커 및 주소란에 표시될 주소 정보
        //mapAddr = detailCommonItem.getAddr1() + " " + detailCommonItem.getAddr2();
        Log.i("Info", "Map Addr : " + mapAddr);

        // 어떤 유형의 컨텐츠인지 구분값 정보 (ex. 관광, 숙박 등)
        contentTypeId = detailCommonItem.getContenttypeid();

        //contentTypeId값에 따라 노출되는 버튼 이미지 다르게...
        button01 = view.findViewById(R.id.detailBtn01);
        button02 = view.findViewById(R.id.detailBtn02);
        button03 = view.findViewById(R.id.detailBtn03);
        button04 = view.findViewById(R.id.detailBtn04);

        switch (contentTypeId) {
            case "12" :
                // 관광인 경우
                button01.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.detail_hotel), null, null);
                button02.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.detail_food), null, null);
                button03.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.detail_shopping), null, null);
                button04.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.detail_festival), null, null);
                button01.setText("호텔");
                button02.setText("맛집");
                button03.setText("쇼핑");
                button04.setText("축제/공연");
                break;

            case "32" :
                // 호텔인 경우
                button01.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.detail_travel), null, null);
                button02.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.detail_food), null, null);
                button03.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.detail_shopping), null, null);
                button04.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.detail_festival), null, null);
                button01.setText("관광");
                button02.setText("맛집");
                button03.setText("쇼핑");
                button04.setText("축제/공연");
                break;

            case "39" :
                // 맛집인 경우
                button01.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.detail_travel), null, null);
                button02.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.detail_hotel), null, null);
                button03.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.detail_shopping), null, null);
                button04.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.detail_festival), null, null);
                button01.setText("관광");
                button02.setText("호텔");
                button03.setText("쇼핑");
                button04.setText("축제/공연");
                break;

            case "15" :
                // 축제인 경우
                button01.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.detail_travel), null, null);
                button02.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.detail_hotel), null, null);
                button03.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.detail_shopping), null, null);
                button04.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.detail_culture), null, null);
                button01.setText("관광");
                button02.setText("호텔");
                button03.setText("쇼핑");
                button04.setText("문화시설");
                break;

            default :
                button01.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.detail_travel), null, null);
                button02.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.detail_hotel), null, null);
                button03.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.detail_shopping), null, null);
                button04.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.detail_festival), null, null);
                button01.setText("관광");
                button02.setText("호텔");
                button03.setText("쇼핑");
                button04.setText("축제/공연");
                break;
        }

        // 구글 데이터 가져오기
        //googleItem = (GooglePlaceDetailItem) detailCommonItem.getGooglePlaceDetailItem();

        // 카카오 데이터 가져오기
        kakaoImage = detailCommonItem.getKakaoImage();
        kakaoBlog = detailCommonItem.getKakaoBlog();

        imageViewPager = (ViewPager) view.findViewById(R.id.info_img_viewpager);    // 구글에서 가져온 이미지를 표시하기 위한 viewpager 세팅
        reviewList = (ListView) view.findViewById(R.id.reviewList);
        reviewListLayout = (LinearLayout) view.findViewById(R.id.google_review_layout);

        if(null != kakaoImage) {
            List<KakaoImage.Documents> kakaoImageList = kakaoImage.getDocuments();

            // 카카오 이미지 리스트가 있을 경우 이미지 뷰에 표시
            if(null != kakaoImageList && 0 < kakaoImageList.size()) {
                List<String> bitmapPhotoList = new ArrayList<String>();

                for(int i=0; i < kakaoImageList.size(); i++) {
                    bitmapPhotoList.add(kakaoImageList.get(i).getImage_url());
                }

                detailImageViewPagerAdapter = new DetailImageViewPagerAdapter(getContext(), inflater, bitmapPhotoList, "");
                imageViewPager.setAdapter(detailImageViewPagerAdapter);
                tabLayout2.setupWithViewPager(imageViewPager, true);
            } else {
                imageViewPager.setVisibility(View.GONE);
            }
        } else {
            imageViewPager.setVisibility(View.GONE);
        }

        if(null != kakaoBlog) {
            List<KakaoBlog.Documents> kakaoReviewList = new ArrayList<KakaoBlog.Documents>();
            kakaoReviewList = kakaoBlog.getDocuments();

            if(null != kakaoReviewList && 0 < kakaoReviewList.size()) {
                kakaoReviewListAdapter = new KakaoReviewListAdapter(getContext(), R.layout.kakao_review_item, kakaoReviewList);
                reviewList.setAdapter(kakaoReviewListAdapter);
                reviewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        KakaoBlog.Documents doc = (KakaoBlog.Documents) parent.getItemAtPosition(position);
                        //Log.i("INFO", "info : " + doc);
                        Intent reviewIntent = new Intent(getContext(), ReviewWebViewActivity.class);
                        reviewIntent.putExtra("reviewUrl", doc.getUrl());
                        reviewIntent.putExtra("reviewTitle", doc.getTitle());

                        startActivity(reviewIntent);
                    }
                });
            } else {
                reviewList.setVisibility(View.GONE);
            }
        } else {
            reviewList.setVisibility(View.GONE);
        }

        // 구글 데이터가 있을 경우 구글 데이터에서 이미지 리스트와 리뷰 리스트 가져오기
        /**
        if(null != googleItem) {
            googlePhotoList = (List<GooglePlaceDetailPhoto>) googleItem.getPhotoList();
            googleReviewList = (List<GooglePlaceDetailReviews>) googleItem.getReviewsList();

            // 구글 데이터가 있을 경우 주소값을 정부 데이터의 주소가 아닌 구글의 주소로 대체 처리
            googleAddr = googleItem.getFormattedAddress();
            mapAddr = googleAddr;

            //phoneNo = googleItem.getInternationalPhoneNumber();
            phoneNo = googleItem.getFormattedPhoneNumber();

            // 구글 이미지 리스트가 있을 경우 이미지 표시에 필요한 photo_reference값 가져와 구글 이미지 api 주소와 결합하여 이미지 노출용 리스트 생성
            if(null != googlePhotoList && 0 < googlePhotoList.size()) {
                List<String> bitmapPhotoList = new ArrayList<String>();

                // google 정책 변경에 따라 구글 포토 호출 수 3으로 제한
                int photoSize = 0;
                if(3 < googlePhotoList.size()) {
                    photoSize = 3;
                }

                for(int i=0; i < photoSize; i++) {
                    String photo = "";
                    photo = googlePhotoUrl + "&maxwidth=400&photoreference=" + googlePhotoList.get(i).getPhotoReference();
                    bitmapPhotoList.add(photo);
                }

                // 이미지 노출용 리스트가 생성된 경우 해당 값을 viewpager에 표시
                if(null != bitmapPhotoList && 0 < bitmapPhotoList.size()) {
                    detailImageViewPagerAdapter = new DetailImageViewPagerAdapter(getContext(), inflater, bitmapPhotoList, "");
                    imageViewPager.setAdapter(detailImageViewPagerAdapter);
                } else {
                    imageViewPager.setVisibility(View.GONE);    // 이미지 노출용 리스트값이 없을 경우 이미지 표시를 위한 viewpager 공간 삭제
                }
            } else {
                imageViewPager.setVisibility(View.GONE);    // 구글 이미지 리스트가 없을 경우 이미지 표시를 위한 viewpager 공간 삭제
            }

            // 구글 리뷰 리스트가 있을 경우 리스트 노출
            if(null != googleReviewList && 0 < googleReviewList.size()) {
                googleReviewListAdapter = new GoogleListAdapter(getContext(), R.layout.google_review_item, googleReviewList);
                reviewList.setAdapter(googleReviewListAdapter);
            } else {
                reviewListLayout.setVisibility(View.GONE);
            }

        } else {
            imageViewPager.setVisibility(View.GONE);    // 구글 데이터가 없을 경우 이미지 표시를 위한 viewpager 공간 삭제
            reviewListLayout.setVisibility(View.GONE);
        }
         **/

        Log.i("Info", "detailItem Info : " + overView + "-" + title + "-" + homepage + "-" + addr1 + "-" + addr2);
        //return inflater.inflate(R.layout.fragment_detail_common, container, false);
        TextView detailOverView = (TextView) view.findViewById(R.id.detailOverView);
        TextView detailAddr1 = (TextView) view.findViewById(R.id.detailAddr1);
        //TextView detailAddr2 = (TextView) view.findViewById(R.id.detailAddr2);
        TextView detailPhone = (TextView) view.findViewById(R.id.detailPhone);
        TextView detailHomepage = (TextView) view.findViewById(R.id.detailHomepage);
        //TextView detailTitle = (TextView) view.findViewById(R.id.detailTitle);
        LinearLayout detailAddr_layout = (LinearLayout) view.findViewById(R.id.detailAddr_layout);
        LinearLayout detailPhone_layout = (LinearLayout) view.findViewById(R.id.detailPhone_layout);
        LinearLayout detailHomepage_layout = (LinearLayout) view.findViewById(R.id.detailHomepage_layout);

        //detailTitle.setText(title);

        commonUtil = new CommonUtil();
        //detailOverView.setText(overView);

        SpannableStringBuilder overViewBuilder = new SpannableStringBuilder();
        if(null != overView && !"".equalsIgnoreCase(overView)) {
            overViewBuilder = commonUtil.convertTxtToLink(getContext(), overView);
        }
        if(null != overViewBuilder.toString() && !"".equalsIgnoreCase(overViewBuilder.toString())
                && 0 < overViewBuilder.toString().length()) {
            detailOverView.setText(overViewBuilder.toString());
        }

        SpannableStringBuilder addr1Builder = new SpannableStringBuilder();
        if(null != mapAddr && !"".equalsIgnoreCase(mapAddr)) {
            addr1Builder = commonUtil.convertTxtToLink(getContext(), mapAddr);
        }
        if(null != addr1Builder.toString() && !"".equalsIgnoreCase(addr1Builder.toString())
                && 0 < addr1Builder.toString().length() && !"null".equalsIgnoreCase(addr1Builder.toString())) {
            if(detailAddr_layout.getVisibility() == View.GONE) {
                detailAddr_layout.setVisibility(View.VISIBLE);
            }
            detailAddr1.setText(addr1Builder.toString());
        } else {
            detailAddr_layout.setVisibility(View.GONE);
            //addressLayout.setVisibility(View.GONE);
        }

        if(null != phoneNo && !"".equalsIgnoreCase(phoneNo) && 0 < phoneNo.length()) {
            if(detailPhone_layout.getVisibility() == View.GONE) {
                detailPhone_layout.setVisibility(View.VISIBLE);
            }
            detailPhone.setText(phoneNo);
        } else {
            detailPhone_layout.setVisibility(View.GONE);
        }

        /**
        SpannableStringBuilder addr2Builder = new SpannableStringBuilder();
        if(null != addr2 && !"".equalsIgnoreCase(addr2)) {
            addr2Builder = commonUtil.convertTxtToLink(getContext(), addr2);
        }
        if(null != addr2Builder) { detailAddr2.setText(addr2Builder.toString()); }
         **/

        SpannableStringBuilder homePageBuilder = new SpannableStringBuilder();
        if(null != homepage && !"".equalsIgnoreCase(homepage)) {
            homePageBuilder = commonUtil.convertTxtToLink(getContext(), homepage);
        }
        if(null != homePageBuilder.toString() && !"".equalsIgnoreCase(homePageBuilder.toString())
                && 0 < homePageBuilder.toString().length() && !"null".equalsIgnoreCase(homePageBuilder.toString())) {
            if(detailHomepage_layout.getVisibility() == View.GONE) {
                detailHomepage_layout.setVisibility(View.VISIBLE);
            }
            detailHomepage.setText(homePageBuilder.toString().trim());
        } else {
            detailHomepage_layout.setVisibility(View.GONE);
        }

        detailImageViewPager = (ViewPager) view.findViewById(R.id.detail_img_viewpager);
        detailImageViewPagerAdapter = new DetailImageViewPagerAdapter(getContext(), inflater, imgUrlList, firstImage);
        detailImageViewPager.setAdapter(detailImageViewPagerAdapter);
        tabLayout1.setupWithViewPager(detailImageViewPager, true);
        detailImageViewPager.requestFocus();

        mapView = (MapView) view.findViewById(R.id.map_info_fragment);
        mapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        LatLng location = new LatLng(mapy, mapx);
        uiSettings = map.getUiSettings();

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location);
        markerOptions.title(title);
        if(null != mapAddr.trim() && !"".equalsIgnoreCase(mapAddr.trim()) && 0 < mapAddr.trim().length() && !"null".equalsIgnoreCase(mapAddr.trim())) {
            markerOptions.snippet(mapAddr);
        }
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        map.addMarker(markerOptions);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
        map.animateCamera(CameraUpdateFactory.zoomTo(15));

        //Marker marker = map.addMarker(markerOptions);
        //marker.showInfoWindow();

        uiSettings.isZoomControlsEnabled();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
    }

    /**
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
