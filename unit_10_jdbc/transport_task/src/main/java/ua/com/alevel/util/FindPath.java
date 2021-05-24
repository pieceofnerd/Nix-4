package ua.com.alevel.util;

import ua.com.alevel.impl.model.LocationServiceImpl;
import ua.com.alevel.model.Location;
import ua.com.alevel.model.Route;
import ua.com.alevel.service.models.LocationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FindPath {
    private static final LocationService locationService = new LocationServiceImpl();
    private static final int MAX_PATH = 200_000;

    public static int findBriefPath(List<Route> routes, List<Location> locations, Location cityFrom, Location cityTo) {
        List<List<Integer>> matrix = new ArrayList<>();
        for (int i = 0; i < locations.size(); i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < locations.size(); j++) {
                row.add(Integer.MAX_VALUE);
            }
            matrix.add(row);
        }
        List<Integer> currentCost = new ArrayList<>();
        List<Integer> attended = new ArrayList<>();
        for (int i = 0; i < locations.size(); i++) {
            Optional<Integer> min;
            List<Route> currentRoutes = new ArrayList<>();
            for (Route route : routes) {
                if (route.getFromId() == locationService.findIdByName(cityFrom.getName())) {
                    currentRoutes.add(route);
                }
            }
            for (int j = 0; j < locations.size(); j++) {
                if (cityFrom.getName().equals(locations.get(j).getName()) && i == 0) {
                    matrix.get(i).set(j, 0);
                    attended.add(j);
                } else {
                    for (Route currentRoute : currentRoutes) {
                        if (locationService.findIdByName(locations.get(j).getName()) == currentRoute.getToId()) {
                            if (i != 0) {
                                if (currentRoute.getCost() + currentCost.get(locations.indexOf(cityFrom)) < matrix.get(i - 1).get(j)) {
                                    matrix.get(i).set(j, currentRoute.getCost() + currentCost.get(locations.indexOf(cityFrom)));
                                } else {
                                    matrix.get(i).set(j, matrix.get(i - 1).get(j));
                                }
                            } else if (currentRoute.getCost() < matrix.get(i).get(j)) {
                                matrix.get(i).set(j, currentRoute.getCost());
                            }

                        }

                    }
                    if (matrix.get(i).get(j) == Integer.MAX_VALUE && i != 0) {
                        matrix.get(i).set(j, matrix.get(i - 1).get(j));
                    }
                }

            }
            attended.add(locations.indexOf(cityFrom));

            int finalI1 = i;
            min = matrix.get(i)
                    .stream()
                    .filter(p -> {
                        boolean flag = true;
                        for (Integer integer : attended) {
                            if (matrix.get(finalI1).indexOf(p) == integer) {
                                flag = false;
                            }
                        }
                        return flag;
                    })
                    .min(Integer::compareTo);


            int finalI = i;
            if (min.isPresent()) {
                int finalMin = min.get();

                cityFrom = locations
                        .stream()
                        .filter(l -> matrix.get(finalI).get(locations.indexOf(l)) == finalMin)
                        .findFirst()
                        .get();

                currentCost = matrix.get(i);
            }

        }
        int path = matrix.get(locations.size() - 1).get(locationService.findIdByName(cityTo.getName()) - 1);
        if (path < MAX_PATH)
            return path;
        else return -1;
    }

}
