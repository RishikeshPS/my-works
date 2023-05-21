package com.example.sara;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class ViewDetails extends AppCompatActivity {
    private ArrayList<view_all>modalArrayList;
    private teacheradapter tadapter;
    Button delete;
    LinearLayout layout;
    String year,dep,sem,a;

    private RecyclerView recyclerView;
    private FirebaseDatabase db=FirebaseDatabase.getInstance();
   private studentadapter adapter;
    private ArrayList<view_allstudent>list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        Bundle b = getIntent().getExtras();
         a = b.getString("a");
        delete=findViewById(R.id.iddelete);
        layout=findViewById(R.id.idl1);

        recyclerView = findViewById(R.id.idviewdetails);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (a.equals("teacher")) {
           teacher();
        } else if (a.equals("student")) {
             dep = b.getString("department");
             sem = b.getString("semester");
            if (sem.equals("S1") || sem.equals("S2")) {
                year = "y1";
            }
            if (sem.equals("S3") || sem.equals("S4")) {
                year = "y2";
            }
            if (sem.equals("S5") || sem.equals("S6")) {
                year = "y3";
            }
           students();
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(ViewDetails.this);
                    builder.setCancelable(true);
                    builder.setTitle("Alert");
                    builder.setMessage("Are you sure to delete all details");
                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DatabaseReference root=db.getReference("teachers");
                            root.removeValue();
                            finish();
                        }
                    });
                    builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    AlertDialog dialog=builder.create();
                    dialog.show();
                }
            });

        }

       else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
       delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               AlertDialog.Builder builder=new AlertDialog.Builder(ViewDetails.this);
               builder.setCancelable(true);
               builder.setTitle("Alert");
               builder.setMessage("Are you sure to delete all details");
               builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       DatabaseReference root=db.getReference("students/"+dep);
                    root.child(year).removeValue();
                       finish();
                   }
               });
               builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                   }
               });
               AlertDialog dialog=builder.create();
               dialog.show();
           }
       });

    }
   public void onRestart(){
        super.onRestart();
        if (a.equals("teacher")) {
            teacher();
        }
        else if(a.equals("student")){
            students();
        }
    }
public void students(){
    DatabaseReference root=db.getReference("students/"+dep).child(year);

    list = new ArrayList<>();
    adapter = new studentadapter(list ,this);
    //recyclerView.setAdapter(null);
    recyclerView.setAdapter(adapter);

    root.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                view_allstudent model = dataSnapshot.getValue(view_allstudent.class);
                list.add(model);
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
}
public void teacher(){
    DatabaseReference root=db.getReference().child("teachers");
    modalArrayList = new ArrayList<>();
    tadapter = new teacheradapter(modalArrayList ,this);

    recyclerView.setAdapter(tadapter);

    root.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                view_all model = dataSnapshot.getValue(view_all.class);
                modalArrayList.add(model);
            }
            tadapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });

}
    }
