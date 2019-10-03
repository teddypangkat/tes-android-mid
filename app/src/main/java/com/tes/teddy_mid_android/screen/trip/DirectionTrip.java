package com.tes.teddy_mid_android.screen.trip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.tes.teddy_mid_android.R;
import com.tes.teddy_mid_android.base.BaseActivity;
import com.tes.teddy_mid_android.model.TripsModel;

import java.util.ArrayList;
import java.util.List;

public class DirectionTrip extends BaseActivity implements OnMapReadyCallback {

    public static final String KEY_TRIP = "trip_model";
    private GoogleMap mGoogleMap;
    private TripsModel tripsModel;
    private ArrayList<LatLng> listLatLngTripHistory = new ArrayList<>();

    @Override
    public void initView() {
        super.initView();
        setupActionBar("Direction Trip");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.directionMap);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        tripsModel = (TripsModel) getIntent().getSerializableExtra(KEY_TRIP);

        if (tripsModel != null) {
            for (TripsModel.Histories histories : tripsModel.getHistories()) {
                listLatLngTripHistory.add(new LatLng(histories.getLat(), histories.getLng()));
            }
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_direction_trip;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        drawPolyLineOnMap(listLatLngTripHistory);
    }


    public void drawPolyLineOnMap(List<LatLng> list) {
        PolylineOptions polyOptions = new PolylineOptions();
        polyOptions.color(Color.RED);
        polyOptions.width(5);
        polyOptions.addAll(list);


        mGoogleMap.clear();
        mGoogleMap.addPolyline(polyOptions);
        mGoogleMap.addMarker(new MarkerOptions().position(list.get(0)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mGoogleMap.addMarker(new MarkerOptions().position(list.get(list.size() - 1)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(list.get(0), 12));
    }

}
