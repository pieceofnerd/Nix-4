package ua.com.alevel.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface BaseDao<T> {
    Logger logger = LoggerFactory.getLogger(BaseDao.class);

    void create(T entity);

    T findById(int id);

    List<T> findAll();

    boolean isExist(T entity);

    static void prepare() {
        try (Statement updateIndex = JDBCConnector.get().createStatement()) {
            String alterTable = "ALTER TABLE location AUTO_INCREMENT = 1";
            updateIndex.execute(alterTable);
        } catch (SQLException throwables) {
            logger.error("Some problems with sql");
        }
    }

}
