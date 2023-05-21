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

public class studentadapter extends RecyclerView.Adapter<studentadapter.ViewHolder>{
    private ArrayList<view_allstudent> sarraylist;
    private Context context;

    // constructor
    public studentadapter(ArrayList<view_allstudent> ModalArrayList, Context context) {
        this.sarraylist = ModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.all_view_teacher, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        view_allstudent modal = sarraylist.get(position);
        holder.sname.setText(modal.getName());
        holder.sdep.setText(modal.getDepartment());
        holder.ssem.setText(modal.getSemester());
        holder.srollno.setText(modal.getRollno());
        holder.sphoneno.setText(modal.getPhoneno());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, add_student.class);
                i.putExtra("a", "update");
                i.putExtra("name", modal.getName());
                i.putExtra("department", modal.getDepartment());
                i.putExtra("semester", modal.getSemester());
                i.putExtra("rollno", modal.getRollno());
                i.putExtra("phoneno", modal.getPhoneno());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return sarraylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView sname, sdep, ssem,srollno, sphoneno;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views

            sname = itemView.findViewById(R.id.idName);
            sdep = itemView.findViewById(R.id.iddep);
            ssem = itemView.findViewById(R.id.idsem);
            srollno = itemView.findViewById(R.id.idroll);
            sphoneno = itemView.findViewById(R.id.idphone);
        }
    }
}

