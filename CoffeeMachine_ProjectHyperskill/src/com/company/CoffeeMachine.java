package com.company;

import java.util.Scanner;

public class CoffeeMachine {
    private static int water = 400;
    private static int milk = 540;
    private static int beans = 120;
    private static int cups = 9;
    private static int money = 550;

    public static void printMenu() {
        System.out.println("The coffee machine has:");
        System.out.println(water + " of water");
        System.out.println(milk + " of milk");
        System.out.println(beans + " of coffee beans");
        System.out.println(cups + " of disposable cups");
        System.out.println(money + " of money");
    }


    public static void buyCoffee(Scanner scanner) {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        String selection = scanner.nextLine();

        if (selection.equals("1")) {
            enoughRessources(250, 0, 16, 1);
            money += 4;

        } else if (selection.equals("2")) {
            enoughRessources(350, 75, 20, 1);
            money += 7;

        } else if (selection.equals("3")) {
            enoughRessources(200, 100, 12, 1);
            money += 6;
        }
    }


    public static void enoughRessources(int waterNeeded, int milkNeeded, int beansNeeded, int cupsNeeded) {
        if (water - waterNeeded < 0) {
            System.out.println("Sorry, not enough water!");
        } else if (milk - milkNeeded < 0) {
            System.out.println("Sorry, not enough milk!");
        } else if (beans - beansNeeded < 0) {
            System.out.println("Sorry, not enough coffee beans!");
        } else if (cups - cupsNeeded < 0) {
            System.out.println("Sorry, not enough cups!");
        } else {
            System.out.println("I have enough resources, making you a coffee!");
            water -= waterNeeded;
            milk -= milkNeeded;
            beans -= beansNeeded;
            cups -= cupsNeeded;
        }
    }


    public static void fill(Scanner scanner) {
        System.out.println("Write how many ml of water do you want to add: ");
        water += Integer.parseInt(scanner.nextLine());
        System.out.println("Write how many ml of milk do you want to add: ");
        milk += Integer.parseInt(scanner.nextLine());
        System.out.println("Write how many grams of coffee beans do you want to add: ");
        beans += Integer.parseInt(scanner.nextLine());
        System.out.println("Write how many disposable cups of coffee do you want to add: ");
        cups += Integer.parseInt(scanner.nextLine());
    }




    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = true;


        while (exit) {
            System.out.println("Write action (buy, fill, take, remaining, exit)");
            String command = scanner.nextLine();
            System.out.println();

            if (command.equals("remaining")) {
                printMenu();
            } else if (command.equals("buy")) {
                buyCoffee(scanner);
            } else if (command.equals("fill")) {
                fill(scanner);
            } else if (command.equals("take")) {
                System.out.println("I gave you " + money);
                money = 0;
            } else if (command.equals("exit")) {
                exit = false;
            }
            
        }
    }
}
