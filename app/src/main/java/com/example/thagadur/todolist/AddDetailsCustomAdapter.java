package com.example.thagadur.todolist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thagadur.todolist.model.ToDoData;

import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by Thagadur on 9/14/2017.
 */

public class AddDetailsCustomAdapter extends RecyclerView.Adapter<AddDetailsCustomAdapter.AddDetailsHolder> {
    Context context;
    ArrayList<ToDoData> toDoDatas;
    List<ToDoData> todoList = new ArrayList<>();

    public AddDetailsCustomAdapter(Context context,List<ToDoData> todoList) {
        this.context = context;
        this.todoList=todoList;
    }

    @Override
    public AddDetailsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_design, parent, false);
        AddDetailsHolder myView = new AddDetailsHolder(view);
        return myView;
    }

    @Override
    public void onBindViewHolder(AddDetailsHolder holder, int position) {
        holder.textView_title.setText(todoList.get(position).getTitle());
        holder.textView_description.setText(todoList.get(position).getDescription());
        holder.textView_date.setText(todoList.get(position).getDate());
        holder.textView_status.setText(todoList.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }


    public class AddDetailsHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView textView_title, textView_description, textView_date, textView_status;

        public AddDetailsHolder(View itemView) {
            super(itemView);
            textView_date = (TextView) itemView.findViewById(R.id.textView_date);
            textView_description = (TextView) itemView.findViewById(R.id.textView_description);
            textView_status = (TextView) itemView.findViewById(R.id.textView_status);
            textView_title = (TextView) itemView.findViewById(R.id.textView_title);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                   int position=getAdapterPosition();
                    Toast.makeText(context,"Selected Position="+position,Toast.LENGTH_LONG).show();
                    return  false;
                }
            });

        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select the option");
            menu.add(0,1,0,"Update");
            menu.add(0,2,1,"Completed");
        }


    }



}


