package com.eye2web.travel;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eye2web.travel.naver.NMapPOIflagType;
import com.eye2web.travel.naver.NMapViewerResourceProvider;
import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

public class NaverFragment extends Fragment {

    
    private NMapContext nMapContext;
    private NMapView mapView;
    private NMapController mapController;
    private NMapViewerResourceProvider mapViewerResourceProvider;
    private NMapOverlayManager mapOverlayManager;

    private static String CLIENT_ID = "";

    private double mapx = 0;
    private double mapy = 0;
    private String title = "";
    private String addr = "";
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.naver_fragment, container, false);
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nMapContext = new NMapContext(super.getActivity());
        nMapContext.onCreate();
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Resources res = getResources();
        CLIENT_ID = res.getString(R.string.naver_client_id);
        mapView = (NMapView) getView().findViewById(R.id.naver_map_view);
        mapView.setClientId(CLIENT_ID);
        nMapContext.setupMapView(mapView);
    }
    
    @Override
    public void onStart() {
        super.onStart();
        nMapContext.onStart();
        getPosition();
    }

    public void getPosition() {
        mapView.setBuiltInAppControl(true);
        mapView.setClickable(true);
        mapView.displayZoomControls(true);
        mapView.setBuiltInZoomControls(true, null);
        mapView.setEnabled(true);
        mapView.setFocusable(true);
        mapView.setFocusableInTouchMode(true);
        //mapView.setScalingFactor(3);
        mapView.setScalingFactor(3, true);
        //mapView.requestFocus();
        mapController = mapView.getMapController();
        mapController.setMapCenter(new NGeoPoint(mapx, mapy), 11);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setMarker();
            }
        }, 1000);
    }

    public void setPosition(double mapx, double mapy, String title, String addr) {
        this.mapx = mapx;
        this.mapy = mapy;
        this.title = title;
        this.addr = addr;
    }

    public void setMarker() {
        int markerId = NMapPOIflagType.PIN;
        mapViewerResourceProvider = new NMapViewerResourceProvider(getContext());
        mapOverlayManager = new NMapOverlayManager(getContext(), mapView, mapViewerResourceProvider);

        NMapPOIdata poIdata = new NMapPOIdata(2, mapViewerResourceProvider);
        poIdata.beginPOIdata(2);
        poIdata.addPOIitem(mapx, mapy, title, markerId, 0);
        //poIdata.addPOIitem(mapx, mapy, addr, markerId, 1);
        poIdata.endPOIdata();

        NMapPOIdataOverlay poIdataOverlay = mapOverlayManager.createPOIdataOverlay(poIdata, null);
        //poIdataOverlay.showAllPOIdata(0);
        poIdataOverlay.showAllPOIdata(10);
        //poIdataOverlay.setOnStateChangeListener(onPOIdataChangeListener);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        nMapContext.onResume();
    }
    
    @Override
    public void onPause() {
        super.onPause();
        nMapContext.onPause();
    }
    
    @Override
    public void onStop() {
        super.onStop();
        nMapContext.onStop();
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    
    @Override
    public void onDestroy() {
        nMapContext.onDestroy();
        super.onDestroy();
    }
}
