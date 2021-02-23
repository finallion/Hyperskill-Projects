package com.company;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class Purchase implements Comparable{
    private String item;
    private double price;

    public Purchase(String item, double price) {
        this.item = item;
        this.price = price;
    }

    public String getItem() {
        return item;
    }

    public double getPrice() {
        return price;
    }

    public String toString() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        NumberFormat format = new DecimalFormat("#0.00", symbols);

        return item + " $" + format.format(price);
    }

    @Override
    public boolean equals(Object compared) {
        if (this == compared) {
            return true;
        }
        if (compared == null) {
            return false;
        }
        if (!(compared instanceof Purchase)) {
            return false;
        }

        Purchase comparedPurchase = (Purchase) compared;

        if (this.item.equals(comparedPurchase.item) && this.price == comparedPurchase.price) {
            return true;
        }
        return false;

    }


    @Override
    public int compareTo(Object compared) {
        if (this.price < ((Purchase)compared).getPrice()) {
            return -1;
        } else if (((Purchase)compared).getPrice() < this.price) {
            return 1;
        } else {
            return 0;
        }
    }
}
