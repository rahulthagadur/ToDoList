package com.example.thagadur.todolist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Thagadur on 9/14/2017.
 */

public class AddDetailsCustomAdapter extends RecyclerView.Adapter<AddDetailsCustomAdapter.AddDetailsHolder> {
    Context context;

    public AddDetailsCustomAdapter(Context context) {
        this.context=context;
    }

    @Override
    public AddDetailsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.add_details_dialog,parent,false);
        AddDetailsHolder myView=new AddDetailsHolder(view);
        return myView;
    }

    @Override
    public void onBindViewHolder(AddDetailsHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 1;
    }


    public class AddDetailsHolder extends RecyclerView.ViewHolder{

        public AddDetailsHolder(View itemView) {
            super(itemView);
        }
    }
}
