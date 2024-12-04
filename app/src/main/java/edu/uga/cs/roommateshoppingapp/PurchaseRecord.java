package edu.uga.cs.roommateshoppingapp;

import java.util.ArrayList;

/**
 * Represents a record of a purchase made by a roommate.
 * This includes the roommate's email, the items they purchased, and the total price of their purchase.
 */
public class PurchaseRecord {
    private String roommateEmail;
    private ArrayList<ShoppingItem> purchasedItems;
    private double totalPrice;

    /**
     * Constructs a PurchaseRecord with the specified roommate email, purchased items, and total price.
     *
     * @param roommateEmail   the email of the roommate making the purchase.
     * @param purchasedItems  a list of items that the roommate has purchased.
     * @param totalPrice      the total price of the purchased items.
     */
    public PurchaseRecord(String roommateEmail, ArrayList<ShoppingItem> purchasedItems, double totalPrice) {
        this.roommateEmail = roommateEmail;
        this.purchasedItems = purchasedItems;
        this.totalPrice = totalPrice;
    }

    /**
     * Gets the email address of the roommate who made the purchase.
     *
     * @return the roommate's email address.
     */
    public String getRoommateEmail() {
        return roommateEmail;
    }

    /**
     * Sets the email address of the roommate who made the purchase.
     *
     * @param roommateEmail the new email address of the roommate.
     */
    public void setRoommateEmail(String roommateEmail) {
        this.roommateEmail = roommateEmail;
    }

    /**
     * Gets the list of items purchased by the roommate.
     *
     * @return a list of {@link ShoppingItem} objects representing the purchased items.
     */
    public ArrayList<ShoppingItem> getPurchasedItems() {
        return purchasedItems;
    }

    /**
     * Sets the list of items purchased by the roommate.
     *
     * @param purchasedItems a list of {@link ShoppingItem} objects representing the purchased items.
     */
    public void setPurchasedItems(ArrayList<ShoppingItem> purchasedItems) {
        this.purchasedItems = purchasedItems;
    }

    /**
     * Gets the total price of the purchased items.
     *
     * @return the total price of the purchased items.
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the total price of the purchased items.
     *
     * @param totalPrice the new total price for the purchased items.
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
