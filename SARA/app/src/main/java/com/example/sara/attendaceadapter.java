package com.example.sara;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class attendaceadapter extends RecyclerView.Adapter<attendaceadapter.ViewHolder>{
    private ArrayList<all_attendance> aarraylist;
    private Context context;

    // constructor
    public attendaceadapter(ArrayList<all_attendance> ModalArrayList, Context context) {
        this.aarraylist = ModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_attendance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        all_attendance modal = aarraylist.get(position);
        String p=modal.getpresent().trim();
        String dep=modal.getDepartment().trim();
        String year=modal.getyear().trim();
        String no=modal.getrollno().trim();
        String sub=modal.getsubject().trim();
        DatabaseReference rootnode1= FirebaseDatabase.getInstance()
                .getReference("students/"+dep+"/"+year+"/"+no);
        rootnode1.child("Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String value = dataSnapshot.getValue(String.class);
                    holder.name.setText(value);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        DatabaseReference rootnode2= FirebaseDatabase.getInstance()
                .getReference("attendance/"+dep+"/"+year+"/"+sub+"/total");
        rootnode2.child("total").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Integer value2 = dataSnapshot.getValue(Integer.class);
                    int a = value2 - Integer.parseInt(p);
                    holder.absent.setText(String.valueOf(a));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        holder.rollno.setText(modal.getrollno());
        holder.present.setText(modal.getpresent());
    }

    @Override
    public int getItemCount() {
        return aarraylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView rollno, name, present,absent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rollno = itemView.findViewById(R.id.idrollno);
            name = itemView.findViewById(R.id.idName);
            present = itemView.findViewById(R.id.idpresent);
            absent = itemView.findViewById(R.id.idabsent);
        }
    }
}

