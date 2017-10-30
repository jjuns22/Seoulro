package com.seoulsi.client.seoulro.main.Fragment;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.seoulsi.client.seoulro.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SanJuku on 2017-10-15.
 */

public class LandScapeFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private MapView mapView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final String TAG = "LandScapeFragment";
        final View view = inflater.inflate(R.layout.fragment_landscape, container, false);

        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
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
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //MapsInitializer.initialize(getActivity().getApplicationContext());
//액티비티가 처음 생성될 때 실행되는 함수

        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        LatLng SEOUL = new LatLng(37.556467, 126.972566);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("수도");
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
        googleMap.addMarker(markerOptions).showInfoWindow();


//        LatLng NEWARK = new LatLng(37.556, 126.97);
//
//        //GroundOverlayOptions newarkMap = new GroundOverlayOptions()
//        //        .image(BitmapDescriptorFactory.fromResource(R.drawable.newark_nj_1922))
//        //        .position(NEWARK, 8600f, 6500f);
//
//// Add an overlay to the map, retaining a handle to the GroundOverlay object.
//        GroundOverlay imageOverlay = map.addGroundOverlay(newarkMap);
//
//        LatLngBounds newarkBounds = new LatLngBounds(
//                new LatLng(40.712216, -74.22655),       // South west corner
//                new LatLng(40.773941, -74.12544));      // North east corner
//        GroundOverlayOptions newarkMap = new GroundOverlayOptions()
//                .image(BitmapDescriptorFactory.fromResource(R.drawable.newark_nj_1922))
//                .positionFromBounds(newarkBounds);

    }

}
