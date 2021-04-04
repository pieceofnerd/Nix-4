package ua.com.alevel.controllers;

import ua.com.alevel.entity.Fiction;
import ua.com.alevel.impl.AuthorConsoleServiceImpl;
import ua.com.alevel.impl.AuthorServiceImpl;
import ua.com.alevel.impl.BookConsoleServiceImpl;
import ua.com.alevel.impl.FictionServiceImpl;
import ua.com.alevel.service.AuthorService;
import ua.com.alevel.service.BookService;
import ua.com.alevel.util.ConsoleHelper;

import java.util.Set;

public class BookController {
    private BookService<Fiction> bookService;
    private AuthorService authorService;
    private static final BookConsoleServiceImpl fictionConsoleService = new BookConsoleServiceImpl();
    private static final AuthorConsoleServiceImpl authorConsoleService = new AuthorConsoleServiceImpl();

    public BookController() {
        this.bookService = new FictionServiceImpl();
        this.authorService = new AuthorServiceImpl();
    }

    public void book() {
        boolean flag = true;

        while (flag) {
            int option = ConsoleHelper.menuBook();
            switch (option) {
                case 0: {
                    flag = false;
                    break;
                }
                case 1: {
                    Fiction fiction = fictionConsoleService.create();
                    bookService.create(fiction);
                    break;
                }
                case 2: {
                    Fiction fiction = fictionConsoleService.update();
                    if (fiction != null)
                        bookService.update(fiction);
                    break;
                }
                case 3: {
                    String bookId = fictionConsoleService.delete();
                    if (bookId != null)
                        bookService.delete(bookId);
                    break;
                }
                case 4: {
                    Set<Fiction> fictionSet = bookService.findAll();
                    fictionConsoleService.findAll(fictionSet);
                    break;
                }
                case 5: {
                    String authorID = authorConsoleService.getID();
                    fictionConsoleService.findBooksByAuthor(authorID);
                    break;
                }
                case 6: {
                    String title = fictionConsoleService.getTitle();
                    Fiction fiction = bookService.findByTitle(title);
                    fictionConsoleService.findByTitle(fiction);
                    break;
                }
            }
        }
    }
}
