package com.company;

public class PlayerMark {
    private char playerOne;
    private char playerTwo;


    public PlayerMark() {
        this.playerOne = 'X';
        this.playerTwo = 'O';
    }


    //return matching mark based on round
    public char getPlayerMark(boolean xTurn) {
        if(xTurn) {
            return playerOne;
        } else {
            return playerTwo;
        }
    }

}
