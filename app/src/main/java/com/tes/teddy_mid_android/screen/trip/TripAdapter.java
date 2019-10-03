package com.tes.teddy_mid_android.screen.trip;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tes.teddy_mid_android.R;
import com.tes.teddy_mid_android.model.TripsModel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripHolder> {

    private List<TripsModel> tripList;
    private ClickItemListener clickItemListener;

    public TripAdapter(List<TripsModel> tripList, ClickItemListener clickItemListener) {
        this.tripList = tripList;
        this.clickItemListener = clickItemListener;
    }


    interface ClickItemListener {
        void onItemClick(TripsModel data);
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
        LinearLayout root;

        public TripHolder(@NonNull View itemView) {
            super(itemView);

            startTrip = (TextView) itemView.findViewById(R.id.txt_start_trip);
            stopTrip = (TextView) itemView.findViewById(R.id.txt_stop_trip);
            timeTrip = (TextView) itemView.findViewById(R.id.txt_time_trip);
            distanceTrip = (TextView) itemView.findViewById(R.id.txt_distance_trip);
            durationTrip = (TextView) itemView.findViewById(R.id.txt_duration_trip);
            scoreTrip = (TextView) itemView.findViewById(R.id.txt_score_trip);
            root = (LinearLayout) itemView.findViewById(R.id.root_view);


        }

        void bindItem(final TripsModel trip) {

            double distanceKM = trip.getDistance() / 1000;
            long minutes = trip.getDuration() / 60;
            String time = trip.getStart().getTracked_at().substring(10);


            startTrip.setText(trip.getStart().getCityName());
            stopTrip.setText(trip.getStop().getCityName());
            timeTrip.setText(time);
            distanceTrip.setText("Distance " + distanceKM + " Km");
            durationTrip.setText("Duration " + minutes + " Mins");
            scoreTrip.setText(trip.getScore() + "");

            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickItemListener.onItemClick(trip);
                }
            });

        }

    }


}
