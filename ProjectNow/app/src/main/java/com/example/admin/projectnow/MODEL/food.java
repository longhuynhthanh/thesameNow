package com.example.admin.projectnow.MODEL;

import java.io.Serializable;

public class food implements Serializable {
    private int id;
    private String nameFood;
    private double price;
    private int status;
    private int idCategory;
    private int idStore;

    public int Status() {
        return status;
    }

    public void Status(int status) {
        this.status = status;
    }

    public food(String nameFood, double price, int status, int idCategory, int idStore) {
        this.nameFood = nameFood;
        this.price = price;
        this.status = status;
        this.idCategory = idCategory;
        this.idStore = idStore;
    }

    public food(int id, String nameFood, double price, int status, int idCategory, int idStore) {
        this.id = id;
        this.nameFood = nameFood;
        this.price = price;
        this.status = status;
        this.idCategory = idCategory;
        this.idStore = idStore;
    }

    public int Id() {
        return id;
    }

    public void Id(int id) {
        this.id = id;
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
}
