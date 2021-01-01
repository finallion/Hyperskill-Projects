package com.company;

import java.util.ArrayList;

public class Logic {
    private GameField playerOneField;
    private GameField playerOneFogOfWarField;
    private GameField playerTwoField;
    private GameField playerTwoFogOfWarField;
    private boolean validPlacement;
    private boolean validCoordLocation;
    private boolean noNearbyShips;
    private boolean hit;
    private boolean playerOneWin;
    private boolean playerTwoWin;
    private ArrayList<String> savedShipsPlayerOne;
    private ArrayList<String> savedShipsPlayerTwo;

    public Logic(){
        this.playerOneField = new GameField();
        this.playerOneFogOfWarField = new GameField();
        this.playerTwoField = new GameField();
        this.playerTwoFogOfWarField = new GameField();
        this.validPlacement = true;
        this.validCoordLocation = true;
        this.noNearbyShips = true;
        this.hit = false;
        this.playerOneWin = false;
        this.playerTwoWin = false;
        this.savedShipsPlayerOne = new ArrayList<>();
        this.savedShipsPlayerTwo = new ArrayList<>();
    }

    public ArrayList<String> getSavedShipsPlayerOne(){
        return savedShipsPlayerOne;
    }

    public ArrayList<String> getSavedShipsPlayerTwo(){
        return savedShipsPlayerTwo;
    }

    public GameField getPlayerOneField(){
        return playerOneField;
    }

    public GameField getPlayerTwoField(){
        return playerTwoField;
    }

    public GameField getPlayerOneFogOfWarField(){
        return playerOneFogOfWarField;
    }

    public GameField getPlayerTwoFogOfWarField(){
        return playerTwoFogOfWarField;
    }

    public boolean getValidPlacement(){
        return validPlacement;
    }

    public boolean getValidCoordLocation(){
        return validCoordLocation;
    }

    public boolean getNoAdjacentShips(){
        return noNearbyShips;
    }

    public boolean getHit(){
        return hit;
    }

    public boolean getPlayerOneWinCondition(){
        return playerOneWin;
    }

    public boolean getPlayerTwoWinCondition(){
        return playerTwoWin;
    }

    public boolean checkPlayerTwoWinCondition(){
        playerTwoWin = true;
        for(int i = 0; i < 10; i++){
            for(int j = 1; j < 11; j++){
                if (playerOneField.checkGameFieldValue(i, j, 'O')){
                    playerTwoWin = false;
                }
            }
        }
        return playerTwoWin;
    }


    public boolean checkPlayerOneWinCondition(){
        playerOneWin = true;
        for(int i = 0; i < 10; i++){
            for(int j = 1; j < 11; j++){
                if (playerTwoField.checkGameFieldValue(i, j, 'O')){
                    playerOneWin = false;
                }
            }
        }
        return playerOneWin;
    }

    public void checkHits(String hitCoord, GameField playerField, GameField playerFogOfWarField, ArrayList<String> savedShipsPlayer){
        hit = false;
        validPlacement = false;
        String[] pieces = hitCoord.split("");
        //translates char-Coord to integer
        int yCoord = pieces[0].charAt(0) - 65;
        int xCoord = Integer.valueOf(pieces[1]);

        //solves later casting of ints higher than 9 into chars
        if(pieces.length > 2){
            if(Integer.valueOf(pieces[2]) == 0){
                xCoord *= 10;
            }
        }


        if(!(yCoord > 9 || yCoord < 0 || xCoord > 10 || xCoord < 1)){
            validPlacement = true;
            //if coord equals to ship or earlier hit
            if(playerField.checkGameFieldValue(yCoord, xCoord, 'O') || playerField.checkGameFieldValue(yCoord, xCoord, 'X')){
                hit = true;
                playerField.setGameFieldValue(yCoord, xCoord, 'X');
                playerFogOfWarField.setGameFieldValue(yCoord, xCoord, 'X');
                //removes value from the list until all values for one ship are removed
                savedShipsPlayer.remove("" + yCoord + xCoord);
            } else {
                playerField.setGameFieldValue(yCoord, xCoord, 'M');
                playerFogOfWarField.setGameFieldValue(yCoord, xCoord, 'M');
            }
        }

    }

    public boolean checkIfShipIsIntact(ArrayList<String> savedShipsPlayer){
        for(int i = 0; i < savedShipsPlayer.size(); i++){
            if(i + 1 < savedShipsPlayer.size()){
                //each hit deletes the the matching ship-coordinate from the list (checkHits-method)
                //until all coordinates from one ship are deleted, so that two sets of ## are next to each other
                //this signals that one ship is completly destroyed
                if(savedShipsPlayer.get(i) == "##" && savedShipsPlayer.get(i + 1) == "##"){
                    savedShipsPlayer.remove(i);
                    return false;
                }
            }
        }
        return true;
    }


    public void saveShip(int yStart, int xStart, int yEnd, int xEnd, ArrayList<String> savedShipsPlayer){
        if(yStart == yEnd){
            for(int i = xStart; i <= xEnd; i++){
                savedShipsPlayer.add("" + yStart + i);
            }
        } else {
            for(int j = yStart; j <= yEnd; j++){
                savedShipsPlayer.add("" + j + xStart);
            }
        }
        savedShipsPlayer.add("##");
    }


    public void placeShip(String input, int size, GameField playerField, ArrayList<String> savedShipsPlayer){
        validPlacement = true;
        validCoordLocation = true;
        noNearbyShips = true;

        //input example: F3 F7
        String[] coordinates = input.split(" ");
        String[] firstCoordinate = coordinates[0].split("");
        String[] secondCoordinate = coordinates[1].split("");

        int yCoordOne = firstCoordinate[0].charAt(0) - 65; //F -> int 5
        int xCoordOne = Integer.valueOf(firstCoordinate[1]); //3
        int yCoordTwo = secondCoordinate[0].charAt(0) - 65; //F -> int 5
        int xCoordTwo = Integer.valueOf(secondCoordinate[1]); //7


        //these handle cases of input-values equal 10
        if(firstCoordinate.length > 2){
            xCoordOne *= 10;
        }

        if(secondCoordinate.length > 2){
            xCoordTwo *= 10;
        }


        //checks first if ship is placed horizontal or vertical
        //places the ship beginning from the lower value

        //horizontal check (letters are the same)
        if(yCoordOne == yCoordTwo){
            if(Math.abs(xCoordOne - xCoordTwo) + 1 == size){
                //analyzes which coordinate is the higher one
                if(xCoordOne < xCoordTwo){
                    //checks for nearby ships
                    if(checkAdjacentFieldsHorizontal(yCoordOne, xCoordOne, xCoordTwo, playerField)){
                        for(int i = xCoordOne; i <= xCoordTwo; i++){
                            playerField.setGameFieldValue(yCoordOne, i, 'O');
                        }
                        saveShip(yCoordOne, xCoordOne, yCoordTwo, xCoordTwo, savedShipsPlayer);
                    } else {
                        noNearbyShips = false;
                    }
                } else if (xCoordOne > xCoordTwo){
                    if(checkAdjacentFieldsHorizontal(yCoordOne, xCoordTwo, xCoordOne, playerField)) {
                        for (int i = xCoordTwo; i <= xCoordOne; i++) {
                            playerField.setGameFieldValue(yCoordOne, i, 'O');
                        }
                        saveShip(yCoordOne, xCoordTwo, yCoordTwo, xCoordOne, savedShipsPlayer);
                    } else {
                        noNearbyShips = false;
                    }

                }
            } else {
                validPlacement = false;
            }

        //vertical check (letters are different, numbers are the same)
        } else if(xCoordOne == xCoordTwo){

            if(Math.abs(yCoordOne - yCoordTwo) + 1 == size){
                //analyzes which coordinate is the higher one
                if(yCoordOne < yCoordTwo){
                    if(checkAdjacentFieldsVertical(yCoordOne, yCoordTwo, xCoordOne, playerField)){
                        for(int j = yCoordOne; j <= yCoordTwo; j++){
                            playerField.setGameFieldValue(j, xCoordOne, 'O');
                        }
                        saveShip(yCoordOne, xCoordOne, yCoordTwo, xCoordTwo, savedShipsPlayer);
                    } else {
                        noNearbyShips = false;
                    }

                } else if(yCoordOne > yCoordTwo){
                    if(checkAdjacentFieldsVertical(yCoordTwo, yCoordOne, xCoordOne, playerField)){
                        for(int j = yCoordTwo; j <= yCoordOne; j++){
                            playerField.setGameFieldValue(j, xCoordOne, 'O');
                        }
                        saveShip(yCoordTwo, xCoordOne, yCoordOne, xCoordTwo, savedShipsPlayer);
                    } else {
                        noNearbyShips = false;
                    }
                }
            }
            else {
                validPlacement = false;
            }
        } else {
            validCoordLocation = false;
        }

    }


    public boolean checkAdjacentFieldsHorizontal(int newCoord, int min, int max, GameField playerField){
        //checks adjacent fields for horizontal placed ships
        //translates y-Axis character to int

        //if ship is placed in upper row
        if(newCoord == 0){
            //for loop checks long side for other ships
            for(int i = min; i < max; i++) {
                if (playerField.checkGameFieldValue(newCoord + 1, i, 'O')) {
                    return false;
                }
            }
            //if-statement checks short side for other ships
            if (min != 1) {
                if (playerField.checkGameFieldValue(newCoord, min - 1, 'O')) {
                    return false;
                }
            }
            if (max != 10){
                if (playerField.checkGameFieldValue(newCoord, max + 1, 'O')) {
                    return false;
                }
            }

        //if ship is placed in lower row
        } else if(newCoord == 9){
            //for loop checks long side for other ships
            for(int j = min; j < max; j++) {
                if (playerField.checkGameFieldValue(newCoord - 1, j, 'O')) {
                    return false;
                }
            }
            //if-statement checks short side for other ships
            if (min != 1) {
                if (playerField.checkGameFieldValue(newCoord, min - 1, 'O')) {
                    return false;
                }
            }

            if (max != 10){
                if (playerField.checkGameFieldValue(newCoord, max + 1, 'O')) {
                    return false;
                }
            }
        //if ship is placed anywhere else
        } else {
            //for loop checks long side for other ships
            for(int k = min; k < max; k++){
                if (playerField.checkGameFieldValue(newCoord - 1, k, 'O')) {
                    return false;
                }
                if (playerField.checkGameFieldValue(newCoord + 1, k, 'O')) {
                    return false;
                }
            }
            //if-statement checks short side for other ships
            if (min != 1) {
                if (playerField.checkGameFieldValue(newCoord, min - 1, 'O')) {
                    return false;
                }
            }
            if (max != 10){
                if (playerField.checkGameFieldValue(newCoord, max + 1, 'O')) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean checkAdjacentFieldsVertical(int newCoordOne, int newCoordTwo, int place, GameField playerField){
        //checks adjacent fields for vertical placed ships
        //translates y-Axis character to int

        //if ship crosses another ship
        for(int i = newCoordOne; i <= newCoordTwo; i++){
            if(playerField.checkGameFieldValue(i, place, 'O')){
                return false;
            }
        }

        //if ship is placed on the most left
        if(place == 0){
            //for loop checks long side for other ships
            for(int i = newCoordOne; i < newCoordTwo; i++) {
                if (playerField.checkGameFieldValue(i, place + 1, 'O')) {
                    return false;
                }
            }
            //if-statement checks short side for other ships
            if(newCoordOne != 0){
                if (playerField.checkGameFieldValue(newCoordOne - 1, place, 'O')) {
                    return false;
                }
            }
            if(newCoordTwo != 9){
                if (playerField.checkGameFieldValue(newCoordTwo + 1, place, 'O')) {
                    return false;
                }
            }

        //if ship is placed on the most right
        } else if(place == 10){
            //for loop checks long side for other ships
            for(int i = newCoordOne; i < newCoordTwo; i++) {
                if (playerField.checkGameFieldValue(i, place - 1, 'O')) {
                    return false;
                }
            }
            // if-statement checks short side for other ships
            if(newCoordOne != 0){
                if (playerField.checkGameFieldValue(newCoordOne - 1, place, 'O')) {
                    return false;
                }
            }
            if(newCoordTwo != 9){
                if (playerField.checkGameFieldValue(newCoordTwo + 1, place, 'O')) {
                    return false;
                }
            }

        //if ship is placed anywhere else
        } else {
            //for loop checks long side for other ships
            for(int i = newCoordOne; i < newCoordTwo; i++) {
                if (playerField.checkGameFieldValue(i, place - 1, 'O')) {
                    return false;
                }
                if (playerField.checkGameFieldValue(i, place + 1, 'O')) {
                    return false;
                }
            }
            // if-statement checks short side for other ships
            if(newCoordOne != 0){
                if (playerField.checkGameFieldValue(newCoordOne - 1, place, 'O')) {
                    return false;
                }
            }

            if(newCoordTwo != 9){
                if (playerField.checkGameFieldValue(newCoordTwo + 1, place, 'O')) {
                    return false;
                }
            }
        }
        return true;
    }
}
