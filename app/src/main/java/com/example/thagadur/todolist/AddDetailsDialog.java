package com.example.thagadur.todolist;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.icu.util.TimeZone;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
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

//import static com.example.thagadur.todolist.MainActivity.addDetailsCustomAdapter;
import static com.example.thagadur.todolist.MainActivity.mainActivity;
import static com.example.thagadur.todolist.MainActivity.toDoDatas;

/**
 * Created by Thagadur on 9/15/2017.
 */

public class AddDetailsDialog extends Dialog {
    Context context;
    //DatePicker datePicker;
    TextView setDatePicker;
    TextView titleTextView;
    TextView descriptionTextView;
    Button save, cancel;
    Calendar myCalendar;
    DBHelper dbHelper;

    DatePickerDialog.OnDateSetListener date;

    /**
     * Constructor Initialisation
     *
     * @param context
     */
    public AddDetailsDialog(Context context) {
        super(context);
        this.context = context;
    }

    /**
     * Initialisation of the layout items and calling the OnclickListner items of it
     * to perform specific operation on click of the event
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_details_dialog);
        setDatePicker = (EditText) findViewById(R.id.set_date);
        titleTextView = (EditText) findViewById(R.id.add_title);
        save = (Button) findViewById(R.id.save_button);
        cancel = (Button) findViewById(R.id.cancel_button);
        descriptionTextView = (EditText) findViewById(R.id.add_description);
        myCalendar = Calendar.getInstance();
        dbHelper = DBHelper.getInstance(context);

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
                if ((titleTextView.getText().toString().length() > 4) && (descriptionTextView.getText().toString().length() > 4)) {
                    inserDataIntoDB();
                    MainActivity.getInstance().readAllData();
                    dismiss();
                } else {
                    Toast.makeText(context, "Please Input Sufficient Data to the fields", Toast.LENGTH_LONG).show();
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

    /**
     * Setting the date and time in specific format
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        setDatePicker.setText(sdf.format(myCalendar.getTime()));
    }

    /**
     * Inserting the data into the table by inputting the contents of the rows
     */
    public void inserDataIntoDB() {
        if (titleTextView.getText().toString() != null && descriptionTextView.getText().toString() != null) {
            ContentValues val = new ContentValues();
            val.put(Constants.KEY_TITLE, titleTextView.getText().toString());
            val.put(Constants.KEY_DATE, setDatePicker.getText().toString());
            val.put(Constants.KEY_DESCRIPTION, descriptionTextView.getText().toString());
            val.put(Constants.KEY_STATUS, 0);
            long id = dbHelper.insertContentVals(Constants.TO_DO_LIST, val);
            Toast.makeText(context, "  hi" + id, Toast.LENGTH_SHORT).show();
        }
    }

}

