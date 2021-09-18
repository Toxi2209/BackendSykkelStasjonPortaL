package com.example.demo.service;

import com.example.demo.dao.StationDao;
import com.example.demo.model.Station;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class StationService {

    private final StationDao stationDao;
    static List<Station> stationsInfo = new ArrayList<>();
    static List<Station> stationsStatus = new ArrayList<>();

    @Autowired
    public StationService(@Qualifier("Dao") StationDao stationDao) {
        firstConnect();
        secondConnect();
        stationDao.insertStationList(stationsInfo);
        this.stationDao = stationDao;
    }

    public int AddStation(Station station) {
        return stationDao.insertStation(station);
    }

    public Optional<Station> getPersonById(String id) {
        return stationDao.selectStationById(id);
    }

    public List<Station> getAllStations() {
        return stationDao.SelectAllStations();
    }


    public static void firstConnect(){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://gbfs.urbansharing.com/oslobysykkel.no/station_information.json")).build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenApply(StationService::parse)
            .join();

    }

    public static void secondConnect(){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://gbfs.urbansharing.com/oslobysykkel.no/station_status.json")).build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenApply(StationService::merge)
            .join();
    }

    public static String parse(String respondsbody){

        JSONObject jsonObjects= new JSONObject(respondsbody);
        JSONObject jsonObject = jsonObjects.getJSONObject("data");
        JSONArray jsonArray = jsonObject.getJSONArray("stations");

        for (int i = 0; i < jsonArray.length(); i++){

            JSONObject station = jsonArray.getJSONObject(i);
            String stationId = station.getString("station_id");
            String name = station.getString("name");
            String address = station.getString("address");
            double lat = station.getDouble("lat");
            double lon = station.getDouble("lon");
            int capacity = station.getInt("capacity");
            Station stationObject = new Station(stationId,name,address,lat,lon,capacity);
            stationsInfo.add(stationObject);

        }
        return null;
    }

    public static String merge(String respondsbody){

        JSONObject jsonObjects= new JSONObject(respondsbody);
        JSONObject jsonObject = jsonObjects.getJSONObject("data");
        JSONArray jsonArray = jsonObject.getJSONArray("stations");
        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject station = jsonArray.getJSONObject(i);
            String stationId = station.getString("station_id");

            int bikes = station.getInt("num_bikes_available");
            int docks = station.getInt("num_docks_available");
            Station stationObject = new Station(stationId,bikes,docks);
            stationsStatus.add(stationObject);
        }


        for (Station station: stationsInfo) {
            for (Station status: stationsStatus) {
                if (station.station_id.equals(status.station_id)) {
                    station.numBikesAvailable = status.numBikesAvailable;
                    station.numDockAvailable = status.numDockAvailable;
                }
            }
        }

        return null;
    }


}
