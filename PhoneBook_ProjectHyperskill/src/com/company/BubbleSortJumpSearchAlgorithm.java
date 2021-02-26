package com.company;

import java.util.ArrayList;
import java.util.List;

public class BubbleSortJumpSearchAlgorithm {

    public List<String> doJumpSearch(List<String> directory, List<String> find) {
        List<String> matches = new ArrayList<>();

        for (String searchedContact : find) {
            int currentRight = 0;
            int previousRight = 0;

            if (directory.get(currentRight).split("[\\d]+\\s")[1].equals(searchedContact)) {
                matches.add(directory.get(currentRight));
            }

            int jumpLength = (int) Math.sqrt(directory.size());

            while (currentRight < directory.size() - 1) {
                currentRight = Math.min(directory.size() - 1, currentRight + jumpLength);

                if (String.CASE_INSENSITIVE_ORDER.compare(directory.get(currentRight).split("[\\d]+\\s")[1], searchedContact) >= 0) {
                    break;
                }
                previousRight = currentRight;
            }

            if (backwardSearch(directory, searchedContact, previousRight, currentRight) != null) {
                matches.add(backwardSearch(directory, searchedContact, previousRight, currentRight));
            }
        }
        return matches;
    }

    private String backwardSearch(List<String> directory, String target, int leftBorder, int rightBorder) {
        for (int i = rightBorder; i > leftBorder; i--) {
            String[] onlyNames = directory.get(i).split("[\\d]+\\s");

            if (onlyNames[1].equals(target)) {
                return directory.get(i);
            }
        }
        return null;
    }

    public List<String> doBubbleSort(List<String> directory, long startSorting, long durationLinearSearch) throws Exception {

        for (int i = 0; i < directory.size() - 1; i++) {
            for (int ii = 0; ii < directory.size() - i - 1; ii++) {
                String[] onlyNameFirst = directory.get(ii).split("[\\d]+\\s");
                String[] onlyNameSecond = directory.get(ii + 1).split("[\\d]+\\s");

                if (String.CASE_INSENSITIVE_ORDER.compare(onlyNameFirst[1], onlyNameSecond[1]) > 0) {
                    String temp = directory.get(ii);
                    directory.set(ii, directory.get(ii + 1));
                    directory.set(ii + 1, temp);
                }

                long end = System.currentTimeMillis();


                // set when to stop bubble sort
                if (end - startSorting > durationLinearSearch * 10) {
                    throw new Exception("Sorting process take too long");
                }

            }
        }
        return directory;
    }


}
