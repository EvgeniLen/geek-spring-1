package ru.geekbrains.products;

public class Product {
    private long id;
    private String title;
    private double cost;

    public long getId() {
        return id;
    }

    public Product(String title, double cost) {
        this.title = title;
        this.cost = cost;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
