package edu.uga.cs.roommateshoppingapp;

public class ShoppingItem {
    private String itemName;
    private String itemQuantity;

    public ShoppingItem() {
        // Default constructor required for calls to DataSnapshot.getValue(ShoppingItem.class)
    }

    public ShoppingItem(String itemName, String itemQuantity) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }
}
