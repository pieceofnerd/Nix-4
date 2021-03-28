package ua.com.alevel.controller;

import ua.com.alevel.GameOfLife.controller.GameOfLifeController;
import ua.com.alevel.view.ApplicationStringView;
import ua.com.alevel.view.ApplicationView;
import ua.com.alevel.view.Menu;

public class ApplicationController {
    private static boolean flag = true;

    public void runApplication() {
        while (flag) {
            int menu = Menu.menu();
            switch (menu) {
                case 1: {
                    ApplicationView.runProgram();
                    break;
                }
                case 2: {
                    ApplicationStringView.run();
                    break;
                }
                case 3: {
                    GameOfLifeController lifeGameControl = new GameOfLifeController();
                    lifeGameControl.run();
                }
            }

        }

    }
}
