package ua.com.alevel.controller;

import ua.com.alevel.controllers.AuthorController;
import ua.com.alevel.controllers.BookAuthorsController;
import ua.com.alevel.controllers.BookController;
import ua.com.alevel.view.Menu;


public class ApplicationController {
    private BookController bookController;
    private AuthorController authorController;
    private BookAuthorsController bookAuthorsController;

    public ApplicationController() {
        this.bookController = new BookController();
        this.authorController = new AuthorController();
        this.bookAuthorsController = new BookAuthorsController();
    }

    public void runApplication() {
        while (true) {
            int menu = Menu.menuFirstLevel();
            switch (menu) {
                case 0:
                    System.exit(0);
                case 1:
                    bookController.book();
                    break;
                case 2:
                    authorController.author();
                    break;
                case 3:
                    bookAuthorsController.bookAuthor();
                    break;
            }
        }

    }
}
