package com.example.sara;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class profile extends AppCompatActivity {
String sname,srollno,sdep,ssem,sphone;
    TextView name,rollno,department,sem,ph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name=findViewById(R.id.idname);
        rollno=findViewById(R.id.idno);
        department=findViewById(R.id.iddep);
        sem=findViewById(R.id.idsem);
        ph=findViewById(R.id.idphno);
        Bundle g=getIntent().getExtras();
        sphone=g.getString("phno");
        sname=g.getString("name");
        srollno=g.getString("rollno");
        sdep=g.getString("dep");
        ssem=g.getString("sem");

        try {
            rollno.setText(": "+srollno);
            name.setText(": "+sname);
            department.setText(": "+sdep);
            sem.setText(": "+ssem);
            ph.setText(": "+sphone);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}