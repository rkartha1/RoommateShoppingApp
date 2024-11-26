package edu.uga.cs.roommateshoppingapp;

import java.util.ArrayList;

public class Purchase {
    private ArrayList<ShoppingItem> items;
    private double totalPrice;

    public Purchase() {
        // Default constructor required for Firebase
    }

    public Purchase(ArrayList<ShoppingItem> items, double totalPrice) {
        this.items = items;
        this.totalPrice = totalPrice;
    }

    public ArrayList<ShoppingItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<ShoppingItem> items) {
        this.items = items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
