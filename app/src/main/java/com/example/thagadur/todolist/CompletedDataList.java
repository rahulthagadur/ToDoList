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
import android.widget.Toast;

import com.example.thagadur.todolist.database.DBHelper;
import com.example.thagadur.todolist.model.ToDoData;
import com.example.thagadur.todolist.utils.CommonUtilities;
import com.example.thagadur.todolist.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.example.thagadur.todolist.MainActivity.updateList;

//import static com.example.thagadur.todolist.MainActivity.addDetailsCustomAdapter;

/**
 * Created by Thagadur on 9/19/2017.
 */

public class CompletedDataList extends AppCompatActivity {

    RecyclerView toDoList;
    Context context;
    public static ArrayList<ToDoData> deleteItems;
    public static List<ToDoData> toDoDatas;
    public static UpdateDetailsCustomAdapter updateDetailsCustomAdapter;
    String title, description, dateTime;
    DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completed_list);
        context = this;
        dbHelper = DBHelper.getInstance(context);
        dbHelper = CommonUtilities.getObject(context);
        toDoDatas = new ArrayList<>();
        toDoDatas = dbHelper.getStatusData();
        toDoList = (RecyclerView) findViewById(R.id.to_do_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        toDoList.setLayoutManager(layoutManager);
        updateDetailsCustomAdapter = new UpdateDetailsCustomAdapter(context, toDoDatas);
        toDoList.setAdapter(updateDetailsCustomAdapter);
        registerForContextMenu(toDoList);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Delete") {
            deleteItems = new ArrayList<>();
            deleteItems.add(toDoDatas.get(updateDetailsCustomAdapter.position));
            System.out.println("hellaaa" + deleteItems.get(0).getId());
            String where = "id=?";
            int return_data=dbHelper.delete(Constants.TO_DO_LIST, where, new String[]{deleteItems.get(0).getId()});
            toDoDatas = dbHelper.getStatusData();
            Toast.makeText(context, "Deleted Data ID"+return_data, Toast.LENGTH_SHORT).show();
            updateDetailsCustomAdapter = new UpdateDetailsCustomAdapter(context, toDoDatas);
            toDoList.setAdapter(updateDetailsCustomAdapter);
            updateDetailsCustomAdapter.notifyDataSetChanged();
        }
        return true;

    }
}
