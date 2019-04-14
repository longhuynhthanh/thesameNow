package com.example.admin.projectnow.MODEL;

public class favorites {
    private int idStore;
    private String nameFood;
    private double price;
    private int count;
    private int idCategory;
    private String nameStore;

    public favorites(int idStore, String nameFood, double price, int count, int idCategory, String nameStore) {
        this.idStore = idStore;
        this.nameFood = nameFood;
        this.price = price;
        this.count = count;
        this.idCategory = idCategory;
        this.nameStore = nameStore;
    }

    public String NameStore() {
        return nameStore;
    }

    public void NameStore(String nameStore) {
        this.nameStore = nameStore;
    }

    public int IdCategory() {
        return idCategory;
    }

    public void IdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public int IdStore() {
        return idStore;
    }

    public void IdStore(int idStore) {
        this.idStore = idStore;
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
}
