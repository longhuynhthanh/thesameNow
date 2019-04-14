package com.example.admin.projectnow.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.admin.projectnow.DATABASE.sqlHelper;

public class billInfoDAO {
    public static final String TABLE = "billinfo";
    public static final String ID = "_id";
    public static final String IDBILL = "idBill";
    public static final String IDFOOD = "idFood";
    public static final String COUNTFOOD = "countFood";

    private static billInfoDAO instance;
    private static Context context;
    private SQLiteDatabase db;

    public static billInfoDAO Instance(Context context)
    {
        if(instance == null)
        {
            instance = new billInfoDAO();
            billInfoDAO.context = context;
            sqlHelper.Instance(context);
        }
        return instance;
    }
    private void Instance(billInfoDAO instance)
    {
        billInfoDAO.instance = instance;
    }
    public void InsertBillInfo(int idBill, int idFood, int countFood)
    {
        db = sqlHelper.Instance(billInfoDAO.context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(billInfoDAO.IDBILL, idBill);
        contentValues.put(billInfoDAO.IDFOOD, idFood);
        contentValues.put(billInfoDAO.COUNTFOOD, countFood);
        db.insert(billInfoDAO.TABLE, null, contentValues);
    }
}
