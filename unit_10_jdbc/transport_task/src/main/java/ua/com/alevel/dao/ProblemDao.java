package ua.com.alevel.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.model.Problem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProblemDao implements BaseDao<Problem> {
    private static final Logger logger = LoggerFactory.getLogger(ProblemDao.class);
    private static final LocationDao locationDao = new LocationDao();

    @Override
    public void create(Problem entity) {
        String query = "Insert Into Problems(from_id, to_id) values(?,?);\n";
        try (PreparedStatement createLocation = JDBCConnector.get().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            createLocation.setInt(1, entity.getFromId());
            createLocation.setInt(2, entity.getToId());
            createLocation.execute();
            logger.info("Problem was created. Path from {} to {}",
                    locationDao.findById(entity.getFromId()).getName(), locationDao.findById(entity.getToId()).getName());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            logger.error("SQL exception");
        }
    }

    @Override
    public Problem findById(int id) {
        try (PreparedStatement findById = JDBCConnector.get().prepareStatement(
                "SELECT *  FROM Problems WHERE id = ?;")) {
            findById.setInt(1, id);
            ResultSet resultSet = findById.executeQuery();

            int idFrom;
            int idTo;
            while (resultSet.next()) {
                idFrom = resultSet.getInt("from_id");
                idTo = resultSet.getInt("to_id");
                return new Problem(id, idFrom, idTo);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            logger.error("SQL exception");
        }
        return null;
    }

    @Override
    public List<Problem> findAll() {
        List<Problem> problems = new ArrayList<>();
        try (PreparedStatement findAll = JDBCConnector.get().prepareStatement(
                "SELECT *  FROM Problems ORDER BY id")) {
            ResultSet resultSet = findAll.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int idFrom = resultSet.getInt("from_id");
                int idTo = resultSet.getInt("to_id");
                problems.add(new Problem(id, idFrom, idTo));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            logger.error("SQL exception");
        }
        return problems;
    }

    @Override
    public boolean isExist(Problem entity) {
        try (PreparedStatement findProblem = JDBCConnector.get().prepareStatement(
                "SELECT *  FROM  Problems WHERE from_id=? AND to_id=?")) {
            findProblem.setInt(1, entity.getFromId());
            findProblem.setInt(2, entity.getToId());
            ResultSet resultSet = findProblem.executeQuery();
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
