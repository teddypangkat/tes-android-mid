package com.tes.teddy_mid_android.screen;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tes.teddy_mid_android.R;
import com.tes.teddy_mid_android.base.BaseActivity;
import com.tes.teddy_mid_android.model.VehiclesModel;
import com.tes.teddy_mid_android.myinterface.ShowImageInterface;
import com.tes.teddy_mid_android.myinterface.VehiclesInterface;
import com.tes.teddy_mid_android.network.ShowImageFromAPI;
import com.tes.teddy_mid_android.network.VehiclesRequest;
import com.tes.teddy_mid_android.screen.trip.TripHistoryActivity;

import org.json.JSONException;
import org.json.JSONObject;


public class HomeActivity extends BaseActivity implements VehiclesInterface, ShowImageInterface, OnMapReadyCallback {

    TextView response;
    private VehiclesRequest requestAPI = new VehiclesRequest();
    private VehiclesModel vehiclesModel = new VehiclesModel();

    private GoogleMap mGoogleMap;
    private LatLng location;

    @Override
    public void initView() {
        super.initView();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        requestAPI.OnAttachView(this);


    }


    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        //requestAPI.OnAttachView(this);
        //requestAPI.execute();


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        requestAPI.execute();

        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent = new Intent(HomeActivity.this, TripHistoryActivity.class);
                startActivity(intent);
                return false;
            }
        });
    }

    @Override
    public void showProgress() {
        showProgressDialog();
    }

    @Override
    public void hideProgress() {
        dismissProgressDialog();
    }

    @Override
    public void requestDataSucces(String responseJson) {

        //parse json
        try {

            JSONObject jObj = new JSONObject(responseJson);
            JSONObject jsonDevice = jObj.getJSONObject("device");
            String vehiclesNumber = jsonDevice.getString("vehicle_number");
            // get image path
            String vehiclesMarker = jsonDevice.getString("marker");
            //get location
            JSONObject jObjMeta = new JSONObject(jsonDevice.getString("meta"));
            String latest = jObjMeta.getString("latest");
            JSONObject jObjLocation = new JSONObject(latest);
            double lat = jObjLocation.getDouble("latitude");
            double lng = jObjLocation.getDouble("longitude");


            //save date to model
            vehiclesModel.setPlateNumber(vehiclesNumber);
            vehiclesModel.setImgUrl(vehiclesMarker);
            vehiclesModel.setLat(lat);
            vehiclesModel.setLng(lng);

            location = new LatLng(vehiclesModel.getLat(), vehiclesModel.getLng());
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
//            mGoogleMap.addMarker(new MarkerOptions().position(location)
//                    .title(vehiclesModel.getPlateNumber()).icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(vehiclesModel.getImgUrl()))));
//
            new ShowImageFromAPI(this).execute(vehiclesModel.getImgUrl());


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

//    private void getMarkerBitmapFromView() {
//
//
//        return returnedBitmap;
//    }

    @Override
    public void requestDataError(String error) {
        //response.setText(error);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }


    @Override
    public void showImageSuccess(Bitmap bitmap) {
        View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
        ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.markerImage);
        TextView numberPlate = (TextView) customMarkerView.findViewById(R.id.markerNumberPlate);
        numberPlate.setText(vehiclesModel.getPlateNumber());
        markerImageView.setImageBitmap(bitmap);

        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);

        //addMarker
        mGoogleMap.addMarker(new MarkerOptions().position(location).
                icon(BitmapDescriptorFactory.fromBitmap(returnedBitmap)));
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 14));

    }
}
