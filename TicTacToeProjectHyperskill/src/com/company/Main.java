package com.company;

import java.util.Scanner;

public class Main {
    public static boolean XWinCondition = false;
    public static boolean OWinCondition = false;
    public static boolean validMove = false;
    public static int roundCounter = 1;
    public static char playerMark = ' ';
    public static char[][] gameField = new char[3][3];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String emptyField = "         ";
        char[][] firstGameField = buildGameField(emptyField);
        printGameField(firstGameField);

        int rowCoordinate = 0;
        int columnCoordinate = 0;


        while(roundCounter < 10){
            System.out.print("Enter the coordinates: ");
            String secondInput = scanner.nextLine();
            try{
                String[] pieces = secondInput.split(" ");
                rowCoordinate = Integer.parseInt(pieces[0]);
                columnCoordinate = Integer.parseInt(pieces[1]);

                if(roundCounter == 1){
                    gameField = turn(firstGameField, rowCoordinate, columnCoordinate);
                } else {
                    gameField = turn(gameField, rowCoordinate, columnCoordinate);
                }

                if(validMove){
                    printGameField(gameField);
                    checkStates(gameField);
                    roundCounter++;
                }


            } catch (Exception e) {
                System.out.println("You should enter numbers!");
            }
        }

    }

    public static char setPlayerMark(){
        if(roundCounter % 2 == 0){
            playerMark = 'O';
        } else {
            playerMark = 'X';
        }
        return playerMark;
    }


    public static char[][] turn(char[][] input, int rowCounter, int columnCounter){
        validMove = false;
        if(rowCounter > 3 || columnCounter > 3 || rowCounter < 0 || columnCounter < 0){
            System.out.println("Coordinates should be from 1 to 3!");
            return input;
        }


        char[][] updatedGameField = new char[3][3];

        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                updatedGameField[i][j] = input[i][j];
            }
        }


        if(updatedGameField[rowCounter - 1][columnCounter - 1] == ' '){
            updatedGameField[rowCounter - 1][columnCounter - 1] = setPlayerMark();
            validMove = true;
        } else {
            System.out.println("This cell is occupied! Choose another one!");
        }


        return updatedGameField;
    }



    public static void checkStates(char[][] inputArray){
        checkRows(inputArray);
        checkColumns(inputArray);
        checkDiagonals(inputArray);
        if(XWinCondition && OWinCondition){
            System.out.println("Impossible");
        } else if (checkIfDraw(inputArray)){
            System.out.println("Draw");
        } else if (!checkIfPlayerPlacementNumberIsEqual(inputArray)){
            System.out.println("Impossible");
        } else if(XWinCondition){
            System.out.println("X wins");
            roundCounter = 10;
        } else if(OWinCondition){
            System.out.println("O wins");
            roundCounter = 10;
        } else {
            //System.out.println("Game not finished");
        }
    }


    public static boolean checkIfPlayerPlacementNumberIsEqual(char[][] inputArray){
        int counter = 0;
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(inputArray[i][j] == 'X'){
                    counter++;
                } else if(inputArray[i][j] == 'O'){
                    counter--;
                }
            }
        }

        if(counter == 0 || counter == 1 || counter == -1){ ;
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkIfDraw(char[][] inputArray){
        boolean hasEmptySpaces = false;
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(inputArray[i][j] == ' '){
                    hasEmptySpaces = true;
                    if(hasEmptySpaces){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void checkRows(char[][] inputArray){
        int sumRows = 0;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                sumRows += inputArray[i][j];
            }
            if(sumRows == 264){
                XWinCondition = true;
            }
            if(sumRows == 237){
                OWinCondition = true;
            }

            sumRows = 0;
        }

    }

    public static void checkColumns(char[][] inputArray){
        int sumColumns = 0;
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sumColumns += inputArray[j][i];
            }
            if(sumColumns == 264){
                XWinCondition = true;
            }
            if(sumColumns == 237){
                OWinCondition = true;
            }

            sumColumns = 0;
        }
    }

    public static void checkDiagonals(char[][] inputArray){
        if(inputArray[0][0] + inputArray[1][1] + inputArray[2][2] == 264){
            XWinCondition = true;
        }
        if(inputArray[0][2] + inputArray[1][1] + inputArray[2][0] == 264){
            XWinCondition = true;
        }
        if(inputArray[0][0] + inputArray[1][1] + inputArray[2][2] == 237){
            OWinCondition = true;
        }
        if(inputArray[0][2] + inputArray[1][1] + inputArray[2][0] == 237){
            OWinCondition = true;
        }
    }



    public static char[][] buildGameField(String input){
        char[][] gameField = new char[3][3];
        int counter = 0;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(input.charAt(counter) == '_'){
                    gameField[i][j] = ' ';
                }else{
                    gameField[i][j] = input.charAt(counter);
                }
                counter++;
            }
        }
        return gameField;
    }

    public static void printGameField(char[][] gameField){
        System.out.print("---------");
        System.out.println();
        for(int i = 0; i < 3; i++){
            System.out.print("| ");
            for(int j = 0; j < 3; j++){
                System.out.print(gameField[i][j]);
                System.out.print(" ");
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("---------");

    }
}