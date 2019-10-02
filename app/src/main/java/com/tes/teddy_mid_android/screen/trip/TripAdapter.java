package com.tes.teddy_mid_android.screen.trip;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tes.teddy_mid_android.R;
import com.tes.teddy_mid_android.model.TripsModel;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripHolder> {

    private List<TripsModel> tripList;

    public TripAdapter(List<TripsModel> tripList) {
        this.tripList = tripList;
    }

    @NonNull
    @Override
    public TripHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_trip, parent, false);
        return new TripHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TripHolder holder, int position) {

        TripsModel dataTrip = tripList.get(position);
        holder.bindItem(dataTrip);
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }


    public class TripHolder extends RecyclerView.ViewHolder {

        TextView startTrip;
        TextView stopTrip;
        TextView timeTrip;
        TextView distanceTrip;
        TextView durationTrip;
        TextView scoreTrip;

        public TripHolder(@NonNull View itemView) {
            super(itemView);

            startTrip = (TextView) itemView.findViewById(R.id.txt_start_trip);
            stopTrip = (TextView) itemView.findViewById(R.id.txt_stop_trip);
            timeTrip = (TextView) itemView.findViewById(R.id.txt_time_trip);
            distanceTrip = (TextView) itemView.findViewById(R.id.txt_distance_trip);
            durationTrip = (TextView) itemView.findViewById(R.id.txt_duration_trip);
            scoreTrip = (TextView) itemView.findViewById(R.id.txt_score_trip);
        }

        void bindItem(TripsModel trip) {

            startTrip.setText(trip.getStart().getLat() + "");
            stopTrip.setText(trip.getStart().getLat() + "");
            timeTrip.setText(trip.getStart().getTracked_at());
            distanceTrip.setText(trip.getDistance() + "");
            durationTrip.setText(trip.getDuration() + "");
            scoreTrip.setText(trip.getScore() + "");


        }

    }


}
