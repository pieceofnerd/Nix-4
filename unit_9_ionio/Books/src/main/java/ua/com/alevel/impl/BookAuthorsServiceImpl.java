package ua.com.alevel.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.dao.BookAuthorDao;
import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Book;
import ua.com.alevel.entity.Fiction;
import ua.com.alevel.service.BookAuthorService;

public class BookAuthorsServiceImpl implements BookAuthorService {
    private final static Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class.getName());
    private final BookAuthorDao bookAuthorDao = new BookAuthorDao();
    @Override
    public void addBookToAuthor(Book book, Author author) {
        logger.info("Start to add a book to the author");
        if (book != null && author != null) {
           bookAuthorDao.add((Fiction) book,author);
            logger.info("Success add a book to the author");
        } else logger.info("Book wasn't added cause parameters are wrong");
    }

}
