package ua.com.alevel;

import ua.com.alevel.controller.DateFormatterController;
import ua.com.alevel.controller.FindFirstUniqueNameController;
import ua.com.alevel.controller.FindShorterPathController;

public class Controller {
    public void menuController() {
        System.out.println("**************** FIRST PART ****************");
        DateFormatterController dateFormatterController = new DateFormatterController();
        dateFormatterController.dateFormatterController();
        System.out.println("********************************************");


        System.out.println("\n\n**************** SECOND PART ****************");
        FindFirstUniqueNameController findFirstUniqueNameController = new FindFirstUniqueNameController();
        findFirstUniqueNameController.findFirstUniqueNameController();
        System.out.println("********************************************");


        System.out.println("\n\n**************** THIRD PART ****************");
        FindShorterPathController findShorterPathController = new FindShorterPathController();
        findShorterPathController.findShorterPathController();
        System.out.println("********************************************");
    }
}
