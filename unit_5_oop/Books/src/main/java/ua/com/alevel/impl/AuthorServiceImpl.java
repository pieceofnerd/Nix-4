package ua.com.alevel.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.db.DBInFile;
import ua.com.alevel.entity.Author;
import ua.com.alevel.service.AuthorService;

import java.util.Set;

public class AuthorServiceImpl implements AuthorService {
    private final static Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class.getName());
    private DBInFile db = DBInFile.getInstance();

    @Override
    public void create(Author author) {
        logger.info("Creating author is started");
        db.createAuthor(author);
        logger.info("Creating author is ended");
    }

    @Override
    public void update(Author author) {
        logger.info("Updating author is started");
        if (author == null) {
            logger.info("Updating author wasn't completed, cause author is null");
            return;
        }
        db.updateAuthor(author);
        logger.info("Updating author is ended");
    }

    @Override
    public void delete(String id) {
        logger.info("Deleting author is started");
        if (db.findAuthorById(id) == null) {
            logger.info("Deleting author wasn't completed, cause author is null");
            return;
        }
        db.deleteAuthor(id);
        logger.info("Deleting author is ended");
    }

    @Override
    public Author findById(String id) {
        logger.debug("finding author by id {}", id);
        return db.findAuthorById(id);

    }

    @Override
    public Set<Author> findAll() {
        logger.info("finding all authors");
        return db.findAllAuthors();
    }

    @Override
    public Set<String> findByBook(String id) {
        logger.debug("finding author by books ID {}", id);
        return db.findAuthorsByBooks(id);
    }
}
