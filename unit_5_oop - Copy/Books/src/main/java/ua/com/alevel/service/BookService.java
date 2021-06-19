package ua.com.alevel.service;

import ua.com.alevel.entity.Book;


import java.util.Set;

public interface BookService<B extends Book> {

    void create(B book);

    void update(B book);

    void delete(String id);

    B findById(String id);

    B findByTitle(String title);

    Set<B> findAll();

    Set<B> findByAuthor(String authorId);

}
