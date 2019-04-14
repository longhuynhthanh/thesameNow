package com.example.admin.projectnow.FRAGMENT;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.projectnow.MODEL.location;
import com.example.admin.projectnow.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MyLocationFragment extends Fragment implements OnMapReadyCallback {
    private static MyLocationFragment instance;
    private GoogleMap googleMap;
    private LocationManager service;
    private location myLocation;
    private String title;

    //region Instance
    public static MyLocationFragment Instance()
    {
        if(instance == null)
        {
            instance = new MyLocationFragment();
        }
        return instance;
    }
    private void Instance(MyLocationFragment instance)
    {
        MyLocationFragment.instance = instance;
    }
    //endregion

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        GetData();
        View view = inflater.inflate(R.layout.fragment_my_location, null, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        UiSettings uiSettings = this.googleMap.getUiSettings();
        uiSettings.setCompassEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setScrollGesturesEnabled(true);
        uiSettings.setTiltGesturesEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//bang M
            if (ActivityCompat.checkSelfPermission(getContext(),android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getContext(),android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                XuLyQuyen();
            } else {
                ActivityCompat.requestPermissions(this.getActivity(), new
                        String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        } else {
            XuLyQuyen();
        }
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    //region Method
    public void XuLyQuyen()
    {
        this.googleMap.setMyLocationEnabled(true);
        service = (LocationManager)getActivity().getSystemService(getContext().LOCATION_SERVICE);
        Location location= service.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null)
        {
            LatLng latLng = new LatLng(myLocation.Longitude(), myLocation.Latitude());
            CreateMarker(latLng);
            this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        }
    }
    private void CreateMarker(LatLng latLng)
    {
        Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet(myLocation.NameLocation())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
        );
    }
    private void GetData()
    {
        Bundle bundle = getArguments();
        if(bundle != null)
        {
            myLocation = (location)bundle.getSerializable("location");
            title = bundle.getString("title");
        }
    }

    //endregion ------------------------------------------------------------
}
