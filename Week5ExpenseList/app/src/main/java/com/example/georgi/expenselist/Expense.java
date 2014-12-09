package com.example.georgi.expenselist;

/**
 * Created by Georgi on 21.11.2014 г..
 */
public class Expense {
    private long id;

    private String description;
    private double price;

    public Expense(String description, double price) {
        this.description = description;
        this.price = price;
    }

    public Expense(long id, String description, double price) {
        this.id = id;
        this.description = description;
        this.price = price;
    }

    public Expense() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
