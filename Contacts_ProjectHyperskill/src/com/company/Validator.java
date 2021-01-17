package com.company;

public class Validator {

    public String checkBirthDate(String date) {
        if (date.isEmpty()) {
            date = "[no data]";
            System.out.println("Bad birth date!");
            return date;
        }
        return date;
    }

    public String checkGender(String gender) {
        if (!(gender.equals("M") || gender.equals("F"))) {
            gender = "[no data]";
            System.out.println("Bad gender!");
            return gender;
        }
        return gender;
    }


    public String checkAddress(String address) {
        if (address.isEmpty()) {
            address = "[no data]";
            System.out.println("Bad address!");
            return address;
        }
        return address;
    }

    public String checkPhoneNumber(String phoneNumber) {
        // parenthesis in first number group
        String correctNumberRegexOne = "\\+?\\(?(\\w{2,}|\\d)\\)?((-|\\s)\\w{2,})*";
        // parenthesis in second number group
        String correctNumberRegexTwo = "\\+?(\\w{2,}|\\d)(-|\\s)\\(?\\w{2,}\\)?((-|\\s)\\w{2,})*";

        if (phoneNumber.matches(correctNumberRegexOne) || phoneNumber.matches(correctNumberRegexTwo)) {
            return phoneNumber;
        }

        System.out.println("Wrong number format!");
        return "[no number]";
    }


}
