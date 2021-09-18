package com.example.demo.dao;

import com.example.demo.model.Station;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository("Dao")
public class StationDataAccessService implements StationDao {

    private static List<Station> DB = new ArrayList<>();

    @Override
    public int insertStation(Station station) {
        DB.add(station);

        return 1;
    }

    @Override
    public int insertStationList(List<Station> stationList) {
        System.out.println(DB);
        DB.addAll(stationList);
        return 1;
    }

    @Override
    public List<Station> SelectAllStations() {
        return DB;
    }

    @Override
    public Optional<Station> selectStationById(String id) {
        return DB.stream()
            .filter(station -> station.getId().equals(id))
            .findFirst();
    }
}
