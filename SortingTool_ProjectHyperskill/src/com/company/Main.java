package com.company;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    private  static Scanner scanner = new Scanner(System.in);

    public static void main(final String[] args) {
        // accepts -dataType: long || line || word &&/|| -sortingType: natural || byCount &&/|| -inputFile input.txt &&/|| -outputFile output.txt

        StringBuilder arguments = new StringBuilder();
        for (String arg : args) {
            arguments.append(arg).append(" ");
        }

        String command = arguments.toString();

        checkExceptions(command);
        checkFileCommands(command, args);
        executeSortingCommand(command);
    }

    private static void executeSortingCommand(String command) {
        boolean naturalOrder = true;
        if (command.contains("byCount")) {
            naturalOrder = false;
        }

        if (command.contains("long")) {
            printResult(sortLong(naturalOrder), 1, naturalOrder);
        } else if (command.contains("line")) {
            printResult(sortLine(naturalOrder), 2, naturalOrder);
        } else if (command.contains("word")) {
            printResult(sortWord(naturalOrder), 3, naturalOrder);
        }
    }


    // stage 5
    private static void checkExceptions(String command) {
        try {
            if (command.contains("-sortingType") && !(command.contains("byCount") || command.contains("natural"))) {
                throw new SortingTypeException("No sorting type defined!");
            }

            if (command.contains("-dataType") && !(command.contains("long") || command.contains("line") || command.contains("word"))) {
                throw new DataTypeException("No data type defined!");
            }

            String[] commandPieces = command.split("(?=-)");
            for (String arg : commandPieces) {
                if (arg.trim().equals("-sortingType byCount") || arg.trim().equals("-sortingType natural") || arg.trim().equals("-dataType long") ||  arg.trim().equals("-dataType word") || arg.trim().equals("-dataType line") || arg.trim().equals("-inputFile input.txt") || arg.trim().equals("-outputFile output.txt")) {
                } else {
                    System.out.println("\"" + arg + "\" is not a valid parameter. It will be skipped.");
                }
            }
        } catch (SortingTypeException | DataTypeException s) {
            System.out.println(s.getMessage());
        }
    }

    // stage 6
    private static void checkFileCommands(String command, String[] args) {
        if (command.contains("-inputFile")) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-inputFile")) {
                    new File(args[i + 1]);
                }
            }
        }
        if (command.contains("-outputFile")) {
            File file = new File("out.txt");

            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    private static List<String> sortWord(boolean order) {
        // implement scanner loop condition; test case didn't liked it

        List<String> words = new ArrayList<>();

        while (scanner.hasNext()) {
            String word = scanner.next();
            words.add(word);
        }

        if (order) {
            words.sort((Comparator.comparingInt(String::length)));
        }

        return words;
    }


    private static List<Long> sortLong(boolean order) {
        // implement scanner loop condition; test case didn't liked it

        List<Long> numbers = new ArrayList<>();

        while (scanner.hasNext()) {
            String input = scanner.next();
            // stage 5
            try {
                long number = Long.parseLong(input);
                numbers.add(number);
            } catch (Exception e) {
                System.out.println("\"" + input + "\"" + " is not a long. It will be skipped.");
            }

        }

        if (order) {
            Collections.sort(numbers);
        }

        return numbers;
    }

    private static List<String> sortLine(boolean order) {
        // implement scanner loop condition; test case didn't liked it
        List<String> lines = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lines.add(line);
        }

        if (order) {
            lines.sort((Comparator.comparingInt(String::length)));
        }
        return lines;
    }


    private static <T> void printResult(List<T> list, int format, boolean natural) {
        if (format == 1) {
            System.out.println("Total numbers: " + list.size());
        } else if (format == 2) {
            System.out.println("Total lines: " + list.size());
        } else if (format == 3) {
            System.out.println("Total words: " + list.size());
        }

        // natural sorting
        if (natural) {
            System.out.print("Sorted data: ");
            for (T integer : list) {
                System.out.print(integer + " ");
            }
            // byCount sorting
        } else {
            List<String[]> printStatements = new ArrayList<>();

            for (int i = 0; i < list.size(); i++) {
                int howMany = countAppearance(list, i);
                int percentage = (int) (((double) howMany / list.size()) * 100);
                String[] singleParts = {String.valueOf(list.get(i)), ": ", howMany + " time(s), ", String.valueOf(percentage), "%"};

                if (!checkDuplicates(printStatements, singleParts)) {
                    printStatements.add(singleParts);
                }
            }

            // sort by integer value (for long input)
            if (format == 1) {
                printStatements.sort((x, y) -> Integer.parseInt(x[0]) - Integer.parseInt(y[0]));

                // sort by input order (for word and line inout)
            } else {
                printStatements.sort((x, y) -> String.CASE_INSENSITIVE_ORDER.compare(x[0], y[0]));
            }

            // after first sort, sort by highest appearance count
            printStatements.sort((x, y) -> Integer.parseInt(x[3]) - Integer.parseInt(y[3]));

            for (String[] printStatement : printStatements) {
                for (String s : printStatement) {
                    System.out.print(s);
                }
                System.out.println();
            }

        }
    }


    private static boolean checkDuplicates(List<String[]> printStatements, String[] input) {
        for (String[] printStatement : printStatements) {
            if (printStatement[0].equals(input[0])) {
                return true;
            }
        }
        return false;
    }


    private static <T> int countAppearance(List<T> list, int index) {
        if (list.isEmpty() || index >= list.size()) {
            return -1;
        }

        T value = list.get(index);
        int counter = 0;
        for (T t : list) {
            if (t.equals(value)) {
                counter++;
            }
        }
        return counter;
    }
}
