package ua.com.alevel.dao;

import java.util.List;

public interface BaseDao<Type> {
    List<String> getAll(String file);
}
