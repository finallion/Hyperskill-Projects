package com.company;

public class TurnLogic {
    private int round = 0;


    public void incrementRound() {
        this.round++;
    }

    public int getRound() {
        return this.round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public boolean xTurn() {
        if(this.round % 2 == 0) {
            return true;
        }
        return false;
    }

}
