package com.company;

public class GenerationAlgorithm {


    public static void updateBoard(LifeBoard board) {
        boolean[][] currentGeneration = board.getBoard();
        int size = currentGeneration.length;

        boolean[][] nextGeneration = new boolean[size][size];

        for (int i = 0; i < size; i++) {
            for (int ii = 0; ii < size; ii++) {

                int counter = countNeighbours(currentGeneration, i, ii);

                // Game of Life rules
                if (!currentGeneration[i][ii] && counter == 3) {
                    nextGeneration[i][ii] = true;
                } else if (currentGeneration[i][ii] && (counter == 2 || counter == 3)) {
                    nextGeneration[i][ii] = true;
                } else {
                    nextGeneration[i][ii] = false;
                }
            }
        }

        board.setBoard(nextGeneration);

    }


    public static int countNeighbours(boolean[][] board, int row, int col) {
        int counter = 0;
        int size = board.length;


        // checks 3 x 3 block, centered around parameters
        for(int i = row - 1; i <= row + 1; i++) {
            for (int j = col -1; j <= col + 1; j++) {

                // skips the middle cell
                if(i == row && j == col){
                    continue;
                }

                // uses modular arithmetic to handle cells at the edges of the array
                if (board[(i + size) % size][(j + size) % size]) {
                    counter++;
                }
            }
        }

        return counter;


    }


}
