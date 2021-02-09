package com.company;

import java.util.LinkedHashMap;
import java.util.Map;

public class FlashCards {
    private Map<String, String> flashCards = new LinkedHashMap<>();

    public Map getFlashCards() {
        return flashCards;
    }

    public int getSize() {
        return flashCards.size();
    }

    public String getDefinitionFromTerm(String term) {
        for (String key : flashCards.keySet()) {
            if (key.equals(term)) {
                return flashCards.get(key);
            }
        }
        return null;
    }


    public void add(String term, String definition) {
        flashCards.put(term, definition);
    }


    public void overwrite(String term, String definition) {
        if (containsTerm(term)) {
            flashCards.remove(term, getDefinitionFromTerm(term));
        } else if (containsDefinition(definition)) {
            flashCards.remove(getTermFromDefinition(definition), definition);
        }
        add(term, definition);
    }

    public boolean containsTerm(String term) {
        for (String key : flashCards.keySet()) {
            if (key.equals(term)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsDefinition(String definition) {
        for (String value : flashCards.values()) {
            if (value.equals(definition)) {
                return true;
            }
        }
        return false;
    }

    public String getTermFromDefinition(String definition) {
        for (String key : flashCards.keySet()) {
            if (flashCards.get(key).equals(definition)) {
                return key;
            }
        }
        return null;
    }

    public void removeCard(String input) {
        if (containsTerm(input)) {
            flashCards.remove(input, getDefinitionFromTerm(input));
        } else if (containsDefinition(input)) {
            flashCards.remove(getTermFromDefinition(input), input);
        }
    }
}
