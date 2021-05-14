package ua.com.alevel.dao;

import ua.com.alevel.db.DBInCSV;
import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Fiction;

import java.util.Set;

public class BookAuthorDao {

    private final DBInCSV db = DBInCSV.getInstance();

    public Set<Author> findAuthorsByBook(String bookId) {
       return db.findAuthorsByBooks(bookId);
    }

    public Set<Fiction> findBooksByAuthor(String authorId) {
        return db.findBooksByAuthorID(authorId);
    }

    public void add(Fiction fiction, Author author){
        db.addAuthorToBook(author.getId(),fiction.getId());
    }
}
