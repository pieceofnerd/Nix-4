package ua.com.alevel.util;

import ua.com.alevel.dao.JDBCConnector;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

public class JDBCRunnerUtil {
    private static final String pathToJDBCProperties = "/jdbc.properties";

    public static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = JDBCConnector.class.getResourceAsStream(pathToJDBCProperties)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return properties;
    }
}
