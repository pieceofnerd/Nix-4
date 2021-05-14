package ua.com.alevel.service;

import java.util.Set;

public interface BaseDao<T> {

    void create(T t);

    void update(T t);

    void delete(String id);

    T find(String id);

    Set<T> findAll();

}
