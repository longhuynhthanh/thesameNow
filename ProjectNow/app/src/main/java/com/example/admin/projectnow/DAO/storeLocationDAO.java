package com.example.admin.projectnow.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.admin.projectnow.DATABASE.sqlHelper;
import com.example.admin.projectnow.MODEL.storeLocation;

import java.util.ArrayList;
import java.util.List;

public class storeLocationDAO {
    private static storeLocationDAO instance;
    private static Context context;
    private SQLiteDatabase db;
    public static storeLocationDAO Instace(Context context)
    {
        if(instance == null)
        {
            instance = new storeLocationDAO();
            storeLocationDAO.context = context;
            sqlHelper.Instance(context);
        }
        return instance;
    }
    private void Instanace(storeLocationDAO instance)
    {
        storeLocationDAO.instance = instance;
    }

    public List<storeLocation> GetLocation()
    {
        List<storeLocation> storeLocations = new ArrayList<>();
        db = sqlHelper.Instance(this.context).getReadableDatabase();
        String sql = "SELECT " + storeDAO.TABLE + "." + storeDAO.ID + ", " + storeDAO.TABLE + "." + storeDAO.NAMESTORE + ", " + locationDAO.TABLE + "." + locationDAO.NAMELOCATION +
                " FROM " + storeDAO.TABLE + ", " + locationDAO.TABLE +
                " WHERE " + storeDAO.TABLE + "." + storeDAO.IDLOCATION + " = " + locationDAO.TABLE + "." + locationDAO.ID + ";";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do {
                int idStore = cursor.getInt(0);
                String nameStore = cursor.getString(1);
                String nameLocation = cursor.getString(2);
                storeLocation storeLocation = new storeLocation(idStore, nameStore, nameLocation);
                storeLocations.add(storeLocation);
            }while (cursor.moveToNext());
        }
        return storeLocations;
    }
}
