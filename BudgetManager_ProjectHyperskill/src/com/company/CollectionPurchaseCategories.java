package com.company;

import java.util.*;

public class CollectionPurchaseCategories {
    private PurchaseCategory all = new PurchaseCategory("All");
    private PurchaseCategory food = new PurchaseCategory("Food");
    private PurchaseCategory clothes = new PurchaseCategory("Clothes");
    private PurchaseCategory entertainment = new PurchaseCategory("Entertainment");
    private PurchaseCategory other = new PurchaseCategory("Other");
    private Map<Purchase, String> allPurchases = new HashMap<>();


    public void addToPurchaseCategory(Purchase item, PurchaseCategory list) {
        list.addItem(item);

        all.addItem(item);
        allPurchases.put(item, list.getName());
    }

    public Map<Purchase, String> getAllPurchases() {
        return allPurchases;
    }

    public void categorizeLoadedPurchases() {
        for (Purchase key : allPurchases.keySet()) {
            all.addItem(key);


            if (allPurchases.get(key).equals("Food")) {
                food.addItem(key);
            } else if (allPurchases.get(key).equals("Clothes")) {
                clothes.addItem(key);
            } else if (allPurchases.get(key).equals("Entertainment")) {
                entertainment.addItem(key);
            } else if (allPurchases.get(key).equals("Other")) {
                other.addItem(key);
            }
        }
    }

    public PurchaseCategory getAll() {
        return all;
    }

    public PurchaseCategory getFood() {
        return food;
    }

    public PurchaseCategory getClothes() {
        return clothes;
    }

    public PurchaseCategory getEntertainment() {
        return entertainment;
    }

    public PurchaseCategory getOther() {
        return other;
    }


    public void printCategory(PurchaseCategory list) {
        list.print();
    }


    public boolean isEmpty() {
        if (getAll().isEmpty() && getFood().isEmpty() && getClothes().isEmpty() && getEntertainment().isEmpty() && getOther().isEmpty()) {
            return true;
        }
        return false;
    }


    public List<PurchaseCategory> sortByType() {

        List<PurchaseCategory> sorted = new ArrayList<>();
        sorted.add(food);
        sorted.add(clothes);
        sorted.add(entertainment);
        sorted.add(other);

        Collections.sort(sorted);

        return sorted;

    }
}
