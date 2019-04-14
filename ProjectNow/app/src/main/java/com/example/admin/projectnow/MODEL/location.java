package com.example.admin.projectnow.MODEL;

import java.io.Serializable;

public class location implements Serializable {
    private int id;
    private String nameLocation;
    private double longitude;
    private double latitude;

    public location(int id, String nameLocation, double longitude, double latitude) {
        this.id = id;
        this.nameLocation = nameLocation;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int Id() {
        return id;
    }

    public void Id(int id) {
        this.id = id;
    }

    public String NameLocation() {
        return nameLocation;
    }

    public void NameLocation(String nameLocation) {
        this.nameLocation = nameLocation;
    }

    public double Longitude() {
        return longitude;
    }

    public void Longitude(double longitude) {
        this.longitude = longitude;
    }

    public double Latitude() {
        return latitude;
    }

    public void Latitude(double latitude) {
        this.latitude = latitude;
    }

}
