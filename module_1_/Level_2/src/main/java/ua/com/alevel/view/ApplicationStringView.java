package ua.com.alevel.view;

import ua.com.alevel.controller.ApplicationLevel2Controller;
import ua.com.alevel.util.ConsoleHelperLevel2Util;

public class ApplicationStringView {
    private static boolean flag = true;
    private static ApplicationLevel2Controller applicationController = new ApplicationLevel2Controller();

    public static void run() {
        while (flag) {
            int menu = ConsoleHelperLevel2Util.menu();
            if (menu == 0) {
                flag = false;
                return;
            }
            applicationController.runApplication();
        }
    }
}
