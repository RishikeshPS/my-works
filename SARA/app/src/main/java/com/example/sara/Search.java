package com.example.sara;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Search extends AppCompatActivity {
Button search;
Spinner dep,sem;
    String[] semesterlist={"S1","S2","S3","S4","S5","S6"};
    String[] departmentlist={"CT","CABM","EL","BM"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        search=findViewById(R.id.search);
        dep=findViewById(R.id.dep);

        dep=findViewById(R.id.dep);
        ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departmentlist);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dep.setAdapter(ad);

        sem=findViewById(R.id.sem);
        ArrayAdapter<String> ad1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, semesterlist);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sem.setAdapter(ad1);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String department=dep.getSelectedItem().toString();
                String semester=sem.getSelectedItem().toString();
                Bundle b=new Bundle();
                    b.putString("a","student");
                    b.putString("department",department);
                    b.putString("semester",semester);
                    Intent p=new Intent(getApplicationContext(), ViewDetails.class);
                    p.putExtras(b);
                    startActivity(p);

            }
        });
    }
}