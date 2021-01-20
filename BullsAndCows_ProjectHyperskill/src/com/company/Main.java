package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    static boolean notFound = true;
    static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        System.out.println("Please, enter the secret code's length:");
        String inputLength = scanner.nextLine();
        if (!catchInputFormatErrors(inputLength)) {
            return;
        }

        System.out.println("Input the number of possible symbols in the code:");
        String inputRange = scanner.nextLine();
        if (!catchInputFormatErrors(inputRange)) {
            return;
        }

        int lengthOfCode = Integer.parseInt(inputLength);
        int rangeOfCharacter = Integer.parseInt(inputRange);
        int turn = 1;

        if (!catchLogicErrors(lengthOfCode, rangeOfCharacter)) {
            return;
        }

        String code = createCode(lengthOfCode, rangeOfCharacter);
        printSecretCode(lengthOfCode, rangeOfCharacter);


        while (notFound) {
            System.out.println("Turn " + turn + ". Answer:");
            String guess = scanner.nextLine();

            // if correct guess, this method sets notFound to false
            System.out.println(analyzeInput(guess, code));
            turn++;
        }

        System.out.println("Congratulations! You guessed the secret code.");
    }


    public static boolean catchInputFormatErrors(String input) {
        if (!input.matches("\\d+")) {
            System.out.println("Error: " + input + " isn't a valid number.");
            return false;
        }
        return true;
    }


    public static boolean catchLogicErrors(int codeLength, int range) {
        if (range < codeLength) {
            System.out.println("Error: it's not possible to generate a code with a length of " + codeLength + " with " + range + " unique symbols.");
            return false;
        } else if (codeLength > 36 || codeLength <= 0) {
            System.out.println("Error: can't generate a secret number with a length of " + codeLength + " because there aren't enough unique digits.");
            return false;
        } else if (range > 36 ||range <= 0) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return false;
        }
        return true;
    }


    public static String createCode(int lengthOfCode, int rangeOfCharacter) {
        List<String> uniqueCharacters = new ArrayList<>(List.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"));

        List<String> selectedCharacters = uniqueCharacters.subList(0, rangeOfCharacter);
        Collections.shuffle(selectedCharacters);

        // code cannot start with 0
        while (selectedCharacters.get(0).equals("0")) {
            Collections.shuffle(selectedCharacters);
        }

        StringBuilder code = new StringBuilder();
        for (int i = 0; i < lengthOfCode; i++) {
            code.append(selectedCharacters.get(i));
        }

        return code.toString();
    }


    public static void printSecretCode(int length, int range) {
        // ASCII-value equal to 'a';
        char maxCharacter = 97;
        // subtract eleven for the possible numbers 0-9 and letter 'a'
        maxCharacter += range - 10 - 1;

        System.out.print("The secret is prepared: ");

        for (int i = 0; i < length; i++) {
            System.out.print("*");
        }

        if (range <= 10) {
            System.out.println(" (0-9).");
        } else if (range == 11) {
            System.out.println(" (0-9, a).");
        } else {
            System.out.println(" (0-9, a-" + maxCharacter + ").");
        }
    }


    public static String analyzeInput(String guess, String code) {
        int bulls = 0;
        int cows = 0;

        if (guess.isEmpty()) {
            return "Grade: None. ";
        }

        String[] guessPieces = guess.split("");
        String[] codePieces = code.split("");

        for (int i = 0; i < guessPieces.length; i++) {
            if (guessPieces[i].equals(codePieces[i])) {
                bulls++;
            } else if (code.contains(guessPieces[i])) {
                cows++;
            }
        }


        if (bulls == code.length()) {
            // win
            notFound = false;
            return "Grade: " + bulls + " bull(s). ";
        } else if (cows == 0 && bulls == 0) {
            return "Grade: None. ";
        } else if (cows == 0) {
            return "Grade: " + bulls + " bull(s). ";
        } else if (bulls == 0) {
            return "Grade: " + cows + " cow(s). ";
        } else {
            return "Grade: " + bulls + " bull(s) and " + cows + " cow(s). ";
        }
    }
}
