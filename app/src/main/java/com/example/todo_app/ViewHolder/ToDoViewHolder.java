package com.example.todo_app.ViewHolder;

import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo_app.R;

public class ToDoViewHolder  extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

    public TextView text_task,text_status;
    public ToDoViewHolder(@NonNull View itemView) {
        super(itemView);

        text_task = itemView.findViewById(R.id.text_task);
        text_status = itemView.findViewById(R.id.text_status);

        itemView.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("select menu");
        menu.add(0,0,getAdapterPosition(),"update");
        menu.add(0,0,getAdapterPosition(),"delete");
    }
}
