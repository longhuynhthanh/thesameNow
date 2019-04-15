package com.example.admin.projectnow.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.admin.projectnow.DATABASE.sqlHelper;
import com.example.admin.projectnow.MODEL.food;

import java.util.ArrayList;
import java.util.List;

public class foodDAO {
    public static final String TABLE = "food";
    public static final String ID = "_id";
    public static final String NAMEFOOD = "nameFood";
    public static final String PRICE = "price";
    public static final String IDCATEGORY = "idCategory";
    public static final String IDSTORE = "idStore";
    public static final String STATUS = "status";

    private static foodDAO instance;
    private static Context context;
    private SQLiteDatabase db;

    public static foodDAO Instance(Context context)
    {
        if(instance == null)
        {
            instance = new foodDAO();
            foodDAO.context = context;
            sqlHelper.Instance(context);
        }
        return instance;
    }
    private void Instance(foodDAO instance)
    {
        foodDAO.instance = instance;
    }
    public List<food> GetFoodByStoreID(int idStore, int idCategory)
    {
        List<food> list = new ArrayList<>();
        db = sqlHelper.Instance(foodDAO.context).getReadableDatabase();
        String sql = "SELECT * FROM " + foodDAO.TABLE + " WHERE " + foodDAO.IDSTORE + " = " + idStore + " AND " + foodDAO.IDCATEGORY + " = " + idCategory+ ";";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do {
                int id = cursor.getInt(0);
                String nameFood = cursor.getString(1);
                double price = cursor.getDouble(2);
                int status = cursor.getInt(5);
                food food = new food(id, nameFood, price, status, idCategory, idStore);
                list.add(food);
            }while (cursor.moveToNext());
        }
        return list;
    }

    public void InsertFood(food f)
    {
        db = sqlHelper.Instance(foodDAO.context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(foodDAO.NAMEFOOD, f.NameFood());
        contentValues.put(foodDAO.IDSTORE, f.IdStore());
        contentValues.put(foodDAO.PRICE, f.Price());
        contentValues.put(foodDAO.IDCATEGORY, f.IdCategory());
        contentValues.put(foodDAO.STATUS, f.Status());
        db.insert(foodDAO.TABLE, null, contentValues);
    }
    public void DeleteFood(int id)
    {
        db = sqlHelper.Instance(foodDAO.context).getWritableDatabase();
        db.delete(foodDAO.TABLE, foodDAO.ID + " = ? ", new String[]{id + ""});
    }
    public void UpdateFood(food f)
    {
        db = sqlHelper.Instance(foodDAO.context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(foodDAO.NAMEFOOD, f.NameFood());
        contentValues.put(foodDAO.IDCATEGORY, f.IdCategory());
        contentValues.put(foodDAO.PRICE, f.Price());
        contentValues.put(foodDAO.STATUS, f.Status());
        db.update(foodDAO.TABLE, contentValues, foodDAO.ID + " = ? ", new String[]{f.Id() + ""});
    }
    public void DeleteFoodByCategoryID(int idCategory)
    {
        db = sqlHelper.Instance(foodDAO.context).getWritableDatabase();
        db.delete(foodDAO.TABLE, foodDAO.IDCATEGORY + " = ? ", new String[]{idCategory + ""});
    }
}
