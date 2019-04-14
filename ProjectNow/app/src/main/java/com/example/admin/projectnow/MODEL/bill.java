package com.example.admin.projectnow.MODEL;

import java.util.Date;

public class bill {
    private  int id;
    private int idStore;
    private String userName;
    private Date checkIn;
    private Date checkOut;
    private int status;

    public int Id() {
        return id;
    }

    public void Id(int id) {
        this.id = id;
    }

    public int IdStore() {
        return idStore;
    }

    public void IdStore(int idStore) {
        this.idStore = idStore;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void CheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date CheckOut() {
        return checkOut;
    }

    public void CheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public int Status() {
        return status;
    }

    public void Status(int status) {
        this.status = status;
    }

    public bill(int id, int idStore, String userName, Date checkIn, Date checkOut, int status) {
        this.id = id;
        this.idStore = idStore;
        this.userName = userName;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
    }
}
