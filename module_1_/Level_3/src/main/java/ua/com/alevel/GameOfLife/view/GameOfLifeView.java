package ua.com.alevel.GameOfLife.view;

import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.GameOfLife.actionListeners.FillCellListener;
import ua.com.alevel.GameOfLife.config.GameOfLifeConfig;
import ua.com.alevel.GameOfLife.controller.GameOfLifeController;

import javax.swing.*;
import java.awt.*;


public class GameOfLifeView implements Runnable {

    @Getter
    private GameCanvas gameCanvas;
    private JPanel btnPanel;
    private JButton fillCellsBtn;
    private JButton nextGenerationsBtn;

    @Setter
    @Getter
    private boolean[][] generation = new boolean[GameOfLifeConfig.CELL_SIZE_ROW][GameOfLifeConfig.CELL_SIZE_COLUMN];
    private boolean[][] nextGeneration = new boolean[GameOfLifeConfig.CELL_SIZE_ROW][GameOfLifeConfig.CELL_SIZE_COLUMN];

    @Override
    public void run() {
        initButtons();
        initPanel();
        initFrame();
    }

    private void initFrame() {
        JFrame frame = new JFrame("Game of life");
        frame.setLocationRelativeTo(null);
        frame.setSize(GameOfLifeConfig.WINDOW_SIZE, GameOfLifeConfig.WINDOW_SIZE + GameOfLifeConfig.BUTTON_PANEL_SIZE);
        frame.getContentPane().add(BorderLayout.CENTER, gameCanvas);
        frame.getContentPane().add(BorderLayout.SOUTH, btnPanel);
        frame.setVisible(true);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                e.getWindow().dispose();
                synchronized (GameOfLifeController.thread) {
                    GameOfLifeController.thread.notifyAll();
                }
            }
        });
    }

    private void initPanel() {
        gameCanvas = new GameCanvas(this);
        gameCanvas.setBackground(Color.pink);
        btnPanel = new JPanel();
        btnPanel.add(fillCellsBtn);
        btnPanel.add(nextGenerationsBtn);
    }

    private void initButtons() {
        fillCellsBtn = new JButton("Create generation");
        fillCellsBtn.addActionListener(new FillCellListener(this));
        nextGenerationsBtn = new JButton("Next generation");
        nextGenerationsBtn.addActionListener(e -> {
            turn();
            gameCanvas.repaint();
        });
    }

    private void turn() {
        for (int x = 0; x < GameOfLifeConfig.CELL_SIZE_ROW; x++) {
            for (int y = 0; y < GameOfLifeConfig.CELL_SIZE_COLUMN; y++) {
                int count = countNeighbors(x, y);
                nextGeneration[x][y] = generation[x][y];
                // if are 3 live neighbors around empty cells - the cell becomes alive
                nextGeneration[x][y] = (count == 3) || nextGeneration[x][y];
                // if cell has less than 2 or greater than 3 neighbors - it will be die
                nextGeneration[x][y] = ((count >= 2) && (count <= 3)) && nextGeneration[x][y];
            }
        }
        for (int x = 0; x < GameOfLifeConfig.CELL_SIZE_ROW; x++) {
            System.arraycopy(nextGeneration[x], 0, generation[x], 0, GameOfLifeConfig.CELL_SIZE_COLUMN);
        }
    }

    int countNeighbors(int x, int y) {
        int count = 0;
        for (int dx = -1; dx < 2; dx++) {
            for (int dy = -1; dy < 2; dy++) {
                int nX = x + dx;
                int nY = y + dy;
                nX = (nX < 0) ? GameOfLifeConfig.CELL_SIZE_ROW - 1 : nX;
                nY = (nY < 0) ? GameOfLifeConfig.CELL_SIZE_COLUMN - 1 : nY;
                nX = (nX > GameOfLifeConfig.CELL_SIZE_ROW - 1) ? 0 : nX;
                nY = (nY > GameOfLifeConfig.CELL_SIZE_COLUMN - 1) ? 0 : nY;
                count += (generation[nX][nY]) ? 1 : 0;
            }
        }
        if (generation[x][y]) {
            count--;
        }
        return count;
    }


}
