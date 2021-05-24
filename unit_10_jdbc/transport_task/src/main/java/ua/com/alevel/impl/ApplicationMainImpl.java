package ua.com.alevel.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.dao.BaseDao;
import ua.com.alevel.impl.model.LocationServiceImpl;
import ua.com.alevel.impl.model.ProblemServiceImpl;
import ua.com.alevel.impl.model.RouterServiceImpl;
import ua.com.alevel.model.Location;
import ua.com.alevel.model.Problem;
import ua.com.alevel.model.Route;
import ua.com.alevel.service.ApplicationService;
import ua.com.alevel.service.models.LocationService;
import ua.com.alevel.service.models.ProblemService;
import ua.com.alevel.service.models.RouteService;
import ua.com.alevel.util.ParserUtil;

import java.util.ArrayList;
import java.util.List;

public class ApplicationMainImpl implements ApplicationService {
    private static final String file = "transport_task/src/main/resources/input.txt";
    private static final Logger logger = LoggerFactory.getLogger(ApplicationMainImpl.class);

    @Override
    public void prepareData() {

        LocationService locationService = new LocationServiceImpl();
        RouteService routeService = new RouterServiceImpl();
        ProblemService problemService = new ProblemServiceImpl();
        BaseDao.prepare();

        String data = ParserUtil.readFile(file);
        String[] lines = data.split("\\r\\n");

        List<Location> locations = new ArrayList<>();
        int size = getSize(lines);
        int count = 1;
        for (int i = 1; i < size + 1; i++) {
            Location location = new Location(lines[i]);
            if (!locationService.isExisted(location)) {
                locationService.create(location);
                locations.add(location);
            } else logger.warn("Location {} is already existed", location.getName());
            count++;
        }

        for (Location location : locations) {
            int length = Integer.parseInt(lines[count]);
            for (int j = 0; j < length; j++) {
                String[] pair = lines[count + 1].split("\\s");
                Route route = new Route(
                        locationService.findIdByName(location.getName()),
                        Integer.parseInt(pair[0]), Integer.parseInt(pair[1]));
                if (!routeService.isExisted(route))
                    routeService.create(route);
                else logger.warn("Route from {} to {} is already existed",
                        locationService.findById(route.getFromId()).getName(), locationService.findById(route.getToId()).getName());
                count++;
            }
            count++;
        }

        int length = Integer.parseInt(lines[count]);
        for (int i = 0; i < length; i++) {
            String[] pair = lines[count + 1].split("\\s");
            if (locationService.findIdByName(pair[0]) != -1 && locationService.findIdByName(pair[1]) != 1) {
                Problem problem = new Problem(locationService.findIdByName(pair[0]), locationService.findIdByName(pair[1]));
                if (!problemService.isExisted(problem))
                    problemService.create(problem);
                else logger.warn("Problem from {} to {} is already existed",
                        locationService.findById(problem.getFromId()).getName(), locationService.findById(problem.getToId()).getName());
            }
            count++;
        }
    }

    private int getSize(String[] data) {
        try {
            return Integer.parseInt(data[0]);
        } catch (NumberFormatException numberFormatException) {
            logger.error("Incorrect format");
            throw new RuntimeException();
        }
    }
}
