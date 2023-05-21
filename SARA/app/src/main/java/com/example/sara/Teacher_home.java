package com.example.sara;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Teacher_home extends AppCompatActivity {
    Button add_student,view_student,mark_attendance,
            view_attendance,logout;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);

        add_student=findViewById(R.id.bnadstudent);
        view_student=findViewById(R.id.bnvstudent);
        view_attendance=findViewById(R.id.bnvattendence);
        mark_attendance=findViewById(R.id.btnmark);
        logout=findViewById(R.id.bnlogout);
        Bundle g=getIntent().getExtras();
        username=g.getString("username");
        Bundle b=new Bundle();
        add_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p=new Intent(getApplicationContext(),add_student.class);
                p.putExtra("a","add");
                startActivity(p);
            }
        });
        view_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.putString("a","student");
                Intent p=new Intent(getApplicationContext(),Search.class);
                p.putExtras(b);
                startActivity(p);
            }
        });
        view_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.putString("a","attendance");
                Intent p=new Intent(getApplicationContext(),Mark_attendance.class);
                p.putExtras(b);
                startActivity(p);
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
        mark_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.putString("a","mark");
                Intent p=new Intent(getApplicationContext(),Mark_attendance.class);
                p.putExtras(b);
                startActivity(p);
            }
        });

    }
}