package com.company;

import java.util.*;

// returns all the persons which match all the query-words
public class SearchAll extends SearchStrategies {

    public SearchAll(Map<String, ArrayList<Integer>> invertedIndex) {
        super(invertedIndex);
    }

    @Override
    public Set<String> search(List<String> fileData, String query) {
        List<Integer> matchingIndexes = new ArrayList<>();
        Set<String> matchingPersons = new HashSet<>();
        String[] queryPieces = query.split(" ");


        for (String piece : queryPieces) {
            if (invertedIndex.containsKey(piece.toLowerCase())) {
                matchingIndexes.addAll(invertedIndex.get(piece.toLowerCase()));
            }
        }

        for (Integer index : matchingIndexes) {
            if (containsAllWords(fileData.get(index), queryPieces)) {
                matchingPersons.add(fileData.get(index));
            }
        }

        return matchingPersons;
    }

    public static boolean containsAllWords(String person, String[] keywords) {
        for (String key : keywords) {
            if (!person.toLowerCase().contains(key) || !person.toUpperCase().contains(key) || !person.contains(key)) {
                return false;
            }
        }
        return true;
    }
}
