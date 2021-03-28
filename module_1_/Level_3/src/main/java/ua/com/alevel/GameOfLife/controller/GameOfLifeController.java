package ua.com.alevel.GameOfLife.controller;

import ua.com.alevel.GameOfLife.view.GameOfLifeView;
import ua.com.alevel.util.LifeGameMenuUtil;


public class GameOfLifeController {
    private static boolean flag = true;
    public static Thread thread;

    public void run() {
        while (flag) {
            int option = LifeGameMenuUtil.menu();
            switch (option) {
                case 0: {
                    flag = false;
                    break;
                }
                case 1:
                    GameOfLifeView gameOfLifeView = new GameOfLifeView();
                    javax.swing.SwingUtilities.invokeLater(gameOfLifeView);
                    thread = new Thread(gameOfLifeView);
                    synchronized (thread) {
                        try {
                            thread.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            }
        }
    }
}
