package edu.uga.cs.roommateshoppingapp;

import java.util.ArrayList;

public class PurchaseRecord {
    private String roommateEmail;
    private ArrayList<ShoppingItem> purchasedItems;
    private double totalPrice;

    // Constructor
    public PurchaseRecord(String roommateEmail, ArrayList<ShoppingItem> purchasedItems, double totalPrice) {
        this.roommateEmail = roommateEmail;
        this.purchasedItems = purchasedItems;
        this.totalPrice = totalPrice;
    }

    // Getters and setters
    public String getRoommateEmail() {
        return roommateEmail;
    }

    public void setRoommateEmail(String roommateEmail) {
        this.roommateEmail = roommateEmail;
    }

    public ArrayList<ShoppingItem> getPurchasedItems() {
        return purchasedItems;
    }

    public void setPurchasedItems(ArrayList<ShoppingItem> purchasedItems) {
        this.purchasedItems = purchasedItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
