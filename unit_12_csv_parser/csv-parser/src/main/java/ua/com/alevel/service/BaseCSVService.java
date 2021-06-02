package ua.com.alevel.service;

import ua.com.alevel.entity.StructureCSV;
import java.util.List;

public interface BaseCSVService<T> {

    List<T> mapAll(StructureCSV structure,Class<T> requiredClass);

    StructureCSV parse(String file);

}
