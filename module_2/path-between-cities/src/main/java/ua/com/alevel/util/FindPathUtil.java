package ua.com.alevel.util;

import ua.com.alevel.entity.City;
import ua.com.alevel.impl.CityServiceImpl;
import ua.com.alevel.service.CityService;

import java.util.ArrayList;
import java.util.List;

public class FindPathUtil {
    private final static CityService cityService = new CityServiceImpl();
    private static final String file = "path-between-cities/src/main/java/ua/com/alevel/input.txt";

    public static List<Integer> computePaths(City source) {
        int size = FileUtil.getSize(file);
        List<List<Integer>> matrix = new ArrayList<>();
        List<Integer> minimumDistance = new ArrayList<>();
        List<Integer> attendedPoint = new ArrayList<>();
        int begin = source.getId();

        for (int i = 0; i < size; i++) {
            List<Integer> row = new ArrayList<>();
            City city = cityService.findCityById(i + 1);
            List<City> cities = cityService.findPathByCity(city);
            for (int j = 0; j < size; j++) {
                row.add(0);
            }
            for (int j = 1; j <= size; j++) {
                for (City city1 : cities) {
                    if (city1.getId() == j) {
                        row.set(j - 1, cityService.getPath(city, city1));
                    }
                }
            }
            matrix.add(row);
        }

        for (int i = 0; i < size; i++) {
            minimumDistance.add(Integer.MAX_VALUE);
            attendedPoint.add(1);
        }
        minimumDistance.set(0, 0);
        int minIndex;
        int min;
        do {
            minIndex = Integer.MAX_VALUE;
            min = Integer.MAX_VALUE;
            for (int i = 0; i < size; i++) {
                if (attendedPoint.get(i) == 1 && minimumDistance.get(i) < min) {
                    min = minimumDistance.get(i);
                    minIndex = i;
                }
            }
            if (minIndex != Integer.MAX_VALUE) {
                for (int i = 0; i < size; i++) {
                    if (matrix.get(minIndex).get(i) > 0) {
                        int temp = min + matrix.get(minIndex).get(i);
                        if (temp < minimumDistance.get(i)) {
                            minimumDistance.set(i, temp);
                        }
                    }
                }
                attendedPoint.set(minIndex, 0);
            }
        }
        while (minIndex < Integer.MAX_VALUE);
        return minimumDistance;
    }

}
