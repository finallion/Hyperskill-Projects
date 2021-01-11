package com.company;

import java.util.Scanner;

public class UserInterface {
    private GameFieldLogic gameFieldLogic;
    private Scanner scanner;
    private TurnLogic turnLogic;
    private PlayerMark playerMark;
    private AI ai;
    private boolean turnNotFinished;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
        this.turnLogic = new TurnLogic();
        this.playerMark = new PlayerMark();
        this.gameFieldLogic = new GameFieldLogic();
        this.ai = new AI(this.gameFieldLogic, this.playerMark, this.turnLogic);
        this.turnNotFinished = false;
    }

    public void start() {

        while (true) {
            System.out.print("Input command: > ");
            String command = scanner.nextLine();

            //set all cells to empty spaces, resets rounds
            gameFieldLogic.initializeGameField();
            turnLogic.setRound(0);

            //menu
            /*
            Examples of valid input (user = human player) (easy/medium/hard = difficulties of AI):
                    exit
                    start user user
                    start user hard
                    start medium easy
             */
            if (command.equals("exit")) {
                break;
            } else if (command.contains("start")) {
                String[] commandPieces = command.split(" ");

                if (commandPieces.length > 3) {
                    System.out.println("Bad parameters!");
                } else if (commandPieces[0].equals("start") && (commandPieces[1].matches("easy|medium|hard") || commandPieces[1].equals("user")) && (commandPieces[2].matches("easy|medium|hard") || commandPieces[2].equals("user"))) {
                    gamePlay(commandPieces[1], commandPieces[2]);
                } else {
                    System.out.println("Bad parameters!");
                }

            } else {
                System.out.println("Bad parameters!");
            }


        }
    }

    public void gamePlay(String playerOne, String playerTwo) {
        boolean onlyPrintOnce = true;

        //while loop equals one match
        while (true) {
            //sets player mark according to round ('X' starts round 0)
            char player = playerMark.getPlayerMark(turnLogic.xTurn());
            //reset
            setFinishedTurn(false);

            //turn
            if (player == 'X') {
                if (playerOne.equals("easy") || playerOne.equals("medium") || playerOne.equals("hard")) {
                    AIMove(player, playerOne);
                } else if (playerOne.equals("user")) {
                    //print game field only one time when user starts
                    if(onlyPrintOnce) {
                        gameFieldLogic.printGameField();
                        onlyPrintOnce = false;
                    }
                    userMove(player);
                }
            } else if (player == 'O'){
                if (playerTwo.equals("easy") || playerTwo.equals("medium") || playerTwo.equals("hard")) {
                    AIMove(player, playerTwo);
                } else if (playerTwo.equals("user")) {
                    userMove(player);
                }
            }

            //game status after every turn
            if (checkWinOrDraw(player)) {
                break;
            }
        }
    }


    public boolean checkWinOrDraw(char player) {
        if (gameFieldLogic.checkWinningConditions(player)) {
            System.out.println(player + " wins");
            System.out.println();
            return true;
        } else if (!gameFieldLogic.checkGameNotFinished() && !gameFieldLogic.checkWinningConditions(player)) {
            System.out.println("Draw");
            System.out.println();
            return true;
        }
        return false;
    }


    public void AIMove(char player, String difficulty) {
        //same as user-move but without feedback
        while (!turnNotFinished) {

            int[] coordinates = new int[]{-1, -1};

            //placement coordinates based on selected difficulty
            if (difficulty.equals("easy")) {
                coordinates = ai.setRandomMark();
            } else if (difficulty.equals("medium")) {
                //coordinates = ai.setRandomMark();
                coordinates = ai.setTargetedMark();
            } else if (difficulty.equals("hard")) {
                coordinates = ai.getBestMove(gameFieldLogic.getGameField());
            }

            int row = coordinates[0];
            int column = coordinates[1];

            //move
            if (row == -1 || column == -1) {
                setFinishedTurn(false);
            } else if (!correctCoordinateRange(row, column)) {
                setFinishedTurn(false);
            } else if (!gameFieldLogic.validPlacement(row, column)) {
                setFinishedTurn(false);
            } else {
                System.out.println("Making move level \"" + difficulty + "\"");
                gameFieldLogic.setGameFieldCell(player, row, column);
                setFinishedTurn(true);
                turnLogic.incrementRound();
                gameFieldLogic.printGameField();
            }
        }
    }


    public void userMove(char player) {

        while (!turnNotFinished) {
            System.out.print("Enter the coordinates: > ");
            String coordinatesInput = scanner.nextLine();

            //placement coordinates
            int[] coordinates = readEnteredCoordinates(coordinatesInput);
            int row = coordinates[0];
            int column = coordinates[1];

            //move and feedback
            if (row == -1 || column == -1) {
                System.out.println("You should enter numbers!");
                setFinishedTurn(false);
            } else if (!correctCoordinateRange(row, column)) {
                System.out.println("Coordinates should be from 1 to 3!");
                setFinishedTurn(false);
            } else if (!gameFieldLogic.validPlacement(row, column)) {
                System.out.println("This cell is occupied! Choose another one!");
                setFinishedTurn(false);
            } else {
                gameFieldLogic.setGameFieldCell(player, row, column);
                setFinishedTurn(true);
                turnLogic.incrementRound();
                gameFieldLogic.printGameField();
            }
        }

    }



    public boolean setFinishedTurn(boolean value) {
        this.turnNotFinished = value;
        return turnNotFinished;
    }


    public int[] readEnteredCoordinates(String input) {
        String[] numbers = input.split(" ");

        try {
            int row = Integer.valueOf(numbers[0]);
            int column = Integer.valueOf(numbers[1]);

            //both values -1, because of game field size 0-2 differs from input
            return new int[]{row - 1, column - 1};

        } catch (Exception e) {
            return new int[]{-1, -1};
        }
    }

    public boolean correctCoordinateRange(int row, int column) {
        //checks actual game field array size 0-2
        if(row > 2 || row < 0 || column > 2 || column < 0) {
            return false;
        }
        return true;
    }



}
