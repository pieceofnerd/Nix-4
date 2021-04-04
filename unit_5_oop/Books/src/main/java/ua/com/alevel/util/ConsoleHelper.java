package ua.com.alevel.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.impl.AuthorServiceImpl;

import java.util.Scanner;

public class ConsoleHelper {
    private final static Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class.getName());


    public static int menuBook() {
        System.out.print("\n" +
                "\nExit: 0" +
                "\nCreate a book: 1" +
                "\nUpdate a book: 2" +
                "\nDelete a book: 3" +
                "\nFind all books: 4" +
                "\nFind books by author: 5" +
                "\nFind book by title: 6" +
                "\nPlease, enter an option: ");
        return menu();
    }

    public static int menuAuthor() {
        System.out.print("\n" +
                "\nExit: 0" +
                "\nCreate an author: 1" +
                "\nUpdate an author: 2" +
                "\nDelete an author: 3" +
                "\nFind all authors: 4" +
                "\nFind authors by book: 5" +
                "\nFind author by id: 6" +
                "\nPlease, enter an option: ");
        return menu();
    }

    private static int menu() {
        int option;
        Scanner scanner = new Scanner(System.in);
        try {
            option = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            logger.error("User input incorrect value");
            return menuBook();
        }
        if (option < 0 || option > 6) {
            System.out.println("\nPlease, enter a correct option");
            return menuBook();
        }
        return option;
    }

    public static int menuBookAuthor() {
        int option;

        System.out.print("\n" +
                "\nExit: 0" +
                "\nAdd book to author: 1" +
                "\nPlease, enter an option: ");
        Scanner scanner = new Scanner(System.in);
        try {
            option = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            logger.error("User input incorrect value");
            return menuBookAuthor();
        }
        if (option < 0 || option > 1) {
            System.out.println("\nPlease, enter a correct option");
            return menuBook();
        }
        return option;
    }
}
