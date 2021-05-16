package ua.com.alevel.service;

import ua.com.alevel.entity.City;

import java.util.List;

public interface CityService {
    void createAll();

    int findPath(String file, String firstCity, String secondCity);

    List<City> findPathByCity(City city);

    City findCityById(int id);

    City findCityByName(String name);

    int getPath(City firstCity, City secondCity);

    void writePath(int result);
}
