package com.company;

import java.io.IOException;
import java.util.Scanner;

public class UserInterface {
    private Scanner scanner;
    private Logic logic;
    private boolean checkIfValidMove;
    private boolean turn;
    private boolean onlyPrintGameFieldOnce;
    private int rounds;

    public UserInterface(Scanner scanner){
        this.scanner = new Scanner(System.in);
        this.logic = new Logic();
        this.checkIfValidMove = true;
        this.turn = true;
        this.onlyPrintGameFieldOnce = true;
        this.rounds = 0;
    }

    public void start(){
        //builds four game fields, two fog of war-maps for each player and the actual map
        logic.getPlayerOneField().buildGameField();
        logic.getPlayerOneFogOfWarField().buildGameField();
        logic.getPlayerTwoField().buildGameField();
        logic.getPlayerTwoFogOfWarField().buildGameField();

        //start player one
        System.out.println("Player 1, place your ships to the game field");
        System.out.println();

        logic.getPlayerOneField().printGameField();

        System.out.println();
        System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells):");
        while(checkIfValidMove) {
            checkStatesPlayerOne("Aircraft Carrier", 5);
        }
        printPlayerOneField();


        System.out.println();
        System.out.println("Enter the coordinates of the Battleship (4 cells):");
        while(checkIfValidMove){
            checkStatesPlayerOne("Battleship", 4);

        }
        printPlayerOneField();

        System.out.println();
        System.out.println("Enter the coordinates of the Submarine (3 cells):");
        while(checkIfValidMove){
            checkStatesPlayerOne("Submarine", 3);
        }
        printPlayerOneField();

        System.out.println();
        System.out.println("Enter the coordinates of the Cruiser (3 cells):");
        while(checkIfValidMove){
            checkStatesPlayerOne("Cruiser", 3);
        }
        printPlayerOneField();

        System.out.println();
        System.out.println("Enter the coordinates of the Destroyer (2 cells):");
        while(checkIfValidMove){
            checkStatesPlayerOne("Destroyer", 2);

        }
        printPlayerOneField();
        System.out.println();

        //start player two
        promptEnter();

        System.out.println("Player 2, place your ships to the game field");
        System.out.println();

        logic.getPlayerTwoField().printGameField();

        System.out.println();
        System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells):");
        while(checkIfValidMove) {
            checkStatesPlayerTwo("Aircraft Carrier", 5);
        }
        printPlayerTwoField();

        System.out.println();
        System.out.println("Enter the coordinates of the Battleship (4 cells):");
        while(checkIfValidMove){
            checkStatesPlayerTwo("Battleship", 4);

        }
        printPlayerTwoField();

        System.out.println();
        System.out.println("Enter the coordinates of the Submarine (3 cells):");
        while(checkIfValidMove){
            checkStatesPlayerTwo("Submarine", 3);
        }
        printPlayerTwoField();

        System.out.println();
        System.out.println("Enter the coordinates of the Cruiser (3 cells):");
        while(checkIfValidMove){
            checkStatesPlayerTwo("Cruiser", 3);
        }
        printPlayerTwoField();

        System.out.println();
        System.out.println("Enter the coordinates of the Destroyer (2 cells):");
        while(checkIfValidMove){
            checkStatesPlayerTwo("Destroyer", 2);

        }
        printPlayerTwoField();

        System.out.println();
        promptEnter();

        // game starts
        System.out.println("The game starts!");
        System.out.println();

        while(true){
            if(onlyPrintGameFieldOnce) {
                //turn == true is player one, turn == false is player two
                if (turn) {
                    logic.getPlayerTwoFogOfWarField().printGameField();
                    System.out.println("---------------------");
                    logic.getPlayerOneField().printGameField();
                    System.out.println();
                    System.out.println("Player 1, it's your turn: ");
                } else {
                    logic.getPlayerOneFogOfWarField().printGameField();
                    System.out.println("---------------------");
                    logic.getPlayerTwoField().printGameField();
                    System.out.println();
                    System.out.println("Player 2, it's your turn: ");
                }

                System.out.println();
                System.out.print("> ");
                String coord = scanner.nextLine();
                System.out.println();

                //checks if hits are valid
                //if so, boolean validPlacement == true;
                if (turn) {
                    logic.checkHits(coord, logic.getPlayerTwoField(), logic.getPlayerTwoFogOfWarField(), logic.getSavedShipsPlayerTwo());
                } else {
                    logic.checkHits(coord, logic.getPlayerOneField(), logic.getPlayerOneFogOfWarField(), logic.getSavedShipsPlayerOne());
                }
            }


            if(logic.getValidPlacement()){
                onlyPrintGameFieldOnce = true;

                //checks win-condition before other results
                if(turn){
                    if(logic.checkPlayerOneWinCondition()){
                        logic.getPlayerTwoFogOfWarField().printGameField();
                        System.out.println();
                        System.out.println("You sank the last ship. You won. Congratulations!");
                        break;
                    }
                } else {
                    if(logic.checkPlayerTwoWinCondition()){
                        logic.getPlayerOneFogOfWarField().printGameField();
                        System.out.println();
                        System.out.println("You sank the last ship. You won. Congratulations!");
                        break;
                    }
                }

                //changes player
                countRounds();
                checkRound();

                //checks results of hit
                //player one
                if(turn){
                    if(!logic.checkIfShipIsIntact(logic.getSavedShipsPlayerTwo())){
                        System.out.println("You sank a ship!");
                        System.out.println();
                        promptEnter();
                    } else {
                        if(logic.getHit()) {
                            System.out.println("You hit a ship!");
                        } else {
                            System.out.println("You missed!");
                        }
                        System.out.println();
                        promptEnter();
                    }

                //player two
                } else {
                    if(!logic.checkIfShipIsIntact(logic.getSavedShipsPlayerOne())){
                        System.out.println("You sank a ship!");
                        System.out.println();
                        promptEnter();
                    } else {
                        if(logic.getHit()) {
                            System.out.println("You hit a ship!");
                        } else {
                            System.out.println("You missed!");
                        }
                        System.out.println();
                        promptEnter();
                    }
                }
            } else {
                //boolean in case of formal wrong coordinates
                //while loop starts again without printing the whole field again
                onlyPrintGameFieldOnce = false;
                System.out.println("Error! You entered the wrong coordinates! Try again:");
            }

        }

    }

    public void checkStatesPlayerOne(String shipName, int size){
        System.out.println();
        System.out.print("> ");
        String input = scanner.nextLine();
        System.out.println();
        logic.placeShip(input, size, logic.getPlayerOneField(), logic.getSavedShipsPlayerOne());
        checkIfValidMove = false;

        if (!logic.getValidPlacement()) {
            System.out.println("Error! Wrong length of the " + shipName + "! Try again:");
            checkIfValidMove = true;
        } else if(!logic.getValidCoordLocation()){
            System.out.println("Error! Wrong ship location! Try again:");
            checkIfValidMove = true;
        } else if(!logic.getNoAdjacentShips()){
            System.out.println("Error! You placed it too close to another one. Try again:");
            checkIfValidMove = true;
        }
    }

    public void checkStatesPlayerTwo(String shipName, int size){
        System.out.println();
        System.out.print("> ");
        String input = scanner.nextLine();
        System.out.println();
        logic.placeShip(input, size, logic.getPlayerTwoField(), logic.getSavedShipsPlayerTwo());
        checkIfValidMove = false;

        if (!logic.getValidPlacement()) {
            System.out.println("Error! Wrong length of the " + shipName + "! Try again:");
            checkIfValidMove = true;
        } else if(!logic.getValidCoordLocation()){
            System.out.println("Error! Wrong ship location! Try again:");
            checkIfValidMove = true;
        } else if(!logic.getNoAdjacentShips()){
            System.out.println("Error! You placed it too close to another one. Try again:");
            checkIfValidMove = true;
        }
    }

    public void promptEnter(){
        System.out.print("Press Enter and pass the move to another player");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();

    }

    public void printPlayerOneField(){
        logic.getPlayerOneField().printGameField();
        //resets validMove for next player
        checkIfValidMove = true;
    }

    public void printPlayerTwoField(){
        logic.getPlayerTwoField().printGameField();
        checkIfValidMove = true;
    }

    public int countRounds(){
        rounds++;
        return rounds;
    }

    public void checkRound(){
        if(rounds % 2 == 0){
            turn = true;
        } else{
            turn = false;
        }
    }
}

