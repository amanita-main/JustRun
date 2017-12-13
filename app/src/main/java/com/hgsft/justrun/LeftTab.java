package com.hgsft.justrun;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeftTab extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap map;

    public LeftTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left_tab, container, false);

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);

        SegmentedGroup group = (SegmentedGroup)view.findViewById(R.id.segmented2);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                onSelectInternalTabBtn(checkedId);
            }
        });
        // Inflate the layout for this fragment
        return view;

    }

    private void onSelectInternalTabBtn(int index) {
        //TODO: change view/mode
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST && isMapPermissionsGranted()) {
            onMapReady(map);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private final int MY_PERMISSIONS_REQUEST = 1;
    private boolean isMapPermissionsGranted() {
        return ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(false);

        if (!isMapPermissionsGranted()) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(getActivity(),
                    new String[] { Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION },
                    MY_PERMISSIONS_REQUEST);
            return;
        }
        map.setMyLocationEnabled(true);
       /*
       //in old Api Needs to call MapsInitializer before doing any CameraUpdateFactory call
        try {
            MapsInitializer.initialize(this.getActivity());
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
       */

        // Updates the location and zoom of the MapView
        /*CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.1, -87.9), 10);
        map.animateCamera(cameraUpdate);*/
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(43.1, -87.9)));
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}
