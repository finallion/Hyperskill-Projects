package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;


public class PhoneBookApplication {

    public void start() {
        List<String> directory = readDirectoryFile();
        List<String> find = readFindFile();

        long durationLinearSearch = doLinearSearch(directory, find);
        doBubbleSortAndJumpSearch(directory, find, durationLinearSearch);
        doQuickSortAndBinarySearch(directory, find);
        doHashTableSearch(directory, find);
    }


    private void doHashTableSearch(List<String> directory, List<String> find) {
        HashingSearchAlgorithm hash = new HashingSearchAlgorithm(directory.size());
        List<String> matchesHashing;

        System.out.println("Start searching (hash table)...");

        long startHashing = System.currentTimeMillis();
        hash.hashing(directory);
        long endHashing = System.currentTimeMillis();

        long startHashSearch = System.currentTimeMillis();
        matchesHashing = hash.doHashSearch(find);
        long endHashSearch = System.currentTimeMillis();

        printDuration(startHashing, endHashSearch, matchesHashing.size(), find.size(), 1, false);
        printDuration(startHashing, endHashing, matchesHashing.size(), find.size(), 4, false);
        printDuration(startHashSearch, endHashSearch, matchesHashing.size(), find.size(), 3, false);
    }


    private void doQuickSortAndBinarySearch(List<String> directory, List<String> find) {
        QuickSortAndBinarySearchAlgorithm binary = new QuickSortAndBinarySearchAlgorithm();
        List<String> matchesBinarySearch;

        System.out.println("Start searching (quick sort + binary search)...");

        long startQuickSort = System.currentTimeMillis();
        List<String> sortedDirectory = binary.doQuickSort(directory, 0, directory.size() - 1);
        long endQuickSort = System.currentTimeMillis();

        long startBinarySearch = System.currentTimeMillis();
        matchesBinarySearch = binary.doBinarySearch(sortedDirectory, find);
        long endBinarySearch = System.currentTimeMillis();

        printDuration(startQuickSort, endBinarySearch, matchesBinarySearch.size(), find.size(), 1, false);
        printDuration(startQuickSort, endQuickSort, matchesBinarySearch.size(), find.size(), 2, false);
        printDuration(startBinarySearch, endBinarySearch, matchesBinarySearch.size(), find.size(), 3, false);
        System.out.println();

    }


    private void doBubbleSortAndJumpSearch(List<String> directory, List<String> find, long durationLinearSearch) {
        LinearSearchAlgorithm linear = new LinearSearchAlgorithm();
        BubbleSortJumpSearchAlgorithm jump = new BubbleSortJumpSearchAlgorithm();

        List<String> sortedDirectory = new ArrayList<>();
        List<String> matchesJumpSearch;

        System.out.println("Start searching (bubble sort + jump search)...");

        // bubble sort; throws exception if too long
        long startBubbleSort = System.currentTimeMillis();
        boolean tooLong = false;
        try {
            sortedDirectory = jump.doBubbleSort(directory, startBubbleSort, durationLinearSearch);
        } catch (Exception e) {
            tooLong = true;
        }
        long endBubbleSort = System.currentTimeMillis();

        // jump search, if too long linear search
        long startJumpSearch = System.currentTimeMillis();
        if (tooLong) {
            matchesJumpSearch = linear.doLinearSearch(directory, find);
        } else {
            matchesJumpSearch = jump.doJumpSearch(sortedDirectory, find);
        }
        long endJumpSearch = System.currentTimeMillis();


        printDuration(startBubbleSort, endJumpSearch, matchesJumpSearch.size(), find.size(), 1, false);
        printDuration(startBubbleSort, endBubbleSort, matchesJumpSearch.size(), find.size(), 2, tooLong);
        printDuration(startJumpSearch, endJumpSearch, matchesJumpSearch.size(), find.size(), 3, false);
        System.out.println();
    }


    private long doLinearSearch(List<String> directory, List<String> find) {
        LinearSearchAlgorithm linear = new LinearSearchAlgorithm();

        System.out.println("Start searching (linear search)...");

        long startLinearSearch = System.currentTimeMillis();
        List<String> matchesLinearSearch = linear.doLinearSearch(directory, find);
        long endLinearSearch = System.currentTimeMillis();

        printDuration(startLinearSearch, endLinearSearch, matchesLinearSearch.size(), find.size(), 1, false);
        System.out.println();

        return endLinearSearch - startLinearSearch;
    }


    private void printDuration(long start, long end, int matchesSize, int findSize, int format, boolean tooLong) {
        Duration durationLinearSearch = Duration.ofMillis(end - start);
        int minutes = durationLinearSearch.toMinutesPart();
        int seconds = durationLinearSearch.toSecondsPart();
        int milliseconds = durationLinearSearch.toMillisPart();

        if (format == 1) {
            System.out.print("Found " + matchesSize + " / " + findSize + " entries. Time taken: ");
        } else if (format == 2) {
            System.out.print("Sorting time: ");
        } else if (format == 3) {
            System.out.print("Searching time: ");
        } else if (format == 4) {
            System.out.print("Creating time: ");
        }
        System.out.print(minutes + " min. " + seconds + " sec. " + milliseconds + " ms. ");

        if (tooLong && format == 2) {
            System.out.print("- STOPPED, moved to linear search \n");
        } else {
            System.out.print("\n");
        }
    }

    private List<String> readFindFile() {
        List<String> findList = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File("C:\\Users\\finallion\\Desktop\\find.txt"));
            while (scanner.hasNext()) {
                String searchedContact = scanner.nextLine();
                findList.add(searchedContact);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return findList;
    }


    private List<String> readDirectoryFile() {
        List<String> phoneBook = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File("C:\\Users\\finallion\\Desktop\\directory.txt"));
            while (scanner.hasNext()) {
                phoneBook.add(scanner.nextLine());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return phoneBook;
    }
}
