package com.example.sara;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class teacheradapter extends RecyclerView.Adapter<teacheradapter.ViewHolder>{
    private ArrayList<view_all> tarraylist;
    private Context context;

    // constructor
    public teacheradapter(ArrayList<view_all> ModalArrayList, Context context) {
        this.tarraylist = ModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        view_all modal = tarraylist.get(position);
        holder.tname.setText(modal.getName());
        holder.tdep.setText(modal.getDepartment());
        holder.tid.setText(modal.getId());
        holder.tphoneno.setText(modal.getPhoneno());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Add_teacher.class);
                i.putExtra("a", "update");
                i.putExtra("name", modal.getName());
                i.putExtra("department", modal.getDepartment());
                i.putExtra("id", modal.getId());
                i.putExtra("phoneno", modal.getPhoneno());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return tarraylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView tname, tdep, tid, tphoneno;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views

            tname = itemView.findViewById(R.id.idName);
            tdep = itemView.findViewById(R.id.iddep);
            tid = itemView.findViewById(R.id.idid);
            tphoneno = itemView.findViewById(R.id.idphone);
        }
    }
}
