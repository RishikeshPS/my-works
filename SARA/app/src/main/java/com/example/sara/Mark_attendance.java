package com.example.sara;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Mark_attendance extends AppCompatActivity {
Button take_attendance;
Spinner dep,sem,sub;
String department,semester,sub1;
String[] semesterlist={"S1","S2","S3","S4","S5","S6"};
String[] departmentlist={"CT","CABM","EL","BM"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);
        take_attendance=findViewById(R.id.btnmark);
        Bundle q = getIntent().getExtras();
        String a = q.getString("a");
        if(a.equals("attendance")){
            take_attendance.setText("View");
        }
        dep=findViewById(R.id.dep);
        ArrayAdapter<String> d = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departmentlist);
        d.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dep.setAdapter(d);
        sem=findViewById(R.id.sem);
        ArrayAdapter<String> s = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, semesterlist);
        s.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sem.setAdapter(s);
        sub=findViewById(R.id.hour);
        dep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int i, long l) {
             read();
             sublist();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        sem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
read();
sublist();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sub.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

read();

                Bundle b=new Bundle();
                take_attendance.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        read();
                        sub1 = sub.getSelectedItem().toString();
                        try {
                            if (a.equals("attendance")) {
                                b.putString("department", department);
                                b.putString("semester", semester);
                                b.putString("subject", sub1);
                                Intent p = new Intent(getApplicationContext(), attendancedep.class);
                                p.putExtras(b);
                                startActivity(p);
                            } else if (a.equals("mark")) {
                                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                                rootRef.child("status").removeValue();
                                sub1 = sub.getSelectedItem().toString();
                                b.putString("department", department);
                                b.putString("semester", semester);
                                b.putString("subject", sub1);
                                Intent p = new Intent(getApplicationContext(), attendance_mark.class);
                                p.putExtras(b);
                                startActivity(p);
                            }
                        } catch (Exception e) {
                        }
                    }
                });


    }
    public void read(){
        department=dep.getSelectedItem().toString();
        semester=sem.getSelectedItem().toString();
    }
    public void sublist() {
        DatabaseReference rootnode;
        rootnode = FirebaseDatabase.getInstance().getReference("subjects/" + department + "/" + semester);
        rootnode.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> a = new ArrayList<String>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String sub = ds.child("subject").getValue(String.class);
                    a.add(sub);
                }
                ArrayAdapter<String> sub2 = new ArrayAdapter<>(Mark_attendance.this, android.R.layout.simple_spinner_item, a);
                sub2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sub.setAdapter(sub2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Mark_attendance.this, "cancelled", Toast.LENGTH_SHORT).show();
            }
        });
    }
}