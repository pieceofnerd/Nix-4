package ua.com.alevel;

import org.junit.Assert;
import org.junit.Test;
import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Fiction;
import ua.com.alevel.impl.AuthorServiceImpl;
import ua.com.alevel.impl.BookAuthorsServiceImpl;
import ua.com.alevel.impl.FictionServiceImpl;
import ua.com.alevel.service.AuthorService;
import ua.com.alevel.service.BookAuthorService;
import ua.com.alevel.service.BookService;

import java.util.HashSet;
import java.util.Set;

public class FullCrudTest {
    private static final String title = "Harry Potter";
    private static final BookService<Fiction> fictionService = new FictionServiceImpl();
    private static final AuthorService authorService = new AuthorServiceImpl();
    private static final BookAuthorService bookAuthorService = new BookAuthorsServiceImpl();

    @Test
    public void fullCrudTest() {
        Fiction fiction = new Fiction(title);
        fictionService.create(fiction);
        Fiction updateFiction = fictionService.findById(fiction.getId());
        updateFiction.setTitle("The game of the thrones");
        updateFiction.setGenre("Fantasy");
        fictionService.update(fiction);
        fictionService.findByTitle("The game of the thrones");
        fictionService.findAll();

        Author author = new Author("Joanne", "Rowling");
        authorService.create(author);
        Author updateAuthor = authorService.findById(author.getId());
        updateAuthor.setFirstName("George");
        updateAuthor.setLastName("Martin");
        authorService.update(author);
        authorService.findById(author.getId());
        authorService.findAll();

        bookAuthorService.addBookToAuthor(fiction, author);
        Author author2 = new Author("Harry", "Edger");
        bookAuthorService.addBookToAuthor(fiction, author2);
        Set<String> authors = new HashSet<>();
        authors.add(author.getId());
        authors.add(author2.getId());

        fictionService.delete(fiction.getId());
        Assert.assertTrue(fictionService.findAll().isEmpty());
        authorService.delete(author.getId());
        authorService.delete(author2.getId());
        Assert.assertTrue(authorService.findAll().isEmpty());


    }
}
