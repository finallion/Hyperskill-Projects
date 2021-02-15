package com.company;

import java.util.Scanner;

public class Main {

    /*
    Adaption from Eugene Fischer from the solutions in Hyperskill. Thanks for the amazing code and implementation of the MVC pattern.
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();

        LifeBoard board = new LifeBoard(size);
        GameOfLife window = new GameOfLife();
        LifeController controller = new LifeController(board, window, size);
        controller.start();

    }
}
