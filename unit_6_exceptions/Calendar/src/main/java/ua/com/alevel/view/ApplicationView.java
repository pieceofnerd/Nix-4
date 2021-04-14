package ua.com.alevel.view;

import ua.com.alevel.controller.ChangeTimeInDateController;
import ua.com.alevel.controller.DifferenceBetweenDatesController;
import ua.com.alevel.controller.SortingDateController;
import ua.com.alevel.impl.MenuServiceImpl;

public class ApplicationView {
    private static final int MENU_CHOOSE_OPTION = 1;
    private MenuServiceImpl menu;
    private boolean flag;
    private DifferenceBetweenDatesController differenceBetweenDatesController;
    private ChangeTimeInDateController changeTimeInDateController;
    private SortingDateController sortingDateController;

    public ApplicationView() {
        this.menu = new MenuServiceImpl();
        this.flag = true;
        this.differenceBetweenDatesController = new DifferenceBetweenDatesController();
        this.changeTimeInDateController = new ChangeTimeInDateController();
        this.sortingDateController=new SortingDateController();
    }

    public void showApplicationView() {
        menu.userGreeting();
        while (flag) {
            menu.mainMenu();
            int option = menu.chooseOption(MENU_CHOOSE_OPTION);
            switch (option) {
                case 0:
                    System.exit(0);
                case 1:
                    differenceBetweenDatesController.differenceBetweenDates();
                    break;
                case 2:
                    changeTimeInDateController.changeTime("Add time to date", 1);
                    break;
                case 3:
                    changeTimeInDateController.changeTime("Subtract time from date", 2);
                    break;
                case 4: sortingDateController.sortDate();
            }

        }
    }
}
