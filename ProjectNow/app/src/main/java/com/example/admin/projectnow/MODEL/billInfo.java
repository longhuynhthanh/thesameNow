package com.example.admin.projectnow.MODEL;

public class billInfo {
    private int id;
    private int idBill;
    private int idFood;
    private int countFood;

    public int Id() {
        return id;
    }

    public void Id(int id) {
        this.id = id;
    }

    public int IdBill() {
        return idBill;
    }

    public void IdBill(int idBill) {
        this.idBill = idBill;
    }

    public int IdFood() {
        return idFood;
    }

    public void IdFood(int idFood) {
        this.idFood = idFood;
    }

    public int CountFood() {
        return countFood;
    }

    public void CountFood(int countFood) {
        this.countFood = countFood;
    }

    public billInfo(int id, int idBill, int idFood, int countFood) {
        this.id = id;
        this.idBill = idBill;
        this.idFood = idFood;
        this.countFood = countFood;
    }
}
