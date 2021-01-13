package com.company;

import java.util.*;

// returns all the persons which matches none the query-words
public class SearchNone extends SearchStrategies {

    public SearchNone(Map<String, ArrayList<Integer>> invertedIndex) {
        super(invertedIndex);
    }

    @Override
    public Set<String> search(List<String> fileData, String query) {
        Set<String> matchingPersons = new HashSet<>();
        String[] queryPieces = query.split(" ");


        for (int i = 0; i < fileData.size(); i++) {
            if (containsNoWord(fileData.get(i), queryPieces)) {
                matchingPersons.add(fileData.get(i));
            }
        }

        return matchingPersons;
    }

    public static boolean containsNoWord(String person, String[] keywords) {

        for (String key : keywords) {
            if (person.toLowerCase().contains(key) || person.toUpperCase().contains(key) || person.contains(key)) {
                return false;
            }
        }
        return true;
    }
}
