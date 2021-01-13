package com.company;

import java.io.File;
import java.util.*;

public class Main {
    public static Map<String, ArrayList<Integer>> invertedIndexes = new HashMap<>();


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> persons = new ArrayList<>();
        Set<String> matchingPersons = new HashSet<>();

        // input file name into args
        if (args[0].equals("--data")) {
            File input = new File(args[1]);
            persons = fileReader(input);
        }

        // reads first single words to map
        // then indexes these words
        mapWordsFromFile(persons);
        mapIndexesFromFile(persons);


        while (true) {
            printMenu();
            String selection = scanner.nextLine();
            System.out.println();

            if (selection.equals("1")) {
                System.out.println("Select a matching strategy: ALL, ANY, NONE");
                String strategy = scanner.nextLine();
                System.out.println();

                if (strategy.equals("ALL") || strategy.equals("ANY") || strategy.equals("NONE")) {
                    System.out.println("Enter a name or email to search all suitable people.");
                    String query = scanner.nextLine();

                    if (strategy.equals("ALL")) {
                        SearchStrategies searchAll = new SearchAll(invertedIndexes);
                        matchingPersons = searchAll.search(persons, query);

                    } else if (strategy.equals("ANY")) {
                        SearchStrategies searchAny = new SearchAny(invertedIndexes);
                        matchingPersons = searchAny.search(persons, query);

                    } else if (strategy.equals("NONE")) {
                        SearchStrategies searchNone = new SearchNone(invertedIndexes);
                        matchingPersons = searchNone.search(persons, query);
                    }

                    printMatches(matchingPersons);

                } else {
                    System.out.println("Incorrect option! Try again.");
                }
                System.out.println();


            } else if (selection.equals("2")) {
                printPersons(persons);

            } else if (selection.equals("0")) {
                System.out.println("Bye!");
                break;

            } else {
                System.out.println("Incorrect option! Try again.");
            }
        }
    }


    public static void printMatches(Set<String> matches) {
        if (matches.isEmpty()) {
            System.out.println("No matching people found.");
            System.out.println();
        } else {
            for (String match : matches) {
                System.out.println(match);
            }
        }
    }


    public static void mapIndexesFromFile(List<String> fileData) {
        for (int index = 0; index < fileData.size(); index++) {
            for (String key : invertedIndexes.keySet()) {
                if (fileData.get(index).toLowerCase().contains(key)) {
                    invertedIndexes.get(key).add(index);
                }
            }
        }
    }



    public static void mapWordsFromFile(List<String> fileData) {
        for (String person : fileData) {
            String[] singleWords = person.split(" ");

            for (String word : singleWords) {
                invertedIndexes.put(word.toLowerCase(), new ArrayList<>());
            }
        }
    }



    public static void printPersons(List<String> input) {
        for (String person : input) {
            System.out.println(person.trim());
        }
    }


    public static void printMenu() {
        System.out.println("=== Menu ===");
        System.out.println("1. Find a person");
        System.out.println("2. Print all people");
        System.out.println("0. Exit");
    }


    public static List<String> fileReader(File file) {
        List<String> input = new ArrayList<>();

        try (Scanner fileScanner = new Scanner(file)) {

            do {
                String line = fileScanner.nextLine();
                input.add(line);
            } while (fileScanner.hasNext());


        } catch (Exception e) {
            e.getMessage();
        }

        return input;
    }
}
