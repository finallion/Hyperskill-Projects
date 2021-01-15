package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean firstRound = true;

        System.out.print("How many mines do you want on the field? ");
        int numberOfMines = Integer.valueOf(scanner.nextLine());

        minesweeper.Map map = new minesweeper.Map(9, 9, numberOfMines);
        map.fillMapsForFirstRound();
        map.printDisplayMap();


        while (true) {
            boolean validMove = false;

            System.out.print("Set/unset mines marks or claim a cell as free: ");
            String input = scanner.nextLine();

            String[] pieces = input.split(" ");

            if (pieces.length < 3) {
                System.out.println("Invalid input");
            } else {
                int col = Integer.valueOf(pieces[0]) - 1;
                int row = Integer.valueOf(pieces[1]) - 1;
                String command = pieces[2];

                if (command.equals("mine")) {
                    if (map.markCell(col, row)) {
                        validMove = true;
                    } else {
                        System.out.println("There is a number here.");
                    }
                } else if (command.equals("free")) {
                    // number 7 handles the recursion calls to reveal the empty fields of the map
                    // this number needs to be higher if the game field gets bigger. BUT: The bigger the slower the loading time
                    boolean free = map.setCell(col, row, 7, firstRound);

                    if (!free) {
                        map.addMinesToLostMap();
                        map.printDisplayMap();
                        System.out.println("You stepped on a mine and failed!");
                        break;
                    } else {
                        validMove = true;
                    }
                } else {
                    System.out.println("Invalid input");
                }
            }

            if (validMove) {
                if ((map.checkWinByMarking() || map.checkWinByEvading())) {
                    map.printDisplayMap();
                    System.out.println("Congratulations! You found all mines!");
                    break;
                } else {
                    System.out.println();
                    map.printDisplayMap();

                }
            }

            firstRound = false;
        }



    }
}
