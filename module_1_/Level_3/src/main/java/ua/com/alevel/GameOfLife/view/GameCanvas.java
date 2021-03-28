package ua.com.alevel.GameOfLife.view;

import ua.com.alevel.GameOfLife.config.GameOfLifeConfig;

import javax.swing.*;
import java.awt.*;

public class GameCanvas extends JPanel {
    GameOfLifeView gameOfLifeView;

    public GameCanvas(GameOfLifeView gameOfLifeView) {
        this.gameOfLifeView = gameOfLifeView;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int x = 0; x < GameOfLifeConfig.CELL_SIZE_ROW; x++) {
            for (int y = 0; y < GameOfLifeConfig.CELL_SIZE_COLUMN; y++) {
                if (gameOfLifeView.getGeneration()[x][y]) {
                    g.fillOval(x * GameOfLifeConfig.ELEMENT_RADIUS, y * GameOfLifeConfig.ELEMENT_RADIUS,
                            GameOfLifeConfig.ELEMENT_RADIUS, GameOfLifeConfig.ELEMENT_RADIUS);
                }
            }
        }
    }
}
