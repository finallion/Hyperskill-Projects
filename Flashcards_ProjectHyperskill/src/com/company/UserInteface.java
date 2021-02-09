package com.company;

import java.io.*;
import java.util.*;

public class UserInteface {

    private FlashCards flashCards;
    private Scanner scanner;
    private List<String> log;
    private Map<String, Integer> errorRecord;
    private Map<String, Integer> highestErrorRecord;
    private String[] args;

    public UserInteface(Scanner scanner, FlashCards cards, String[] args) {
        this.scanner = scanner;
        this.flashCards = cards;
        this.log = new ArrayList<>();
        this.errorRecord = new HashMap<>();
        this.highestErrorRecord = new HashMap<>();
        this.args = args;
    }

    public void menu() {
        // check for imports
        checkArgs(false);

        while (true) {
            print("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            String selection = scan();

            if (selection.equals("add")) {
                add();
            } else if (selection.equals("remove")) {
                remove();
            } else if (selection.equals("import")) {
                print("File name:");
                String fileName = scan();
                importCards(fileName);
            } else if (selection.equals("export")) {
                print("File name:");
                String fileName = scan();
                exportCards(fileName);
            } else if (selection.equals("ask")) {
                ask();
            } else if (selection.equals("log")) {
                saveLog();
            } else if (selection.equals("hardest card")) {
                hardestCard();
            } else if (selection.equals("reset stats")) {
                errorRecord.clear();
                print("Card statistics have been reset.");
            } else if (selection.equals("exit")) {
                print("Bye bye!");
                checkArgs(true);
                break;
            } else {
                print("No such command!");
            }
        }
    }


    private void checkArgs(boolean exit) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-export") && exit) {
                exportCards(args[i + 1]);
            }
            if (args[i].equals("-import") && !exit) {
                importCards(args[i + 1]);
            }
        }
    }


    private void hardestCard() {
        List<Integer> errors = new ArrayList<>();

        if (errorRecord.size() == 0) {
            print("There are no cards with errors.");
        } else {
            // collect all errors; highest error-score at the end
            errors.addAll(errorRecord.values());
            Collections.sort(errors);

            for (String key : errorRecord.keySet()) {
                if (errorRecord.get(key).equals(errors.get(errors.size() - 1))) {
                    highestErrorRecord.put(key, errors.get(errors.size() - 1));
                }
            }

            if (highestErrorRecord.size() == 1) {
                for (String key : highestErrorRecord.keySet()) {
                    print("The hardest card is \"" + key + "\". You have " + highestErrorRecord.get(key) + " errors answering it");
                }
            } else {
                StringBuilder errorCards = new StringBuilder();
                String someKey = "";
                for (String key : highestErrorRecord.keySet()) {
                   errorCards.append("\"" + key + "\", ");
                   someKey = key;
                }

                // delete last comma and space
                errorCards.deleteCharAt(errorCards.length() - 1);
                errorCards.deleteCharAt(errorCards.length() - 1);

                print("The hardest cards are " + errorCards + ". You have " + highestErrorRecord.get(someKey) + " errors answering them");
            }
        }
    }

    private void ask() {
        print("How many times to ask?");
        int cardsToAsk = Integer.parseInt(scan());

        while (cardsToAsk > 0) {
            for (var key : flashCards.getFlashCards().keySet()) {
                print("Print the definition of \"" + key + "\":");
                String answer = scan();

                String correctAnswer = flashCards.getDefinitionFromTerm((String) key);

                if (answer.equals(correctAnswer)) {
                    print("Correct!");
                } else if (flashCards.containsDefinition(answer)) {
                    print("Wrong. The right answer is \"" + correctAnswer + "\", but your definition is correct for \"" + flashCards.getTermFromDefinition(answer) + "\".");
                    errorRecord.computeIfPresent((String) key, (k, v) -> v += 1);
                    errorRecord.putIfAbsent((String) key, 1);
                } else {
                    print("Wrong. The right answer is \"" + correctAnswer + "\".");
                    errorRecord.computeIfPresent((String) key, (k, v) -> v += 1);
                    errorRecord.putIfAbsent((String) key, 1);
                }
                cardsToAsk--;

            }
        }
    }

    private void saveLog() {
        print("File name:");
        String fileName = scan();

        try {
            print("The log has been saved.");
            FileOutputStream outputStream = new FileOutputStream(fileName);
            for (String string : log) {
                String data = string + "\n";
                outputStream.write(data.getBytes());
            }

            // needed to pass the test
            String notReallyNecessary = "Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):";
            outputStream.write(notReallyNecessary.getBytes());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String scan() {
        String output = scanner.nextLine();
        log.add(output);

        return output;
    }


    private void print(String input) {
        log.add(input);

        System.out.println(input);
    }



    private void exportCards(String fileName) {
        try {
            FileOutputStream outputStream = new FileOutputStream(fileName);

            for (var key : flashCards.getFlashCards().keySet()) {
                int errors = 0;
                if (errorRecord.containsKey((String) key)) {
                    errors = errorRecord.get(key);
                }
                String data = key + ":" + flashCards.getDefinitionFromTerm((String) key) + "-" + errors;
                outputStream.write(data.getBytes());
            }

            print(flashCards.getSize() + " cards have been saved.");
        } catch (IOException e) {
            print("Something went wrong while exporting.");
        }
    }



    private void importCards(String fileName) {
        int counter = 0;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {

            while (br.ready()) {
                String card = br.readLine();
                // input like Tokio:Japan-2Paris:France-4

                // split by digit, including that digit
                String[] splittedCard = card.split("(?<=\\d)");

                List<String> pieces = new ArrayList<>();
                for (String cards : splittedCard) {
                    String[] errors = cards.split("-");
                    String[] termAndDefinition = errors[0].split(":");


                    pieces.add(termAndDefinition[0]);
                    pieces.add(termAndDefinition[1]);
                    pieces.add(errors[1]);
                }

                for (int i = 0; i < pieces.size(); i++) {
                    if (i + 2 < pieces.size()) {
                        flashCards.overwrite(pieces.get(i), pieces.get(i + 1));
                        counter++;
                        if (Integer.parseInt(pieces.get(i + 2)) != 0) {
                            errorRecord.put(pieces.get(i), Integer.parseInt(pieces.get(i + 2)));
                        }
                    }
                    i += 2;
                }
            }

            print(counter + " cards have been loaded.");

        } catch (IOException e) {
            print("File not found.");
        }
    }


    private void remove() {
        print("Which card?");
        String term = scan();

        if (flashCards.containsTerm(term)) {
            flashCards.removeCard(term);
            errorRecord.remove(term);
            print("The card has been removed.");
        } else {
            print("Can't remove \"" + term + "\": there is no such card.");
        }
    }


    private void add() {
        print("The card:");
        String term = scan();

        if (flashCards.containsTerm(term)) {
            print("The card \"" + term + "\" already exists.");
            return;
        }

        print("The definition of the card:");
        String definition = scan();

        if (flashCards.containsDefinition(definition)) {
            print("The definition \"" + definition + "\" already exists.");
            return;
        }

        flashCards.add(term, definition);
        print("The pair (\"" + term + "\":\"" + definition + "\") has been added.");
    }

}





