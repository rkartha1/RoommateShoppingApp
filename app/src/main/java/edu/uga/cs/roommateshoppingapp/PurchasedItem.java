package edu.uga.cs.roommateshoppingapp;

public class PurchasedItem {

    private String itemId; // Unique identifier for the item
    private String itemName;
    private String itemQuantity;
    private double price;  // Price of the item (for purchased items)
    private String purchasedBy;  // Roommate who purchased the item

    public PurchasedItem() {

    }

    public PurchasedItem(String itemName, String itemQuantity) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }

    public PurchasedItem(String itemId, String itemName, String itemQuantity, double price, String purchasedBy) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.price = price;
        this.purchasedBy = purchasedBy;
    }

    public PurchasedItem(String itemId, String itemName, String itemQuantity) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }

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

    @Override
    public String toString() {
        return itemName;
    }
}
