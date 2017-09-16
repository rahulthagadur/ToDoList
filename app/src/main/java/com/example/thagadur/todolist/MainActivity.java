package com.example.thagadur.todolist;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thagadur.todolist.database.DBHelper;
import com.example.thagadur.todolist.model.ToDoData;
import com.example.thagadur.todolist.utils.CommonUtilities;
import com.example.thagadur.todolist.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView toDoList;
    Context context;
    List<ToDoData> toDoDatas;
    DBHelper dbHelper;
    public static AddDetailsCustomAdapter addDetailsCustomAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        dbHelper=DBHelper.getInstance(context);
        dbHelper= CommonUtilities.getObject(context);
        toDoDatas=dbHelper.getAllData();

        toDoList=(RecyclerView) findViewById(R.id.to_do_list);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        toDoList.setLayoutManager(layoutManager);
        System.out.println("size"+toDoDatas.size());
        addDetailsCustomAdapter=new AddDetailsCustomAdapter(context,toDoDatas);
        toDoList.setAdapter(addDetailsCustomAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                Toast.makeText(this, "clicked plus button", Toast.LENGTH_SHORT).show();
                AddDetailsDialog addDetailsDialog=new AddDetailsDialog(MainActivity.this);
                addDetailsDialog.show();
                break;
//                AddDetailsCustomAdapter addDetailsCustomAdapter=new AddDetailsCustomAdapter(context);
//                toDoList.setAdapter(addDetailsCustomAdapter);
        }
        return true;
    }
}
