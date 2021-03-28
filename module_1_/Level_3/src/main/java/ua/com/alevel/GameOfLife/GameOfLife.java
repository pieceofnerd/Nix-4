package ua.com.alevel.GameOfLife;

import ua.com.alevel.GameOfLife.controller.GameOfLifeController;

public class GameOfLife {
    public static void main(String[] args) {
        GameOfLifeController gameOfLifeController = new GameOfLifeController();
        gameOfLifeController.run();
    }
}
