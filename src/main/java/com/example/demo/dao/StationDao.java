package com.example.demo.dao;

import com.example.demo.model.Station;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StationDao {

    int insertStation(Station station);

    int insertStationList(List<Station> stationList);



    List<Station> SelectAllStations();

    Optional<Station> selectStationById(String id);
}
