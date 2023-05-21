package com.example.sara;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class adminhome extends AppCompatActivity {
Button add_teacher,add_student,view_teacher,view_student,
        view_attendance,logout,add_sub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhome);
        add_teacher=findViewById(R.id.bnadteacher);
        add_student=findViewById(R.id.bnadstudent);
        view_teacher=findViewById(R.id.bnvteacher);
        view_student=findViewById(R.id.bnvstudent);
        view_attendance=findViewById(R.id.bnvattendence);
        add_sub=findViewById(R.id.bnaddsub);
        logout=findViewById(R.id.bnlogout);
        Bundle b=new Bundle();
        add_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p=new Intent(getApplicationContext(),Add_teacher.class);
                p.putExtra("a","add");
                startActivity(p);
            }
        });
        add_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p=new Intent(getApplicationContext(),add_student.class);
                p.putExtra("a","add");
                startActivity(p);
            }
        });
        view_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.putString("a","teacher");
                Intent p=new Intent(getApplicationContext(), ViewDetails.class);
                p.putExtras(b);
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
add_sub.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent p=new Intent(getApplicationContext(),addsubject.class);
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
    }
}