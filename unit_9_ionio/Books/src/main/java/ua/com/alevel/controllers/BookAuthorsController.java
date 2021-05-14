package ua.com.alevel.controllers;

import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Fiction;
import ua.com.alevel.impl.*;
import ua.com.alevel.service.AuthorService;
import ua.com.alevel.service.BookAuthorService;
import ua.com.alevel.service.BookService;
import ua.com.alevel.util.ConsoleHelper;

import java.util.Objects;

public class BookAuthorsController {
    private static final BookConsoleServiceImpl bookConsoleService = new BookConsoleServiceImpl();
    private static final AuthorConsoleServiceImpl authorConsoleService = new AuthorConsoleServiceImpl();
    private static final BookService<Fiction> bookService = new FictionServiceImpl();
    private static final AuthorService authorService = new AuthorServiceImpl();
    private static final BookAuthorService bookAuthorService = new BookAuthorsServiceImpl();

    public void bookAuthor() {
        boolean flag = true;
        while (flag) {
            int option = ConsoleHelper.menuBookAuthor();
            switch (option) {
                case 0: {
                    flag = false;
                    break;
                }
                case 1: {
                    System.out.println("Choose the author: ");
                    String authorId = authorConsoleService.getID();
                    Author author = authorService.findById(authorId);
                    System.out.println("Choose the book: ");
                    String bookTitle = bookConsoleService.getTitle();
                    Fiction fiction = bookService.findByTitle(bookTitle);
                    if (Objects.nonNull(author) && Objects.nonNull(fiction))
                        bookAuthorService.addBookToAuthor(fiction, author);
                    break;
                }

            }
        }
    }

}
