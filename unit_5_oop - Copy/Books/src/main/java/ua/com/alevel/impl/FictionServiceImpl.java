package ua.com.alevel.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.entity.Fiction;
import ua.com.alevel.service.BookService;
import ua.com.alevel.db.DBInMemory;

import java.util.Set;

public class FictionServiceImpl implements BookService<Fiction> {
    private final DBInMemory db = DBInMemory.getInstance();
    private final static Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class.getName());

    @Override
    public void create(Fiction fiction) {
        logger.info("Creating book is started");
        if (db.existBookByTitle(fiction.getTitle())) {
            logger.error("Book with this title  already exists");
            throw new RuntimeException("user already exist");
        }
        db.createBook(fiction);
        logger.info("Creating book is ended");
    }

    @Override
    public void update(Fiction fiction) {
        logger.info("Updating book is started");
        db.updateBook(fiction);
        logger.info("Updating book is ended");
    }

    @Override
    public void delete(String id) {
        logger.info("Deleting book is started");
        db.deleteBook(id);
        logger.info("Deleting book is ended");
    }

    @Override
    public Fiction findById(String id) {
        logger.debug("finding book by id {}", id);
        return null;
    }

    @Override
    public Fiction findByTitle(String title) {
        logger.debug("finding book by title {}", title);
        return db.findBookByTitle(title);
    }

    @Override
    public Set<Fiction> findAll() {
        logger.info("finding all books");
        return db.findAllBooks();
    }

    @Override
    public Set<Fiction> findByAuthor(String authorId) {
        logger.info("finding book by author");
        Set<Fiction> fictions = db.findBooksByAuthorID(authorId);
        if (fictions == null) {
            logger.error("Author has no books");
        }
        return fictions;
    }


}
