package com.example.admin.projectnow.INTERFACE;

import com.example.admin.projectnow.MODEL.food;

public interface ShoppingCart {
    public void TransactionData(food food, int count, double totalPrice, boolean check);
}
