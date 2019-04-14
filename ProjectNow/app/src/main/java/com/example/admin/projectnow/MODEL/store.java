package com.example.admin.projectnow.MODEL;

import java.io.Serializable;

public class store implements Serializable {
    private int id;
    private String nameStore;
    private String phoneNumber;
    private String info;
    private String userName;
    private int idLocation;

    public store(int id, String nameStore, String phoneNumber, String info, String userName, int idLocation) {
        this.id = id;
        this.nameStore = nameStore;
        this.phoneNumber = phoneNumber;
        this.info = info;
        this.userName = userName;
        this.idLocation = idLocation;
    }

    public String UserName() {
        return userName;
    }

    public void UserName(String userName) {
        this.userName = userName;
    }

    public int Id() {
        return id;
    }

    public void Id(int id) {
        this.id = id;
    }

    public String NameStore() {
        return nameStore;
    }

    public void NameStore(String nameStore) {
        this.nameStore = nameStore;
    }

    public String PhoneNumber() {
        return phoneNumber;
    }

    public void PhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String Info() {
        return info;
    }

    public void Info(String info) {
        this.info = info;
    }

    public int IdLocation() {
        return idLocation;
    }

    public void IdLocation(int idLocation) {
        this.idLocation = idLocation;
    }
}
