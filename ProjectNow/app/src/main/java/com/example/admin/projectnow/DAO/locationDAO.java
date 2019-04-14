package com.example.admin.projectnow.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.admin.projectnow.DATABASE.sqlHelper;
import com.example.admin.projectnow.MODEL.location;

public class locationDAO {
    public static final String TABLE = "location";
    public static final String ID = "_id";
    public static final String NAMELOCATION = "nameLocation";
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";


    private static locationDAO instance;
    private static Context context;
    private SQLiteDatabase db;
    public static locationDAO Instance(Context context)
    {
        if(instance == null)
        {
            instance = new locationDAO();
            locationDAO.context = context;
            sqlHelper.Instance(context);
        }
        return instance;
    }
    private void Instance(locationDAO instance)
    {
        locationDAO.instance = instance;
    }
    public String GetNameLocationByID(int id)
    {
        db = sqlHelper.Instance(locationDAO.context).getReadableDatabase();
        String sql = "SELECT " + locationDAO.NAMELOCATION + " FROM " + locationDAO.TABLE +
                " WHERE " + locationDAO.ID + " = " + id + ";";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            String nameLocation = cursor.getString(0);
            return nameLocation;
        }
        return null;
    }
    public int getMaxLocationID()
    {
        db = sqlHelper.Instance(this.context).getReadableDatabase();
        String sql = "SELECT MAX(" + locationDAO.ID + ")" +
                " FROM " + locationDAO.TABLE;
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            return cursor.getInt(0);
        }
        return -1;
    }
    public void InsertLocation(String nameLocation)
    {
        db = sqlHelper.Instance(this.context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(locationDAO.NAMELOCATION, nameLocation);
        db.insert(locationDAO.TABLE, null, contentValues);
    }
    public location getLocationByID(int id)
    {
        db = sqlHelper.Instance(locationDAO.context).getReadableDatabase();
        String sql = "SELECT * FROM " + locationDAO.TABLE + " WHERE " + locationDAO.ID + " = " + id + ";";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            String nameLocation = cursor.getString(1);
            double longitude = cursor.getDouble(2);
            double latitude = cursor.getDouble(3);
            location location = new location(id, nameLocation, longitude, latitude);
            return location;
        }
        return null;
    }
    public void UpdateLocation(location location)
    {
        db = sqlHelper.Instance(locationDAO.context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(locationDAO.NAMELOCATION, location.NameLocation());
        contentValues.put(locationDAO.LONGITUDE, location.Longitude());
        contentValues.put(locationDAO.LATITUDE, location.Latitude());
        db.update(locationDAO.TABLE, contentValues, locationDAO.ID + " = ?", new String[]{location.Id()+ ""});
    }
}
