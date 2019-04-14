package com.example.admin.projectnow.MODEL;

public class foodInfo {
    private String nameFood;
    private double price;
    private int count;
    private double totalPrice;
    private int idCategory;

    public foodInfo(String nameFood, double price, int count, double totalPrice, int idCategory) {
        this.nameFood = nameFood;
        this.price = price;
        this.count = count;
        this.totalPrice = totalPrice;
        this.idCategory = idCategory;
    }

    public int IdCategory() {
        return idCategory;
    }

    public void IdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String NameFood() {
        return nameFood;
    }

    public void NameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public double Price() {
        return price;
    }

    public void Price(double price) {
        this.price = price;
    }

    public int Count() {
        return count;
    }

    public void Count(int count) {
        this.count = count;
    }

    public double TotalPrice() {
        return totalPrice;
    }

    public void TotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
