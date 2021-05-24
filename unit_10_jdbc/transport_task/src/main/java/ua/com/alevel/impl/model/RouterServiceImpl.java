package ua.com.alevel.impl.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.dao.BaseDao;
import ua.com.alevel.dao.RouteDao;
import ua.com.alevel.impl.ApplicationMainImpl;
import ua.com.alevel.model.Route;
import ua.com.alevel.service.models.RouteService;

import java.util.List;

public class RouterServiceImpl implements RouteService {
    private static final BaseDao<Route> routeDao = new RouteDao();
    private static final Logger logger = LoggerFactory.getLogger(RouterServiceImpl.class);

    @Override
    public void create(Route entity) {
        if (!routeDao.isExist(entity))
            routeDao.create(entity);
        else{
            logger.warn("Route from {} to {} has already been created",entity.getFromId(), entity.getToId() );
        }
    }

    @Override
    public List<Route> findAll() {
        return routeDao.findAll();
    }

    @Override
    public boolean isExisted(Route entity) {
        return routeDao.isExist(entity);
    }
}
