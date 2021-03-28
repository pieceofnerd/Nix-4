package ua.com.alevel.view;

import ua.com.alevel.controller.ApplicationLevel1Controller;

public class ApplicationView {
    public static void runProgram() {
        ApplicationLevel1Controller applicationController = new ApplicationLevel1Controller();
        applicationController.runApplication();
    }
}
