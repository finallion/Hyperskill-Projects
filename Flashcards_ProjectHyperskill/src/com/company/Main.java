package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FlashCards flashCards = new FlashCards();
        UserInteface ui = new UserInteface(scanner, flashCards, args);

        //start
        ui.menu();
    }
}
