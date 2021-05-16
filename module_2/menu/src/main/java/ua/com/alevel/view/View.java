package ua.com.alevel.view;

import ua.com.alevel.Controller;

public class View {

    private static final Controller controller = new Controller();

    private void greatUser() {
        System.out.println("Hello, dear user!");
    }

    public void menuView() {
        greatUser();
        controller.menuController();
    }
}
