package com.example.admin.projectnow.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.admin.projectnow.DATABASE.sqlHelper;
import com.example.admin.projectnow.MODEL.favorites;

import java.util.ArrayList;
import java.util.List;

public class favoritesDAO {
    private static favoritesDAO instance;
    private static Context context;
    private SQLiteDatabase db;

    public static favoritesDAO Instance(Context context) {
        if (instance == null) {
            instance = new favoritesDAO();
            favoritesDAO.context = context;
            sqlHelper.Instance(context);
        }
        return instance;
    }
    private void Instance(favoritesDAO instance)
    {
        favoritesDAO.instance = instance;
    }

    public List<favorites> GetFavorites()
    {
        List<favorites> list = new ArrayList<>();
        db = sqlHelper.Instance(favoritesDAO.context).getReadableDatabase();
        String sql = "SELECT DISTINCT (SELECT SUM(bi1."+ billInfoDAO.COUNTFOOD + ")" +
                " FROM " + billInfoDAO.TABLE + " AS bi1 " +
                " WHERE bi1." + billInfoDAO.IDFOOD + " = f." + foodDAO.ID + ") AS totalCount, f." + foodDAO.IDSTORE + ", f." + foodDAO.NAMEFOOD + ", f." + foodDAO.IDCATEGORY + ", f." + foodDAO.PRICE + ", s." + storeDAO.NAMESTORE +
                " FROM " + billDAO.TABLE + " AS b, " + billInfoDAO.TABLE + " AS bi, " + foodDAO.TABLE + " AS f, " + storeDAO.TABLE + " AS s " +
                " WHERE b." + billDAO.ID + " = bi." + billInfoDAO.IDBILL + " AND f." + foodDAO.ID + " = bi." + billInfoDAO.IDFOOD + " AND f." + foodDAO.IDSTORE + " = s." + storeDAO.ID +
                " ORDER BY totalCount DESC" +
                " LIMIT 5;";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do {
                int countFood = cursor.getInt(0);
                String nameFood = cursor.getString(2);
                int idStore = cursor.getInt(1);
                double price = cursor.getDouble(4);
                int idCategory = cursor.getInt(3);
                String nameStore = cursor.getString(5);
                favorites favorites = new favorites(idStore, nameFood, price, countFood, idCategory, nameStore);
                list.add(favorites);
            }while (cursor.moveToNext());
        }
        return list;
    }
}
