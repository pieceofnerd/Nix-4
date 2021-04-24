package ua.com.alevel.view;

import ua.com.alevel.controller.Controller;
import ua.com.alevel.util.MenuUtil;

public class View{
    private static final Controller CONTROLLER = new Controller();

    private static final int MIN_MATHSET_MENU = 0;
    private static final int MAX_MATHSET_MENU = 10;

    public void applicationView() {
        while (true) {
            int option = chooseOption();
            if (option == 0) {
                System.exit(0);
            }
            CONTROLLER.showLibraryWithCustomObject(option);
        }
    }


    public static int chooseOption() {
        MenuUtil.orderListMenu();
        return MenuUtil.chooseOption(MIN_MATHSET_MENU, MAX_MATHSET_MENU);
    }
}
