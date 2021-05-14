package ua.com.alevel.db;

import ua.com.alevel.entity.*;
import ua.com.alevel.util.ParserCSV;

import java.util.*;

public class DBInCSV {
    private static DBInCSV db;
    private Set<Book> books;
    private Set<Author> authors;
    private static final String bookTablePath = "Book.csv";
    private static final String authorTablePath = "Author.csv";
    private static final String bookAuthorTablePath = "BookAuthor.csv";
    private static final String bookHeader = "Book ID,Book Title, Genre, Visible";
    private static final String authorHeader = "Author ID,first Name, last name, Visible";
    private static final String bookAuthorHeader = "Author ID, Book ID";

    private DBInCSV() {
        this.books = new HashSet<>();
        this.authors = new HashSet<>();
        ParserCSV.initializeTable(bookHeader, bookTablePath);
        ParserCSV.initializeTable(authorHeader, authorTablePath);
        ParserCSV.initializeTable(bookAuthorHeader, bookAuthorTablePath);
    }

    public static DBInCSV getInstance() {
        if (db == null) {
            db = new DBInCSV();
        }
        return db;
    }

    public void createBook(Fiction book) {
        book.setId(generateId(UUID.randomUUID().toString(), Book.class));
        ParserCSV.writeIntoDatabase(book, bookTablePath);
    }

    public <B extends Book> void updateBook(B book) {
        Fiction currentBook = findBookById(book.getId());
        if (currentBook != null) {
            List<String[]> books = ParserCSV.readAllDataFromDatabase(bookTablePath);
            Fiction fiction = (Fiction) book;
            for (String[] bookFromDatabase : books) {
                if (bookFromDatabase[0].equals(currentBook.getId())) {
                    bookFromDatabase[1] = fiction.getTitle();
                    bookFromDatabase[2] = fiction.getGenre();
                }
            }
            ParserCSV.rewriteBookTable(books, bookTablePath);
        }
    }

    public void deleteBook(String id) {
        Fiction currentBook = findBookById(id);
        if (currentBook != null) {
            List<String[]> books = ParserCSV.readAllDataFromDatabase(bookTablePath);
            for (String[] book : books) {
                if(book[0].equals(currentBook.getId())){
                    book[3]="false";
                }
            }
            ParserCSV.rewriteBookTable(books, bookTablePath);
        }
    }

    public <B extends Book> Set<B> findAllBooks() {
        List<String[]> books = ParserCSV.readAllDataFromDatabase(bookTablePath);
        Set<Fiction> fictions = new HashSet<>();
        books.remove(0);
        for (String[] book : books) {
            if(book[3].equals("false")){
                continue;
            }
            Fiction fiction = new Fiction(book[1], book[0]);
            fiction.setGenre(book[2]);
            fictions.add(fiction);
        }
        return (Set<B>) fictions;
    }

    public <B extends Book> B findBookById(String id) {
        List<String[]> books = ParserCSV.readAllDataFromDatabase(bookTablePath);
        String[] currentBook = books
                .stream()
                .filter(b -> b[0].equals(id))
                .findFirst()
                .orElse(null);

        if (currentBook == null||currentBook[3].equals("false")) {
            System.out.println("\n There is no book with such title in our library");
            return null;
        }
        else return (B) new Fiction(currentBook[1], currentBook[0]);
    }

    public <B extends Book> B findBookByTitle(String bookTitle) {
        String finalBookTitle = bookTitle.trim();
        List<String[]> books = ParserCSV.readAllDataFromDatabase(bookTablePath);
        String[] currentBook = books
                .stream()
                .filter(b -> b[1].equals(finalBookTitle))
                .findFirst()
                .orElse(null);

        if (currentBook == null||currentBook[3].equals("false")) {
            System.out.println("\nThere is no such book in our library");
            return null;
        } else return (B) new Fiction(currentBook[1], currentBook[0]);
    }

    public void createAuthor(Author author) {
        author.setId(generateId(UUID.randomUUID().toString(), Author.class));
        ParserCSV.writeIntoDatabase(author, authorTablePath);
    }

    public void updateAuthor(Author author) {
        Author currentAuthor = findAuthorById(author.getId());
        if (currentAuthor != null) {
            List<String[]> authors = ParserCSV.readAllDataFromDatabase(authorTablePath);
            Author updateAuthor = author;
            for (String[] authorFromDatabase : authors) {
                if (authorFromDatabase[0].equals(currentAuthor.getId())) {
                    authorFromDatabase[1] = updateAuthor.getFirstName();
                    authorFromDatabase[2] = updateAuthor.getLastName();
                }
            }
            ParserCSV.rewriteBookTable(authors, authorTablePath);
        }
    }

    public void deleteAuthor(String id) {
        Author currentAuthor = findAuthorById(id);
        if (currentAuthor != null) {
            List<String[]> authors = ParserCSV.readAllDataFromDatabase(authorTablePath);
            for (String[] author : authors) {
                if(author[0].equals(currentAuthor.getId())){
                    author[3]="false";
                }
            }
            ParserCSV.rewriteBookTable(authors, authorTablePath);
        }
    }

    public Set<Author> findAllAuthors() {
        List<String[]> authors = ParserCSV.readAllDataFromDatabase(authorTablePath);
        Set<Author> allAuthors = new HashSet<>();
        authors.remove(0);
        for (String[] author : authors) {
            if(author[3].equals("false")){
                continue;
            }
            Author currentAuthor = new Author(author[0], author[1], author[2]);
            allAuthors.add(currentAuthor);
        }
        return allAuthors;
    }

    public Author findAuthorById(String id) {
        List<String[]> authors = ParserCSV.readAllDataFromDatabase(authorTablePath);
        String[] currentAuthor = authors
                .stream()
                .filter(a -> a[0].equals(id))
                .findFirst()
                .orElse(null);

        if (currentAuthor == null||currentAuthor[3].equals("false")) {
            System.out.println("\n There is no authors");
            return null;
        }
        return new Author(currentAuthor[0], currentAuthor[1], currentAuthor[2]);
    }

    public boolean existBookByTitle(String title) {
        List<String[]> books = ParserCSV.readAllDataFromDatabase(bookTablePath);
        boolean doesBookExist = false;
        for (String[] book : books) {
            if (Arrays.asList(book).contains(title)&&book[3].equals("true")) {
                doesBookExist = true;
            }
        }

        return doesBookExist;
    }

    public boolean isBookExist(String id) {
        return books.stream()
                .anyMatch(u -> u.getId().equals(id));
    }

    public boolean isAuthorExist(String id) {
        return books.stream()
                .anyMatch(author -> author.getId().equals(id));
    }

    public  void addAuthorToBook(String authorId, String bookID) {
        ParserCSV.writeBookAuthorIntoDatabase(authorId, bookID, bookAuthorTablePath);
    }

    public Set<Author> findAuthorsByBooks(String id) {
        Set<Author> authorsId= new HashSet<>();
        List<String[]> authorsBook=ParserCSV.readAllDataFromDatabase(bookAuthorTablePath);
        for (String[] s : authorsBook) {
            if(s[1].equals(id)){
                Author author= findAuthorById(s[0]);
                authorsId.add(new Author(author.getId(),author.getFirstName(),author.getLastName()));
            }
        }
        return authorsId;
    }

    public Set<Fiction> findBooksByAuthorID(String authorID) {
        Set<Fiction> booksByAuthorID = new HashSet<>();
        List<String[]> authorsBook=ParserCSV.readAllDataFromDatabase(bookAuthorTablePath);
        for (String[] s : authorsBook) {
            if(s[0].equals(authorID)){
                booksByAuthorID.add(new Fiction(s[1],s[0],s[2]));
            }
        }
        return booksByAuthorID;
    }

    public <C extends BaseEntity> String generateId(String id, Class<C> cClass) {
        if (cClass.isAssignableFrom(Book.class)) {
            if (isBookExist(id)) {
                return generateId(UUID.randomUUID().toString(), Book.class);
            }
        } else if (cClass.isAssignableFrom(Author.class)) {
            if (isAuthorExist(id)) {
                return generateId(UUID.randomUUID().toString(), Author.class);
            }
        }
        return id;
    }

}
