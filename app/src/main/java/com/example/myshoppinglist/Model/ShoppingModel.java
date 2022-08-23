package com.example.myshoppinglist.Model;

public class ShoppingModel {

    // each item has three attributes:
    // id will be how we get it from the database
    // status is an int to be 0 not in basket or 1 in basket
    // string is the name of the item
    private int id, status;
    private String item;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
