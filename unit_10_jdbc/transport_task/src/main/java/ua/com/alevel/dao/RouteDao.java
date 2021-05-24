package ua.com.alevel.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.com.alevel.model.Route;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RouteDao implements BaseDao<Route> {
    private static final Logger logger = LoggerFactory.getLogger(RouteDao.class);
    private static final LocationDao locationDao = new LocationDao();

    @Override
    public void create(Route entity) {
        String query = "Insert Into Routes(from_id, to_id,cost) values(?,?,?);";
        try (PreparedStatement createRoute = JDBCConnector.get().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            createRoute.setInt(1, entity.getFromId());
            createRoute.setInt(2, entity.getToId());
            createRoute.setInt(3, entity.getCost());
            createRoute.execute();
            logger.info("Route from {} to {} was created",
                    locationDao.findById(entity.getFromId()).getName(), locationDao.findById(entity.getToId()).getName());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            logger.error("SQL exception");
        }
    }

    @Override
    public Route findById(int id) {
        try (PreparedStatement findById = JDBCConnector.get().prepareStatement(
                "SELECT *  FROM routes WHERE id = ?;")) {
            findById.setInt(1, id);
            ResultSet resultSet = findById.executeQuery();

            int idFrom;
            int idTo;
            while (resultSet.next()) {
                idFrom = resultSet.getInt("from_id");
                idTo = resultSet.getInt("to_id");
                return new Route(id, idFrom, idTo);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            logger.error("SQL exception");
        }
        return null;
    }

    @Override
    public List<Route> findAll() {
        List<Route> routes = new ArrayList<>();
        try (PreparedStatement findAll = JDBCConnector.get().prepareStatement(
                "SELECT *  FROM  Routes")) {
            ResultSet resultSet = findAll.executeQuery();

            while (resultSet.next()) {
                int fromId = resultSet.getInt("from_id");
                int toId = resultSet.getInt("to_id");
                int cost = resultSet.getInt("cost");
                routes.add(new Route(fromId, toId, cost));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            logger.error("SQL exception");
        }
        return routes;
    }

    @Override
    public boolean isExist(Route entity) {
        try (PreparedStatement findRoute = JDBCConnector.get().prepareStatement(
                "SELECT *  FROM  Routes WHERE from_id=? AND to_id=?")) {
            findRoute.setInt(1, entity.getFromId());
            findRoute.setInt(2, entity.getToId());
            ResultSet resultSet = findRoute.executeQuery();

            if (resultSet.next()) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            logger.error("SQL exception");
        }
        return false;
    }
}
