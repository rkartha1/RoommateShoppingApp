package edu.uga.cs.roommateshoppingapp;

/**
 * Represents an item in the shopping list or a purchased item.
 */
public class ShoppingItem {

    private String itemId; // Unique identifier for the item
    private String itemName; // Name of the item
    private String itemQuantity; // Quantity of the item
    private double price; // Price of the item (only applicable for purchased items)
    private String purchasedBy; // Roommate who purchased the item

    /**
     * Default constructor required for Firebase's DataSnapshot.getValue(ShoppingItem.class).
     */
    public ShoppingItem() {
        // Default constructor required for calls to DataSnapshot.getValue(ShoppingItem.class)
    }

    /**
     * Constructor for adding a new item to the shopping list.
     *
     * @param itemName The name of the item.
     * @param itemQuantity The quantity of the item.
     */
    public ShoppingItem(String itemName, String itemQuantity) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }

    /**
     * Constructor for creating a purchased item with all details.
     *
     * @param itemId The unique ID of the item.
     * @param itemName The name of the item.
     * @param itemQuantity The quantity of the item.
     * @param price The price of the item.
     * @param purchasedBy The roommate who purchased the item.
     */
    public ShoppingItem(String itemId, String itemName, String itemQuantity, double price, String purchasedBy) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.price = price;
        this.purchasedBy = purchasedBy;
    }

    /**
     * Constructor for creating a shopping item without price and purchase details (used for non-purchased items).
     *
     * @param itemId The unique ID of the item.
     * @param itemName The name of the item.
     * @param itemQuantity The quantity of the item.
     */
    public ShoppingItem(String itemId, String itemName, String itemQuantity) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }

    /**
     * Returns the unique identifier of the item.
     *
     * @return The unique ID of the item.
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * Sets the unique identifier for the item.
     *
     * @param itemId The unique ID to set for the item.
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * Returns the name of the item.
     *
     * @return The name of the item.
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Sets the name of the item.
     *
     * @param itemName The name to set for the item.
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Returns the quantity of the item.
     *
     * @return The quantity of the item.
     */
    public String getItemQuantity() {
        return itemQuantity;
    }

    /**
     * Sets the quantity of the item.
     *
     * @param itemQuantity The quantity to set for the item.
     */
    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    /**
     * Returns the price of the item.
     * This is only relevant for purchased items.
     *
     * @return The price of the item.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the item.
     * This is only relevant for purchased items.
     *
     * @param price The price to set for the item.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns the name of the roommate who purchased the item.
     *
     * @return The name of the roommate who purchased the item.
     */
    public String getPurchasedBy() {
        return purchasedBy;
    }

    /**
     * Sets the name of the roommate who purchased the item.
     *
     * @param purchasedBy The roommate to set as the purchaser of the item.
     */
    public void setPurchasedBy(String purchasedBy) {
        this.purchasedBy = purchasedBy;
    }
}

