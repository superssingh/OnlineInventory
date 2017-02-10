package com.santossingh.onlineinventory.Models;

/**
 * Created by santoshsingh on 04/02/17.
 */

public class Inventory {
    private String item;
    private String quantity;
    private String price;
    private String photoUrl;

    public Inventory() {
    }

    public Inventory(String item,  String quantity, String price, String photoUrl) {
        this.item = item;
        this.quantity = quantity;
        this.price = price;
        this.photoUrl = photoUrl;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
