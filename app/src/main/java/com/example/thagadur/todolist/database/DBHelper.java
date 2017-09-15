package com.example.thagadur.todolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.thagadur.todolist.utils.Constants;

/**
 * Created by Thagadur on 9/14/2017.
 */

public class DBHelper {
    private SQLiteDatabase db;
    private final Context context;
    private final TableClass dbHelper;
    private static DBHelper db_helper;

    public DBHelper(Context context) {
        this.context = context;
        dbHelper = new TableClass(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);

    }

    //SingleTon class to get the instance of the class
    public static DBHelper getInstance(Context context) {
        try {
            if (db_helper == null) {
                db_helper = new DBHelper(context);
                db_helper.open();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return db_helper;
    }

    //to open the database in readable mode or writable mode
    public void open() throws SQLException {
        try {
            db = dbHelper.getWritableDatabase();

        } catch (Exception e) {
            e.printStackTrace();
            db = dbHelper.getReadableDatabase();
        }
    }

    //to close the database if Open
    public void close() throws SQLException {
        if (db.isOpen()) {
            db.close();
        }
    }
//insert values into DB
    public long insertContentVals(String tablename, ContentValues contentValues){
        long id=0;
        try {
            db.beginTransaction();
            id=db.insert(tablename,null,contentValues);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return id;
    }
//
    public Cursor getTableRecords(String tablename, String[] columns, String where, String orderBy){
        Cursor c=db.query(false,tablename,columns,where,null,null,null,orderBy,null);
        return c;
    }

}
