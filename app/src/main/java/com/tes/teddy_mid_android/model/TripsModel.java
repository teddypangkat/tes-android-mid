package com.tes.teddy_mid_android.model;

public class TripsModel {

    public Start start;
    public End stop;
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


    public class Start {
        public double lat;

        public Start(double lat, double lng, String tracked_at) {
            this.lat = lat;
            this.lng = lng;
            this.tracked_at = tracked_at;
        }

        public double lng;
        public String tracked_at;



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


    public class End {
        public double lat;
        public double lng;

        public End(double lat, double lng) {
            this.lat = lat;
            this.lng = lng;
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

