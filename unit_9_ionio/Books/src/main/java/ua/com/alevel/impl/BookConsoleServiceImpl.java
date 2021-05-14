package ua.com.alevel.impl;

import ua.com.alevel.dao.AuthorDao;
import ua.com.alevel.dao.BookAuthorDao;
import ua.com.alevel.dao.BookDao;
import ua.com.alevel.entity.Fiction;
import ua.com.alevel.service.ConsoleService;
import ua.com.alevel.util.ReadParametersUtil;

import java.util.Set;

public class BookConsoleServiceImpl implements ConsoleService<Fiction> {
    private final BookDao bookDao = new BookDao();
    private final BookAuthorDao bookAuthorDao = new BookAuthorDao();
    private final AuthorDao authorDao=new AuthorDao();

    @Override
    public Fiction create() {
        String title;
        System.out.println("\nCreating a book: ");
        System.out.print("Please enter a title of the book: ");
        title = ReadParametersUtil.readParams();
        if (bookDao.exist(title)) {
            System.out.println("Book with this title already exists");
            return create();
        }
        return new Fiction(title);
    }

    @Override
    public Fiction update() {
        String newTitle;
        String title;
        String genre;
        System.out.println("\nUpdating a book: ");
        System.out.print("Please enter a title of a book that you want to update: ");
        title = ReadParametersUtil.readParams();
        Fiction fiction = bookDao.findByTitle(title);
        if (fiction != null) {
            newTitle = getTitle();
            System.out.print("Please enter a genre of a book: ");
            genre = ReadParametersUtil.readParams();
            fiction.setTitle(newTitle);
            fiction.setGenre(genre);
        }
        return fiction;
    }

    @Override
    public String delete() {
        System.out.println("\nDeleting a book: ");
        String title;
        System.out.print("Please, enter a title of a book: ");
        title = getTitle();
        Fiction fiction = bookDao.findByTitle(title);
        if (fiction == null) {
            return null;
        }
        return fiction.getId();
    }

    @Override
    public void findAll(Set<Fiction> values) {
        System.out.println("\nFinding all books: ");
        for (Fiction f : values) {
            System.out.println("Title = " + f.getTitle() + ", genre = " + f.getGenre());
        }
    }

    public void findByTitle(Fiction fiction) {
        if (fiction != null)
            if (fiction.getGenre() == null) {
                fiction.setGenre("field not set");
                System.out.println("\nTitle = " + fiction.getTitle() + ", genre = " + fiction.getGenre());
            }
    }

    public void findBooksByAuthor(String authorId) {
        System.out.println("\nFind books by author " + authorDao.find(authorId).getFirstName() + " " + authorDao.find(authorId).getLastName());
        Set<Fiction> booksByAuthor = bookAuthorDao.findBooksByAuthor(authorId);
        for (Fiction fiction : booksByAuthor) {
            findByTitle(bookDao.findByTitle(fiction.getTitle()));
        }
    }

    public String getTitle() {
        String newTitle;
        System.out.print("Please enter a title of a Book: ");
        newTitle = ReadParametersUtil.readParams();
        if (newTitle.isBlank()) {
            System.out.println("title cannot be empty");
            return getTitle();
        }
        return newTitle;
    }

}
