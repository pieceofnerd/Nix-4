package ua.com.alevel.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.annotation.CellCSV;
import ua.com.alevel.dao.BaseCSVDao;
import ua.com.alevel.dao.CSVDao;
import ua.com.alevel.entity.StructureCSV;
import ua.com.alevel.service.BaseCSVService;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CSVServiceImpl<T> implements BaseCSVService<T> {

    private final Logger logger = LoggerFactory.getLogger(CSVServiceImpl.class);

    @Override
    public StructureCSV parse(String file) {
        BaseCSVDao csvDao = new CSVDao();
        return csvDao.readCSV(file);
    }

    @Override
    public List<T> mapAll(StructureCSV structure, Class<T> requiredClass) {
        List<T> listCSVObjects = new ArrayList<>();
        List<String[]> data = structure.getData();
        for (String[] array : data) {
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

            for (int i = 0; i < fields.length; i++) {

                if (fields[i].isAnnotationPresent(CellCSV.class)) {
                    fields[i].setAccessible(true);
                    Class<?> type = fields[i].getType();
                    try {
                        if (type == int.class || type == Integer.class) {
                            fields[i].set(object, Integer.parseInt(array[i]));

                        } else if (type == String.class) {
                            fields[i].set(object, array[i]);

                        } else if (type == Boolean.class || type == boolean.class) {
                            fields[i].set(object, Boolean.parseBoolean(array[i]));

                        } else if (type.isEnum()) {
                            //noinspection unchecked,rawtypes
                            fields[i].set(object, Enum.valueOf((Class<Enum>) fields[i].getType(), array[i]));

                        } else {
                            logger.warn("There is a wrong field in java class ");
                        }
                    } catch (IllegalAccessException e) {
                        logger.error("Something goes wrong during parsing annotation");
                        throw new RuntimeException();
                    }
                }

            }
            listCSVObjects.add(object);
        }

        return listCSVObjects;
    }

}
