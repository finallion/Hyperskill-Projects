package com.company;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class HashingSearchAlgorithm {
    Hashtable<String, String> contacts;

    public HashingSearchAlgorithm(int size) {
        contacts = new Hashtable<>(size * 2);
    }

    public List<String> doHashSearch(List<String> find) {
        List<String> matchesHashing = new ArrayList<>();

        for (String searchedContact : find) {
            if (contacts.containsKey(searchedContact)) {
                matchesHashing.add(contacts.get(searchedContact) + " " + searchedContact);
            }
        }
        return matchesHashing;
    }


    public void hashing(List<String> directory) {
        for (int i = 0; i < directory.size(); i++) {
            contacts.put(directory.get(i).split("[\\d]+\\s")[1], directory.get(i).split(" ")[0]);
        }
    }

}
