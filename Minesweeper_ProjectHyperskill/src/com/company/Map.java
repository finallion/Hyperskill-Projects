package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Map {
    private int sizeX;
    private int sizeY;
    private char[][] map;
    private char[][] displayMap;
    private int numberOfMines;


    public Map(int sizeX, int sizeY, int numberOfMines) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.numberOfMines = numberOfMines;
        this.map = new char[this.sizeX][this.sizeY];
        this.displayMap = new char[this.sizeX][this.sizeY];
    }


    public boolean checkWinByEvading() {
        for (int row = 0; row < this.sizeX; row++) {
            for (int col = 0; col < this.sizeY; col++) {
                // win by exploring all free cells
                if (map[row][col] == Marks.EMPTY.getMark()) {
                    return false;
                }
            }
        }
        return true;
    }


    public boolean checkWinByMarking() {
        int foundMines = 0;
        for (int row = 0; row < this.sizeX; row++) {
            for (int col = 0; col < this.sizeY; col++) {
                // win by marking all mines
                if (map[row][col] == Marks.MINE.getMark() && displayMap[row][col] == Marks.FLAGGED.getMark()) {
                    foundMines++;
                }

            }
        }

        if (foundMines == numberOfMines) {
            return true;
        }

        return false;
    }

    public void addMinesToLostMap() {
        for (int row = 0; row < this.sizeX; row++) {
            for (int col = 0; col < this.sizeY; col++) {
                if (map[row][col] == Marks.MINE.getMark()) {
                    displayMap[row][col] = map[row][col];
                }
            }
        }
    }


    public boolean setCell(int col, int row, int iterator, boolean firstRound) {
        if (map[row][col] == Marks.EMPTY.getMark()) {

            map[row][col] = Marks.CLEAR.getMark();

            // fill map with mines after first game move
            if (firstRound) {
                fillMaps();
            }

            returnValuesFromAdjacentCells(row, col, iterator);


            // this loop allows to reduce the recursion calls 
            for (int i = 0; i < this.sizeX; i++) {
                for (int j = 0; j < this.sizeY; j++) {
                    if (displayMap[i][j] == Marks.CLEAR.getMark()) {
                        returnValuesFromAdjacentCells(i, j, iterator);
                    }
                }
            }

            return true;

        // game over
        } else if (map[row][col] == Marks.MINE.getMark()) {
            return false;
        }
        return false;
    }


    public boolean markCell(int col, int row) {
        if (displayMap[row][col] == Marks.EMPTY.getMark()) {
            displayMap[row][col] = Marks.FLAGGED.getMark();
            return true;

        } else if (displayMap[row][col] == Marks.FLAGGED.getMark()) {
            displayMap[row][col] = Marks.EMPTY.getMark();
            return true;
        }
        return false;

    }



    public void returnValuesFromAdjacentCells(int row, int col, int iterator) {
        // first: check if field is in a corner, on a side, or in the middle
        // second: count all adjacing mines
        // third: if the field is clear (= no mines adjacent) recall the method (recursion)
        // forth: set value (clear or number of mines adjacent) to the cell
        
        // ASCII value for 0
        char mineCounter = 48;

        if (iterator > 0) {
            // corners
            if (row == 0 && col == 0) {
                if (map[row][col + 1] == Marks.MINE.getMark()) {
                    mineCounter++;
                }
                if (map[row + 1][col + 1] == Marks.MINE.getMark()) {
                    mineCounter++;
                }
                if (map[row + 1][col] == Marks.MINE.getMark()) {
                    mineCounter++;
                }

                if (mineCounter == 48) {
                    if (displayMap[row][col + 1] == Marks.CLEAR.getMark() || displayMap[row][col + 1] == Marks.EMPTY.getMark() || displayMap[row][col + 1] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row, col + 1, iterator - 1);
                    }
                    if (displayMap[row + 1][col + 1] == Marks.CLEAR.getMark() || displayMap[row + 1][col + 1] == Marks.EMPTY.getMark() || displayMap[row + 1][col + 1] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row + 1, col + 1, iterator - 1);
                    }
                    if (displayMap[row + 1][col] == Marks.CLEAR.getMark() || displayMap[row + 1][col] == Marks.EMPTY.getMark() || displayMap[row + 1][col] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row + 1, col, iterator - 1);
                    }
                }


            } else if (row == 0 && col == this.sizeY - 1) {
                if (map[row][col - 1] == Marks.MINE.getMark()) {
                    mineCounter++;
                }
                if (map[row + 1][col - 1] == Marks.MINE.getMark()) {
                    mineCounter++;
                }
                if (map[row + 1][col] == Marks.MINE.getMark()) {
                    mineCounter++;
                }

                if (mineCounter == 48) {
                    if (displayMap[row][col - 1] == Marks.CLEAR.getMark() || displayMap[row][col - 1] == Marks.EMPTY.getMark() || displayMap[row][col - 1] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row, col - 1, iterator - 1);
                    }
                    if (displayMap[row + 1][col - 1] == Marks.CLEAR.getMark() || displayMap[row + 1][col - 1] == Marks.EMPTY.getMark() || displayMap[row + 1][col - 1] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row + 1, col - 1, iterator - 1);
                    }
                    if (displayMap[row + 1][col] == Marks.CLEAR.getMark() || displayMap[row + 1][col] == Marks.EMPTY.getMark() || displayMap[row + 1][col] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row + 1, col, iterator - 1);
                    }
                }

            } else if (row == this.sizeX - 1 && col == 0) {
                if (map[row][col + 1] == Marks.MINE.getMark()) {
                    mineCounter++;
                }
                if (map[row - 1][col + 1] == Marks.MINE.getMark()) {
                    mineCounter++;
                }
                if (map[row - 1][col] == Marks.MINE.getMark()) {
                    mineCounter++;
                }

                if (mineCounter == 48) {
                    if (displayMap[row][col + 1] == Marks.CLEAR.getMark() || displayMap[row][col + 1] == Marks.EMPTY.getMark() || displayMap[row][col + 1] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row, col + 1, iterator - 1);
                    }
                    if (displayMap[row - 1][col + 1] == Marks.CLEAR.getMark() || displayMap[row - 1][col + 1] == Marks.EMPTY.getMark() || displayMap[row - 1][col + 1] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row - 1, col + 1, iterator - 1);
                    }
                    if (displayMap[row - 1][col] == Marks.CLEAR.getMark() || displayMap[row - 1][col] == Marks.EMPTY.getMark() || displayMap[row - 1][col] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row - 1, col, iterator - 1);
                    }
                }
            } else if (row == this.sizeX - 1 && col == this.sizeY - 1) {
                if (map[row][col - 1] == Marks.MINE.getMark()) {
                    mineCounter++;
                }
                if (map[row - 1][col - 1] == Marks.MINE.getMark()) {
                    mineCounter++;
                }
                if (map[row - 1][col] == Marks.MINE.getMark()) {
                    mineCounter++;
                }

                if (mineCounter == 48) {
                    if (displayMap[row][col - 1] == Marks.CLEAR.getMark() || displayMap[row][col - 1] == Marks.EMPTY.getMark() || displayMap[row][col - 1] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row, col - 1, iterator - 1);
                    }
                    if (displayMap[row - 1][col - 1] == Marks.CLEAR.getMark() || displayMap[row - 1][col - 1] == Marks.EMPTY.getMark() || displayMap[row - 1][col - 1] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row - 1, col - 1, iterator - 1);
                    }
                    if (displayMap[row - 1][col] == Marks.CLEAR.getMark() || displayMap[row - 1][col] == Marks.EMPTY.getMark() || displayMap[row - 1][col] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row - 1, col, iterator - 1);
                    }
                }
                // sides
            } else if (row == 0) {
                if (map[row][col + 1] == Marks.MINE.getMark()) {
                    mineCounter++;
                }
                if (map[row][col - 1] == Marks.MINE.getMark()) {
                    mineCounter++;
                }
                if (map[row + 1][col] == Marks.MINE.getMark()) {
                    mineCounter++;
                }
                if (map[row + 1][col + 1] == Marks.MINE.getMark()) {
                    mineCounter++;
                }
                if (map[row + 1][col - 1] == Marks.MINE.getMark()) {
                    mineCounter++;
                }

                if (mineCounter == 48) {
                    if (displayMap[row][col + 1] == Marks.CLEAR.getMark() || displayMap[row][col + 1] == Marks.EMPTY.getMark() || displayMap[row][col + 1] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row, col + 1, iterator - 1);
                    }
                    if (displayMap[row][col - 1] == Marks.CLEAR.getMark() || displayMap[row][col - 1] == Marks.EMPTY.getMark() || displayMap[row][col - 1] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row, col - 1, iterator - 1);
                    }
                    if (displayMap[row + 1][col] == Marks.CLEAR.getMark() || displayMap[row + 1][col] == Marks.EMPTY.getMark() || displayMap[row + 1][col] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row + 1, col, iterator - 1);
                    }
                    if (displayMap[row + 1][col + 1] == Marks.CLEAR.getMark() || displayMap[row + 1][col + 1] == Marks.EMPTY.getMark() || displayMap[row + 1][col + 1] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row + 1, col + 1, iterator - 1);
                    }
                    if (displayMap[row + 1][col - 1] == Marks.CLEAR.getMark() || displayMap[row + 1][col - 1] == Marks.EMPTY.getMark() || displayMap[row + 1][col - 1] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row + 1, col - 1, iterator - 1);
                    }
                }

            } else if (col == 0) {
                if (map[row + 1][col] == Marks.MINE.getMark()) {
                    mineCounter++;
                }
                if (map[row - 1][col] == Marks.MINE.getMark()) {
                    mineCounter++;
                }
                if (map[row + 1][col + 1] == Marks.MINE.getMark()) {
                    mineCounter++;
                }
                if (map[row - 1][col + 1] == Marks.MINE.getMark()) {
                    mineCounter++;
                }
                if (map[row][col + 1] == Marks.MINE.getMark()) {
                    mineCounter++;
                }

                if (mineCounter == 48) {
                    if (displayMap[row + 1][col] == Marks.CLEAR.getMark() || displayMap[row + 1][col] == Marks.EMPTY.getMark() || displayMap[row + 1][col] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row + 1, col, iterator - 1);
                    }
                    if (displayMap[row - 1][col] == Marks.CLEAR.getMark() || displayMap[row - 1][col] == Marks.EMPTY.getMark() || displayMap[row - 1][col] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row - 1, col, iterator - 1);
                    }
                    if (displayMap[row + 1][col + 1] == Marks.CLEAR.getMark() || displayMap[row + 1][col + 1] == Marks.EMPTY.getMark() || displayMap[row + 1][col + 1] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row + 1, col + 1, iterator - 1);
                    }
                    if (displayMap[row - 1][col + 1] == Marks.CLEAR.getMark() || displayMap[row - 1][col + 1] == Marks.EMPTY.getMark() || displayMap[row - 1][col + 1] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row - 1, col + 1, iterator - 1);
                    }
                    if (displayMap[row][col + 1] == Marks.CLEAR.getMark() || displayMap[row][col + 1] == Marks.EMPTY.getMark() || displayMap[row][col + 1] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row, col + 1, iterator - 1);
                    }
                }


            } else if (row == this.sizeX - 1) {
                if (map[row][col + 1] == Marks.MINE.getMark()) {
                    mineCounter++;
                }
                if (map[row][col - 1] == Marks.MINE.getMark()) {
                    mineCounter++;
                }
                if (map[row - 1][col] == Marks.MINE.getMark()) {
                    mineCounter++;
                }
                if (map[row - 1][col + 1] == Marks.MINE.getMark()) {
                    mineCounter++;
                }
                if (map[row - 1][col - 1] == Marks.MINE.getMark()) {
                    mineCounter++;
                }

                if (mineCounter == 48) {
                    if (displayMap[row][col + 1] == Marks.CLEAR.getMark() || displayMap[row][col + 1] == Marks.EMPTY.getMark() || displayMap[row][col + 1] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row, col + 1, iterator - 1);
                    }
                    if (displayMap[row][col - 1] == Marks.CLEAR.getMark() || displayMap[row][col - 1] == Marks.EMPTY.getMark() || displayMap[row][col - 1] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row, col - 1, iterator - 1);
                    }
                    if (displayMap[row - 1][col] == Marks.CLEAR.getMark() || displayMap[row - 1][col] == Marks.EMPTY.getMark() || displayMap[row - 1][col] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row - 1, col, iterator - 1);
                    }
                    if (displayMap[row - 1][col + 1] == Marks.CLEAR.getMark() || displayMap[row - 1][col + 1] == Marks.EMPTY.getMark() || displayMap[row - 1][col + 1] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row - 1, col + 1, iterator - 1);
                    }
                    if (displayMap[row - 1][col - 1] == Marks.CLEAR.getMark() || displayMap[row - 1][col - 1] == Marks.EMPTY.getMark() || displayMap[row - 1][col - 1] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row - 1, col - 1, iterator - 1);
                    }
                }
            } else if (col == this.sizeY - 1) {
                if (map[row + 1][col] == Marks.MINE.getMark()) {
                    mineCounter++;
                }
                if (map[row - 1][col] == Marks.MINE.getMark()) {
                    mineCounter++;
                }
                if (map[row + 1][col - 1] == Marks.MINE.getMark()) {
                    mineCounter++;
                }
                if (map[row - 1][col - 1] == Marks.MINE.getMark()) {
                    mineCounter++;
                }
                if (map[row][col - 1] == Marks.MINE.getMark()) {
                    mineCounter++;
                }

                if (mineCounter == 48) {
                    if (displayMap[row + 1][col] == Marks.CLEAR.getMark() || displayMap[row + 1][col] == Marks.EMPTY.getMark() || displayMap[row + 1][col] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row + 1, col, iterator - 1);
                    }
                    if (displayMap[row - 1][col] == Marks.CLEAR.getMark() || displayMap[row - 1][col] == Marks.EMPTY.getMark() || displayMap[row - 1][col] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row - 1, col, iterator - 1);
                    }
                    if (displayMap[row + 1][col - 1] == Marks.CLEAR.getMark() || displayMap[row + 1][col - 1] == Marks.EMPTY.getMark() || displayMap[row + 1][col - 1] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row + 1, col - 1, iterator - 1);
                    }
                    if (displayMap[row - 1][col - 1] == Marks.CLEAR.getMark() || displayMap[row - 1][col - 1] == Marks.EMPTY.getMark() || displayMap[row - 1][col - 1] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row - 1, col - 1, iterator - 1);
                    }
                    if (displayMap[row][col - 1] == Marks.CLEAR.getMark() || displayMap[row][col - 1] == Marks.EMPTY.getMark() || displayMap[row][col - 1] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row, col - 1, iterator - 1);
                    }
                }
                // middle
            } else {
                if (map[row][col + 1] == Marks.MINE.getMark()) {
                    mineCounter++;
                }
                if (map[row][col - 1] == Marks.MINE.getMark()) {
                    mineCounter++;
                }
                if (map[row + 1][col + 1] == Marks.MINE.getMark()) {
                    mineCounter++;
                }
                if (map[row + 1][col] == Marks.MINE.getMark()) {
                    mineCounter++;
                }
                if (map[row + 1][col - 1] == Marks.MINE.getMark()) {
                    mineCounter++;
                }
                if (map[row - 1][col] == Marks.MINE.getMark()) {
                    mineCounter++;
                }
                if (map[row - 1][col - 1] == Marks.MINE.getMark()) {
                    mineCounter++;
                }
                if (map[row - 1][col + 1] == Marks.MINE.getMark()) {
                    mineCounter++;
                }

                if (mineCounter == 48) {
                    if (displayMap[row][col + 1] == Marks.CLEAR.getMark() || displayMap[row][col + 1] == Marks.EMPTY.getMark() || displayMap[row][col + 1] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row, col + 1, iterator - 1);
                    }
                    if (displayMap[row][col - 1] == Marks.CLEAR.getMark() || displayMap[row][col - 1] == Marks.EMPTY.getMark() || displayMap[row][col - 1] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row, col - 1, iterator - 1);
                    }
                    if (displayMap[row + 1][col + 1] == Marks.CLEAR.getMark() || displayMap[row + 1][col + 1] == Marks.EMPTY.getMark() || displayMap[row + 1][col + 1] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row + 1, col + 1, iterator - 1);
                    }
                    if (displayMap[row + 1][col] == Marks.CLEAR.getMark() || displayMap[row + 1][col] == Marks.EMPTY.getMark() || displayMap[row + 1][col] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row + 1, col, iterator - 1);
                    }
                    if (displayMap[row + 1][col - 1] == Marks.CLEAR.getMark() || displayMap[row + 1][col - 1] == Marks.EMPTY.getMark() || displayMap[row + 1][col - 1] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row + 1, col - 1, iterator - 1);
                    }
                    if (displayMap[row - 1][col] == Marks.CLEAR.getMark() || displayMap[row - 1][col] == Marks.EMPTY.getMark() || displayMap[row - 1][col] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row - 1, col, iterator - 1);
                    }
                    if (displayMap[row - 1][col - 1] == Marks.CLEAR.getMark() || displayMap[row - 1][col - 1] == Marks.EMPTY.getMark() || displayMap[row - 1][col - 1] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row - 1, col - 1, iterator - 1);
                    }
                    if (displayMap[row - 1][col + 1] == Marks.CLEAR.getMark() || displayMap[row - 1][col + 1] == Marks.EMPTY.getMark() || displayMap[row - 1][col + 1] == Marks.FLAGGED.getMark()) {
                        returnValuesFromAdjacentCells(row - 1, col + 1, iterator - 1);
                    }
                }




            }

            if (mineCounter == 48) {
                //ASCII value for /
                mineCounter--;
            }

            displayMap[row][col] = mineCounter;
            map[row][col] = Marks.CLEAR.getMark();

        }
    }



    public char getCell(int row, int col) {
        return map[row][col];
    }


    public void fillMapsForFirstRound() {
        for (int row = 0; row < this.sizeX; row++) {
            for (int col = 0; col < this.sizeY; col++) {
                map[row][col] = Marks.EMPTY.getMark();
                displayMap[row][col] = Marks.EMPTY.getMark();
            }
        }
    }



    public void fillMaps() {
        List<Character> fields = new ArrayList<>();
        int mines = numberOfMines;

        for (int i = 0; i < sizeX * sizeY; i++) {
            if (mines > 0) {
                fields.add(Marks.MINE.getMark());
                mines--;
            } else {
                fields.add(Marks.EMPTY.getMark());
            }
        }

        Collections.shuffle(fields);

        int index = 0;
        for (int row = 0; row < this.sizeX; row++) {
            for (int col = 0; col < this.sizeY; col++) {
                if (displayMap[row][col] == Marks.CLEAR.getMark()) {
                    map[row][col] = fields.get(index);
                    displayMap[row][col] = Marks.EMPTY.getMark();
                } else {
                    map[row][col] = fields.get(index);
                    displayMap[row][col] = Marks.EMPTY.getMark();
                }
                index++;
            }
        }
    }


    public void printMap() {
        for (int row = 0; row < this.sizeX; row++) {

            for (int col = 0; col < this.sizeY; col++) {
                System.out.print(map[row][col]);
            }
            System.out.println();

        }
    }


    public void printDisplayMap() {
        int coords = 1;

        // prints x-coordinates
        System.out.print(" |");
        while(coords <= this.sizeX) {
            System.out.print(coords);
            coords++;
        }
        System.out.println("|");

        // prints upper border
        coords = 1;
        System.out.print("-|");
        while(coords <= this.sizeX) {
            System.out.print("—");
            coords++;
        }
        System.out.println("|");

        // prints game field and y-coordinates
        coords = 1;
        for (int row = 0; row < this.sizeX; row++) {
            System.out.print(coords);
            System.out.print("|");
            for (int col = 0; col < this.sizeY; col++) {
                System.out.print(displayMap[row][col]);
            }
            System.out.print("|");
            System.out.println();
            coords++;
        }

        // prints lower border
        coords = 1;
        System.out.print("-|");
        while(coords <= this.sizeX) {
            System.out.print("—");
            coords++;
        }
        System.out.println("|");
    }

}




