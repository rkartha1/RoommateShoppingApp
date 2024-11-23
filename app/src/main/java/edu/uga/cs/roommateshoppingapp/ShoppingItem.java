package edu.uga.cs.roommateshoppingapp;

public class ShoppingItem {
    private String itemId; // Unique identifier for the item
    private String itemName;
    private String itemQuantity;
    private double price;  // Price of the item (for purchased items)
    private String purchasedBy;  // Roommate who purchased the item

    public ShoppingItem() {
        // Default constructor required for calls to DataSnapshot.getValue(ShoppingItem.class)
    }

    // Constructor for adding a new item to the shopping list
    public ShoppingItem(String itemName, String itemQuantity) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }

    // Constructor for purchased items
    public ShoppingItem(String itemId, String itemName, String itemQuantity, double price, String purchasedBy) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.price = price;
        this.purchasedBy = purchasedBy;
    }

    public ShoppingItem(String itemId, String itemName, String itemQuantity) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }

    // Getters and setters
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPurchasedBy() {
        return purchasedBy;
    }

    public void setPurchasedBy(String purchasedBy) {
        this.purchasedBy = purchasedBy;
    }
}
