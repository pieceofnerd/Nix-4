package ua.com.alevel.view;

import ua.com.alevel.controller.Controller;
import ua.com.alevel.util.MenuUtil;

public class View <Type extends Comparable<? super Type>>{
    private final Controller<Type> CONTROLLER = new Controller<>();
    private static final int MIN_MAIN_MENU = 0;
    private static final int MAX_MAIN_MENU = 2;
    private static final int MIN_ORDERLIST_MENU = 0;
    private static final int MAX_ORDERLIST_MENU = 9;

    public void applicationView() {
        while (true) {
            MenuUtil.mainMenu();
            int option = MenuUtil.chooseOption(MIN_MAIN_MENU, MAX_MAIN_MENU);
            switch (option) {
                case 0:
                    System.exit(0);
                case 1:
                case 2: {
                    CONTROLLER.showLibraryWithCustomObject(option);
                    break;
                }

            }
        }
    }

    public static int chooseOption() {
        MenuUtil.orderListMenu();
        return MenuUtil.chooseOption(MIN_ORDERLIST_MENU, MAX_ORDERLIST_MENU);
    }
}
