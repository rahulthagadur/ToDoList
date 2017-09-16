package com.example.thagadur.todolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.thagadur.todolist.model.ToDoData;
import com.example.thagadur.todolist.utils.Constants;

import java.util.LinkedList;
import java.util.List;

import static android.R.attr.id;

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
    public long insertContentVals(String tablename, ContentValues contentValues) {
        long id = 0;
        try {
            db.beginTransaction();
            id = db.insert(tablename, null, contentValues);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();}


        return id;
    }

    //
    public Cursor getTableRecords(String tablename, String[] columns, String where, String orderBy) {
        Cursor c = db.query(false, tablename, columns, where, null, null, null, orderBy, null);
        return c;
    }

    //Total number of counts
    public int getFullContent(String tablename, String where) {
        int rowCount = 0;
        Cursor c = db.query(false, tablename, null, where, null, null, null, null, null);
        try {
            c.moveToFirst();
            if (c != null) {
                rowCount = c.getCount();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            c.close();
        }
        return rowCount;
    }

    //to delete the data
    public void delete(String tablename, String where, String[] whereArgs) {
        try {
            db.beginTransaction();
            db.delete(tablename, where, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    //to update the data in the database
    public int update(String tablename, ContentValues contentValues, String where, String[] whereArgs) {
        int rowCount = 0;
        try {
            db.beginTransaction();
            rowCount = db.update(tablename, contentValues, where, whereArgs);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return rowCount;
    }

    //this method will give single row
    public String getValue(String tablename, String column, String where) {
        Cursor cur = db.query(false, tablename, new String[]{column}, where, null, null, null, Constants.KEY_ID, null);
        String value = "";
        try {
            if (cur.moveToFirst()) {
                value = cur.getString(0);

            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cur.close();
        }
        return value;
    }

    //
    public List<ToDoData> getAllData(){
        List<ToDoData> toDoList=new LinkedList<>();

        String query="SELECT *FROM "+Constants.TO_DO_LIST;

        Cursor cursor=db.rawQuery(query,null);
        ToDoData toDoData=null;
        if (cursor.moveToFirst()){
            do {
                toDoData=new ToDoData();
                toDoData.setId(cursor.getString(0).toString());
                toDoData.setTitle(cursor.getString(1));
                toDoData.setDescription(cursor.getString(2));
                toDoData.setDate(cursor.getString(2));
                toDoData.setStatus(cursor.getString(2));
                toDoList.add(toDoData);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return toDoList;
    }

}
