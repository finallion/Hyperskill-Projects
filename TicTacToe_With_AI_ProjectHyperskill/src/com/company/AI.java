package com.company;

import java.util.Random;

public class AI {
    private int row;
    private int column;
    private PlayerMark mark;
    private GameFieldLogic gameFieldLogic;
    private TurnLogic turnLogic;


    public AI(GameFieldLogic gameFieldLogic, PlayerMark mark, TurnLogic turnLogic) {
        this.row = 0;
        this.column = 0;
        this.gameFieldLogic = gameFieldLogic;
        this.mark = mark;
        this.turnLogic = turnLogic;
    }


    public int[] setRandomMark() {
        Random random = new Random();
        row = random.nextInt(3);
        column = random.nextInt(3);
        return new int[]{row, column};
    }


    public int[] setTargetedMark() {
        int[] coords = gameFieldLogic.checkForTwos(mark.getPlayerMark(turnLogic.xTurn()));

        //if the method didn't found a winning or blocking move
        if(coords[0] == -1) {
            return setRandomMark();
        }
        return coords;
    }


    public int[] getBestMove(char[][] board) {
        int[] coordsOfBestMove = new int[]{-1, -1};
        int bestValue = Integer.MIN_VALUE;

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                if (board[row][col] == ' ') {

                    board[row][col] = 'X';

                    //depth describes how far the AI looks into future moves
                    //higher depth = stronger AI
                    int moveValue = minimax(board, 3, Integer.MIN_VALUE, Integer.MAX_VALUE,false);
                    board[row][col] = ' ';

                    if (moveValue > bestValue) {
                        coordsOfBestMove[0] = row;
                        coordsOfBestMove[1] = col;
                        bestValue = moveValue;
                    }
                }
            }
        }
        return coordsOfBestMove;
    }



    public int minimax(char[][] board, int depth, int alpha, int beta, boolean isMax) {
        int valueOfBoard = evaluateBoard(board, depth);

        if(valueOfBoard == 10) {
            return valueOfBoard;
        } else if(valueOfBoard == -10) {
            return valueOfBoard;
        } else if(!checkIfMovesAreLeft(board)) {
            return valueOfBoard;
        } else if(depth == 0) {
            return valueOfBoard;
        }

        // Maximising player
        if (isMax) {
            int highestVal = Integer.MIN_VALUE;
            for (int row = 0; row < board.length; row++) {
                for (int col = 0; col < board.length; col++) {
                    if (board[row][col] == ' ') {

                        board[row][col] = 'X';

                        highestVal = Math.max(highestVal, minimax(board, depth - 1, alpha, beta,false));

                        board[row][col] = ' ';

                        alpha = Math.max(alpha, highestVal);

                        if (alpha >= beta) {
                            return highestVal;
                        }

                    }
                }
            }
            return highestVal;
        // Minimising player
        } else {
            int lowestVal = Integer.MAX_VALUE;
            for (int row = 0; row < board.length; row++) {
                for (int col = 0; col < board.length; col++) {
                    if (board[row][col] == ' ') {

                        board[row][col] = 'O';

                        lowestVal = Math.min(lowestVal, minimax(board, depth - 1, alpha, beta,true));

                        board[row][col] = ' ';

                        beta = Math.min(beta, lowestVal);

                        if (beta <= alpha) {
                            return lowestVal;
                        }
                    }
                }
            }
            return lowestVal;
        }
    }


    public boolean checkIfMovesAreLeft(char[][] board) {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                if(board[i][j] == ' ') {
                    return true;
                }
            }
        }
        return false;
    }



    public int evaluateBoard(char[][] board, int depth) {
        int sumOfThreeField = 0;
        int xWin = 'X' * board.length;
        int oWin = 'O' * board.length;


        //horizontal
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                sumOfThreeField += board[i][j];
            }
            if(sumOfThreeField == xWin) {
                return 10 + depth;
            } else if(sumOfThreeField == oWin) {
                return -10 + depth;
            }
            sumOfThreeField = 0;
        }

        //vertical
        sumOfThreeField = 0;
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                sumOfThreeField += board[j][i];
            }
            if(sumOfThreeField == xWin) {
                return 10 + depth;
            } else if(sumOfThreeField == oWin) {
                return -10 + depth;
            }
            sumOfThreeField = 0;
        }

        //diagonal one
        sumOfThreeField = 0;
        for (int i = 0; i < board.length; i++) {
            sumOfThreeField += board[i][i];;
        }
        if(sumOfThreeField == xWin) {
            return 10 + depth;
        } else if(sumOfThreeField == oWin) {
            return -10 + depth;
        }
        sumOfThreeField = 0;

        //diagonal two
        int max = board.length - 1;
        for (int i = 0; i <= max; i++) {
            sumOfThreeField += board[i][max - i];
        }
        if(sumOfThreeField == xWin) {
            return 10 + depth;
        } else if(sumOfThreeField == oWin) {
            return -10 + depth;
        }

        //no winner
        return 0;

    }

}
