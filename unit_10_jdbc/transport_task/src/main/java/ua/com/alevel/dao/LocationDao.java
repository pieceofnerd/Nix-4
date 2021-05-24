package ua.com.alevel.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.model.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class LocationDao implements BaseDao<Location> {
    private static final Logger logger = LoggerFactory.getLogger(LocationDao.class);

    @Override
    public void create(Location entity) {
        String query = "Insert Into Location(name) values(?);\n";
        try (PreparedStatement createLocation = JDBCConnector.get().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            createLocation.setString(1, entity.getName());
            createLocation.execute();
            logger.info("Location {} was created", entity.getName());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            logger.error("SQL exception");
        }
    }

    @Override
    public Location findById(int id) {

        try (PreparedStatement findById = JDBCConnector.get().prepareStatement(
                "SELECT *  FROM Location WHERE id = ?;")) {
            findById.setInt(1, id);
            ResultSet resultSet = findById.executeQuery();
            String name;
            while (resultSet.next()) {
                name = resultSet.getString("name");
                return new Location(id, name);
            }
        } catch (SQLException throwables) {
            System.out.println("There is no location");
        }
        return null;

    }

    @Override
    public List<Location> findAll() {
        List<Location> locations = new ArrayList<>();
        try (PreparedStatement findAll = JDBCConnector.get().prepareStatement(
                "SELECT *  FROM Location ORDER BY id")) {
            ResultSet resultSet = findAll.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                locations.add(new Location(name));
            }
        } catch (SQLException throwables) {
            System.out.println("There is no location");
        }
        return locations;
    }

    @Override
    public boolean isExist(Location entity) {
        int id = findIdByName(entity.getName());
        return id != -1;
    }

    public int findIdByName(String name) {
        try (PreparedStatement findIdByName = JDBCConnector.get().prepareStatement(
                "SELECT id  FROM Location WHERE name = ?;")) {
            findIdByName.setString(1, name);
            ResultSet resultSet = findIdByName.executeQuery();

            while (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException throwables) {
            System.out.println("There is no location");
        }
        return -1;
    }
}
