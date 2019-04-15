package com.example.admin.projectnow.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.admin.projectnow.DATABASE.sqlHelper;
import com.example.admin.projectnow.MODEL.account;

import java.util.ArrayList;
import java.util.List;

public class accountDAO {
    public static final String TABLE = "account";
    public static final String USERNAME = "userName";
    public static final String PASSWORD = "passWord";
    public static final String DISPLAYNAME = "Name";
    public static final String Phone = "phone";
    public static final String ADDRESS = "address";
    public static final String TYPE = "type";

    private static accountDAO instance;
    private static Context context;

    private SQLiteDatabase db;
    public static accountDAO Instance(Context context)
    {
        if(instance == null)
        {
            instance = new accountDAO();
            accountDAO.context = context;
            sqlHelper.Instance(context);
        }
        return instance;
    }
    private void Instance(accountDAO instance)
    {
        accountDAO.instance = instance;
    }
    public List<String> getUserNameAccount()
    {
        List<String> list = new ArrayList<>();
        db = sqlHelper.Instance(this.context).getReadableDatabase();
        String sql = "SELECT " + accountDAO.USERNAME + " FROM " + accountDAO.TABLE + ";";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do{
                String userName = cursor.getString(0);
                list.add(userName);
            }while (cursor.moveToNext());
        }
        return list;
    }
    public void InsertAccount(account a)
    {
        db = sqlHelper.Instance(accountDAO.context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(accountDAO.USERNAME, a.UserName());
        contentValues.put(accountDAO.PASSWORD, a.Password());
        contentValues.put(accountDAO.DISPLAYNAME, a.DisplayName());
        contentValues.put(accountDAO.Phone, a.Phone());
        contentValues.put(accountDAO.ADDRESS, a.Address());
        contentValues.put(accountDAO.TYPE, a.Type());
        if(db.insert(accountDAO.TABLE, null, contentValues) < 0)
        {
            Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
        }
    }
    public List<account> getAccountIsStores()
    {
        db = sqlHelper.Instance(this.context).getReadableDatabase();
        List<account> list = new ArrayList<>();
        String sql = "SELECT * FROM " + accountDAO.TABLE +
                " WHERE " + accountDAO.TYPE + " = 1;";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do {
                String userName = cursor.getString(0);
                String password = cursor.getString(1);
                String displayName = cursor.getString(2);
                String phone = cursor.getString(3);
                String address = cursor.getString(4);
                account account = new account(userName, password, displayName, phone, address, 1);
                list.add(account);
            }while (cursor.moveToNext());
        }
        return list;
    }
    public List<account> getAccountIsCustomer()
    {
        db = sqlHelper.Instance(this.context).getReadableDatabase();
        List<account> list = new ArrayList<>();
        String sql = "SELECT * FROM " + accountDAO.TABLE +
                " WHERE " + accountDAO.TYPE + " = 2;";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do {
                String userName = cursor.getString(0);
                String password = cursor.getString(1);
                String displayName = cursor.getString(2);
                String phone = cursor.getString(3);
                String address = cursor.getString(4);
                account account = new account(userName, password, displayName, phone, address, 2);
                list.add(account);
            }while (cursor.moveToNext());
        }
        return list;
    }
    public List<account> getAccount()
    {
        db = sqlHelper.Instance(this.context).getReadableDatabase();
        List<account> list = new ArrayList<>();
        String sql = "SELECT * FROM " + accountDAO.TABLE + ";";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do {
                String userName = cursor.getString(0);
                String password = cursor.getString(1);
                String displyaName = cursor.getString(2);
                String phone = cursor.getString(3);
                String address = cursor.getString(4);
                int type = cursor.getInt(5);
                account account = new account(userName, password, displyaName, phone, address, type);
                list.add(account);
            }while (cursor.moveToNext());
        }
        return list;
    }
    public int UpdateAccount(account account)
    {
        db = sqlHelper.Instance(accountDAO.context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(accountDAO.PASSWORD, account.Password());
        contentValues.put(accountDAO.DISPLAYNAME, account.DisplayName());
        contentValues.put(accountDAO.Phone, account.Phone());
        contentValues.put(accountDAO.ADDRESS, account.Address());
        if(db.update(accountDAO.TABLE, contentValues, accountDAO.USERNAME + " = ?", new String[]{account.UserName()}) > 0)
        {
            return 1;
        }
        return -1;
    }
    public void DeleteAccount(String userName)
    {
        db = sqlHelper.Instance(accountDAO.context).getWritableDatabase();
        db.delete(accountDAO.TABLE, accountDAO.USERNAME + " = ? ", new String[]{userName});
    }
}
