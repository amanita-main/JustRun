package com.hgsft.justrun;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.hgsft.justrun.model.Data;
import com.hgsft.justrun.model.tasks.RunTaskManager;

import java.util.HashMap;
import java.util.Map;

import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * A simple {@link Fragment} subclass.
 * Start training View
 * Contains map on 'background', taraining mode choser, settings, and start button
 */
public class LeftTab extends Fragment implements OnMapReadyCallback {

    private View mainView;
    private MapView mapView;
    private GoogleMap map;
    private Button modeSettings;
    private SegmentedGroup group;
    private int selectedMode = R.id.basicMode;
    //settings Map, contains pairs of linked pairs: radio button id <-> Run mode
    private static final Map<Integer, RunTaskManager.Mode> modeDialogMapping = new HashMap<Integer, RunTaskManager.Mode>() {{
        put(Integer.valueOf(R.id.basicMode), RunTaskManager.Mode.BASIC);
        put(Integer.valueOf(R.id.distanceMode), RunTaskManager.Mode.DISTANCE);
        put(Integer.valueOf(R.id.speedMode), RunTaskManager.Mode.SPEED);
        put(Integer.valueOf(R.id.timeMode), RunTaskManager.Mode.TIME);
        put(Integer.valueOf(R.id.advancedMode), RunTaskManager.Mode.ADVANCED);
    }};

    View.OnClickListener onClickModeSettings = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //show settings dialog for current training mode (like pick a distance/time, etc)
            RunTaskManager.Mode mode = modeDialogMapping.get(selectedMode);
            RunModeSettingsDialog.openDialog(getActivity(), (Button)v, mode);
        }
    };

    View.OnClickListener onClickSettings = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO: show settings activity
        }
    };

    View.OnClickListener onClickStartRun = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO: hide button/show timer/show running activity?
            //start countdown new timer before start training
            final Button btn = (Button)v;
            //not tested behaviour with enable/disable button
            //btn.setEnabled(false);
            new CountDownTimer(Data.getCurrent().getSettings().getCountDownTimer(), 1000) {

                public void onTick(long millisUntilFinished) {
                    btn.setText(String.valueOf(millisUntilFinished / 1000));
                }

                public void onFinish() {
                    //TODO: start training in training controller, unlock button?
                    //btn.setEnabled(true);
                }
            }.start();
        }
    };

    View.OnClickListener onClickMusicSettings = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO: open music settings activity
        }
    };

    public LeftTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_left_tab, container, false);

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) mainView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);

        group = (SegmentedGroup)mainView.findViewById(R.id.segmented2);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                onSelectInternalTabBtn(checkedId);
            }
        });

        modeSettings = (Button)mainView.findViewById(R.id.modeSettings);
        modeSettings.setOnClickListener(onClickModeSettings);

        FloatingActionButton settingsBtn = (FloatingActionButton)mainView.findViewById(R.id.settingsButton);
        settingsBtn.setOnClickListener(onClickSettings);

        Button startTrainingBtn = (Button) mainView.findViewById(R.id.startTrainingButton);
        startTrainingBtn.setOnClickListener(onClickStartRun);

        FloatingActionButton musicButton = (FloatingActionButton)mainView.findViewById(R.id.musicButton);
        musicButton.setOnClickListener(onClickMusicSettings);

        // Inflate the layout for this fragment
        return mainView;

    }

    private void onSelectInternalTabBtn(int index) {
        selectedMode = index;
        int visibility = selectedMode == R.id.basicMode ? View.INVISIBLE : View.VISIBLE;
        modeSettings.setVisibility(visibility);
        //TODO: update button text (read from settings)
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
        //check if permissions is granted
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
        //TODO: set map position at recieved positioning data or last known
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
