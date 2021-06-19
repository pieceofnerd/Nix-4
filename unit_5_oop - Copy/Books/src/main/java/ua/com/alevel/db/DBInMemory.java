package ua.com.alevel.db;

import ua.com.alevel.entity.*;
import ua.com.alevel.util.ParserCSV;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class DBInMemory {
    private static DBInMemory db;
    private final String file = "Books/src/main/java/ua/com/alevel/db/Book.csv";
    private static final List<String[]> bookTableHeader = ParserCSV.prepareCVSData("Id Book, Book Title, Genre,\n");
    private Set<Book> books;
    private Set<Author> authors;

    private DBInMemory() {
        this.books = new HashSet<>();
        this.authors = new HashSet<>();
    }

    public static DBInMemory getInstance() {
        if (db == null) {
            db = new DBInMemory();
        }
        return db;
    }

    public void createBook(Fiction book) {
        book.setId(generateId(UUID.randomUUID().toString(), Book.class));
        ParserCSV.writeDataCSVBook(bookTableHeader, book, file);
    }

    public <B extends Fiction> void updateBook(B book) {
        String[] currentBook = findBookById(book.getId());
        currentBook[1]=book.getTitle();
        currentBook[2]=book.getGenre();
        List<String[]> db = getDB();
        for (int i = 0; i < db.size(); i++) {
            if (db.get(i)[0] == currentBook[0]) {
                db.set(i, currentBook);
            }
        }
        ParserCSV.clearAll(new File(file));
        ParserCSV.writeDataCSVAllBook(db,file);
    }

    public void deleteBook(String id) {
      //  Book currentBook = findBookById(id);
        //books.removeIf(b -> b.getId().equals(currentBook.getId()));
    }

    public <B extends Book> Set<B> findAllBooks() {
        return (Set<B>) books;
    }

    public String[] findBookById(String id) {
        List<String[]> books = ParserCSV.readDataCSVBook(file);
        String[] currentBookId = books.stream()
                .filter(b -> b[0].equals(id))
                .findFirst()
                .get();

        if (currentBookId == null) {
            System.out.println("\n There is no book with such title in our library");
        }
        return currentBookId;
    }

    public <B extends Book> B findBookByTitle(String bookTitle) {
        bookTitle = bookTitle.trim();
        String finalBookTitle = bookTitle;

        Book currentBook = books
                .stream()
                .filter(b -> b.getTitle().equals(finalBookTitle))
                .findFirst()
                .orElse(null);

        if (currentBook == null) {
            System.out.println("\nThere is no such book in our library");
        }
        return (B) currentBook;
    }

    public void createAuthor(Author author) {
        author.setId(generateId(UUID.randomUUID().toString(), Author.class));
        authors.add(author);
    }

    public void updateAuthor(Author author) {
        Author currentAuthor = findAuthorById(author.getId());
        currentAuthor.setFirstName(author.getFirstName());
        currentAuthor.setLastName(author.getLastName());
    }

    public void deleteAuthor(String id) {
        Author currentAuthor = findAuthorById(id);
        if (currentAuthor == null) {
            return;
        }
        authors.removeIf(a -> currentAuthor.getId().equals(a.getId()));
    }

    public Set<Author> findAllAuthors() {
        return authors;
    }

    public Author findAuthorById(String id) {
        Author currentAuthor = authors
                .stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (currentAuthor == null) {
            System.out.println("\n There is no authors with this id");
            return null;
        }
        return currentAuthor;
    }

    public boolean existBookByTitle(String title) {
        return books.stream()
                .anyMatch(b -> b.getTitle().equals(title));
    }

    public boolean isBookExist(String id) {
        return books.stream()
                .anyMatch(u -> u.getId().equals(id));
    }

    public boolean isAuthorExist(String id) {
        return books.stream()
                .anyMatch(author -> author.getId().equals(id));
    }


    public Set<String> findAuthorsByBooks(String id) {
       // Book book = findBookById(id);
        return null;
    }

    public <B extends Book> Set<B> findBooksByAuthorID(String authorID) {
        Set<B> booksByAuthorID = new HashSet<>();
        for (Book b : books) {
            if (b.getAuthorsId().stream().filter(a -> a.equals(authorID)).collect(Collectors.toList()) != null) {
                booksByAuthorID.add((B) b);
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

    private List<String[]> getDB() {
        return ParserCSV.readDataCSVBook(file);
    }


}
