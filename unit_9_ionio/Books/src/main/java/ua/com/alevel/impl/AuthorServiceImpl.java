package ua.com.alevel.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.dao.AuthorDao;
import ua.com.alevel.dao.BookAuthorDao;
import ua.com.alevel.entity.Author;
import ua.com.alevel.service.AuthorService;

import java.util.Set;

public class AuthorServiceImpl implements AuthorService {
    private final static Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class.getName());
    private final AuthorDao authorDao=new AuthorDao();
    private final BookAuthorDao bookAuthorDao = new BookAuthorDao();

    @Override
    public void create(Author author) {
        logger.info("Creating author is started");
        authorDao.create(author);
        logger.info("Creating author is ended");
    }

    @Override
    public void update(Author author) {
        logger.info("Updating author is started");
        if (author == null) {
            logger.info("Updating author wasn't completed, cause author is null");
            return;
        }
        authorDao.update(author);
        logger.info("Updating author is ended");
    }

    @Override
    public void delete(String id) {
        logger.info("Deleting author is started");
        if (authorDao.find(id) == null) {
            logger.info("Deleting author wasn't completed, cause author is null");
            return;
        }
        authorDao.delete(id);
        logger.info("Deleting author is ended");
    }

    @Override
    public Author findById(String id) {
        logger.debug("finding author by id {}", id);
        return authorDao.find(id);

    }

    @Override
    public Set<Author> findAll() {
        logger.info("finding all authors");
        return authorDao.findAll();
    }

    @Override
    public Set<Author> findByBook(String id) {
        logger.debug("finding author by books ID {}", id);
        return bookAuthorDao.findAuthorsByBook(id);
    }
}
