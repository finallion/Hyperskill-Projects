package com.company;

import java.util.ArrayList;
import java.util.List;

public class LinearSearchAlgorithm {

    public List<String> doLinearSearch(List<String> directory, List<String> find) {
        List<String> matches = new ArrayList<>();

        for (String searchedContact : find) {
            for (String contact : directory) {
                String[] contactPieces = contact.split(" ");
                if (contactPieces.length > 2) {
                    if (searchedContact.equals(contactPieces[1] + " " + contactPieces[2])) {
                        matches.add(contact);
                    }
                } else {
                    if (searchedContact.equals(contactPieces[1])) {
                        matches.add(contact);
                    }
                }
            }
        }
        return matches;
    }


}
