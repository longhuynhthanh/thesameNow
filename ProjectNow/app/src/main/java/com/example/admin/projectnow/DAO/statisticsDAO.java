package com.example.admin.projectnow.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.admin.projectnow.DATABASE.sqlHelper;
import com.example.admin.projectnow.MODEL.statistics;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class statisticsDAO {
    private static statisticsDAO instance;
    private static Context context;
    private SQLiteDatabase db;

    public static statisticsDAO Instance(Context context)
    {
        if (instance == null)
        {
            instance = new statisticsDAO();
            statisticsDAO.context = context;
            sqlHelper.Instance(context);
        }
        return instance;
    }
    private void Instance(statisticsDAO instance)
    {
        statisticsDAO.instance = instance;
    }

    public List<statistics> GetStatistics(String date, int idStore)
    {
        String reformattedStr = "";
        SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            reformattedStr = myFormat.format(fromUser.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<statistics> list = new ArrayList<>();
        db = sqlHelper.Instance(statisticsDAO.context).getReadableDatabase();
        String sql = "SELECT DISTINCT b." + billDAO.ID + ", b." + billDAO.CHECKOUT + ", b." + billDAO.CHECKIN + ", (SELECT SUM(f1." + foodDAO.PRICE + "*bi1." + billInfoDAO.COUNTFOOD + ") " +
                " FROM " + foodDAO.TABLE + " AS f1, " + billDAO.TABLE + " AS b1, " + billInfoDAO.TABLE + " AS bi1" +
                " WHERE f1." + foodDAO.ID + " = bi1." + billInfoDAO.IDFOOD + " AND b1." + billDAO.ID + " = bi1." + billInfoDAO.IDBILL + " AND b1." + billDAO.STATUS + " = 1 AND b1." + billDAO.ID + " = b." + billDAO.ID + ")" +
                " FROM " + billDAO.TABLE + " AS b, " + billInfoDAO.TABLE + " AS bi" +
                " WHERE b." + billDAO.ID + " = bi." + billInfoDAO.IDBILL + " AND b.idStore = " + idStore + " AND  strftime('%d', (b." + billDAO.CHECKOUT + ")) = strftime('%d', ('" + reformattedStr + "'));";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do {
                int idBill = cursor.getInt(0);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date checkOut = new Date();
                Date checkIn = new Date();
                try {
                    checkIn = dateFormat.parse(cursor.getString(2));
                    checkOut = dateFormat.parse(cursor.getString(1));
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
                double totalPrice = cursor.getDouble(3);
                statistics statistics = new statistics(idBill, checkOut, checkIn, totalPrice);
                list.add(statistics);
            }while (cursor.moveToNext());
        }
        return list;
    }
}
