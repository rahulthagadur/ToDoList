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

/**
 * Created by Thagadur on 9/20/2017.
 */

public class UpdateDetailsCustomAdapter extends RecyclerView.Adapter<UpdateDetailsCustomAdapter.UpdateDetailsHolder> {
    Context context;
    ArrayList<ToDoData> toDoDatas;
    static int position;
    List<ToDoData> todoList = new ArrayList<>();

    /**
     * Constructor to initialise the values
     *
     * @param context
     * @param todoList
     */
    public UpdateDetailsCustomAdapter(Context context, List<ToDoData> todoList) {
        this.context = context;
        this.todoList = todoList;
    }

    /**
     * onCreateViewHolder method to inflate the layout of particular row data
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public UpdateDetailsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_design, parent, false);
        UpdateDetailsHolder myView = new UpdateDetailsHolder(view);
        return myView;
    }

    /**
     * onBindViewHolder --Here we are going to bind the data of the row to the custom adapter of the
     * RecylervView list
     *
     * @param holder--holds     the row data
     * @param position-position of the current row
     */
    @Override
    public void onBindViewHolder(UpdateDetailsHolder holder, int position) {
        holder.display_title_date.setText(todoList.get(position).getDate());
        holder.textView_title.setText(todoList.get(position).getTitle());
        holder.textView_description.setText(todoList.get(position).getDescription());
        holder.textView_date.setText(todoList.get(position).getDate());
    }

    /**
     * returns the count of the todolist
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return todoList.size();
    }

    /**
     * Initialisation of the layout items and calling the OnclickListner items of it
     * to perform specific operation on click of the event
     */

    public class UpdateDetailsHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView textView_title, textView_description, textView_date, textView_status, display_title_date;

        public UpdateDetailsHolder(View itemView) {
            super(itemView);
            display_title_date = (TextView) itemView.findViewById(R.id.date_display);
            textView_date = (TextView) itemView.findViewById(R.id.textView_date);
            textView_description = (TextView) itemView.findViewById(R.id.textView_description);
            textView_title = (TextView) itemView.findViewById(R.id.textView_title);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    position = getAdapterPosition();
                    return false;
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select the option");
            menu.add(0, 1, 0, "Delete");
        }
    }
}
