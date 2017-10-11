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
        toDoList.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        readAllData();
//        System.out.println("size"+toDoDatas.size());

        registerForContextMenu(toDoList);

    }

    public static  MainActivity getInstance(){
        return mainActivity;
    }

    public  void readAllData(){
        toDoDatas = dbHelper.getAllData();
        addDetailsCustomAdapter = new AddDetailsCustomAdapter(context, toDoDatas);
        toDoList.setAdapter(addDetailsCustomAdapter);
        addDetailsCustomAdapter.notifyDataSetChanged();
    }

    public void updateAlldata(){
        toDoDatas = dbHelper.getAllData();
        addDetailsCustomAdapter = new AddDetailsCustomAdapter(context, toDoDatas);
        toDoList.setAdapter(addDetailsCustomAdapter);
        addDetailsCustomAdapter.notifyDataSetChanged();
    }

    /*@Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select the option");
        menu.add(0, 1, 0, "Update");
        menu.add(0, 2, 1, "Completed");
    }*/


    @Override
    public boolean onContextItemSelected(MenuItem item) {
//        ToDoData getdata=new ToDoData();
        if (item.getTitle() == "Update") {
            updateList = new ArrayList<>();
            Toast.makeText(context,""+position, Toast.LENGTH_SHORT).show();
            updateList.add(toDoDatas.get(AddDetailsCustomAdapter.position));
            updateDetailsDialog = new UpdateDetailsDialog(MainActivity.this, updateList);
            updateDetailsDialog.show();
            //Toast.makeText(context, "" + toDoDatas.get(AddDetailsCustomAdapter.position).getTitle(), Toast.LENGTH_SHORT).show();
        }
        else if (item.getTitle()=="Completed"){
            completedList= new ArrayList<>();
            completedList.add(toDoDatas.get(AddDetailsCustomAdapter.position));
            ContentValues val=new ContentValues();
            val.put(Constants.KEY_STATUS,"1");
            String where = "id=?";
            dbHelper.update(Constants.TO_DO_LIST,val,where,new String[]{completedList.get(0).getId()});

            readAllData();
            //updateDetailsCustomAdapter.notifyDataSetChanged();

            /*dbHelper.completeUpdate(updateList);
            String query="UPDATE "+ Constants.TO_DO_LIST+" SET "+Constants.KEY_STATUS+" =1 "
                    +" WHERE "+updateList.get(0).getId();
*/
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Toast.makeText(this, "clicked plus button", Toast.LENGTH_SHORT).show();
                AddDetailsDialog addDetailsDialog = new AddDetailsDialog(MainActivity.this);
                addDetailsDialog.show();
                break;
            case R.id.completed_display:
                Toast.makeText(context, "Complete Button clicked", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,CompletedDataList.class);
                startActivity(intent);
//                AddDetailsCustomAdapter addDetailsCustomAdapter=new AddDetailsCustomAdapter(context);
//                toDoList.setAdapter(addDetailsCustomAdapter);
        }
        return true;
    }
}
