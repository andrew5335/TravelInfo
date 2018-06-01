package com.eye2web.travel.fragment;

import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eye2web.travel.R;
import com.eye2web.travel.util.CommonUtil;
import com.eye2web.travel.vo.DetailCommonItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailMapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailMapFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View view;
    private MapView mapView;
    private DetailCommonItem detailCommonItem;
    private CommonUtil commonUtil;

    private double mapx = 0;
    private double mapy = 0;
    private String title = "";
    private String mapAddr = "";
    private UiSettings uiSettings;
    private FragmentManager parentFragmentManager;

    public DetailMapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailMapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailMapFragment newInstance(String param1, String param2) {
        DetailMapFragment fragment = new DetailMapFragment();
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
                viewGroupParent.removeAllViews();
            }
        }
         **/

        view = inflater.inflate(R.layout.fragment_detail_map, container, false);
        Bundle bundle = getArguments();
        DetailCommonItem detailCommonItem = (DetailCommonItem) bundle.getSerializable("detailCommonItem");

        title = (String) detailCommonItem.getTitle();
        TextView detailTitle = (TextView) view.findViewById(R.id.detailTitle2);
        detailTitle.setText(title);

        mapx = detailCommonItem.getMapx();
        mapy = detailCommonItem.getMapy();
        mapAddr = detailCommonItem.getAddr1() + " " + detailCommonItem.getAddr2();

        /**
        FragmentManager fragmentManager = getActivity().getFragmentManager();
        MapFragment mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.map_info_fragment);
        //MapFragment mapFragment = (MapFragment) view.findViewById(R.id.map_info_fragment);
        mapFragment.getMapAsync(this);
         **/
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
        markerOptions.snippet(mapAddr);
        map.addMarker(markerOptions);

        map.moveCamera(CameraUpdateFactory.newLatLng(location));
        map.animateCamera(CameraUpdateFactory.zoomTo(15));

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
