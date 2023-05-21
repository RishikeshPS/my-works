package com.example.sara;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class adapter extends RecyclerView.Adapter<adapter.ViewHolder>{
    private ArrayList<view_allstudent> sarraylist;
    private Context context;

    // constructor
    public adapter(ArrayList<view_allstudent> ModalArrayList, Context context) {
        this.sarraylist = ModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mark, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        view_allstudent modal = sarraylist.get(position);
        holder.name.setText(modal.getName());
        holder.rollno.setText(modal.getRollno());
        holder.status.setText(modal.getRollno());
        holder.status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(holder.status.isChecked()){
                    String rollno=holder.status.getText().toString();
                    HashMap<String , String> userMap = new HashMap<>();
                    userMap.put("rollno" ,rollno );
                    userMap.put("status" , "present");
                    DatabaseReference rootnode= FirebaseDatabase.getInstance().getReference("status");
                    rootnode.child(rollno).setValue(userMap);
                }
                if(holder.status.isChecked()==false){
                    String rollno=holder.status.getText().toString();
                    HashMap<String , String> userMap = new HashMap<>();
                    userMap.put("rollno" ,rollno );
                    userMap.put("status" , "absent");
                    DatabaseReference rootnode= FirebaseDatabase.getInstance().getReference("status");
                    rootnode.child(rollno).setValue(userMap);
                }
            }
        });
        String rollno=holder.status.getText().toString();
        HashMap<String , String> userMap = new HashMap<>();
        userMap.put("rollno" ,rollno );
        userMap.put("status" , "absent");
        DatabaseReference rootnode= FirebaseDatabase.getInstance().getReference("status");
        rootnode.child(rollno).setValue(userMap);
    }

    @Override
    public int getItemCount() {
        return sarraylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name,rollno;
        private CheckBox status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.idName);
            rollno = itemView.findViewById(R.id.idroll);
            status = itemView.findViewById(R.id.idcheck);
        }
    }
}


