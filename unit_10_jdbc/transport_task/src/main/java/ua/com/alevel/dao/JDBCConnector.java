package ua.com.alevel.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.util.JDBCRunnerUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCConnector {

    private static final Logger logger = LoggerFactory.getLogger(JDBCConnector.class);
    private static final Properties properties = JDBCRunnerUtil.loadProperties();
    private static final String url = properties.getProperty("url");

    private static Connection connection;


    private JDBCConnector() {
        try {
            connection = DriverManager.getConnection(url, properties);
            logger.info("Connect to url {}", url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            logger.error("SQL exception");
        }
    }

    public static Connection get() {
        if (connection == null) {
            new JDBCConnector();
        }
        return connection;
    }
}
