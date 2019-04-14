package com.example.admin.projectnow.DATABASE;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.admin.projectnow.DAO.accountDAO;
import com.example.admin.projectnow.DAO.billDAO;
import com.example.admin.projectnow.DAO.billInfoDAO;
import com.example.admin.projectnow.DAO.categoryDAO;
import com.example.admin.projectnow.DAO.foodDAO;
import com.example.admin.projectnow.DAO.locationDAO;
import com.example.admin.projectnow.DAO.storeDAO;

public class sqlHelper extends SQLiteOpenHelper {
    private static sqlHelper instance;
    private static final String DATABASE = "thesameNow";

    public static sqlHelper Instance(Context context)
    {
        if(instance == null)
        {
            instance = new sqlHelper(context.getApplicationContext());
        }
        return instance;
    }
    private sqlHelper(Context context) {
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create Table Account
        String sql8 = "CREATE TABLE IF NOT EXISTS " + accountDAO.TABLE +
                "(" +
                accountDAO.USERNAME + " text PRIMARY KEY," +
                accountDAO.PASSWORD + " text NOT NULL DEFAULT ''," +
                accountDAO.DISPLAYNAME + " text NOT NULL DEFAULT ''," +
                accountDAO.Phone + " text NOT NULL DEFAULT ''," +
                accountDAO.ADDRESS + " text NOT NULL DEFAULT ''," +
                accountDAO.TYPE + " integer NOT NULL DEFAULT 2" +
                ");";
        sqLiteDatabase.execSQL(sql8);

        // Create Table Location
        String sql1 = "CREATE TABLE IF NOT EXISTS " + locationDAO.TABLE +
                "(" +
                 locationDAO.ID + " integer PRIMARY KEY autoincrement, " +
                locationDAO.NAMELOCATION + " text NOT NULL DEFAULT 'no name', " +
                locationDAO.LONGITUDE + " real NOT NULL DEFAULT 1, " +
                locationDAO.LATITUDE + " real NOT NULL DEFAULT 1" +
                ");";
        sqLiteDatabase.execSQL(sql1);

        // Create Table Store
        String sql2 = "CREATE TABLE IF NOT EXISTS " + storeDAO.TABLE +
                "(" +
                storeDAO.ID + " integer PRIMARY KEY autoincrement, " +
                storeDAO.NAMESTORE + " text NOT NULL DEFAULT 'no name', " +
                storeDAO.USERNAMEACCOUNT + " text NOT NULL, " +
                storeDAO.IDLOCATION + " integer NOT NULL, " +
                storeDAO.PHONENUMBER + " text NOT NULL DEFAULT '', " +
                storeDAO.INFO + " text DEFAULT '', " +
                " FOREIGN KEY(" + storeDAO.IDLOCATION + ") REFERENCES " + locationDAO.TABLE + "(" + locationDAO.ID + ")," +
                " FOREIGN KEY(" + storeDAO.USERNAMEACCOUNT + ") REFERENCES " + accountDAO.TABLE + "(" + accountDAO.USERNAME + ")" +
                ");";
        sqLiteDatabase.execSQL(sql2);

        // Create Table category
        String sql3 = "CREATE TABLE IF NOT EXISTS " + categoryDAO.TABLE +
                "(" +
                categoryDAO.ID + " integer PRIMARY KEY autoincrement, " +
                categoryDAO.NAMECATEGORY + " text NOT NULL DEFAULT 'no name'" +
                ");";
        sqLiteDatabase.execSQL(sql3);

        // Create Table food
        String sql4 = "CREATE TABLE IF NOT EXISTS " + foodDAO.TABLE +
                "(" +
                foodDAO.ID + " integer PRIMARY KEY autoincrement," +
                foodDAO.NAMEFOOD + " text NOT NULL DEFAULT 'no name'," +
                foodDAO.PRICE + " real NOT NULL DEFAULT 0," +
                foodDAO.IDCATEGORY + " integer NOT NULL," +
                foodDAO.IDSTORE + " integer NOT NULL," +
                foodDAO.STATUS + " integer NOT NULL DEFAULT 1," +
                "FOREIGN KEY(" + foodDAO.IDCATEGORY + ") REFERENCES " + categoryDAO.TABLE + "(" + categoryDAO.ID + ")," +
                "FOREIGN KEY(" + foodDAO.IDSTORE + ") REFERENCES " + storeDAO.TABLE + "(" + storeDAO.ID + ")" +
                ");";
        sqLiteDatabase.execSQL(sql4);

        // Create Table bill
        String sql6 = "CREATE TABLE IF NOT EXISTS " + billDAO.TABLE +
                "(" +
                billDAO.ID + " integer PRIMARY KEY autoincrement," +
                billDAO.IDSTORE + " integer NOT NULL," +
                billDAO.USERNAMEACCOUNT + " text NOT NULL," +
                billDAO.CHECKIN + " DATETIME NOT NULL DEFAULT (DATETIME('now','localtime'))," +
                billDAO.CHECKOUT + " DATETIME," +
                billDAO.STATUS + " integer NOT NULL DEFAULT 0," +
                "FOREIGN KEY(" + billDAO.IDSTORE + ") REFERENCES " + storeDAO.TABLE + "(" + storeDAO.ID + "), " +
                "FOREIGN KEY(" + billDAO.USERNAMEACCOUNT + ") REFERENCES " + accountDAO.TABLE + "(" + accountDAO.USERNAME + ")" +
                ");";
        sqLiteDatabase.execSQL(sql6);

        // Create Table Bill Info
        String sql7 = "CREATE TABLE IF NOT EXISTS " + billInfoDAO.TABLE +
                "(" +
                billInfoDAO.ID + " integer PRIMARY KEY autoincrement," +
                billInfoDAO.IDBILL + " integer NOT NULL," +
                billInfoDAO.IDFOOD + " integer NOT NULL, " +
                billInfoDAO.COUNTFOOD + " integer NOT NULL DEFAULT 0," +
                "FOREIGN KEY(" + billInfoDAO.IDBILL + ") REFERENCES " + billDAO.TABLE + "(" + billDAO.ID + ")," +
                "FOREIGN KEY(" + billInfoDAO.IDFOOD + ") REFERENCES " + foodDAO.TABLE + "(" + foodDAO.ID + ")" +
                ");";

        sqLiteDatabase.execSQL(sql7);


//        // Create Trigger khi thêm tài khoản type = 1: chủ store => thêm location
//        String sql9 = "CREATE TRIGGER insert_location AFTER INSERT ON " + accountDAO.TABLE +
//                " WHEN new." + accountDAO.TYPE + " = 1" +
//                " BEGIN" +
//                " INSERT INTO " + locationDAO.TABLE + "(" + locationDAO.NAMELOCATION + ", " + locationDAO.LONGITUDE + ", " + locationDAO.LATITUDE + ") VALUES ('', 1, 1);" +
//                " END;";
//        sqLiteDatabase.execSQL(sql9);
//        // Create Trigger khi thêm tài khoản type = 1: chủ store => thêm store
//        String sql10 = "CREATE TRIGGER insert_store AFTER INSERT ON " + accountDAO.TABLE +
//                " WHEN new." + accountDAO.TYPE + " = 1" +
//                " BEGIN" +
//                " INSERT INTO " + storeDAO.TABLE + " (" + storeDAO.NAMESTORE + ", " + storeDAO.IDLOCATION + ", " + storeDAO.USERNAMEACCOUNT + "," + storeDAO.PHONENUMBER + ", " + storeDAO.INFO + ") VALUES ('', (SELECT MAX(" + locationDAO.ID + ") FROM " + locationDAO.TABLE + "), new." + accountDAO.USERNAME + ", '', '');" +
//                " END;";
//        sqLiteDatabase.execSQL(sql10);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + locationDAO.TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + storeDAO.TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + categoryDAO.TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + foodDAO.TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + billDAO.TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + billInfoDAO.TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + accountDAO.TABLE);
//        sqLiteDatabase.execSQL("DROP TRIGGER IF EXISTS insert_location");
//        sqLiteDatabase.execSQL("DROP TRIGGER IF EXISTS insert_store");
        onCreate(sqLiteDatabase);
    }
}
