package edu.uga.cs.roommateshoppingapp;

import java.util.List;

/**
 * The PurchasedItemGroup class represents a collection of purchased items grouped together by a specific
 * roommate.
 */
public class PurchasedItemGroup {

    private String groupID;
    private List<PurchasedItem> purchasedItems;
    private String roommateEmail;
    private double totalPrice;

    /**
     * Default constructor for the PurchasedItemGroup class.
     * Initializes an empty purchased item group.
     */
    public PurchasedItemGroup() {

    }

    /**
     * Constructs a PurchasedItemGroup with the specified group ID, list of purchased items, roommate's email,
     * and total price of the items.
     *
     * @param groupID The unique identifier for the purchased item group.
     * @param purchasedItems The list of purchased items in the group.
     * @param roommateEmail The email address of the roommate who owns this group.
     * @param totalPrice The total price of all purchased items in the group.
     */
    public PurchasedItemGroup(String groupID, List<PurchasedItem> purchasedItems, String roommateEmail, double totalPrice) {
        this.groupID = groupID;
        this.purchasedItems = purchasedItems;
        this.roommateEmail = roommateEmail;
        this.totalPrice = totalPrice;
    }

    /**
     * Retrieves the unique identifier of the purchased item group.
     *
     * @return The group ID.
     */
    public String getGroupID() {
        return groupID;
    }

    /**
     * Sets the unique identifier for the purchased item group.
     *
     * @param groupID The new group ID to set.
     */
    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    /**
     * Retrieves the list of purchased items in the group.
     *
     * @return A list of PurchasedItem objects.
     */
    public List<PurchasedItem> getPurchasedItems() {
        return purchasedItems;
    }

    /**
     * Sets the list of purchased items for the group.
     *
     * @param purchasedItems The list of PurchasedItem objects to set.
     */
    public void setPurchasedItems(List<PurchasedItem> purchasedItems) {
        this.purchasedItems = purchasedItems;
    }

    /**
     * Retrieves the email address of the roommate who owns this purchased item group.
     *
     * @return The email address of the roommate.
     */
    public String getRoommateEmail() {
        return roommateEmail;
    }

    /**
     * Sets the email address of the roommate who owns this purchased item group.
     *
     * @param roommateEmail The new email address to set.
     */
    public void setRoommateEmail(String roommateEmail) {
        this.roommateEmail = roommateEmail;
    }

    /**
     * Retrieves the total price of the purchased items in the group.
     *
     * @return The total price of the purchased items.
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the total price of the purchased items in the group.
     *
     * @param totalPrice The new total price to set.
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}

