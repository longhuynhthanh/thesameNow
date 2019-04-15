package com.example.admin.projectnow.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.admin.projectnow.DATABASE.sqlHelper;
import com.example.admin.projectnow.MODEL.category;

import java.util.ArrayList;
import java.util.List;

public class categoryDAO {
    public static final String TABLE = "category";
    public static final String ID = "_id";
    public static final String NAMECATEGORY = "nameCategory";

    private static categoryDAO instance;
    private static Context context;
    private SQLiteDatabase db;

    public static categoryDAO Instance(Context context)
    {
        if(instance == null)
        {
            instance = new categoryDAO();
            categoryDAO.context = context;
            sqlHelper.Instance(context);
        }
        return instance;
    }
    private void Instance(categoryDAO instance)
    {
        categoryDAO.instance = instance;
    }

    public List<category> GetCategoryByStoreID(int storeID)
    {
        List<category> list = new ArrayList<>();
        db = sqlHelper.Instance(categoryDAO.context).getReadableDatabase();
        String sql = "SELECT DISTINCT " + "c." + categoryDAO.ID + ", " + "c." + categoryDAO.NAMECATEGORY +
                " FROM " + categoryDAO.TABLE + " AS c, " + foodDAO.TABLE + " AS f" +
                " WHERE c." + categoryDAO.ID + " = f." + foodDAO.IDCATEGORY + " AND f." + foodDAO.IDSTORE + " = " + storeID + ";";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                category category = new category(id, name);
                list.add(category);
            }while (cursor.moveToNext());
        }
        return list;
    }
    public List<category> GetCategories()
    {
        List<category> list = new ArrayList<>();
        db = sqlHelper.Instance(categoryDAO.context).getReadableDatabase();
        String sql = "SELECT * FROM " + categoryDAO.TABLE + ";";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do {
                int id = cursor.getInt(0);
                String nameCategory = cursor.getString(1);
                category category = new category(id, nameCategory);
                list.add(category);
            }while (cursor.moveToNext());
        }
        return list;
    }
    public List<String> GetNameCategory()
    {
        List<String> list = new ArrayList<>();
        db = sqlHelper.Instance(categoryDAO.context).getReadableDatabase();
        String sql = "SELECT " + categoryDAO.NAMECATEGORY + " FROM " + categoryDAO.TABLE + ";";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do {
                String namecategory = cursor.getString(0);
                list.add(namecategory);
            }while (cursor.moveToNext());
        }
        return list;
    }
    public void InsertCategory(String categoryName)
    {
        db = sqlHelper.Instance(categoryDAO.context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(categoryDAO.NAMECATEGORY, categoryName);
        db.insert(categoryDAO.TABLE, null, contentValues);
    }
    public void DeleteCategory(int id)
    {
        db = sqlHelper.Instance(categoryDAO.context).getWritableDatabase();
        db.delete(categoryDAO.TABLE, categoryDAO.ID + " = ? ", new String[]{id + ""});
    }
    public void UpdateCategory(category c)
    {
        db = sqlHelper.Instance(categoryDAO.context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(categoryDAO.NAMECATEGORY, c.NameCategory());
        db.update(categoryDAO.TABLE, contentValues, categoryDAO.ID + " = ? ", new String[]{c.Id() + ""});
    }
}
