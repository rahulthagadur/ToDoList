package com.example.thagadur.todolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.thagadur.todolist.model.ToDoData;
import com.example.thagadur.todolist.utils.Constants;

import java.util.ArrayList;
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

    /**
     * Constructor Initialisation
     * @param context
     */
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
            e.printStackTrace();
            System.out.println("exception of the insert command ");}finally {
            db.endTransaction();
        }
        return id;
    }


    //to delete the data
    public int  delete(String tablename, String where, String[] whereArgs) {
        int count=0;
        try {
            db.beginTransaction();
            count=db.delete(tablename, where, whereArgs);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return count;
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

    //getting all the data from the database
    public List<ToDoData> getAllData(){
        List<ToDoData> toDoList=new ArrayList<>();
        //db=dbHelper.getReadableDatabase();
        String query="SELECT * FROM "+Constants.TO_DO_LIST+" where "+Constants.KEY_STATUS+" = "+0;

        Cursor cursor=dbHelper.getReadableDatabase().rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                ToDoData toDoData=new ToDoData();
                toDoData.setId(cursor.getString(0).toString());
                toDoData.setTitle(cursor.getString(1));
                toDoData.setDescription(cursor.getString(2));
                toDoData.setDate(cursor.getString(3));
                toDoData.setStatus(cursor.getString(4));
                toDoList.add(toDoData);
            }while (cursor.moveToNext());
        }
        System.out.println("size"+toDoList.size());
        cursor.close();
        return toDoList;
    }

    public List<ToDoData> getStatusData(){
        List<ToDoData> toDoList=new ArrayList<>();
        //db=dbHelper.getReadableDatabase();
        String query="SELECT * FROM "+Constants.TO_DO_LIST+" where "+Constants.KEY_STATUS+" = "+1;

        Cursor cursor=dbHelper.getReadableDatabase().rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                ToDoData toDoData=new ToDoData();
                toDoData.setId(cursor.getString(0).toString());
                toDoData.setTitle(cursor.getString(1));
                toDoData.setDescription(cursor.getString(2));
                toDoData.setDate(cursor.getString(3));
                toDoData.setStatus(cursor.getString(4));
                toDoList.add(toDoData);
            }while (cursor.moveToNext());
        }
        System.out.println("size"+toDoList.size());
        cursor.close();
        return toDoList;
    }
/*

    public int completeUpdate(ArrayList<ToDoData> updateList){
        String query="UPDATE "+ Constants.TO_DO_LIST+" SET "+Constants.KEY_STATUS+" =1 "
                +" WHERE "+updateList.get(0).getId();
        return

    }
*/

}
