package com.company;

import java.util.Scanner;

public class Cinema {
    private static int currentIncome = 0;
    private static int purchasedTickets = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of rows:");
        int row = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter the number of seats in each row:");
        int col = Integer.parseInt(scanner.nextLine());

        CinemaManager cinema = new CinemaManager(row, col);
        cinema.fillCinema();
        System.out.println();

        while (true) {
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");

            String input = scanner.nextLine();
            System.out.println();

            if (input.equals("1")) {
                cinema.printCinema();
            } else if (input.equals("2")) {
                buyTicket(scanner, cinema);
            } else if (input.equals("3")) {
                displayStatistics(cinema);
            } else if (input.equals("0")) {
                break;
            }
        }
    }

    public static void displayStatistics(CinemaManager cinema) {
        double percentage = 0.00;

        if (purchasedTickets != 0) {
            percentage = ((double) purchasedTickets / cinema.getSize() * 100);
        }

        System.out.println();
        System.out.println("Number of purchased tickets: " + purchasedTickets);
        System.out.println("Percentage: " + String.format("%.2f", percentage) + "%");
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + cinema.totalPricing());
    }


    public static void buyTicket(Scanner scanner, CinemaManager cinema) {
        boolean invalidPlacement = true;

        while (invalidPlacement) {
            System.out.println("Enter a row number: ");
            int rowNumber = scanner.nextInt() - 1;

            System.out.println("Enter a seat number in that row:");
            int colNumber = scanner.nextInt() - 1;


            if (!cinema.validSize(rowNumber,colNumber)) {
                System.out.println("Wrong input!");

            } else if (cinema.getSeat(rowNumber, colNumber) == 'B') {
                System.out.println("That ticket has already been purchased!");

            } else {
                cinema.setSeat(rowNumber, colNumber, 'B');

                int prize = cinema.singlePricing(rowNumber + 1);
                currentIncome += prize;
                purchasedTickets++;

                System.out.println();
                System.out.println("Ticket price: $" + prize);
                System.out.println();

                invalidPlacement = false;
            }
        }
    }
}