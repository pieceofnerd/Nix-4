package ua.com.alevel.impl;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runners.MethodSorters;
import ua.com.alevel.db.DBInFile;
import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Fiction;
import ua.com.alevel.service.AuthorService;
import ua.com.alevel.service.BookAuthorService;
import ua.com.alevel.service.BookService;

import java.util.HashSet;
import java.util.Set;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@TestMethodOrder(OrderAnnotation.class)
class FictionServiceImplTest {
    private static final String title = "Harry Potter";
    private static final Author author = new Author("Joanne", "Rowling");
    private static final Set<String> authorsID = new HashSet<>();
    private static Fiction fiction;
    private static final BookService<Fiction> fictionService = new FictionServiceImpl();
    private static final AuthorService authorService = new AuthorServiceImpl();
    private static final BookAuthorService bookAuthorService = new BookAuthorsServiceImpl();
    private static final DBInFile db= DBInFile.getInstance();


    @BeforeAll
    static void setUp() {

        fiction = new Fiction("Test");
        fictionService.create(fiction);

    }

    @Test
    @Order(1)
    void T1create() {
        fiction = new Fiction(title);
        fictionService.create(fiction);
        Fiction fictionAfterAdding = fictionService.findById(fiction.getId());
        Assert.assertEquals(fictionAfterAdding, fiction);

    }

    @Test
    @Order(2)
    void T2update() {
        fiction.setTitle("The Game of Throne");
        fiction.setGenre("Fantasy novel");
        fictionService.update(fiction);
        Assert.assertSame("The Game of Throne", fictionService.findById(fiction.getId()).getTitle());
    }

    @Test
    @Order(3)
    void T3findById() {
        Fiction findingFiction = fictionService.findById(fiction.getId());
        Assert.assertEquals(findingFiction, fiction);
    }

    @Test
    @Order(4)
    void T4findAll() {
        Set<Fiction> books = fictionService.findAll();
        Assert.assertFalse(books.isEmpty());
    }



    @Test
    @Order(5)
    void T6delete() {
        fictionService.delete(fiction.getId());
        Assert.assertTrue(fictionService.findById(fiction.getId())==null);
    }

}