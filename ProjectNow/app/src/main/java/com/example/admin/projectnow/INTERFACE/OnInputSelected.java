package com.example.admin.projectnow.INTERFACE;

import com.example.admin.projectnow.MODEL.food;

public interface OnInputSelected{
    void sendInput(String nameFood, double price, int idCategory, String nameCategory, int status);
        void DeleteFood(food f);
        void UpdateFood(food f, int oldIdCategory, String nameCategory);
}
