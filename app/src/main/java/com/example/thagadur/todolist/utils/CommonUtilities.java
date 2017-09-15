package com.example.thagadur.todolist.utils;

import android.content.Context;

import com.example.thagadur.todolist.database.DBHelper;

/**
 * Created by Thagadur on 9/14/2017.
 */

public class CommonUtilities {
    public static  DBHelper getObject(Context mContext){
        DBHelper dbHelper=DBHelper.getInstance(mContext);
        return dbHelper;
    }
}
