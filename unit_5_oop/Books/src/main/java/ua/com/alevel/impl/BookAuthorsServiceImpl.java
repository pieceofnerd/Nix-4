package ua.com.alevel.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Book;
import ua.com.alevel.service.BookAuthorService;

import java.util.Set;

public class BookAuthorsServiceImpl implements BookAuthorService {
    private final static Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class.getName());

    @Override
    public void addBookToAuthor(Book book, Author author) {
        logger.info("Start to add a book to the author");
        if (book != null && author != null) {
            Set<String> authors = book.getAuthorsId();
            authors.add(author.getId());
            book.setAuthorsId(authors);
            logger.info("Success add a book to the author");
        } else logger.info("Book wasn't added cause parameters are wrong");
    }

}
