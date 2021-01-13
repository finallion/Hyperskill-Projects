package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class SearchStrategies {
    Map<String, ArrayList<Integer>> invertedIndex;

    public SearchStrategies(Map<String, ArrayList<Integer>> invertedIndex) {
        this.invertedIndex = invertedIndex;
    }


    public abstract Set<String> search(List<String> fileData, String query);

}
