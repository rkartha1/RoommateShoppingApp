package edu.uga.cs.roommateshoppingapp;

import java.util.ArrayList;

/**
 * The Purchase class represents a shopping purchase, which contains a list of items
 * and the total price of those items.
 */
public class Purchase {

    private ArrayList<ShoppingItem> items;
    private double totalPrice;

    /**
     * Default constructor required for Firebase.
     */
    public Purchase() {
        // Default constructor required for Firebase
    }

    /**
     * Constructs a Purchase object with the specified list of shopping items and total price.
     *
     * @param items The list of shopping items included in the purchase.
     * @param totalPrice The total price of the items in the purchase.
     */
    public Purchase(ArrayList<ShoppingItem> items, double totalPrice) {
        this.items = items;
        this.totalPrice = totalPrice;
    }

    /**
     * Retrieves the list of shopping items in the purchase.
     *
     * @return The list of shopping items.
     */
    public ArrayList<ShoppingItem> getItems() {
        return items;
    }

    /**
     * Sets the list of shopping items for the purchase.
     *
     * @param items The new list of shopping items to set.
     */
    public void setItems(ArrayList<ShoppingItem> items) {
        this.items = items;
    }

    /**
     * Retrieves the total price of the purchase.
     *
     * @return The total price of the purchase.
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the total price for the purchase.
     *
     * @param totalPrice The new total price to set.
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}

