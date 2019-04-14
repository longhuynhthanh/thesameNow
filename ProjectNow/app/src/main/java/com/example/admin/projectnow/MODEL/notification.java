package com.example.admin.projectnow.MODEL;

import java.util.Date;

public class notification {
    private int idBill;
    private String name;
    private String phone;
    private String address;
    private Date checkIn;

    public notification(int idBill, String name, String phone, String address, Date checkIn) {
        this.idBill = idBill;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.checkIn = checkIn;
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

    public String Name() {
        return name;
    }

    public void Name(String name) {
        this.name = name;
    }

    public String Phone() {
        return phone;
    }

    public void Phone(String phone) {
        this.phone = phone;
    }

    public String Address() {
        return address;
    }

    public void Address(String address) {
        this.address = address;
    }
}
