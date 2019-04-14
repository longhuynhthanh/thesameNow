package com.example.admin.projectnow.MODEL;


public class storeLocation {
    private int idStore;
    private String nameStore;
    private String nameLocation;

    public storeLocation(int idStore, String nameStore, String nameLocation) {
        this.idStore = idStore;
        this.nameStore = nameStore;
        this.nameLocation = nameLocation;
    }

    public int IdStore() {
        return idStore;
    }

    public void IdStore(int idStore) {
        this.idStore = idStore;
    }

    public String NameStore() {
        return nameStore;
    }

    public void NameStore(String nameStore) {
        this.nameStore = nameStore;
    }

    public String NameLocation() {
        return nameLocation;
    }

    public void NameLocation(String nameLocation) {
        this.nameLocation = nameLocation;
    }
}
