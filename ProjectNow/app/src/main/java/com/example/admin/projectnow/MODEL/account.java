package com.example.admin.projectnow.MODEL;

import java.io.Serializable;

public class account implements Serializable {
    private String userName;
    private String password;
    private String displayName;
    private String phone;
    private String address;
    private int type;

    public account() {
    }

    public account(String userName, String password, String displayName, String phone, String address, int type) {
        this.userName = userName;
        this.password = password;
        this.displayName = displayName;
        this.phone = phone;
        this.address = address;

        this.type = type;
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

    public int Type() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String UserName() {
        return userName;
    }

    public void UserName(String userName) {
        this.userName = userName;
    }

    public String Password() {
        return password;
    }

    public void Password(String password) {
        this.password = password;
    }

    public String DisplayName() {
        return displayName;
    }

    public void DisplayName(String displayName) {
        this.displayName = displayName;
    }

}
