package com.company;


public class GameField {
    private char[][] field;
    private char[][] fogOfWarField;
    private int[] xAxis;

    public GameField(){
        this.field = new char[10][11];
        this.fogOfWarField = new char[10][11];
        this.xAxis = new int[11];

    }

    public void setFogOfWarFieldValue(int x, int y, char newValue){
        fogOfWarField[x][y] = newValue;
    }


    public void setGameFieldValue(int x, int y, char newValue){
        field[x][y] = newValue;
    }

    public boolean checkGameFieldValue(int x, int y, char value){
        if(field[x][y] == value){
            return true;
        }
        return false;
    }

    public boolean checkFogOfWarFieldValue(int x, int y, char value){
        if(fogOfWarField[x][y] == value){
            return true;
        }
        return false;
    }


    public void buildGameField(){
        char firstLetter = 'A';
        int firstNumber = 1;

        //build yAxis
        for(int i = 0; i < 10; i++){
            this.field[i][0] = firstLetter;
            firstLetter++;
        }

        //build xAxis
        for(int j = 1; j < 11; j++){
            xAxis[j] = firstNumber;
            firstNumber++;
        }

        //build fog of war
        for(int k = 0; k < 10; k++){
            for(int l = 1; l < 11; l++){
                this.field[k][l] = '~';
            }
        }

    }


    public void printGameField(){
        System.out.print("  ");
        for(int i = 1; i < 11; i++){
            System.out.print(xAxis[i]);
            System.out.print(" ");
        }
        System.out.println();


        for(int j = 0; j < 10; j++){
            for(int k = 0; k < 11; k++){
                System.out.print(this.field[j][k]);
                System.out.print(" ");
            }
            System.out.println();
        }

    }
}
