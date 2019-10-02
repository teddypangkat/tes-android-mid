package com.tes.teddy_mid_android.screen.trip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

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

public class TripHistoryActivity extends BaseActivity {

    private RecyclerView recTrip;
    private TripAdapter tripAdapter;
    private ArrayList<TripsModel> arrTrips = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;


    @Override
    public void initView() {
        super.initView();
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
        tripAdapter = new TripAdapter(arrTrips);
        layoutManager = new LinearLayoutManager(this);
        recTrip.setLayoutManager(layoutManager);
        recTrip.setAdapter(tripAdapter);

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

                    TripsModel.Start startTrip = tripsModel.new Start(latStart, lngStart, trackedAt);

                    //stop
                    String end = jArrayTrip.getJSONObject(i).getString("end");
                    JSONObject jobjEnd = new JSONObject(end);
                    double latEnd = jobjEnd.getDouble("latitude");
                    double lngEnd = jobjEnd.getDouble("longitude");

                    TripsModel.End endTrip = tripsModel.new End(latEnd, lngEnd);


                    tripsModel.setStart(startTrip);
                    tripsModel.setStop(endTrip);

                    arrTrips.add(tripsModel);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
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
}
