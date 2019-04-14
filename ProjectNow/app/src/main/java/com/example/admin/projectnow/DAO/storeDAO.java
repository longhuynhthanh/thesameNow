package com.example.admin.projectnow.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.admin.projectnow.DATABASE.sqlHelper;
import com.example.admin.projectnow.MODEL.store;

public class storeDAO {
    public static final String TABLE = "store";
    public static final String ID = "_id";
    public static final String NAMESTORE = "nameStore";
    public static final String IDLOCATION = "idLocation";
    public static final String PHONENUMBER = "phoneNumber";
    public static final String INFO = "info";
    public static final String USERNAMEACCOUNT = "userNameAccount";

    private static storeDAO instance;
    private static Context context;
    private SQLiteDatabase db;
    public static storeDAO Instance(Context context)
    {
        if(instance == null)
        {
            instance = new storeDAO();
            storeDAO.context = context;
            sqlHelper.Instance(context);
        }
        return instance;
    }
    private void Instance(storeDAO instance)
    {
        storeDAO.instance = instance;
    }

    public void InsertStore(String nameStore,int idLocation, String userName)
    {
        db = sqlHelper.Instance(this.context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(storeDAO.NAMESTORE, nameStore);
        contentValues.put(storeDAO.IDLOCATION, idLocation);
        contentValues.put(storeDAO.USERNAMEACCOUNT, userName);
        db.insert(storeDAO.TABLE, null, contentValues);
    }
    public store GetStoreByUserName(String userName)
    {
        db = sqlHelper.Instance(storeDAO.context).getReadableDatabase();
        String sql = "SELECT * FROM " + storeDAO.TABLE +
                " WHERE " + storeDAO.USERNAMEACCOUNT + " = '" + userName + "';";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            int id = cursor.getInt(0);
            String nameStore = cursor.getString(1);
            int idLocation = cursor.getInt(3);
            String phone = cursor.getString(4);
            String info = cursor.getString(5);
            store store = new store(id, nameStore, phone, info, userName, idLocation);
            return store;
        }
        return null;
    }
    public store GetStoreByID(int id)
    {
        db = sqlHelper.Instance(storeDAO.context).getReadableDatabase();
        String sql = "SELECT * FROM " + storeDAO.TABLE +
                " WHERE " + storeDAO.ID + " = " + id + ";";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            String nameStore = cursor.getString(1);
            String userName = cursor.getString(2);
            int idLocation = cursor.getInt(3);
            String phone = cursor.getString(4);
            String info = cursor.getString(5);
            store store = new store(id, nameStore, phone, info , userName, idLocation);
            return store;
        }
        return null;
    }
    public void UpdateStore(store store)
    {
        db = sqlHelper.Instance(storeDAO.context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(storeDAO.NAMESTORE, store.NameStore());
        contentValues.put(storeDAO.PHONENUMBER, store.PhoneNumber());
        contentValues.put(storeDAO.INFO, store.Info());
        db.update(storeDAO.TABLE, contentValues, storeDAO.ID + " = ? ", new String[]{store.Id()+ ""});
    }
}
