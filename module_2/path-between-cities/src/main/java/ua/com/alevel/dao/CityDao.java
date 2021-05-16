package ua.com.alevel.dao;

import ua.com.alevel.db.Database;
import ua.com.alevel.entity.City;

import java.util.List;

public class CityDao implements BaseDao {
    private static final Database db = Database.getInstance();

    @Override
    public void createAll() {

        db.createAll();
    }

    @Override
    public List<City> findPathByCity(City city) {
        return db.findAllPathByCity(city);
    }

    @Override
    public City findCityById(int id) {
        return db.findCityById(id);
    }

    @Override
    public City findCityByName(String name) {
        return db.findCityByName(name);
    }

    @Override
    public void writePath(int result) {
        db.writePath(result);
    }

    @Override
    public int getPath(City firstCity, City secondCity) {
        return db.getPath(firstCity, secondCity);
    }
}
