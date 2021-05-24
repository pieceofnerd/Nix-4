package ua.com.alevel.impl.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.dao.LocationDao;
import ua.com.alevel.impl.ApplicationMainImpl;
import ua.com.alevel.model.Location;
import ua.com.alevel.service.models.LocationService;

import java.util.List;

public class LocationServiceImpl implements LocationService {

    private static final LocationDao dao = new LocationDao();
    private static final Logger logger = LoggerFactory.getLogger(LocationServiceImpl.class);

    @Override
    public void create(Location entity) {
        if (!dao.isExist(entity))
            dao.create(entity);
        else {
            logger.warn("Location {} has already been created", entity.getName());
        }
    }

    @Override
    public List<Location> findAll() {
        return dao.findAll();
    }

    @Override
    public boolean isExisted(Location entity) {
        return dao.isExist(entity);
    }

    @Override
    public int findIdByName(String name) {
        return dao.findIdByName(name);
    }

    @Override
    public Location findById(int id) {
        return dao.findById(id);
    }
}
