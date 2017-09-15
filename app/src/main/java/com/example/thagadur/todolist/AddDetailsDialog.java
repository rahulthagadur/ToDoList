package com.example.thagadur.todolist;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;

import android.icu.util.TimeZone;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.widget.Toast;

import com.example.thagadur.todolist.database.DBHelper;
import com.example.thagadur.todolist.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Thagadur on 9/15/2017.
 */

public class AddDetailsDialog extends Dialog {
    Context context;
    //DatePicker datePicker;
    TextView setDatePicker;
    TextView titleTextView;
    TextView descriptionTextView;
    Button save,cancel;
    Calendar myCalendar;
    DBHelper dbHelper;

    DatePickerDialog.OnDateSetListener date;

    public AddDetailsDialog(Context context) {
        super(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_details_dialog);
        //setDatePicker = (TextView) findViewById(R.id.set_date);
        setDatePicker = (EditText) findViewById(R.id.set_date);
        titleTextView= (EditText) findViewById(R.id.add_title);
        descriptionTextView = (EditText) findViewById(R.id.add_description);
        myCalendar = Calendar.getInstance();

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

        setDatePicker.setOnClickListener(new View.OnClickListener() {
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
                inserDataIntoDB();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        setDatePicker.setText(sdf.format(myCalendar.getTime()));
    }


    public void inserDataIntoDB(){
        ContentValues val=new ContentValues();
        val.put(Constants.KEY_TITLE,titleTextView.getText().toString());
        val.put(Constants.KEY_DATE,setDatePicker.getText().toString());
        val.put(Constants.KEY_DESCRIPTION,descriptionTextView.getText().toString());
        dbHelper.insertContentVals(Constants.TO_DO_LIST,val);
    }

}
