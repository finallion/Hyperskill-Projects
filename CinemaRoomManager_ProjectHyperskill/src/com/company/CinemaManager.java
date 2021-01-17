package com.company;

public class CinemaManager {
    private char[][] seats;
    private int rowSize;
    private int colSize;

    public CinemaManager(int row, int col) {
        this.rowSize = row;
        this.colSize = col;
        this.seats = new char[rowSize][colSize];
    }

    public boolean validSize(int row, int col) {
        if (row >= rowSize || row < 0 || col >= colSize || col < 0) {
            return false;
        }
        return true;
    }

    public void setSeat(int row, int col, char seat) {
        seats[row][col] = seat;
    }

    public char getSeat(int row, int col) {
        return seats[row][col];
    }

    public int getSize() {
        return rowSize * colSize;
    }


    public int singlePricing(int row) {
        if (rowSize * colSize <= 60) {
            return 10;
        } else {
            if (row <= rowSize / 2) {
                return 10;
            }
            return 8;
        }
    }

    public int totalPricing() {
        if (rowSize * colSize <= 60) {
            return 10 * rowSize * colSize;
        } else {
            int front = rowSize / 2;
            int back = rowSize - front;

            return 10 * front * colSize + 8 * back * colSize;
        }
    }


    public void fillCinema() {
        for (int i = 0; i < rowSize; i++) {
            for (int ii = 0; ii < colSize; ii++) {
                seats[i][ii] = 'S';
            }
        }
    }

    public void printCinema() {
        System.out.println();
        System.out.println("Cinema:");

        //x-coord
        System.out.print("  ");
        for (int i = 1; i <= colSize; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        //y-coord + seats
        for (int i = 0; i < rowSize; i++) {
            System.out.print(i + 1 + " ");
            for (int ii = 0; ii < colSize; ii++) {
                System.out.print(seats[i][ii]);
                System.out.print(' ');
            }
            System.out.println();
        }
        System.out.println();
    }
}
