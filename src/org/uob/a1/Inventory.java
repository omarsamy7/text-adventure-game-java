package org.uob.a1;

public class Inventory {
    // this class is used to store and manage the items the player collect throughout the game also it allows adding and checking and removing and showing items from the player inventory

    final int MAX_ITEMS = 10; // this is the maximum number of items the player can carry at once 
    
    private String[] items; // this os an array that stores the names of all items the player has
    
    private int count; // this keeps track of how may items are currently in the inventory of the player

// this is the constructor which sets up the empty inventory when the game starts
    public Inventory() {
        
        items = new String[MAX_ITEMS]; // creates a list that hold up to ten items 
        count = 0; // to make the counts start with 0 items
    }

    // this method that adds an item to the inventory if there is space and it isn't already there
    public void addItem(String item) {
        
        if (item == null || item.length() == 0) {
            return; // make sure that the item is not empty 
        }
        if (count >= MAX_ITEMS) {
            return; // stops adding if the inventory is full
        }
        if (hasItem(item) != -1) {
            return; // doesn't add the item if it is already there
        }
        items[count] = item; // this puts the new item in the next empty slot
        count++; // this increases the number of items by one
    }

// this method is used to check if an item is already there in the inventory and it returns the index number if it is found or -1 when it is not found
    public int hasItem(String item) {
        
        for (int i = 0; i < count; i++) {
            if (items[i].equalsIgnoreCase(item)) {// it compares items without caring about if it is lowercase or uppercase 
                return i;
            }
        }
        return -1; // means that the item wasn't found
    }

   // this method removes items from the inventory if it exists and it also shifts the other items so there is no gaps
    public void removeItem(String item) {
        
        int idx = hasItem(item); // find the item index
        if (idx == -1) {
            return; // this is for if not found it stop
        }
        for (int i = idx; i < count - 1; i++) {
            items[i] = items[i + 1]; // shifts items to fill the gap
        }
        items[count - 1] = null; // clears the last slot 
        count--; // this decreases the total count by one
    }

    // this method shows the player item in a line text
    public String displayInventory() {
        
        if (count == 0) {
            return ""; // this is if there is no items it will return an empty string
        }
        String result = "";
        for (int i = 0; i < count; i++) {
            result += items[i] + " "; //this adds each item name with a space after it
        }
        return result; // this returns all items as one string
    }
}
