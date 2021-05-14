package ua.com.alevel.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.dao.BookDao;
import ua.com.alevel.entity.Fiction;
import ua.com.alevel.service.BookService;

import java.util.Set;

public class FictionServiceImpl implements BookService<Fiction> {

    private final static Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class.getName());
    private final BookDao bookDao = new BookDao();

    @Override
    public void create(Fiction fiction) {
        logger.info("Creating book is started");
        if (bookDao.exist(fiction.getTitle())) {
            logger.error("Book with this title  already exists");
            throw new RuntimeException("user already exist");
        }
        bookDao.create(fiction);
        logger.info("Creating book is ended");
    }

    @Override
    public void update(Fiction fiction) {
        logger.info("Updating book is started");
        bookDao.update(fiction);
        logger.info("Updating book is ended");
    }

    @Override
    public void delete(String id) {
        logger.info("Deleting book is started");
        bookDao.delete(id);
        logger.info("Deleting book is ended");
    }

    @Override
    public Fiction findById(String id) {
        logger.debug("finding book by id {}", id);
        return bookDao.find(id);
    }

    @Override
    public Fiction findByTitle(String title) {
        logger.debug("finding book by title {}", title);
        return bookDao.findByTitle(title);
    }

    @Override
    public Set<Fiction> findAll() {
        logger.info("finding all books");
        return bookDao.findAll();
    }

}
