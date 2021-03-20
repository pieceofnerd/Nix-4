package ua.com.alevel.view;

import ua.com.alevel.controller.ApplicationController;

public class ApplicationView {
    private static ApplicationController controller = new ApplicationController();

    public static void viewApplication() {
        controller.runApplication();
    }
}
