package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    // IMPORTANT: Test files currently broken
    // The tests replace the regular hyphen with some other hyphen, causing the tests to fail
    // and output "No age .... "
    // replace the second hyphen in the test code line 150 with your regular hyphen
    // this is the line 150: reply = reply.replace("–", "-");
    // additionally you need to delete one empty line, although it is shown in the example
    // see line 27

    public static void main(String[] args) throws IOException {
        String file = readFile(args[0]);

        System.out.println("The text is:");
        System.out.println(file);
        //System.out.println();

        Scanner scanner = new Scanner(System.in);
        int[] count = countWordsCharactersSyllables(file);

        int sumOfWords = count[0];
        int sumOfCharacters = count[1];
        int sumOfSyllables = count[2];
        int sumOfPolysyllables = count[3];
        int sumOfSentences = countSentences(file);

        System.out.println("Words: " + sumOfWords);
        System.out.println("Sentences: " + sumOfSentences);
        System.out.println("Characters: " + sumOfCharacters);
        System.out.println("Syllables: " + sumOfSyllables);
        System.out.println("Polysyllables: " + sumOfPolysyllables);

        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        String command = scanner.nextLine();
        System.out.println();

        double ariScore = calculateAutomatedReadabilityIndex(sumOfSentences, sumOfWords, sumOfCharacters);
        double fkScore = calculateFleschKincaidReadability(sumOfSentences, sumOfWords, sumOfSyllables);
        double smogScore = calculateSimpleMeasure(sumOfSentences, sumOfPolysyllables);
        double clScore = calculateColemanLiauIndex(sumOfSentences, sumOfWords, sumOfCharacters);

        double average = calculateAverage(ariScore, fkScore, smogScore, clScore);

        if (command.equals("ARI")) {
            System.out.println("Automated Readability Index: " + ariScore + " (about " + getAgeToScore(ariScore) + "-year–olds).");
        } else if (command.equals("FK")) {
            System.out.println("Flesch–Kincaid readability tests: " + fkScore + " (about " + getAgeToScore(fkScore) + "-year–olds).");
        } else if (command.equals("SMOG")) {
            System.out.println("Simple Measure of Gobbledygook: " + smogScore + " (about " + getAgeToScore(smogScore) + "-year–olds).");
        } else if (command.equals("CL")) {
            System.out.println("Coleman–Liau index: " + clScore + " (about " + getAgeToScore(clScore) + "-year–olds).");
        } else if (command.equals("all")) {
            System.out.println("Automated Readability Index: " + ariScore + " (about " + getAgeToScore(ariScore) + "-year–olds).");
            System.out.println("Flesch–Kincaid readability tests: " + fkScore + " (about " + getAgeToScore(fkScore) + "-year–olds).");
            System.out.println("Simple Measure of Gobbledygook: " + smogScore + " (about " + getAgeToScore(smogScore) + "-year–olds).");
            System.out.println("Coleman–Liau index: " + clScore + " (about " + getAgeToScore(clScore) + "-year–olds).");

            System.out.println();
            System.out.println("This text should be understood in average by " + average + "-year-olds.");
        }

    }

    private static double calculateAverage(double scoreOne, double scoreTwo, double scoreThree, double scoreFour) {
        double average = getAgeToScore(scoreOne) + getAgeToScore(scoreTwo) + getAgeToScore(scoreThree) + getAgeToScore(scoreFour);
        return average / 4;
    }

    private static double calculateColemanLiauIndex(int sentences, int words, int characters) {
        double sIndex = (double) sentences / words * 100;
        double lIndex = (double) characters / words * 100;
        return Math.round((0.0588 * lIndex - 0.296 * sIndex - 15.8) * 100.0) / 100.0;
    }

    private static double calculateSimpleMeasure(int sentences, int polysyllable) {
        return Math.round((1.043 * Math.sqrt((double) polysyllable * 30 / sentences) + 3.1291) * 100.0) / 100.0;
    }

    private static double calculateFleschKincaidReadability(int sentences, int words, int syllables) {
        return Math.round((0.39 * words / sentences + 11.8 * syllables / words - 15.59) * 100.0) / 100.0;
    }


    private static double calculateAutomatedReadabilityIndex(int sentences, int words, int characters) {
        return Math.round((4.71 * characters / words + 0.5 * words / sentences - 21.43) * 100.0) / 100.0;
    }

    private static int countSyllables(String word) {
        int sumOfSyllables = 0;

        List<Character> vowels = new ArrayList<>(List.of('a', 'e', 'i', 'o', 'u', 'y', 'A', 'E', 'I', 'O', 'U', 'Y'));

        for (Character c : word.toCharArray()) {
            for (int i = 0; i < vowels.size(); i++) {
                if (c == vowels.get(i)) {
                    sumOfSyllables++;
                }
            }
        }

        // reduce count for double-vowels
        Matcher matcher = Pattern.compile("[aeiouyAEIOUY]{2}").matcher(word);

        while (matcher.find()) {
            sumOfSyllables--;
        }

        // words that end with dot
        if (word.charAt(word.length() - 1) == '.' || word.charAt(word.length() - 1) == '!' || word.charAt(word.length() - 1) == '?') {
            if (word.charAt(word.length() - 2) == 'e') {
                sumOfSyllables--;
            }
        }

        // words that end with e
        if (word.charAt(word.length() - 1) == 'e') {
            sumOfSyllables--;
        }

        // the word "you" counts as 1 syllable
        Matcher you = Pattern.compile("[aeiouyAEIOUY]{3}").matcher(word);
        while (you.find()) {
            sumOfSyllables = 1;
        }


        if (sumOfSyllables == 0) {
            return 1;
        } else {
            return sumOfSyllables;
        }
    }

    private static int countSentences(String file) {
        int sumOfSentences = 0;

        for (String sentence : file.split("\\.|\\!|\\?")) {
            sumOfSentences += 1;
        }

        return sumOfSentences;
    }


    private static int[] countWordsCharactersSyllables(String file) {
        int sumOfWords = 0;
        int sumOfCharacters = 0;
        int sumOfSyllables = 0;
        int sumOfPolysyllables = 0;

        for (String word : file.split(" ")) {
            sumOfWords += 1;
            sumOfCharacters += word.length();
            sumOfSyllables += countSyllables(word);
            if (countSyllables(word) >= 3) {
                sumOfPolysyllables++;
            }
        }

        int[] counts = {sumOfWords, sumOfCharacters, sumOfSyllables, sumOfPolysyllables};
        return counts;
    }

    private static int getAgeToScore(double score) {
        if (score >= 14) {
            return 25;
        } else if (score >= 13) {
            return 24;
        } else if (score >= 12) {
            return 18;
        } else if (score >= 11) {
            return 17;
        } else if (score >= 10) {
            return 16;
        } else if (score >= 9) {
            return 15;
        } else if (score >= 8) {
            return 14;
        } else if (score >= 7) {
            return 13;
        } else if (score >= 6) {
            return 12;
        } else if (score >= 5) {
            return 11;
        } else if (score >= 4) {
            return 10;
        } else if (score >= 3) {
            return 9;
        } else if (score >= 2) {
            return 7;
        } else if (score >= 1) {
            return 6;
        }
        return -1;
    }

    private static String readFile(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }
}
