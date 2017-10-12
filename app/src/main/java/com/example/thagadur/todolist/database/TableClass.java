package com.example.thagadur.todolist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;

import com.example.thagadur.todolist.utils.Constants;

/**
 * Created by Thagadur on 9/14/2017.
 */

public class TableClass extends SQLiteOpenHelper {

    Context context;
    String cQuery = "Create table IF NOT EXISTS " + Constants.TO_DO_LIST + "("
            + Constants.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Constants.KEY_TITLE + " TEXT,"
            + Constants.KEY_DESCRIPTION + " TEXT,"
            + Constants.KEY_DATE + " TEXT,"
            + Constants.KEY_STATUS + " INTEGER )";

    public TableClass(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(cQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        context.deleteDatabase(Constants.DATABASE_NAME);
        onCreate(sqLiteDatabase);
    }
}
