package com.example.sara;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class Student_home extends AppCompatActivity {
Button attendance,profile,logout;
    Bundle b=new Bundle();
  private   String username,sno,sname,sdep,sem,phoneno;
   String year="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        attendance=findViewById(R.id.bnvattendence);
        profile=findViewById(R.id.bnprofile);
        logout=findViewById(R.id.bnlogout);
        Bundle s=getIntent().getExtras();
        String uname=s.getString("username");
        String pass=s.getString("password");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("users/students");
        databaseReference.child(uname.trim()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    year = dataSnapshot.child("year").getValue(String.class);
                sdep = dataSnapshot.child("dep").getValue(String.class);
                sno = dataSnapshot.child("rollno").getValue(String.class);
                DatabaseReference data = FirebaseDatabase.getInstance()
                        .getReference("students/"+sdep.trim()+"/"+year.trim()+"/"+sno.trim());
                data.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        sname = dataSnapshot.child("Name").getValue(String.class);
                        sem = dataSnapshot.child("Semester").getValue(String.class);
                        phoneno = dataSnapshot.child("Phoneno").getValue(String.class);
                        profile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Bundle s=new Bundle();
                                s.putString("phno",phoneno);
                                s.putString("name",sname);
                                s.putString("rollno",sno);
                                s.putString("dep",sdep);
                                s.putString("sem",sem);
                                Intent p=new Intent(getApplicationContext(),profile.class);
                                p.putExtras(s);
                                startActivity(p);
                            }
                        });

                        attendance.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseReference rootnode;
                                rootnode= FirebaseDatabase.getInstance().getReference("subjects/"+sdep+"/"+sem);
                                rootnode.addValueEventListener(new  ValueEventListener() {
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        List<String> a=new ArrayList<String>();
                                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                                            String sub = ds.child("subject").getValue(String.class);
                                            a.add(sub);
                                        }
                                        Spinner sub1=new Spinner(Student_home.this);
                                        ArrayAdapter<String> ad2 = new ArrayAdapter<>(Student_home.this, android.R.layout.simple_spinner_item, a);
                                        ad2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        sub1.setAdapter(ad2);
                                        AlertDialog.Builder builder=new AlertDialog.Builder(Student_home.this);
                                        builder.setCancelable(true);
                                        builder.setMessage("Select subject");
                                        builder.setView(sub1);
                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                b.putString("department",sdep);
                                                b.putString("year",year);
                                                b.putString("rollno",sno);
                                                b.putString("subject",sub1.getSelectedItem().toString());
                                                Intent p=new Intent(getApplicationContext(),attendance.class);
                                                p.putExtras(b);
                                                startActivity(p);
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
                                    @Override
                                    public void onCancelled(DatabaseError error) {
                                        Toast.makeText(Student_home.this, "cancelled", Toast.LENGTH_SHORT).show();
                                    }});

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent p=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(p);
            }
        });
    }


                }