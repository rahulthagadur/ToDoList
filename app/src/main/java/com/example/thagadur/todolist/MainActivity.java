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
    ArrayList<ToDoData> updateList;
    ArrayList<ToDoData> completedList;
    public static List<ToDoData> toDoDatas;
    String title, description, dateTime;
    DBHelper dbHelper;
    public static UpdateDetailsDialog updateDetailsDialog;
    public static AddDetailsCustomAdapter addDetailsCustomAdapter;
    public static UpdateDetailsCustomAdapter updateDetailsCustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        dbHelper = DBHelper.getInstance(context);
        dbHelper = CommonUtilities.getObject(context);
        toDoDatas = new ArrayList<>();
        ///readAllData();
        toDoDatas = dbHelper.getAllData();
        updateList = new ArrayList<>();
        completedList= new ArrayList<>();

        toDoList = (RecyclerView) findViewById(R.id.to_do_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        toDoList.setLayoutManager(layoutManager);
//        System.out.println("size"+toDoDatas.size());
        addDetailsCustomAdapter = new AddDetailsCustomAdapter(context, toDoDatas);
        toDoList.setAdapter(addDetailsCustomAdapter);
        registerForContextMenu(toDoList);

    }


    public  void readAllData(){
        toDoDatas = dbHelper.getAllData();
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
//        ToDoData getdata=new ToDoData();
        if (item.getTitle() == "Update") {
            updateList.add(toDoDatas.get(AddDetailsCustomAdapter.position));
            updateDetailsDialog = new UpdateDetailsDialog(MainActivity.this, updateList);
            updateDetailsDialog.show();
            //Toast.makeText(context, "" + toDoDatas.get(AddDetailsCustomAdapter.position).getTitle(), Toast.LENGTH_SHORT).show();
        }
        else if (item.getTitle()=="Completed"){
            completedList.add(toDoDatas.get(AddDetailsCustomAdapter.position));
            ContentValues val=new ContentValues();
            val.put(Constants.KEY_STATUS,"1");
            dbHelper.update(Constants.TO_DO_LIST,val,updateList.get(0).getId(),null);
            updateDetailsCustomAdapter.notifyDataSetChanged();

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
