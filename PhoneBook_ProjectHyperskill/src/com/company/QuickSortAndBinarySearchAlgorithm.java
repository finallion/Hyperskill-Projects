package com.company;

import java.util.ArrayList;
import java.util.List;

public class QuickSortAndBinarySearchAlgorithm {

    public List<String> doQuickSort(List<String> directory, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(directory, left, right);
            doQuickSort(directory, left, pivotIndex - 1);
            doQuickSort(directory, pivotIndex + 1, right);
        }
        return directory;
    }

    private int partition(List<String> directory, int left, int right) {
        String pivot = directory.get(right).split("[\\d]+\\s")[1];
        int partitionIndex = left;

        for (int i = left; i < right; i++) {


            if (String.CASE_INSENSITIVE_ORDER.compare(directory.get(i).split("[\\d]+\\s")[1], pivot) <= 0) {
                swap(directory, i, partitionIndex);
                partitionIndex++;
            }
        }
        swap(directory, partitionIndex, right);
        return partitionIndex;
    }

    private void swap(List<String> directory, int i, int ii) {
        String temp = directory.get(i);
        directory.set(i, directory.get(ii));
        directory.set(ii, temp);
    }

    public List<String> doBinarySearch(List<String> directory, List<String> find) {
        List<String> matches = new ArrayList<>();

        for (String searchedContact : find) {
            //int match = binarySearchIterative(directory, searchedContact, 0, directory.size() - 1);
            int match = binarySearchRecursive(directory, searchedContact, 0, directory.size() - 1);
            if (match != -1) {
                matches.add(directory.get(match));
            }
        }
        return matches;
    }


    private int binarySearchRecursive(List<String> directory, String target, int left, int right) {
        if (left > right) {
            return -1;
        }

        int middle = left + (right - left) / 2;

        String checkedContact = directory.get(middle).split("[\\d]+\\s")[1];

        if (target.equals(checkedContact)) {
            return middle;
        } else if (String.CASE_INSENSITIVE_ORDER.compare(target, checkedContact) < 0) {
            return binarySearchRecursive(directory, target, left, middle - 1);
        } else {
            return binarySearchRecursive(directory, target, middle + 1, right);
        }
    }

    private int binarySearchIterative(List<String> directory, String target, int left, int right) {
        while (left <= right) {
            int middle = left + (right - left) / 2;

            String checkedContact = directory.get(middle).split("[\\d]+\\s")[1];

            if (target.equals(checkedContact)) {
                return middle;
            } else if (String.CASE_INSENSITIVE_ORDER.compare(target, checkedContact) < 0) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return -1;
    }
}
