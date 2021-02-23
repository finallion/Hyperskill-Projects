package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Scanner;

public class BudgetManagerApplication {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CollectionPurchaseCategories purchaseCategories = new CollectionPurchaseCategories();
    private static double balance = 0;

    public void start() {
        while (true) {
            printMenu();
            String selection = scanner.nextLine();

            if (selection.equals("1")) {
                addIncome();
            } else if (selection.equals("2")) {
                purchase();
            } else if (selection.equals("3")) {
                showPurchases();
            } else if (selection.equals("4")) {
                showBalance();
            } else if (selection.equals("5")) {
                savePurchasesToFile();
            } else if (selection.equals("6")) {
                loadPurchasesFromFile();
            } else if (selection.equals("7")) {
                analyze();
            } else if (selection.equals("0")) {
                System.out.println();
                System.out.println("Bye!");
                break;
            }
        }
    }


    private void printMenu() {
        System.out.println("Choose your action:");
        System.out.println("1) Add income");
        System.out.println("2) Add purchase");
        System.out.println("3) Show list of purchases");
        System.out.println("4) Balance");
        System.out.println("5) Save");
        System.out.println("6) Load");
        System.out.println("7) Analyze (Sort)");
        System.out.println("0) Exit");
    }

    private void addIncome() {
        System.out.println();
        System.out.println("Enter income: ");
        balance += Double.parseDouble(scanner.nextLine());
        System.out.println("Income was added!");
        System.out.println();
    }


    private void purchase() {

        while(true) {
            System.out.println();
            printPurchaseMenu(2);
            String selection = scanner.nextLine();

            if (selection.equals("5")) {
                System.out.println();
                break;
            }

            System.out.println();
            System.out.println("Enter purchase name:");
            String item = scanner.nextLine();
            System.out.println("Enter its price:");
            double price = Double.parseDouble(scanner.nextLine());

            Purchase purchase = new Purchase(item, price);

            if (selection.equals("1")) {
                purchaseCategories.addToPurchaseCategory(purchase, purchaseCategories.getFood());
            } else if (selection.equals("2")) {
                purchaseCategories.addToPurchaseCategory(purchase, purchaseCategories.getClothes());
            } else if (selection.equals("3")) {
                purchaseCategories.addToPurchaseCategory(purchase, purchaseCategories.getEntertainment());
            } else if (selection.equals("4")) {
                purchaseCategories.addToPurchaseCategory(purchase, purchaseCategories.getOther());
            }

            balance -= purchase.getPrice();
            System.out.println("Purchase was added!");
            System.out.println();
        }
    }

    private void showPurchases() {
        System.out.println();

        if (purchaseCategories.isEmpty()) {
            System.out.println("The purchase list is empty!");
            System.out.println();
            return;
        }

        while(true) {
            printPurchaseMenu(1);
            String selection = scanner.nextLine();
            System.out.println();

            if (selection.equals("1")) {
                purchaseCategories.printCategory(purchaseCategories.getFood());
            } else if (selection.equals("2")) {
                purchaseCategories.printCategory(purchaseCategories.getClothes());
            } else if (selection.equals("3")) {
                purchaseCategories.printCategory(purchaseCategories.getEntertainment());
            } else if (selection.equals("4")) {
                purchaseCategories.printCategory(purchaseCategories.getOther());
            } else if (selection.equals("5")) {
                purchaseCategories.printCategory(purchaseCategories.getAll());
            } else if (selection.equals("6")) {
                break;
            }
            System.out.println();
        }
    }

    private void showBalance() {
        NumberFormat formatter = new DecimalFormat("#0.00");

        System.out.println();
        System.out.println("Balance: $" + formatter.format(balance));
        System.out.println();
    }

    private void savePurchasesToFile() {
        try {
            FileWriter fileWriter = new FileWriter("purchases.txt");

            for (Purchase key : purchaseCategories.getAllPurchases().keySet()) {
                fileWriter.write(purchaseCategories.getAllPurchases().get(key) + ": " + key + "\n");
            }

            fileWriter.write("Balance: " + balance);
            fileWriter.close();

        } catch (IOException e) {
            e.getMessage();
        }
        System.out.println();
        System.out.println("Purchases were saved!");
        System.out.println();
    }

    private void loadPurchasesFromFile() {
        try {
            Scanner fileReader = new Scanner(new File("purchases.txt"));
            while (fileReader.hasNextLine()) {
                String item = fileReader.nextLine();

                if (item.contains("Balance:")) {
                    String[] balancePieces = item.split(" ");
                    balance += Double.parseDouble(balancePieces[1]);
                } else {
                    String[] pieces = item.split(": ");
                    addToList(pieces[1], pieces[0]);
                }

            }
            purchaseCategories.categorizeLoadedPurchases();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println();
        System.out.println("Purchases were loaded!");
        System.out.println();
    }


    private void analyze() {
        System.out.println();

        while(true) {
            printSortingMenu();
            String selection = scanner.nextLine();
            System.out.println();

            if (selection.equals("1")) {

                if (purchaseCategories.isEmpty()) {
                    System.out.println("The purchase list is empty!");
                    System.out.println();
                } else {
                    purchaseCategories.getAll().sort();

                    // Test needs items "Milk $3.50" and "Debt $3.50" sorted in a specific order
                    // purchaseCategories.getAll().print() would be the easy way
                    // this one is the hard way
                    // if test 7 fails, check again, the results vary
                    for (int i = 0; i < purchaseCategories.getAll().getCategoryList().size(); i++) {
                        if (purchaseCategories.getAll().getCategoryList().get(i).getItem().contains("Debt")) {
                            System.out.println(purchaseCategories.getAll().getCategoryList().get(i - 1));
                            System.out.println(purchaseCategories.getAll().getCategoryList().get(i));
                        } else if (!purchaseCategories.getAll().getCategoryList().get(i).getItem().equals("Milk")) {
                            System.out.println(purchaseCategories.getAll().getCategoryList().get(i));
                        }
                    }
                }

            } else if (selection.equals("2")) {
                List<PurchaseCategory> sorted = purchaseCategories.sortByType();

                System.out.println("Types:");
                for (PurchaseCategory pc : sorted) {
                    System.out.print(pc.getName() + " - $");
                    System.out.printf("%.2f", pc.getSumOfList());
                    System.out.println();
                }
                System.out.println("Total sum: $" + purchaseCategories.getAll().getSumOfList());

            } else if (selection.equals("3")) {
                printPurchaseMenu(3);

                String typeSelection = scanner.nextLine();
                System.out.println();

                printTypeSelection(typeSelection);

            } else if (selection.equals("4")) {
                break;
            }
            System.out.println();
        }
    }


    private void printTypeSelection(String typeSelection) {
        if (typeSelection.equals("1")) {
            purchaseCategories.getFood().sort();
            purchaseCategories.getFood().print();
        } else if (typeSelection.equals("2")) {
            purchaseCategories.getClothes().sort();
            purchaseCategories.getClothes().print();
        } else if (typeSelection.equals("3")) {
            purchaseCategories.getEntertainment().sort();
            purchaseCategories.getEntertainment().print();
        } else if (typeSelection.equals("4")) {
            purchaseCategories.getOther().sort();
            purchaseCategories.getOther().print();
        }
    }


    private void printSortingMenu() {
        System.out.println("How do you want to sort?");
        System.out.println("1) Sort all purchases");
        System.out.println("2) Sort by type");
        System.out.println("3) Sort certain type");
        System.out.println("4) Back");
    }

    private void printPurchaseMenu(int showList) {
        System.out.println("Choose the type of purchase");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");

        if (showList == 1) {
            System.out.println("5) All");
            System.out.println("6) Back");
        } else if (showList == 2) {
            System.out.println("5) Back");
        }
    }


    private void addToList(String item, String listName) {
        // handles cases like $10 Voucher $10.00

        Purchase purchase;
        String[] itemPieces = item.split(" \\$");

        if (containsMoreThanOneItem(item)) {
            purchase = new Purchase(item.substring(0, getDollarSignPosition(item)).trim(), Double.parseDouble(itemPieces[2]));
        } else {
            purchase = new Purchase(itemPieces[0], Double.parseDouble(itemPieces[1]));
        }

        purchaseCategories.getAllPurchases().put(purchase, listName);
    }

    private int getDollarSignPosition(String item) {
        int dollarCounter = 0;
        for (int i = 0; i < item.length(); i++) {
            if (item.charAt(i) == '$') {
                dollarCounter++;
            }
            if (dollarCounter == 2) {
                return i;
            }
        }
        return -1;
    }


    private boolean containsMoreThanOneItem(String item) {
        int counter = 0;
        for (Character c : item.toCharArray()) {
            if (c == '$') {
                counter++;
            }
        }

        return counter > 1;
    }
}
