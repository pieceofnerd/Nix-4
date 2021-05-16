package ua.com.alevel.db;

import ua.com.alevel.entity.City;
import ua.com.alevel.util.FileUtil;
import ua.com.alevel.util.ParserCSV;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Database {
    private static Database database;
    private static final String cityHeader = "City id, City name";
    private static final String pathHeader = "First city id, Second city id, Path";
    private static final String findCityHeader = "First city id, Second city id";
    private static final String cityTablePath = "path-between-cities/city.csv";
    private static final String pathTablePath = "path-between-cities/path.csv";
    private static final String findCityTablePath = "path-between-cities/findCity.csv";
    private static final String file = "path-between-cities/src/main/java/ua/com/alevel/input.txt";
    private static final String result = "path-between-cities/src/main/java/ua/com/alevel/output.txt";

    private Database() {
        ParserCSV.initializeTable(findCityHeader, findCityTablePath);
        ParserCSV.initializeTable(cityHeader, cityTablePath);
        ParserCSV.initializeTable(pathHeader, pathTablePath);
    }

    public static Database getInstance() {
        if (Objects.isNull(database)) {
            database = new Database();
        }
        return database;
    }

    public City findCityByName(String name) {
        List<String[]> cities = ParserCSV.readAllDataFromDatabase(cityTablePath);
        String[] currentCity = cities
                .stream()
                .filter(b -> b[1].equals(name))
                .findFirst()
                .orElse(null);

        if (currentCity == null) {
            return null;
        } else return new City(Integer.parseInt(currentCity[0]), currentCity[1]);
    }

    public City findCityById(int id) {
        List<String[]> cities = ParserCSV.readAllDataFromDatabase(cityTablePath);
        String[] currentCity = cities
                .stream()
                .filter(b -> b[0].equals(String.valueOf(id)))
                .findFirst()
                .orElse(null);

        if (currentCity == null) {
            return null;
        } else return new City(Integer.parseInt(currentCity[0]), currentCity[1]);
    }


    public List<City> findAllPathByCity(City city) {
        List<City> cities = new ArrayList<>();
        List<String[]> citiesFromBd = ParserCSV.readAllDataFromDatabase(pathTablePath);
        citiesFromBd.remove(0);
        for (String[] c : citiesFromBd) {
            if (city.getId() == Integer.parseInt(c[0])) {
                cities.add(findCityById(Integer.parseInt(c[1])));
            }
        }
        return cities;
    }

    public int getPath(City cityFirst, City citySecond) {
        int path = 0;
        List<String[]> citiesFromBd = ParserCSV.readAllDataFromDatabase(pathTablePath);
        citiesFromBd.remove(0);
        for (String[] c : citiesFromBd) {

            if (cityFirst.getId() == Integer.parseInt(c[0]) && citySecond.getId() == Integer.parseInt(c[1])) {
                path = Integer.parseInt(c[2]);
            }
        }
        return path;
    }

    private void writeIntoCsv(List<City> cityList) {
        cityList.forEach(c -> ParserCSV.writeIntoDatabase(c, cityTablePath));
        cityList.forEach(c -> {
            Set<List<Integer>> keys = c.getNeighbours().keySet();
            keys.forEach(k -> ParserCSV.writePathIntoDatabase(k.get(0), k.get(1), c.getNeighbours().get(k), pathTablePath));
        });

    }

    public void createAll() {
        String[] citiesList = FileUtil.readFile(file);
        List<City> cities = new ArrayList<>();
        if (Objects.isNull(citiesList)) {
            return;
        }
        String name;
        int id;
        int counter = 1;
        for (int i = 2; i < citiesList.length; ) {
            name = citiesList[i - 1];
            id = counter;
            Map<List<Integer>, Integer> path = new LinkedHashMap<>();
            try {
                int number = Integer.parseInt(citiesList[i]);
                int innerCounter = 0;
                for (int j = 1; j <= number; j++) {
                    String[] distance = citiesList[i + j].split("\\s");
                    List<Integer> pairs = new ArrayList<>();
                    pairs.add(counter);
                    pairs.add(Integer.parseInt(distance[0]));
                    int value = Integer.parseInt(distance[1]);
                    path.put(pairs, value);
                    innerCounter = j;
                }
                cities.add(new City(id, name, path));
                i += innerCounter + 2;
                counter++;
                if (counter > FileUtil.getSize(file)) {
                    for (int j = 0; j < Integer.parseInt(citiesList[i - 1]); j++) {
                        String[] twoCity = citiesList[i + j].split("\\s");
                        ParserCSV.writeFindCityIntoDatabase(twoCity[0], twoCity[1], findCityTablePath);
                    }
                    i = citiesList.length + 1;
                }

            } catch (ClassCastException c) {
                throw new ClassCastException("Incorrect file");
            }
        }
        writeIntoCsv(cities);

    }

    public void writePath(int answer) {
        FileUtil.writeFile(result, String.valueOf(answer));
    }

}
