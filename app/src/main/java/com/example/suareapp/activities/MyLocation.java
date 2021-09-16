package com.example.suareapp.activities;

import com.google.firebase.firestore.DocumentSnapshot;

public class MyLocation {
    private double Latitude;
    private double Longitude;

    public MyLocation(double latitude,double longitude) {
        Latitude = latitude;
        Longitude = longitude;
    }

    public MyLocation() {
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

}
