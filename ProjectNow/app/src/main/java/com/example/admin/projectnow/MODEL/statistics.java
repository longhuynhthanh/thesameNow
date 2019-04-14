package com.example.admin.projectnow.MODEL;

import java.util.Date;

public class statistics {
    private int idBill;
    private Date checkOut;
    private Date checkIn;
    private double totalPrice;

    public statistics(int idBill, Date checkOut, Date checkIn, double totalPrice) {
        this.idBill = idBill;
        this.checkOut = checkOut;
        this.checkIn = checkIn;
        this.totalPrice = totalPrice;
    }

    public Date CheckIn() {
        return checkIn;
    }

    public void CheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public int IdBill() {
        return idBill;
    }

    public void IdBill(int idBill) {
        this.idBill = idBill;
    }

    public Date CheckOut() {
        return checkOut;
    }

    public void CheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public double TotalPrice() {
        return totalPrice;
    }
    public void TotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
