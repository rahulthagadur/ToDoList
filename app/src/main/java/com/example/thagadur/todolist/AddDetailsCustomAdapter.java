package com.example.thagadur.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thagadur.todolist.database.DBHelper;
import com.example.thagadur.todolist.model.ToDoData;
import com.example.thagadur.todolist.utils.CommonUtilities;
import com.example.thagadur.todolist.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.example.thagadur.todolist.MainActivity.completedList;

/**
 * Created by Thagadur on 9/14/2017.
 */

public class AddDetailsCustomAdapter extends RecyclerView.Adapter<AddDetailsCustomAdapter.AddDetailsHolder> {
    Context context;
    ArrayList<ToDoData> toDoDatas;
    static int position;
    DBHelper dbHelper;
    List<ToDoData> todoList = new ArrayList<>();

    public AddDetailsCustomAdapter(Context context, List<ToDoData> todoList) {
        dbHelper = DBHelper.getInstance(context);
        dbHelper = CommonUtilities.getObject(context);
        this.context = context;
        this.todoList = todoList;
    }

    @Override
    public AddDetailsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_design, parent, false);
        AddDetailsHolder myView = new AddDetailsHolder(view);
        return myView;
    }

    @Override
    public void onBindViewHolder(AddDetailsHolder holder, int position) {


        holder.display_title_date.setText(todoList.get(position).getDate());
        holder.textView_title.setText(todoList.get(position).getTitle());
        holder.textView_description.setText(todoList.get(position).getDescription());
        holder.textView_date.setText(todoList.get(position).getDate());
        //holder.textView_status.setText(todoList.get(position).getId());

    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }


    public class AddDetailsHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView textView_title, textView_description, textView_date, textView_status, display_title_date;
        ImageView thumb_up;

        public AddDetailsHolder(View itemView) {
            super(itemView);

            display_title_date=(TextView)itemView.findViewById(R.id.date_display);
            textView_date = (TextView) itemView.findViewById(R.id.textView_date);
            textView_description = (TextView) itemView.findViewById(R.id.textView_description);
            //textView_status = (TextView) itemView.findViewById(R.id.textView_status);
            textView_title = (TextView) itemView.findViewById(R.id.textView_title);
            thumb_up=(ImageView)itemView.findViewById(R.id.thumb_completed);


            /*//
            // ----------------------
            // Thumb Implementation Left
            thumb_up.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    position = getAdapterPosition();
                    //Toast.makeText(context, "current ="+position, Toast.LENGTH_SHORT).show();
                    String pos=Integer.toString(position);
                    Toast.makeText(context, ""+pos, Toast.LENGTH_SHORT).show();
                    ContentValues val=new ContentValues();
                    val.put(Constants.KEY_STATUS,"1");
                    String where = "id=?";
                    int i=dbHelper.update(Constants.TO_DO_LIST,val,where,new String[]{""+pos});
                    Toast.makeText(context, "updated value="+i, Toast.LENGTH_SHORT).show();
                    MainActivity.getInstance().readAllData();
                    return true;
                }
            });*/
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    position = getAdapterPosition();

                    /*UpdateDetailsDialog updateDetailsDialog=new UpdateDetailsDialog(context);
                    updateDetailsDialog.show();*/
                    Toast.makeText(context, "Selected Position=" + position, Toast.LENGTH_LONG).show();
                    return false;
                }
            });
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select the option");
            menu.add(0, 1, 0, "Update");
            menu.add(0, 2, 1, "Completed");
        }
    }
}
