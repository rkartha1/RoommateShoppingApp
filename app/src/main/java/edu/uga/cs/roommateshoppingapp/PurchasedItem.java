package edu.uga.cs.roommateshoppingapp;

/**
 * The PurchasedItem class represents an item that has been purchased, including its unique
 * identifier, name, quantity, price, and the roommate who purchased it.
 */
public class PurchasedItem {

    private String itemId; // Unique identifier for the item
    private String itemName;
    private String itemQuantity;
    private double price;  // Price of the item (for purchased items)
    private String purchasedBy;  // Roommate who purchased the item

    /**
     * Default constructor for the PurchasedItem class.
     */
    public PurchasedItem() {

    }

    /**
     * Constructs a PurchasedItem with the specified name and quantity.
     *
     * @param itemName The name of the purchased item.
     * @param itemQuantity The quantity of the purchased item.
     */
    public PurchasedItem(String itemName, String itemQuantity) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }

    /**
     * Constructs a PurchasedItem with the specified item ID, name, quantity, price, and purchaser.
     *
     * @param itemId The unique identifier for the purchased item.
     * @param itemName The name of the purchased item.
     * @param itemQuantity The quantity of the purchased item.
     * @param price The price of the purchased item.
     * @param purchasedBy The name of the roommate who purchased the item.
     */
    public PurchasedItem(String itemId, String itemName, String itemQuantity, double price, String purchasedBy) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.price = price;
        this.purchasedBy = purchasedBy;
    }

    /**
     * Constructs a PurchasedItem with the specified item ID, name, and quantity.
     *
     * @param itemId The unique identifier for the purchased item.
     * @param itemName The name of the purchased item.
     * @param itemQuantity The quantity of the purchased item.
     */
    public PurchasedItem(String itemId, String itemName, String itemQuantity) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }

    /**
     * Retrieves the unique identifier of the purchased item.
     *
     * @return The item ID.
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * Sets the unique identifier of the purchased item.
     *
     * @param itemId The new item ID to set.
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * Retrieves the name of the purchased item.
     *
     * @return The item name.
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Sets the name of the purchased item.
     *
     * @param itemName The new item name to set.
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Retrieves the quantity of the purchased item.
     *
     * @return The item quantity.
     */
    public String getItemQuantity() {
        return itemQuantity;
    }

    /**
     * Sets the quantity of the purchased item.
     *
     * @param itemQuantity The new item quantity to set.
     */
    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    /**
     * Retrieves the price of the purchased item.
     *
     * @return The price of the purchased item.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the purchased item.
     *
     * @param price The new price to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Retrieves the name of the roommate who purchased the item.
     *
     * @return The name of the purchaser.
     */
    public String getPurchasedBy() {
        return purchasedBy;
    }

    /**
     * Sets the name of the roommate who purchased the item.
     *
     * @param purchasedBy The new purchaser name to set.
     */
    public void setPurchasedBy(String purchasedBy) {
        this.purchasedBy = purchasedBy;
    }

    /**
     * Returns a string representation of the purchased item, which is its name.
     *
     * @return The name of the purchased item.
     */
    @Override
    public String toString() {
        return itemName;
    }
}

