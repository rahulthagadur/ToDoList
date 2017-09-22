package com.example.thagadur.todolist;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.thagadur.todolist.database.DBHelper;
import com.example.thagadur.todolist.model.ToDoData;
import com.example.thagadur.todolist.utils.Constants;

import java.util.ArrayList;
import java.util.Calendar;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
//import static com.example.thagadur.todolist.MainActivity.addDetailsCustomAdapter;
import static com.example.thagadur.todolist.MainActivity.updateDetailsDialog;

/**
 * Created by Thagadur on 9/18/2017.
 */

public class UpdateDetailsDialog extends Dialog {
    Context context;
    TextView updateDatePicker;
    TextView updateTitleTextView;
    TextView updateDescriptionTextView;
    Button save, cancel;
    Calendar myCalendar;
    DBHelper dbHelper;


    private RecyclerView mRecyclerList = null;

    ArrayList<ToDoData> updateList;

    public UpdateDetailsDialog(@NonNull Context context, ArrayList<ToDoData> updateList) {
        //, ArrayList<ToDoData> updateList) //{
        super(context);
        this.context = context;
//        this.mRecyclerList=toDoList;
        this.updateList = updateList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.update_details_dialog);


        updateDatePicker = (EditText) findViewById(R.id.set_date);
        updateTitleTextView = (EditText) findViewById(R.id.add_title);
        save = (Button) findViewById(R.id.save_button);
        updateDescriptionTextView = (EditText) findViewById(R.id.add_description);
//        myCalendar = Calendar.getInstance();
        dbHelper = DBHelper.getInstance(context);
        //Setting the  text in the fields
        updateTitleTextView.setText(updateList.get(0).getTitle());
        updateDescriptionTextView.setText(updateList.get(0).getDescription());
        updateDatePicker.setText(updateList.get(0).getDate());


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDataIntoDB();
                MainActivity.getInstance().updateAlldata();
//                addDetailsCustomAdapter.notifyDataSetChanged();
                dismiss();
            }


        });
    }

    public void updateDataIntoDB() {
        ContentValues val = new ContentValues();
        val.put(Constants.KEY_TITLE, updateTitleTextView.getText().toString());
        val.put(Constants.KEY_DESCRIPTION, updateDescriptionTextView.getText().toString());
        val.put(Constants.KEY_DATE, updateDatePicker.getText().toString());
        String where = "id=?";
        int id=dbHelper.update(Constants.TO_DO_LIST,val,where,(new String[]{updateList.get(0).getId()+""}));

        Toast.makeText(context, "rows updates"+id, Toast.LENGTH_SHORT).show();
    }
}
