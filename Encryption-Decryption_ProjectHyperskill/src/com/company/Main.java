package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // set default values if not passed via args
        String mode = "enc";
        int key = 0;
        String data = "";
        String inPath = "";
        String outPath = "output.txt";
        String code = "";
        String algorithm = "";

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-mode")) {
                mode = args[i+1];
            } else if (args[i].equals("-data")) {
                data = args[i+1];
            } else if (args[i].equals("-key")) {
                key = Integer.parseInt(args[i+1]);
            } else if (args[i].equals("-in")) {
                inPath = args[i+1];
            } else if (args[i].equals("-out")) {
                outPath = args[i+1];
            } else if (args[i].equals("-alg")) {
                algorithm = args[i + 1];
            }
        }

        // reads message from file
        String dataFromFile = readFile(inPath);

        if (algorithm.isEmpty() || algorithm.equals("shift")) {
            // prioritizes direct data input over input from file
            if (!inPath.isEmpty() && !data.isEmpty()) {
                if (mode.equals("enc")) {
                    code = encryptShift(data, key);
                } else {
                    code = decryptShift(data, key);
                }
            } else {
                if (mode.equals("enc")) {
                    code = encryptShift(dataFromFile, key);
                } else {
                    code = decryptShift(dataFromFile, key);
                }
            }
        } else {
            // prioritizes direct data input over input from file
            if (!inPath.isEmpty() && !data.isEmpty()) {
                if (mode.equals("enc")) {
                    code = encryptUnicode(data, key);
                } else {
                    code = decryptUnicode(data, key);
                }
            } else {
                if (mode.equals("enc")) {
                    code = encryptUnicode(dataFromFile, key);
                } else {
                    code = decryptUnicode(dataFromFile, key);
                }
            }
        }

        // reads message to file
        readToFile(outPath, code);
    }

    private static void readToFile(String path, String code) {
        File file = new File(path);

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(code);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String readFile(String path) {
        File file = new File(path);
        String message = "";

        try (Scanner scanner = new Scanner(file)) {
            message = scanner.nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return message;
    }

    private static String decryptUnicode(String word, int key) {
        StringBuilder builder = new StringBuilder();

        for (Character character : word.toCharArray()) {
            builder.append((char) (character - key));
        }

        return builder.toString();
    }

    private static String encryptUnicode(String word, int key) {
        StringBuilder builder = new StringBuilder();

        for (Character character : word.toCharArray()) {
            builder.append((char) (character + key));
        }

        return builder.toString();
    }


    private static String encryptShift(String word, int shift) {
        /*
        ASCII: a = 97.... z = 123;
         */

        StringBuilder builder = new StringBuilder();

        for (Character character : word.toCharArray()) {
            if (character >= 97 && character < 123) {
                if (character + shift < 123) {
                    builder.append((char) (character + shift));
                } else {
                    builder.append((char) (character + shift - 26));
                }
                // characters which are not letters
            } else {
                builder.append(character);
            }
        }

        return builder.toString();
    }

    private static String decryptShift(String word, int shift) {
        StringBuilder builder = new StringBuilder();

        for (Character character : word.toCharArray()) {
            if (character >= 97 && character < 123) {
                if (character - shift >= 97) {
                    builder.append((char) (character - shift));
                } else {
                    builder.append((char) (character - shift + 26));
                }
            } else {
                builder.append(character);
            }
        }

        return builder.toString();
    }
}
