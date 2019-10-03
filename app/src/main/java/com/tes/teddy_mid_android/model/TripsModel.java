package com.tes.teddy_mid_android.model;

import java.io.Serializable;
import java.util.List;

public class TripsModel implements Serializable{

    public Start start;
    public End stop;
    public List<Histories> histories;
    public int distance;
    public int duration;
    public double maxSpeed;
    public double average_speed;
    public int score;


    public TripsModel(int distance, int duration, double maxSpeed, double average_speed, int score) {
        this.distance = distance;
        this.duration = duration;
        this.maxSpeed = maxSpeed;
        this.average_speed = average_speed;
        this.score = score;
    }

    public Start getStart() {
        return start;
    }

    public void setStart(Start start) {
        this.start = start;
    }

    public End getStop() {
        return stop;
    }

    public void setStop(End stop) {
        this.stop = stop;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getAverage_speed() {
        return average_speed;
    }

    public void setAverage_speed(double average_speed) {
        this.average_speed = average_speed;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Histories> getHistories() {
        return histories;
    }

    public void setHistories(List<Histories> histories) {
        this.histories = histories;
    }

    public class Histories implements Serializable {
        public double lat;
        public double lng;


        public Histories(double lat, double lng) {
            this.lat = lat;
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public double getLng() {
            return lng;
        }
    }


    public class Start implements Serializable{
        public double lat;
        public double lng;
        public String cityName;
        public String tracked_at;


        public Start(double lat, double lng, String cityName, String tracked_at) {
            this.lat = lat;
            this.lng = lng;
            this.cityName = cityName;
            this.tracked_at = tracked_at;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public String getTracked_at() {
            return tracked_at;
        }

        public void setTracked_at(String tracked_at) {
            this.tracked_at = tracked_at;
        }
    }


    public class End implements Serializable{
        public double lat;
        public double lng;
        public String cityName;

        public End(double lat, double lng, String cityName) {
            this.lat = lat;
            this.lng = lng;
            this.cityName = cityName;
        }

        public End(double lat) {
            this.lat = lat;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }

}

