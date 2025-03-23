package com.example.crunchy_app.comidas.model;

public class Food {
    private String name;
    private String info;
    private double price;

    public Food(String name, String info, double price) {
        this.name = name;
        this.info = info;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public double getPrice() {
        return price;
    }
}
