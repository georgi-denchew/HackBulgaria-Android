package com.example.georgi.expenselist;

/**
 * Created by Georgi on 21.11.2014 г..
 */
public class Expense {

    private String description;
    private Double price;

    public Expense(String description, double price) {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
