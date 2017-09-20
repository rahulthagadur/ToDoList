package com.example.thagadur.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;

import com.example.thagadur.todolist.database.DBHelper;
import com.example.thagadur.todolist.model.ToDoData;
import com.example.thagadur.todolist.utils.CommonUtilities;
import com.example.thagadur.todolist.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.example.thagadur.todolist.MainActivity.addDetailsCustomAdapter;

/**
 * Created by Thagadur on 9/19/2017.
 */

public class CompletedDataList extends AppCompatActivity {

    RecyclerView toDoList;
    Context context;
    ArrayList<ToDoData> updateList;
    public static List<ToDoData> toDoDatas;
    public static UpdateDetailsCustomAdapter updateDetailsCustomAdapter;
    String title, description, dateTime;
    DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        dbHelper = DBHelper.getInstance(context);
        dbHelper = CommonUtilities.getObject(context);
        toDoDatas = new ArrayList<>();
        ///readAllData();
        toDoDatas = dbHelper.getStatusData();
        updateList = new ArrayList<>();
        toDoList = (RecyclerView) findViewById(R.id.to_do_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        toDoList.setLayoutManager(layoutManager);
//        System.out.println("size"+toDoDatas.size());
        updateDetailsCustomAdapter= new UpdateDetailsCustomAdapter(context, toDoDatas);
        toDoList.setAdapter(updateDetailsCustomAdapter);

        registerForContextMenu(toDoList);


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle()=="Delete"){

            updateList.add(toDoDatas.get(AddDetailsCustomAdapter.position));
            System.out.println("hellaaa"+updateList.get(0).getId());
//            ContentValues val=new ContentValues();
//            val.put(Constants.KEY_STATUS,"1");
            dbHelper.delete(Constants.TO_DO_LIST,updateList.get(0).getId(),null);
            updateDetailsCustomAdapter.notifyDataSetChanged();
        }
        return true;

    }
}
