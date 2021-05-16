package ua.com.alevel.dao;

import ua.com.alevel.entity.City;

import java.util.List;

public interface BaseDao {
    void createAll();

    List<City> findPathByCity(City city);

    City findCityById(int id);

    City findCityByName(String name);

    void writePath(int result);

    int getPath(City firstCity, City secondCity);
}
