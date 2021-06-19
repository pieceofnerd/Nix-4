package ua.com.alevel.service;

import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Book;


public interface BookAuthorService {
    void addBookToAuthor(Book book, Author author);

}
