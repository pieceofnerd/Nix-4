package ua.com.alevel.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.com.alevel.model.Solution;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SolutionDao implements BaseDao<Solution> {
    private static final Logger logger = LoggerFactory.getLogger(SolutionDao.class);


    @Override
    public void create(Solution entity) {
        String query = "Insert Into Solutions(problem_id, cost) values(?,?);";
        try (PreparedStatement createRoute = JDBCConnector.get().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            createRoute.setInt(1, entity.getProblemId());
            createRoute.setInt(2, entity.getCost());
            createRoute.execute();
            logger.info("Solutions of {} problem is {} was created",
                    entity.getProblemId(), entity.getCost());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            logger.error("SQL exception");
        }
    }

    @Override
    public Solution findById(int id) {
        try (PreparedStatement findById = JDBCConnector.get().prepareStatement(
                "SELECT *  FROM solutions WHERE problem_id = ?;")) {
            findById.setInt(1, id);
            ResultSet resultSet = findById.executeQuery();

            int cost;
            while (resultSet.next()) {
                cost = resultSet.getInt("cost");
                return new Solution(id, cost);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            logger.error("SQL exception");
        }
        return null;
    }

    @Override
    public List<Solution> findAll() {
        List<Solution> solutions = new ArrayList<>();
        try (PreparedStatement findAll = JDBCConnector.get().prepareStatement(
                "SELECT *  FROM  solutions")) {
            ResultSet resultSet = findAll.executeQuery();

            while (resultSet.next()) {
                int problemId = resultSet.getInt("problem_id");
                int cost = resultSet.getInt("cost");
                solutions.add(new Solution(problemId, cost));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            logger.error("SQL exception");
        }
        return solutions;
    }

    @Override
    public boolean isExist(Solution entity) {
        try (PreparedStatement findSolution = JDBCConnector.get().prepareStatement(
                "SELECT *  FROM  Solutions WHERE problem_id=?")) {
            findSolution.setInt(1, entity.getProblemId());
            ResultSet resultSet = findSolution.executeQuery();
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
