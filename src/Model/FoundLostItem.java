/***********************
 Yogev Yosef - 312273410
 Eldor Shir  - 311362461
 ***********************/

package Model;

import java.time.LocalDateTime;

public class FoundLostItem {
    public static int instanceCounter = 0;

    private int serial;
    private Item item;
    private User contact;
    private Address address;
    private LocalDateTime foundLostDate;
    private ItemType itemType;
    private String details;

    public enum ItemType {
        Lost,
        Found
    }


    /*********************************************************************************************
     C'tors
     ********************************************************************************************/
    public FoundLostItem() {
    }

    public FoundLostItem(Item item, User contact, Address address, LocalDateTime foundLostDate, ItemType itemType, String details) {
        this.serial = ++instanceCounter;
        this.item = item;
        this.contact = contact;
        this.address = address;
        this.foundLostDate = foundLostDate;
        this.itemType = itemType;
        this.details = details;
    }


    /*********************************************************************************************
     Getters
     ********************************************************************************************/
    public int getSerial() {
        return serial;
    }

    public Item getItem() {
        return item;
    }

    public User getContact() {
        return contact;
    }

    public Address getAddress() {
        return address;
    }

    public LocalDateTime getFoundLostDate() {
        return foundLostDate;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public String getDetails() { return details; }


    /*********************************************************************************************
     Setters
     ********************************************************************************************/
    public void setSerial(int serial) {
        this.serial = serial;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setContact(User contact) {
        this.contact = contact;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setFoundLostDate(LocalDateTime foundLostDate) {
        this.foundLostDate = foundLostDate;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public static void increaseInstanceCounter() { instanceCounter++; }

    public static void setInstanceCounter(int instanceCounter) { FoundLostItem.instanceCounter = instanceCounter; }
}

