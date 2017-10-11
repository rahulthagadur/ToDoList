package com.example.thagadur.todolist;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.support.annotation.RequiresApi;
import com.example.thagadur.todolist.database.DBHelper;
import com.example.thagadur.todolist.model.ToDoData;
import com.example.thagadur.todolist.utils.Constants;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

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
    DatePickerDialog.OnDateSetListener date;

    private RecyclerView mRecyclerList = null;

    ArrayList<ToDoData> updateList;

    public UpdateDetailsDialog(@NonNull Context context, ArrayList<ToDoData> updateList) {
        //, ArrayList<ToDoData> updateList) //{
        super(context);
        this.context = context;
//        this.mRecyclerList=toDoList;
        this.updateList = updateList;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.update_details_dialog);
        myCalendar = Calendar.getInstance();

        updateDatePicker = (EditText) findViewById(R.id.set_date);
        updateTitleTextView = (EditText) findViewById(R.id.add_title);
        save = (Button) findViewById(R.id.save_button);
        cancel=(Button)findViewById(R.id.cancel_button);
        updateDescriptionTextView = (EditText) findViewById(R.id.add_description);
//        myCalendar = Calendar.getInstance();
        dbHelper = DBHelper.getInstance(context);
        //Setting the  text in the fields
        updateTitleTextView.setText(updateList.get(0).getTitle());
        updateDescriptionTextView.setText(updateList.get(0).getDescription());
        updateDatePicker.setText(updateList.get(0).getDate());

        //EditText edittext= (EditText) findViewById(R.id.Birthday);
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        updateDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((updateTitleTextView.getText().toString().length() > 4) && (updateDescriptionTextView.getText().toString().length() > 4)) {
                    updateDataIntoDB();
                    MainActivity.getInstance().updateAlldata();
//                addDetailsCustomAdapter.notifyDataSetChanged();
                    dismiss();
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        updateDatePicker.setText(sdf.format(myCalendar.getTime()));
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
