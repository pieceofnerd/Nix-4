package ua.com.alevel.service.models;

import ua.com.alevel.model.Location;

public interface LocationService extends BaseService<Location> {
    int findIdByName(String name);

    Location findById(int id);

}
