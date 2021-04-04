package ua.com.alevel.service;

import ua.com.alevel.entity.Author;

import java.util.Set;

public interface AuthorService {

    void create(Author author);

    void update(Author author);

    void delete(String id);

    Author findById(String id);

    Set<Author> findAll();

    Set<String> findByBook(String id);
}
