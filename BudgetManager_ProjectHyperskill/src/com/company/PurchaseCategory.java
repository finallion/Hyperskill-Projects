package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PurchaseCategory implements Comparable {
    private List<Purchase> categoryList;
    private double sumOfList;
    private String name;


    public PurchaseCategory(String name) {
        this.categoryList = new ArrayList<>();
        this.sumOfList = 0;
        this.name = name;
    }

    public void addItem(Purchase purchase) {
        categoryList.add(purchase);
        sumOfList += purchase.getPrice();

    }

    public List<Purchase> getCategoryList() {
        return categoryList;
    }

    public String getName() {
        return this.name;
    }


    public void print() {
        System.out.println(name + ":");
        if (categoryList.isEmpty()) {
            System.out.println("The purchase list is empty!");
        } else {
            for (Purchase purchase : categoryList) {
                System.out.println(purchase);
            }
            System.out.print("Total sum: $");
            System.out.printf("%.2f", sumOfList);
            System.out.println();
        }
    }

    public boolean isEmpty() {
        if (categoryList.isEmpty()) {
            return true;
        }
        return false;
    }

    public double getSumOfList() {
        return sumOfList;
    }

    public void sort() {
        Collections.sort(this.categoryList, Collections.reverseOrder());
    }


    @Override
    public int compareTo(Object compared) {
        double comparedSum = ((PurchaseCategory)compared).getSumOfList();
        return (int) (comparedSum - this.getSumOfList());
    }

}
