package ua.com.alevel.dao;

import ua.com.alevel.entity.StructureCSV;

public interface BaseCSVDao {

    StructureCSV readCSV(String file);

}
