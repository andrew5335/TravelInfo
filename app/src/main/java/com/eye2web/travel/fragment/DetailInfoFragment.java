package com.eye2web.travel.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eye2web.travel.R;
import com.eye2web.travel.adapter.DetailImageViewPagerAdapter;
import com.eye2web.travel.util.CommonUtil;
import com.eye2web.travel.vo.DetailCommonItem;
import com.eye2web.travel.vo.GooglePlaceDetailItem;
import com.eye2web.travel.vo.GooglePlaceDetailPhoto;
import com.eye2web.travel.vo.GooglePlaceDetailReviews;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private DetailCommonItem detailCommonItem;
    private CommonUtil commonUtil;
    private String googlePhotoUrl;
    private String googleKey;

    private ViewPager imageViewPager;
    private DetailImageViewPagerAdapter detailImageViewPagerAdapter;

    private OnFragmentInteractionListener mListener;

    public DetailInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailInfoFragment newInstance(String param1, String param2) {
        DetailInfoFragment fragment = new DetailInfoFragment();
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
        /**
        if(view != null) {
            ViewGroup viewGroupParent = (ViewGroup) view.getParent();
            if(viewGroupParent != null) {
                viewGroupParent.removeView(view);
            }
        }
         **/

        view = inflater.inflate(R.layout.fragment_detail_info, container, false);
        Bundle bundle = getArguments();
        detailCommonItem = (DetailCommonItem) bundle.getSerializable("detailCommonItem");
        googlePhotoUrl = view.getResources().getString(R.string.google_places_api_photo_url);
        googleKey = view.getResources().getString(R.string.google_maps_key);
        googlePhotoUrl = googlePhotoUrl + "?key=" + googleKey;

        GooglePlaceDetailItem googleItem = new GooglePlaceDetailItem();
        List<GooglePlaceDetailPhoto> googlePhotoList = new ArrayList<GooglePlaceDetailPhoto>();
        List<GooglePlaceDetailReviews> googleReviewList = new ArrayList<GooglePlaceDetailReviews>();

        String title = "";

        title = (String) detailCommonItem.getTitle();
        //googleItem = (GooglePlaceDetailItem) detailCommonItem.getGooglePlaceDetailItem();

        if(null != googleItem) {
            googlePhotoList = (List<GooglePlaceDetailPhoto>) googleItem.getPhotoList();
            googleReviewList = (List<GooglePlaceDetailReviews>) googleItem.getReviewsList();

            List<GooglePlaceDetailPhoto> photoList = new ArrayList<GooglePlaceDetailPhoto>();
            //photoList = (List<GooglePlaceDetailPhoto>) detailCommonItem.getGooglePlaceDetailItem().getPhotoList();

            if(null != photoList && 0 < photoList.size()) {
                List<String> bitmapPhotoList = new ArrayList<String>();

                for(int i=0; i < photoList.size(); i++) {
                    String photo = "";
                    photo = googlePhotoUrl + "&maxwidth=400&photoreference=" + photoList.get(i).getPhotoReference();
                    bitmapPhotoList.add(photo);
                }

                if(null != bitmapPhotoList && 0 < bitmapPhotoList.size()) {
                    imageViewPager = (ViewPager) view.findViewById(R.id.info_img_viewpager);
                    detailImageViewPagerAdapter = new DetailImageViewPagerAdapter(getContext(), inflater, bitmapPhotoList, "");
                    imageViewPager.setAdapter(detailImageViewPagerAdapter);
                }

                for(int j=0; j < bitmapPhotoList.size(); j++) {
                    Log.i("Info", "Google Photo Url : " + bitmapPhotoList.get(j));
                }
            }

            TextView info_address = (TextView) view.findViewById(R.id.info_address);
            info_address.setText(googleItem.getFormattedAddress());

            TextView titleText = (TextView) view.findViewById(R.id.detailTitle);
            titleText.setText(title);


        } else {

        }



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
