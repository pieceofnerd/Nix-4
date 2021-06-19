package ua.com.alevel.impl;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Fiction;
import ua.com.alevel.service.AuthorService;
import ua.com.alevel.service.BookAuthorService;
import ua.com.alevel.service.BookService;

import java.util.HashSet;
import java.util.Set;



@TestMethodOrder(OrderAnnotation.class)
class AuthorServiceImplTest {
    private static final String firstName = "Joanne";
    private static final String lastName = "Rowling";
    private static final Author author = new Author(firstName, lastName);
    private static final AuthorService authorService = new AuthorServiceImpl();
    private static final BookService<Fiction> fictionService = new FictionServiceImpl();
    private static final BookAuthorService bookAuthorService = new BookAuthorsServiceImpl();

    @BeforeAll
    static void setUp() {

    }

    @Test
    @Order(1)
    void create() {
        authorService.create(author);
        Assert.assertFalse(authorService.findAll().isEmpty());
    }

    @Test
    @Order(2)
    void update() {
        Author authorUpdate = authorService.findById(author.getId());
        authorUpdate.setFirstName("George");
        authorUpdate.setLastName("Martin");
        authorService.update(authorUpdate);
        Assert.assertEquals("George", authorService.findById(author.getId()).getFirstName());
        Assert.assertEquals("Martin", authorService.findById(author.getId()).getLastName());
    }

    @Test
    @Order(6)
    void delete() {
        authorService.delete(author.getId());
        Assert.assertTrue(authorService.findAll().isEmpty());
    }

    @Test
    @Order(3)
    void findById() {
        Author findingAuthor = authorService.findById(author.getId());
        Assert.assertEquals(findingAuthor, author);
    }

    @Test
    @Order(4)
    void findAll() {
        Set<Author> authors = authorService.findAll();
        Set<Author> authorTest = new HashSet<>();
        authorTest.add(author);
        Assert.assertEquals(authorTest, authors);
    }

    @Test
    @Order(5)
    void findByBook() {
        Set<String> authorsSet = new HashSet<>();
        authorsSet.add(author.getId());
        Fiction book = new Fiction("The lord of rings");
        fictionService.create(book);
        bookAuthorService.addBookToAuthor(book,author);
        Set<String> authorFindByBook = authorService.findByBook(book.getId());
        Assert.assertEquals(authorsSet,authorFindByBook);
    }

}