package ua.com.alevel.impl;

import ua.com.alevel.dao.CityDao;
import ua.com.alevel.entity.City;
import ua.com.alevel.service.CityService;
import ua.com.alevel.util.FindPathUtil;

import java.util.List;

public class CityServiceImpl implements CityService {
    private CityDao dao = new CityDao();


    @Override
    public void createAll() {

        dao.createAll();
    }

    @Override
    public int findPath(String file, String firstCity, String secondCity) {
        City cityFirst = findCityByName(firstCity);
        City citySecond = findCityByName(secondCity);
        List<Integer> minimalDistance = FindPathUtil.computePaths(cityFirst);
        return minimalDistance.get(citySecond.getId()-1);

    }

    @Override
    public List<City> findPathByCity(City city) {
        return dao.findPathByCity(city);
    }

    @Override
    public City findCityById(int id) {
        return dao.findCityById(id);
    }

    @Override
    public City findCityByName(String name) {
        return dao.findCityByName(name);
    }

    @Override
    public int getPath(City firstCity, City secondCity) {
        return dao.getPath(firstCity, secondCity);
    }

    @Override
    public void writePath(int result) {
        dao.writePath(result);
    }


}
