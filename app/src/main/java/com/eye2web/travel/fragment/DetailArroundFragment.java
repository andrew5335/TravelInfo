package com.eye2web.travel.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.eye2web.travel.R;
import com.eye2web.travel.adapter.KakaoArroundListAdapter;
import com.eye2web.travel.apivo.KakaoLocal;
import com.eye2web.travel.vo.DetailCommonItem;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailArroundFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailArroundFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailArroundFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private DetailCommonItem detailCommonItem;
    private String googlePhotoUrl;
    private String googleKey;
    //private List<GooglePlaceItem> gasPlaceList;
    //private List<GooglePlaceItem> parkingPlaceList;
    private List<KakaoLocal.Documents> gasPlaceList;
    private List<KakaoLocal.Documents> parkingPlaceList;
    private ListView gasList;
    private ListView parkingList;
    //private GoogleArroundListAdapter googleArroundListAdapter;
    private KakaoArroundListAdapter kakaoArroundListAdapter;

    private AdView mAdView1;    // ad banner

    private OnFragmentInteractionListener mListener;

    public DetailArroundFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailArroundFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailArroundFragment newInstance(String param1, String param2) {
        DetailArroundFragment fragment = new DetailArroundFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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

        view = inflater.inflate(R.layout.fragment_detail_arround, container, false);

        String admobId = getResources().getString(R.string.google_admob_id);
        MobileAds.initialize(getContext(), admobId);    // admob 초기화

        mAdView1 = view.findViewById(R.id.adView);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mAdView1.loadAd(adRequest1);

        Bundle bundle = getArguments();
        detailCommonItem = (DetailCommonItem) bundle.getSerializable("detailCommonItem");

        //String title = (String) detailCommonItem.getTitle();

        //TextView detailTitle = (TextView) view.findViewById(R.id.detailTitle);
        //detailTitle.setText(title);

        //googlePhotoUrl = view.getResources().getString(R.string.google_places_api_photo_url);
        //googleKey = view.getResources().getString(R.string.google_maps_key);
        //googlePhotoUrl = googlePhotoUrl + "?key=" + googleKey;

        if(null != detailCommonItem) {
            /**
            gasPlaceList = new ArrayList<GooglePlaceItem>();
            parkingPlaceList = new ArrayList<GooglePlaceItem>();

            gasPlaceList = detailCommonItem.getGasList();
            parkingPlaceList = detailCommonItem.getParkingList();
             **/
            gasPlaceList = new ArrayList<KakaoLocal.Documents>();
            parkingPlaceList = new ArrayList<KakaoLocal.Documents>();

            gasPlaceList = detailCommonItem.getKakaoLocalGas().getDocuments();
            parkingPlaceList = detailCommonItem.getKakaoLocalPark().getDocuments();

            if(null != gasPlaceList && 0 < gasPlaceList.size()) {
                gasList = (ListView) view.findViewById(R.id.gaslist);

                /**
                for(int i=0; i < gasPlaceList.size(); i++) {
                    String gasPhoto = "";
                    if(null != gasPlaceList.get(i).getPhotoReference() && !"".equalsIgnoreCase(gasPlaceList.get(i).getPhotoReference())) {
                        gasPhoto = googlePhotoUrl + "&maxwidth=400&photoreference=" + gasPlaceList.get(i).getPhotoReference();
                        //Log.i("Info", "Gas Photo Info : " + gasPhoto);
                        gasPlaceList.get(i).setGooglePhotoUrl(gasPhoto);
                    } else {
                        gasPlaceList.get(i).setGooglePhotoUrl(null);
                    }
                }
                 **/

                kakaoArroundListAdapter = new KakaoArroundListAdapter(getContext(), R.layout.kakao_arround_item, gasPlaceList, "gas");
                gasList.setAdapter(kakaoArroundListAdapter);
                gasList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        KakaoLocal.Documents item = (KakaoLocal.Documents) gasList.getAdapter().getItem(position);
                        goGoogleMap(Double.parseDouble(item.getY()), Double.parseDouble(item.getX()), item.getPlace_name(), item.getAddress_name());
                        //Toast.makeText(getContext(), "Data : " + item.getLat(), Toast.LENGTH_LONG).show();
                    }
                });

            }

            if(null != parkingPlaceList && 0 < parkingPlaceList.size()) {
                parkingList = (ListView) view.findViewById(R.id.parkinglist);

                /**
                for(int i=0; i < parkingPlaceList.size(); i++) {
                    String parkingPhoto = "";
                    if(null != parkingPlaceList.get(i).getPhotoReference() && !"".equalsIgnoreCase(parkingPlaceList.get(i).getPhotoReference())) {
                        parkingPhoto = googlePhotoUrl + "&maxwidth=400&photoreference=" + parkingPlaceList.get(i).getPhotoReference();
                        //Log.i("Info", "Parking Photo Info : " + parkingPhoto);
                        parkingPlaceList.get(i).setGooglePhotoUrl(parkingPhoto);
                    } else {
                        parkingPlaceList.get(i).setGooglePhotoUrl(null);
                    }
                }
                 **/

                kakaoArroundListAdapter = new KakaoArroundListAdapter(getContext(), R.layout.kakao_arround_item, parkingPlaceList, "parking");
                parkingList.setAdapter(kakaoArroundListAdapter);
                parkingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        KakaoLocal.Documents item = (KakaoLocal.Documents) parkingList.getAdapter().getItem(position);
                        goGoogleMap(Double.parseDouble(item.getY()), Double.parseDouble(item.getX()), item.getPlace_name(), item.getAddress_name());
                        //Toast.makeText(getContext(), "Data : " + item.getName(), Toast.LENGTH_LONG).show();
                    }
                });
            }
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

    public void goGoogleMap(double lat, double lng, String locName, String locAddress) {
        Double tmpLat = lat;
        Double tmpLng = lng;

        if(!tmpLat.isNaN() && !tmpLng.isNaN()) {
            Uri googleMapIntentUri = Uri.parse("geo:" + lat + "," + lng + "?z=7&q=" + locName + "+" + locAddress);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, googleMapIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            if(mapIntent.resolveActivity(getContext().getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                String googleMapUrl = getString(R.string.google_map_url);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleMapUrl + "?api=1&query=" + locName + "+" + locAddress));
                startActivity(browserIntent);
            }
        }
    }

}
