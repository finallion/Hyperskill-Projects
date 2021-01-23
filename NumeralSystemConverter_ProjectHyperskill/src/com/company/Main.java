package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            int sourceRadix = Integer.parseInt(scanner.nextLine());
            String sourceNumber = scanner.nextLine();
            int targetRadix = Integer.parseInt(scanner.nextLine());

            if (sourceRadix > 36 || sourceRadix < 1 || targetRadix > 36 || targetRadix < 1) {
                System.out.println("Error");
                return;
            }

            if (!sourceNumber.contains(".")) {
                int sourceNumberAsDecimal = 0;

                if (sourceRadix == 1) {
                    sourceNumberAsDecimal = sourceNumber.length();
                } else {
                    sourceNumberAsDecimal = Integer.parseInt(sourceNumber, sourceRadix);
                }

                if (targetRadix == 1) {
                    for (int i = 0; i < sourceNumberAsDecimal; i++) {
                        System.out.print("1");
                    }

                } else {
                    System.out.println(Integer.toString(sourceNumberAsDecimal, targetRadix));
                }

            } else {
                System.out.println(convertFractionToDecimal(sourceNumber, sourceRadix, targetRadix));
            }

        } catch (Exception e) {
            System.out.println("Error");
        }
    }


    public static String convertFractionToDecimal(String sourceNumber, int sourceRadix, int targetRadix) {
        String result = "";
        String[] pieces = sourceNumber.split("\\.");

        int integerPart = 0;

        if (sourceRadix == 1) {
            integerPart = pieces[0].length();
        } else {
            integerPart = Integer.parseInt(pieces[0], sourceRadix);
        }


        double fractionPart = 0;

        for (int i = 0; i < pieces[1].length(); i++) {
            if (Character.isDigit(pieces[1].charAt(i))) {
                fractionPart += (pieces[1].charAt(i) - '0') / Math.pow(sourceRadix, i + 1);
            } else {
                fractionPart += (pieces[1].charAt(i) - 'a' + 10) / Math.pow(sourceRadix, i + 1);
            }
        }

        double fractionalNumber = integerPart + fractionPart;

        result = convertFractionWithTargetRadix(fractionalNumber, targetRadix);

        return result;
    }

    public static String convertFractionWithTargetRadix(double decimalFractionalNumber, int targetRadix) {
        // returns number value before the dot
        int integerPart = (int) decimalFractionalNumber;

        // returns number value after the dot
        double fractionalPart = decimalFractionalNumber - integerPart;

        StringBuilder result = new StringBuilder();
        StringBuilder radixIsOneBuilderFractional = new StringBuilder();
        StringBuilder radixIsOneBuilderDecimal = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            int digit = (int) (fractionalPart * targetRadix);
            fractionalPart = fractionalPart * targetRadix - digit;

            if (targetRadix == 1) {
                for (int j = 0; j < digit; j++) {
                    radixIsOneBuilderFractional.append("1");
                }
                result.append(radixIsOneBuilderFractional.toString());
            } else {
                result.append(Integer.toString(digit, targetRadix));
            }
        }

        if (targetRadix == 1) {
            for (int i = 0; i < integerPart; i++) {
                radixIsOneBuilderDecimal.append("1");
            }
            return radixIsOneBuilderDecimal.toString() + "." + result;
        } else {
            return Integer.toString(integerPart, targetRadix) + "." + result;
        }

    }
    
}
