package com.example.thieu.hunger.db.object;

/**
 * Created by HugoCastanheiro on 07.11.15.
 */
public class Product {
    private int id;
    private String name;
    private int price;
    private int category;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getCategory() {
        return category;
    }
    public void setCategory(int category) {
        this.category = category;
    }

}
