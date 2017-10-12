package com.example.thagadur.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.thagadur.todolist.database.DBHelper;
import com.example.thagadur.todolist.model.ToDoData;
import com.example.thagadur.todolist.utils.CommonUtilities;
import com.example.thagadur.todolist.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;
import static com.example.thagadur.todolist.AddDetailsCustomAdapter.position;

public class MainActivity extends AppCompatActivity {
    /**
     * Iinitialisation of all data member and the objects of specific class
     */
    public static MainActivity mainActivity;
    RecyclerView toDoList;
    Context context;
    public static ArrayList<ToDoData> updateList;
    public static ArrayList<ToDoData> completedList;
    public static List<ToDoData> toDoDatas;
    String title, description, dateTime;
    DBHelper dbHelper;
    public static UpdateDetailsDialog updateDetailsDialog;
    public  AddDetailsCustomAdapter addDetailsCustomAdapter;
    public static UpdateDetailsCustomAdapter updateDetailsCustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        mainActivity=this;
        dbHelper = DBHelper.getInstance(context);
        dbHelper = CommonUtilities.getObject(context);
        toDoDatas = new ArrayList<>();
        toDoDatas = dbHelper.getAllData();

        toDoList = (RecyclerView) findViewById(R.id.to_do_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        toDoList.setLayoutManager(layoutManager);
        readAllData();
        registerForContextMenu(toDoList);
    }

    public static  MainActivity getInstance(){
        return mainActivity;
    }

    /**
     * Function to set the data change in of the recyclerView after Inserting the records
     */
    public  void readAllData(){
        toDoDatas = dbHelper.getAllData();
        addDetailsCustomAdapter = new AddDetailsCustomAdapter(context, toDoDatas);
        toDoList.setAdapter(addDetailsCustomAdapter);
        addDetailsCustomAdapter.notifyDataSetChanged();
    }

    /**
     * Function to set the data change in of the recyclerView after Updating the records
     */
    public void updateAlldata(){
        toDoDatas = dbHelper.getAllData();
        addDetailsCustomAdapter = new AddDetailsCustomAdapter(context, toDoDatas);
        toDoList.setAdapter(addDetailsCustomAdapter);
        addDetailsCustomAdapter.notifyDataSetChanged();
    }

    /**
     * Overriding the Context Menu items of Update and Completed option
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Update") {
            updateList = new ArrayList<>();
            //Toast.makeText(context,""+position, Toast.LENGTH_SHORT).show();
            updateList.add(toDoDatas.get(AddDetailsCustomAdapter.position));
            updateDetailsDialog = new UpdateDetailsDialog(MainActivity.this, updateList);
            updateDetailsDialog.show();
        }
        else if (item.getTitle()=="Completed"){
            completedList= new ArrayList<>();
            completedList.add(toDoDatas.get(AddDetailsCustomAdapter.position));
            ContentValues val=new ContentValues();
            val.put(Constants.KEY_STATUS,"1");
            String where = "id=?";
            dbHelper.update(Constants.TO_DO_LIST,val,where,new String[]{completedList.get(0).getId()});
            readAllData();
        }
        return true;
    }

    /**
     * Inflating the Menu for the Options Settings
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     *Overriding rhe OptionsItemSelected to get populate the items in the toolbar
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                //Toast.makeText(this, "clicked plus button", Toast.LENGTH_SHORT).show();
                AddDetailsDialog addDetailsDialog = new AddDetailsDialog(MainActivity.this);
                addDetailsDialog.show();
                break;
            case R.id.completed_display:
                //Toast.makeText(context, "Complete Button clicked", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,CompletedDataList.class);
                startActivity(intent);
        }
        return true;
    }
}
