package com.example.thagadur.todolist;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import com.example.thagadur.todolist.model.ToDoData;

import java.util.ArrayList;

/**
 * Created by Thagadur on 9/18/2017.
 */

public class UpdateDetailsDialog extends Dialog {
    Context context;

    private RecyclerView mRecyclerList = null;
    //ArrayList<ToDoData>updateList;
    public UpdateDetailsDialog(@NonNull Context context, RecyclerView toDoList){
                               //, ArrayList<ToDoData> updateList) //{
        super(context);
        this.context=context;
        this.mRecyclerList=toDoList;

        //this.updateList=updateList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.update_details_dialog);

    }
}
