package edu.uga.cs.roommateshoppingapp;

import java.util.List;

public class PurchasedItemGroup {

    private String groupID;
    private List<PurchasedItem> purchasedItems;
    private String roommateEmail;
    private double totalPrice;

    public PurchasedItemGroup() {

    }

    public PurchasedItemGroup(String groupID, List<PurchasedItem> purchasedItems, String roommateEmail, double totalPrice) {
        this.groupID = groupID;
        this.purchasedItems = purchasedItems;
        this.roommateEmail = roommateEmail;
        this.totalPrice = totalPrice;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public List<PurchasedItem> getPurchasedItems() {
        return purchasedItems;
    }

    public void setPurchasedItems(List<PurchasedItem> purchasedItems) {
        this.purchasedItems = purchasedItems;
    }

    public void setRoommateEmail(String roommateEmail) {
        this.roommateEmail = roommateEmail;
    }

    public String getRoommateEmail() {
        return roommateEmail;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
