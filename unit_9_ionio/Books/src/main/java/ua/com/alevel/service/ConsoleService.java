package ua.com.alevel.service;

import java.util.Set;

public interface ConsoleService<T> {
    T create();

    T update();

    String delete();

    void findAll(Set<T> values);
}
