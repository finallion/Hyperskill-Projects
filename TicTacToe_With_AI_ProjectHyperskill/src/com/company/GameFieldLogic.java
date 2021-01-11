package com.company;


public class GameFieldLogic {
    private char[][] gameField;
    private int size;

    public GameFieldLogic() {
        this.size = 3;
        this.gameField = new char[this.size][this.size];
    }

    public int getSize() {
        return this.size;
    }

    public char[][] getGameField() {
        return gameField;
    }

    public char getGameFieldCell(int row, int column) {
        return gameField[row][column];
    }

    public boolean validPlacement(int row, int column) {
        if(gameField[row][column] == ' ') {
            return true;
        }
        return false;
    }

    public void setGameFieldCell(char mark, int row, int column) {
        if(validPlacement(row, column)) {
            gameField[row][column] = mark;
        }
    }

    public boolean checkGameNotFinished() {
        for(int i = 0; i < this.size; i++) {
            for(int j = 0; j < this.size; j++) {
                if(gameField[i][j] == ' ') {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkWinningConditions(char playerMark) {
        if(checkHorizontal(playerMark) || checkVertical(playerMark) || checkDiagonal(playerMark)) {
            return true;
        }
        return false;
    }


    public boolean checkHorizontal(char playerMark) {
        if(gameField[0][0] == playerMark && gameField[0][1] == playerMark && gameField[0][2] == playerMark) {
            return true;
        } else if(gameField[1][0] == playerMark && gameField[1][1] == playerMark && gameField[1][2] == playerMark) {
            return true;
        } else if(gameField[2][0] == playerMark && gameField[2][1] == playerMark && gameField[2][2] == playerMark) {
            return true;
        }
        return false;
    }

    public boolean checkVertical(char playerMark) {
        if(gameField[0][0] == playerMark && gameField[1][0] == playerMark && gameField[2][0] == playerMark) {
            return true;
        } else if(gameField[0][1] == playerMark && gameField[1][1] == playerMark && gameField[2][1] == playerMark) {
            return true;
        } else if(gameField[0][2] == playerMark && gameField[1][2] == playerMark && gameField[2][2] == playerMark) {
            return true;
        }
        return false;
    }

    public boolean checkDiagonal(char playerMark) {
        if(gameField[0][0] == playerMark && gameField[1][1] == playerMark && gameField[2][2] == playerMark) {
            return true;
        } else if(gameField[0][2] == playerMark && gameField[1][1] == playerMark && gameField[2][0] == playerMark) {
            return true;
        }
        return false;
    }


    //method needed for medium movement
    public int[] checkForTwos(char playerMark) {
        //winning move gets returned immediately; blocker move at the end
        int[] blocker = new int[]{-1, -1};

        //diagonal one
        int countMarks = 0;
        int rowCoord = -1;
        int columnCoord = -1;

        for (int row = 0; row < this.size; row++) {
            if (gameField[row][row] == playerMark) {
                countMarks++;
            } else if (gameField[row][row] == ' ') {
                rowCoord = row;
                columnCoord = row;
            } else {
                countMarks--;
            }
        }
        //for winning
        if(countMarks == 2 && rowCoord != -1) {
            return new int[]{rowCoord, columnCoord};
        }
        //for blocking
        if(countMarks == -2 && rowCoord != -1) {
            blocker = new int[]{rowCoord, columnCoord};
        }


        //diagonal two
        //reset variables
        countMarks = 0;
        rowCoord = -1;
        columnCoord = -1;

        for (int row = this.size - 1; row >= 0; row--) {
            if (gameField[row][row] == playerMark) {
                countMarks++;
            } else if (gameField[row][row] == ' ') {
                rowCoord = row;
                columnCoord = row;
            } else {
                countMarks--;
            }
        }

        //for winning
        if(countMarks == 2 && rowCoord != -1) {
            return new int[]{rowCoord, columnCoord};
        }
        //for blocking
        if(countMarks == -2 && rowCoord != -1) {
            blocker = new int[]{rowCoord, columnCoord};
        }

        //horizontal
        //reset variables
        countMarks = 0;
        rowCoord = -1;
        columnCoord = -1;

        for (int row = 0; row < this.size; row++) {
            for (int column = 0; column < this.size; column++) {
                if (gameField[row][column] == playerMark) {
                    countMarks++;
                } else if (gameField[row][column] == ' ') {
                    rowCoord = row;
                    columnCoord = column;
                } else {
                    countMarks--;
                }
            }

            //for winning
            if(countMarks == 2 && rowCoord != -1) {
                return new int[]{rowCoord, columnCoord};
            }
            //for blocking
            if(countMarks == -2 && rowCoord != -1) {
                blocker = new int[]{rowCoord, columnCoord};
            }
            //reset after each row
            countMarks = 0;
            rowCoord = -1;
            columnCoord = -1;
        }

        //vertical
        countMarks = 0;
        rowCoord = -1;
        columnCoord = -1;

        for (int column = 0; column < this.size; column++) {
            for (int row = 0; row < this.size; row++) {
                if (gameField[row][column] == playerMark) {
                    countMarks++;
                } else if (gameField[row][column] == ' ') {
                    rowCoord = row;
                    columnCoord = column;
                } else {
                    countMarks--;
                }
            }

            //for winning
            if(countMarks == 2 && rowCoord != -1) {
                return new int[]{rowCoord, columnCoord};
            }
            //for blocking
            if(countMarks == -2 && rowCoord != -1) {
                blocker = new int[]{rowCoord, columnCoord};
            }
            //reset after each row
            countMarks = 0;
            rowCoord = -1;
            columnCoord = -1;
        }

        //no winning match, return actual blocker-array or empty blocker
        return blocker;
    }

    public void initializeGameField() {
        for(int i = 0; i < this.size; i++) {
            for(int j = 0; j < this.size; j++) {
                gameField[i][j] = ' ';
            }
        }
    }


    public void printGameField() {
        System.out.println("---------");
        for(int i = 0; i < this.size; i++) {
            System.out.print("| ");
            for(int j = 0; j < this.size; j++) {
                System.out.print(gameField[i][j]);
                System.out.print(" ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

}
