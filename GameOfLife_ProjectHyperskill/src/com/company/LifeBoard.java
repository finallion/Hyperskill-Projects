package com.company;

import java.util.Random;

/*
 Model class for the MVC-Pattern
 */



public class LifeBoard {
    private Random random;
    private boolean[][] board;


    public LifeBoard(int size) {
        this.board = new boolean[size][size];
        random = new Random();
        initialize();

    }

    private void initialize() {
        for(int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = random.nextBoolean();
            }
        }
    }

    public boolean[][] getBoard() {
        return board;
    }

    public void setBoard(boolean[][] b) {
        board = b;
    }

    public int countLivingNeighbours() {
        int counter = 0;

        for (boolean[] row : board) {
            for (boolean cell : row) {
                if (cell) {
                    counter ++;
                }
            }
        }
        return counter;
    }



}
