package ua.com.alevel.GameOfLife.actionListeners;

import ua.com.alevel.GameOfLife.config.GameOfLifeConfig;
import ua.com.alevel.GameOfLife.view.GameOfLifeView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class FillCellListener implements ActionListener {
    private GameOfLifeView gameOfLifeView;

    public FillCellListener(GameOfLifeView gameOfLifeView) {
        this.gameOfLifeView = gameOfLifeView;
    }

    private Random random = new Random();

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean[][] array = new boolean[GameOfLifeConfig.CELL_SIZE_ROW][GameOfLifeConfig.CELL_SIZE_COLUMN];
        for (int x = 1; x < (GameOfLifeConfig.CELL_SIZE_ROW - 17); x++) {
            for (int y = 1; y < (GameOfLifeConfig.CELL_SIZE_COLUMN - 17); y++) {

                array[x][y] = random.nextBoolean();
            }
        }
        gameOfLifeView.setGeneration(array);
        gameOfLifeView.getGameCanvas().repaint();
    }
}

