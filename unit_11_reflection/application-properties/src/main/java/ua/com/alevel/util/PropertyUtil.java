package ua.com.alevel.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.annotation.PropertyKey;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class PropertyUtil {
    private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);

    public static <T> T initialize(String propertiesFile, Class<T> requiredClass) {
        T object;
        try {
            Constructor<T> constructor = requiredClass.getConstructor();
            object = constructor.newInstance();
        } catch (NoSuchMethodException e) {
            logger.error("There is no standard constructor ");
            throw new RuntimeException();
        } catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
            logger.error("Problem occurs during  creating object ");
            throw new RuntimeException();
        }

        Field[] fields = object.getClass().getDeclaredFields();

        Properties properties = getProperties(propertiesFile);

        for (Field field : fields) {
            if (field.isAnnotationPresent(PropertyKey.class)) {
                field.setAccessible(true);
                Class<?> type = field.getType();
                try {
                    if (type == int.class || type == Integer.class) {
                        field.set(object, Integer.parseInt(properties.getProperty(field.getAnnotation(PropertyKey.class).value())));
                    } else if (type == String.class) {
                        field.set(object, properties.getProperty(field.getAnnotation(PropertyKey.class).value()));
                    } else if (type == Boolean.class || type == boolean.class) {
                        field.set(object, Boolean.parseBoolean(properties.getProperty(field.getAnnotation(PropertyKey.class).value())));
                    } else if (type.isEnum()) {
                        //noinspection unchecked,rawtypes
                        field.set(object, Enum.valueOf((Class<Enum>) field.getType(), properties.getProperty(field.getAnnotation(PropertyKey.class).value())));
                    } else {
                        logger.warn("There is a wrong field in java class ");
                    }
                } catch (IllegalAccessException e) {
                    logger.error("Something goes wrong during parsing annotation");
                    throw new RuntimeException();
                }
            }
        }
        return object;
    }

    private static Properties getProperties(String propertiesFile) {
        Properties properties = new Properties();
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesFile)) {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("Error occurred during loading properties");
            throw new RuntimeException();
        }
        return properties;

    }

}
