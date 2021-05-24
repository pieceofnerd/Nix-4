package ua.com.alevel.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.db.DBInFile;
import ua.com.alevel.entity.Fiction;
import ua.com.alevel.service.ConsoleService;
import ua.com.alevel.util.ReadParametersUtil;

import java.util.HashSet;
import java.util.Set;

public class BookConsoleServiceImpl implements ConsoleService<Fiction> {
    private final static Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class.getName());
    private static final DBInFile db = DBInFile.getInstance();

    @Override
    public Fiction create() {
        String title;
        System.out.println("\nCreating a book: ");
        System.out.print("Please enter a title of the book: ");
        title = ReadParametersUtil.readParams();
        if (db.existBookByTitle(title)) {
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
        Fiction fiction = db.findBookByTitle(title);
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
        Fiction fiction = db.findBookByTitle(title);
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
            System.out.println("\nTitle = " + fiction.getTitle() + ", genre = " + fiction.getGenre());

    }

    public void findBooksByAuthor(String authorId) {
        Set<Fiction> books = db.findAllBooks();
        Set<Fiction> booksByAuthor = new HashSet<>();
        for (Fiction f : books) {
            Set<String> aid = f.getAuthorsId();
            for (String s : aid) {
                if (s.equals(authorId)) {
                    booksByAuthor.add(f);
                }
            }
        }
        findAll(booksByAuthor);
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
