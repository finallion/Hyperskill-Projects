package com.company;

import javax.swing.*;
import java.awt.*;

public class LifePanel extends JPanel {

    private GameCell[][] population;

    public LifePanel() {
        super();
    }

    public void initialize(int size) {
        this.setLayout(new GridLayout(size, size, 1, 1));
        this.population = new GameCell[size][size];

        for (int i = 0; i < size; i++) {
            for (int ii = 0; ii < size; ii++) {
                population[i][ii] = new GameCell();

                this.add(population[i][ii]);
            }
        }
    }

    public void updatePopulation(boolean[][] board) {
        if (population != null) {
            for (int i = 0; i < board.length; i++) {
                for (int ii = 0; ii < board.length; ii++) {

                    if (population[i][ii].isAlive() != board[i][ii]) {
                        population[i][ii].setAlive(board[i][ii]);
                    }

                }
            }

            repaint();
        } else {
            initialize(board.length);
            updatePopulation(board);
        }
    }



}
