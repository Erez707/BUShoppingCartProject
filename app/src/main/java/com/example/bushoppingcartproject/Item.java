package com.example.bushoppingcartproject;


public class Item {

    // Attributes
    private String name;
    private double price;
    private int priority;
    private boolean purchased;
    private int image;

    // Constructor
    public Item() {

    }

    public Item( String name, double price, int priority, int image, boolean purchased) {
        name = "";
        price = 0;
        priority = 0;
        image = 0;
        purchased = false;
    }

    // Name: getter & setter
    public String getName() {
        return name;
    }
    public void setName(String itemName) {
        this.name = itemName;
    }

    // Price: getter & setter
    public double getPrice() {
        return price;
    }
    public void setPrice(double itemPrice) {
        this.price = itemPrice;
    }

    // Priority: getter & setter
    public int getPriority() {
        return priority;
    }
    public void setPriority(int itemPriority) {
        this.priority = itemPriority;
    }

    // Image: getter & setter
    public int getImage() {
        return image;
    }

    public void setImage(int itemImage) {
        this.image = itemImage;
    }

    // Check if an item already exists
    public boolean compareItem(String shoppingItem) {
        if(getName().equals(shoppingItem)) {
            return true;
        } else {
            return false;
        }
    }

//    public boolean isPurchased() {
//        return purchased;
//    }
//
//    public void setPurchased(boolean purchased) {
//        purchased = purchased;
//    }
}
