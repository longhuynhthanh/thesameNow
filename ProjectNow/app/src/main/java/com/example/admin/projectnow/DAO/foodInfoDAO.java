package com.example.admin.projectnow.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.admin.projectnow.DATABASE.sqlHelper;
import com.example.admin.projectnow.MODEL.foodInfo;

import java.util.ArrayList;
import java.util.List;

public class foodInfoDAO {
    private static foodInfoDAO instance;
    private static Context context;
    private SQLiteDatabase db;

    public static foodInfoDAO Instance(Context context)
    {
        if(instance == null)
        {
            instance = new foodInfoDAO();
            foodInfoDAO.context = context;
            sqlHelper.Instance(context);
        }
        return instance;
    }
    private void Instance(foodInfoDAO instance)
    {
        foodInfoDAO.instance = instance;
    }

    public List<foodInfo> GetFoodInfoByBillID(int id)
    {
        List<foodInfo> list = new ArrayList<>();
        db = sqlHelper.Instance(foodInfoDAO.context).getReadableDatabase();
        String sql = "SELECT f." + foodDAO.NAMEFOOD + ", f." + foodDAO.PRICE + ", bi." + billInfoDAO.COUNTFOOD + ", f." + foodDAO.PRICE + "*bi." + billInfoDAO.COUNTFOOD + ", f." + foodDAO.IDCATEGORY +
                " FROM " + foodDAO.TABLE + " AS f, " + billDAO.TABLE + " AS b, " + billInfoDAO.TABLE + " AS bi" +
                " WHERE f." + foodDAO.ID + " = bi." + billInfoDAO.IDFOOD + " AND b." + billDAO.ID + " = bi." + billInfoDAO.IDBILL + " AND b." + billDAO.ID + " = " + id + ";";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do {
                String nameFood = cursor.getString(0);
                double price = cursor.getDouble(1);
                int count = cursor.getInt(2);
                double totalPrice = cursor.getDouble(3);
                int idCategory = cursor.getInt(4);
                foodInfo foodInfo = new foodInfo(nameFood, price, count, totalPrice, idCategory);
                list.add(foodInfo);
            }while(cursor.moveToNext());
        }
        return list;
    }
}
