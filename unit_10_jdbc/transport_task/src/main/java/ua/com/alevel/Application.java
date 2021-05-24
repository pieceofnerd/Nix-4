package ua.com.alevel;

import ua.com.alevel.impl.ApplicationMainImpl;
import ua.com.alevel.impl.model.LocationServiceImpl;
import ua.com.alevel.impl.model.ProblemServiceImpl;
import ua.com.alevel.impl.model.RouterServiceImpl;


import ua.com.alevel.impl.model.SolutionServiceImpl;
import ua.com.alevel.model.Location;
import ua.com.alevel.model.Problem;
import ua.com.alevel.model.Route;
import ua.com.alevel.model.Solution;
import ua.com.alevel.service.ApplicationService;
import ua.com.alevel.service.models.LocationService;
import ua.com.alevel.service.models.ProblemService;
import ua.com.alevel.service.models.RouteService;
import ua.com.alevel.service.models.SolutionService;
import ua.com.alevel.util.FindPath;

import java.util.List;


public class Application {
    public static void main(String[] args) {
        ApplicationService applicationService = new ApplicationMainImpl();
        applicationService.prepareData();
        runApplication();
    }

    private static void runApplication() {
        LocationService locationService = new LocationServiceImpl();
        RouteService routeService = new RouterServiceImpl();
        ProblemService problemService = new ProblemServiceImpl();
        SolutionService solutionService = new SolutionServiceImpl();

        List<Location> locations = locationService.findAll();
        List<Route> routes = routeService.findAll();
        List<Problem> problems = problemService.findAll();

        problems.forEach(p -> {
            Location locationFrom = locationService.findById(p.getFromId());
            Location locationTo = locationService.findById(p.getToId());
            int result = FindPath.findBriefPath(routes, locations, locationFrom, locationTo);
            solutionService.create(new Solution(p.getId(), result));
        });
    }
}
