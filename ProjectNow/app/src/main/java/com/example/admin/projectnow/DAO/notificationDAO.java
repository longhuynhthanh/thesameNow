package com.example.admin.projectnow.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.admin.projectnow.DATABASE.sqlHelper;
import com.example.admin.projectnow.MODEL.notification;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class notificationDAO {
    private static notificationDAO instance;
    private static Context context;
    private SQLiteDatabase db;

    public static notificationDAO Instance(Context context)
    {
        if (instance == null)
        {
            instance = new notificationDAO();
            notificationDAO.context = context;
            sqlHelper.Instance(context);
        }
        return instance;
    }
    private void Instance(notificationDAO instance)
    {
        notificationDAO.instance = instance;
    }

    public List<notification> GetNotificationByIDStore(int idStore)
    {
        List<notification> list = new ArrayList<>();
        db = sqlHelper.Instance(notificationDAO.context).getReadableDatabase();
        String sql = "SELECT b." + billDAO.ID + ", a." + accountDAO.DISPLAYNAME + ", a." + accountDAO.Phone + ", a." + accountDAO.ADDRESS + ", b." + billDAO.CHECKIN +
                " FROM " + billDAO.TABLE + " AS b, " + accountDAO.TABLE + " AS a" +
                " WHERE b." + billDAO.IDSTORE + " = " + idStore + " AND b." + billDAO.STATUS + " = 0 AND b." + billDAO.USERNAMEACCOUNT + " = a." + accountDAO.USERNAME + ";";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do {
                int idBill = cursor.getInt(0);
                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                String address = cursor.getString(3);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date checkIn = new Date();
                try {
                    checkIn = dateFormat.parse(cursor.getString(4));
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
                notification notification = new notification(idBill, name, phone, address, checkIn);
                list.add(notification);
            }while (cursor.moveToNext());
        }
        return list;
    }
}
