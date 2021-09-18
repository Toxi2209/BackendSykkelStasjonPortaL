package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Station {
    public String station_id;
    public String name;
    public String address;
    public double lat;
    public double lon;
    public int capacity;
    public int numBikesAvailable;
    public int numDockAvailable;

    public Station(@JsonProperty("station_id") String station_id,
                   @JsonProperty("name") String name,
                   @JsonProperty("address") String address,
                   @JsonProperty("lat") double lat,
                   @JsonProperty("lon") double lon,
                   @JsonProperty("capacity") int capacity) {
        this.station_id = station_id;
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
        this.capacity = capacity;
    }
    public Station(String station_id,  int bikes, int docks) {
        this.station_id = station_id;
        this.numBikesAvailable = bikes;
        this.numDockAvailable = docks;
    }

    public String getId(){
        return this.station_id;
    }
    public void setNumBikesAvailable(int i) {
        this.numBikesAvailable = i;
    }

    public void setNumDockAvailable(int i) {
        this.numDockAvailable = i;
    }


    @Override
    public String toString() {
        return "Station{" +
            "station_id='" + station_id + '\'' +
            ", name='" + name + '\'' +
            ", address='" + address + '\'' +
            ", numBikesAvailable=" + numBikesAvailable +
            ", numDockAvailable=" + numDockAvailable +
            '}';
    }
}
