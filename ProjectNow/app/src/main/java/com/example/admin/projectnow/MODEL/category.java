package com.example.admin.projectnow.MODEL;

public class category {
    private int id;
    private String nameCategory;

    public int Id() {
        return id;
    }

    public category(int id, String nameCategory) {
        this.id = id;
        this.nameCategory = nameCategory;
    }
    public void Id(int id) {
        this.id = id;
    }

    public String NameCategory() {
        return nameCategory;
    }

    public category(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public void NameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }
}
