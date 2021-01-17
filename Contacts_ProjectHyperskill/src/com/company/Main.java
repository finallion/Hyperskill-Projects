package com.company;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main implements Serializable {
    private static Contacts contacts = new Contacts();
    private static Validator val = new Validator();
    private static final long serialVersionUID = 7L;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // file name specified in command-line arguments
        String fileName = args[1];

        while (true) {
            System.out.print("[menu] Enter action (add, list, search, count, exit): ");
            String action = scanner.nextLine();

            if (action.equals("count")) {
                countContacts();
            }  else if (action.equals("add")) {
                addMenu(scanner);
            } else if (action.equals("list")) {
                listMenu(scanner);
            } else if (action.equals("search")) {
                searchContacts(scanner);
            } else if (action.equals("exit")) {
                break;
            }
        }

        // all objects contained in contacts including contacts implement Serializable
        try {
            SerializationUtils.serialize(contacts, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void countContacts() {
        System.out.println("The Phone Book has " + contacts.getSize() + " records.");
        System.out.println();
    }

    public static void addMenu(Scanner scanner) {
        LocalDateTime time = LocalDateTime.now();
        System.out.print("Enter the type (person, organization): ");
        String type = scanner.nextLine();

        if (type.equals("person")) {
            System.out.print("Enter the name: ");
            String name = scanner.nextLine();

            System.out.print("Enter the surname: ");
            String surname = scanner.nextLine();

            System.out.print("Enter the birth date: ");
            String birthDate = val.checkBirthDate(scanner.nextLine());

            System.out.print("Enter the gender (M, F): ");
            String gender = val.checkGender(scanner.nextLine());

            System.out.print("Enter the number: ");
            String phoneNumber = val.checkPhoneNumber(scanner.nextLine());

            Records record = new PersonRecord(name, surname, birthDate, gender, phoneNumber, time, time);
            contacts.addRecord(record);

            System.out.println("The record added.");
            System.out.println();

        } else if (type.equals("organization")) {
            System.out.print("Enter the organization name: ");
            String name = scanner.nextLine();

            System.out.print("Enter the address: ");
            String address = val.checkAddress(scanner.nextLine());

            System.out.print("Enter the number: ");
            String phoneNumber = val.checkPhoneNumber(scanner.nextLine());

            Records record = new OrganizationRecord(name, address, phoneNumber, time, time);
            contacts.addRecord(record);

            System.out.println("The record added.");
            System.out.println();
        }
    }

    public static void listMenu(Scanner scanner) {
        contacts.printContacts();
        System.out.println();

        System.out.print("[list] Enter action ([number], back): ");
        String index = scanner.nextLine();

        if (index.matches("\\d+")) {
            System.out.println(contacts.printAllContactInfo(Integer.valueOf(index) - 1));
            System.out.println();
            editMenu(scanner, Integer.valueOf(index) - 1);
        } else {
            System.out.println();
        }
    }


    public static void searchContacts(Scanner scanner) {
        List<Records> searchResults = new ArrayList<>();

        System.out.print("Enter search query: ");
        String query = scanner.nextLine();
        int counter = 0;
        int index = 1;

        for (int i = 0; i < contacts.getSize(); i++) {
            Matcher matcher = Pattern.compile("\\.*" + query + "\\.*", Pattern.CASE_INSENSITIVE).matcher(contacts.getRecords(i).info());

            if (matcher.find()) {
                searchResults.add(contacts.getRecords(i));
                counter++;
            }
        }

        if (counter > 0) {
            System.out.println("Found " + counter + " results:");
            for (Records records : searchResults) {
                System.out.print(index + ". ");
                System.out.println(records);
                index++;
            }
        } else {
            System.out.println("No results.");
        }
        System.out.println();
        searchMenu(scanner);
    }

    public static void searchMenu(Scanner scanner) {
        while (true) {
            System.out.print("[search] Enter action ([number], back, again): ");
            String action = scanner.nextLine();

            if (action.matches("\\d+")) {
                System.out.println(contacts.printAllContactInfo(Integer.valueOf(action) - 1));
                System.out.println();
                editMenu(scanner, Integer.valueOf(action) - 1);
                break;
            } else if (action.equals("again")) {
                searchContacts(scanner);
            } else if (action.equals("back")) {
                break;
            }
        }
    }


    public static void editMenu(Scanner scanner, int index) {
        LocalDateTime time = LocalDateTime.now();

        while (true) {
            System.out.print("[record] Enter action (edit, delete, menu): ");
            String action = scanner.nextLine();

            if (action.equals("edit")) {
                if (contacts.getRecords(index) instanceof PersonRecord) {
                    System.out.print("Select a field (name, surname, birth, gender, number): ");
                    String field = scanner.nextLine();

                    if (field.equals("name")) {
                        System.out.print("Enter name: ");
                        String newName = scanner.nextLine();
                        contacts.getRecords(index).setName(newName);

                    } else if (field.equals("surname")) {
                        System.out.print("Enter surname: ");
                        String newSurname = scanner.nextLine();
                        contacts.getPersonRecord(index).setSurname(newSurname);

                    } else if (field.equals("birth")) {
                        System.out.print("Enter birth date: ");
                        String newBirthDate = scanner.nextLine();
                        contacts.getPersonRecord(index).setBirthDate(newBirthDate);

                    } else if (field.equals("gender")) {
                        System.out.print("Enter gender: ");
                        String newGender = scanner.nextLine();
                        contacts.getPersonRecord(index).setGender(newGender);

                    } else if (field.equals("number")) {
                        System.out.print("Enter number: ");
                        String newNumber = scanner.nextLine();
                        contacts.getRecords(index).setPhoneNumber(newNumber);
                    }
                } else {
                    System.out.print("Select a field (name, address, number): ");
                    String field = scanner.nextLine();

                    if (field.equals("name")) {
                        System.out.print("Enter name: ");
                        String newName = scanner.nextLine();
                        contacts.getRecords(index).setName(newName);

                    } else if (field.equals("address")) {
                        System.out.print("Enter address: ");
                        String newAddress = scanner.nextLine();
                        contacts.getOrganizationRecord(index).setAddress(newAddress);

                    } else if (field.equals("number")) {
                        System.out.print("Enter number: ");
                        String newNumber = scanner.nextLine();
                        contacts.getRecords(index).setPhoneNumber(newNumber);
                    }
                }

                contacts.getRecords(index).setEditionTime(time);

                System.out.println("Saved");
                System.out.println(contacts.printAllContactInfo(index));

            } else if (action.equals("delete")) {
                contacts.removeRecord(index);
                System.out.println("Deleted");
            } else if (action.equals("menu")) {
                System.out.println();
                break;
            }
        }
    }
}