package ua.com.alevel.service.models;

import java.util.List;

public interface BaseService<T> {
    void create(T entity);

    List<T> findAll();

    boolean isExisted(T entity);
}
