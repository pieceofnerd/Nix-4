package ua.com.alevel.dao;

import ua.com.alevel.db.DBInCSV;
import ua.com.alevel.entity.Author;
import ua.com.alevel.service.BaseDao;

import java.util.Set;

public class AuthorDao implements BaseDao<Author> {
    private final DBInCSV db = DBInCSV.getInstance();

    @Override
    public void create(Author author) {
        db.createAuthor(author);
    }

    @Override
    public void update(Author author) {
        db.updateAuthor(author);
    }

    @Override
    public void delete(String id) {
        db.deleteAuthor(id);
    }

    @Override
    public Author find(String id) {
        return db.findAuthorById(id);
    }

    @Override
    public Set<Author> findAll() {
        return db.findAllAuthors();
    }
}
