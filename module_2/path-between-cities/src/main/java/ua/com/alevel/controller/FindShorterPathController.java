package ua.com.alevel.controller;

import ua.com.alevel.impl.CityServiceImpl;
import ua.com.alevel.service.CityService;
import ua.com.alevel.util.FileUtil;
import ua.com.alevel.util.FilesConsoleUtil;
import ua.com.alevel.util.ParserCSV;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FindShorterPathController {
    private static final String findCity = "path-between-cities/findCity.csv";
    private static final String result = "path-between-cities/src/main/java/ua/com/alevel/output.txt";
    private static final String source = "path-between-cities/src/main/java/ua/com/alevel/input.txt";
    private CityService cityService = new CityServiceImpl();

    public void findShorterPathController() {
        cityService.createAll();
        List<String[]> cities = ParserCSV.readAllDataFromDatabase(findCity);
        fulfilAlgorithm(cities);
    }

    private void fulfilAlgorithm(List<String[]> cities) {
        cities.remove(0);
        for (String[] city : cities) {
            String[] file = FileUtil.readFile(result);
            if (Objects.isNull(file)) {
                file = new String[FileUtil.getSize(source)];
            }
            List<String> data = new ArrayList<>(Arrays.asList(file));
            data.add(String.valueOf(cityService.findPath(findCity, city[0], city[1])));
            FileUtil.writeFile(result, data);
        }
        FilesConsoleUtil.output();
    }

}
