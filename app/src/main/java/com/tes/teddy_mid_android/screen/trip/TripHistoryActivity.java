package com.tes.teddy_mid_android.screen.trip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.tes.teddy_mid_android.R;
import com.tes.teddy_mid_android.base.BaseActivity;
import com.tes.teddy_mid_android.model.TripsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TripHistoryActivity extends BaseActivity implements TripAdapter.ClickItemListener {

    private RecyclerView recTrip;
    private TripAdapter tripAdapter;
    private ArrayList<TripsModel> arrTrips = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;


    @Override
    public void initView() {
        super.initView();
        setupActionBar("Trip History");
        recTrip = (RecyclerView) findViewById(R.id.trip_list);
    }


    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        iniadapter();
        getDataFromJson();

        if (!arrTrips.isEmpty()) {
            tripAdapter.notifyDataSetChanged();
        }
    }

    void iniadapter() {
        tripAdapter = new TripAdapter(arrTrips, this);
        layoutManager = new LinearLayoutManager(this);
        recTrip.setLayoutManager(layoutManager);
        recTrip.setAdapter(tripAdapter);
        recTrip.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

    }


    void getDataFromJson() {

        String jsonTrip = readJSONFromAsset();
        if (jsonTrip != null) {

            try {

                JSONObject jObjTrip = new JSONObject(jsonTrip);
                String trips = jObjTrip.getString("trips");

                //parse json Array
                JSONArray jArrayTrip = new JSONArray(trips);

                for (int i = 0; i < jArrayTrip.length(); i++) {


                    int distance = jArrayTrip.getJSONObject(i).getInt("distance");
                    int duration = jArrayTrip.getJSONObject(i).getInt("duration");
                    double maxSpeed = jArrayTrip.getJSONObject(i).getDouble("max_speed");
                    double avarageSpeed = jArrayTrip.getJSONObject(i).getDouble("average_speed");
                    int score = jArrayTrip.getJSONObject(i).getInt("score");
                    TripsModel tripsModel = new TripsModel(distance, duration, maxSpeed, avarageSpeed, score);

                    //start
                    String start = jArrayTrip.getJSONObject(i).getString("start");
                    JSONObject jobjStart = new JSONObject(start);
                    double latStart = jobjStart.getDouble("latitude");
                    double lngStart = jobjStart.getDouble("longitude");
                    String trackedAt = jobjStart.getString("tracked_at");
                    String cityNameStart = getCityNameFromLocation(latStart, lngStart);


                    String histories = jArrayTrip.getJSONObject(i).getString("histories");
                    JSONArray jArrayHistories = new JSONArray(histories);

                    List<TripsModel.Histories> arrHistories = new ArrayList<>();

                    for (int j = 0; j < jArrayHistories.length(); j++) {

                        TripsModel.Histories historiesClass = tripsModel.new Histories(
                                jArrayHistories.getJSONObject(j).getDouble("latitude"),
                                jArrayHistories.getJSONObject(j).getDouble("longitude")
                        );

                        arrHistories.add(historiesClass);

                    }


                    TripsModel.Start startTrip = tripsModel.new Start(latStart, lngStart, cityNameStart, trackedAt);

                    //stop
                    String end = jArrayTrip.getJSONObject(i).getString("end");
                    JSONObject jobjEnd = new JSONObject(end);
                    double latEnd = jobjEnd.getDouble("latitude");
                    double lngEnd = jobjEnd.getDouble("longitude");

                    String cityNameEnd = getCityNameFromLocation(latEnd, lngEnd);
                    TripsModel.End endTrip = tripsModel.new End(latEnd, lngEnd, cityNameEnd);

                    getCityNameFromLocation(latStart, lngStart);

                    tripsModel.setHistories(arrHistories);
                    tripsModel.setStart(startTrip);
                    tripsModel.setStop(endTrip);

                    arrTrips.add(tripsModel);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private String getCityNameFromLocation(double lat, double lng) {

        String cityName = "";
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            String address = addresses.get(0).getAddressLine(0);
            String[] arrAddress = address.split(",");
            cityName = arrAddress[1];
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cityName;
    }

    public String readJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("vehicle-trip-20190223.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_trip_history;
    }

    @Override
    public void onItemClick(TripsModel data) {
        Intent intent = new Intent(this, DirectionTrip.class);
        intent.putExtra(DirectionTrip.KEY_TRIP, data);
        startActivity(intent);
    }


}
