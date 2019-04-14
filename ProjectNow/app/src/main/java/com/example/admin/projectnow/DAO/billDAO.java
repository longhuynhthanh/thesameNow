package com.example.admin.projectnow.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.admin.projectnow.DATABASE.sqlHelper;
import com.example.admin.projectnow.MODEL.bill;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class billDAO {
    public static final String TABLE = "bill";
    public static final String ID = "_id";
    public static final String IDSTORE = "idStore";
    public static final String USERNAMEACCOUNT = "userName";
    public static final String CHECKIN = "checkIn";
    public static final String CHECKOUT = "checkOut";
    public static final String STATUS = "status";

    private static billDAO instance;
    private static Context context;
    private SQLiteDatabase db;

    public static billDAO Instance(Context context)
    {
        if(instance == null)
        {
            instance = new billDAO();
            billDAO.context = context;
            sqlHelper.Instance(context);
        }
        return instance;
    }
    private void Instance(billDAO instance)
    {
        billDAO.instance = instance;
    }
    public void InsertBill(int idStore, String userName)
    {
        db = sqlHelper.Instance(billDAO.context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(billDAO.IDSTORE, idStore);
        contentValues.put(billDAO.USERNAMEACCOUNT, userName);
        db.insert(billDAO.TABLE, null, contentValues);
    }
    public int GetMaxBillID()
    {
        db = sqlHelper.Instance(this.context).getReadableDatabase();
        String sql = "SELECT MAX(" + billDAO.ID + ")" +
                " FROM " + billDAO.TABLE;
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            return cursor.getInt(0);
        }
        return -1;
    }
    public void UpdateBill(int idBill)
    {
        db = sqlHelper.Instance(billDAO.context).getWritableDatabase();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        ContentValues contentValues = new ContentValues();
        contentValues.put(billDAO.STATUS, 1);
        contentValues.put(billDAO.CHECKOUT, dateFormat.format(date));
        db.update(billDAO.TABLE, contentValues, billDAO.ID + " = ? ", new String[]{idBill + ""});
    }
    public List<bill> GetBillByUserName(String userName)
    {
        List<bill> list = new ArrayList<>();
        db = sqlHelper.Instance(billDAO.context).getReadableDatabase();
        String sql = "SELECT *" +
                " FROM " + billDAO.TABLE + " AS b" +
                " WHERE b." + billDAO.STATUS + " = 0 AND b." + billDAO.USERNAMEACCOUNT + " = '" + userName + "';";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do {
                int idBill = cursor.getInt(0);
                int idStore = cursor.getInt(1);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date checkIn = new Date();
                Date checkOut = new Date();
                try {
                    checkIn = dateFormat.parse(cursor.getString(3));
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
                bill bill = new bill(idBill, idStore, userName, checkIn, checkOut, 0);
                list.add(bill);
            }while (cursor.moveToNext());
        }
        return list;
    }
}
