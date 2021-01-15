package com.company;

public enum Marks {
    MINE('X'), EMPTY('.'), FLAGGED('*'), CLEAR('/');

    private char c;

    Marks(char c) {
        this.c = c;
    }


    public char getMark() {
        return c;
    }
}
