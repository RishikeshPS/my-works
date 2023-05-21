package com.example.sara;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class add_student extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
EditText name,rollno,phoneno,password;
TextView pass;
Spinner department,semester;
Button save,clear;
String dep,sem,sname,sroll,sphone,spass,key;
String[] semesterlist={"S1","S2","S3","S4","S5","S6"};
String[] departmentlist={"CT","CABM","EL","BM"};
    String year;
    DatabaseReference rootnode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        name=findViewById(R.id.txtsname);
        department=findViewById(R.id.txtsdepartment);
        ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departmentlist);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        department.setAdapter(ad);
        semester=findViewById(R.id.txtssemester);
        ArrayAdapter<String> ad2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, semesterlist);
        ad2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semester.setAdapter(ad2);
        rollno=findViewById(R.id.txtsrollno);
        phoneno=findViewById(R.id.txtsphoneno);
        password=findViewById(R.id.txtspassword);
        save=findViewById(R.id.btnssave);
        clear=findViewById(R.id.btnsclear);
        pass=findViewById(R.id.view5);
        String a=getIntent().getStringExtra("a");
        if(a.equals("add")) {
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                getvalues();
                    if (sname.equals("") || dep.equals("") || sem.equals("") || sroll.equals("") || sphone.equals("") || spass.equals("")) {
                        Toast.makeText(add_student.this, "Fill all field", Toast.LENGTH_SHORT).show();
                    } else if(sphone.length()<10 || sphone.length()>10){
                        Toast.makeText(add_student.this, "enter valid phone number", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        getyear();

                        rootnode=FirebaseDatabase.getInstance().getReference("students/"+dep+"/"+year);
                        HashMap<String , String> userMap = new HashMap<>();
                        userMap.put("Name" , sname);
                        userMap.put("Department" , dep);
                        userMap.put("Semester" , sem);
                        userMap.put("Rollno" , sroll);
                        userMap.put("Phoneno" , sphone);
                        rootnode.child(sroll).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(add_student.this, "Data Saved", Toast.LENGTH_SHORT).show();
                            }
                        });

                        studentuser users = new studentuser(sphone,spass,sroll,dep,year);
                        rootnode= FirebaseDatabase.getInstance().getReference("users/students");
                        rootnode.child(sphone).setValue(users);

                                name.setText("");
                                rollno.setText("");
                                phoneno.setText("");
                                password.setText("");

                    }
                    }

            });
            clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    name.setText("");
                    rollno.setText("");
                    phoneno.setText("");
                    password.setText("");
                }
            });
        }
        if(a.equals("update")){
            pass.setVisibility(View.INVISIBLE);
            password.setVisibility(View.INVISIBLE);
            save.setText("Update");
            clear.setText("Delete");
            name.setText(getIntent().getStringExtra("name"));
            int pos=ad.getPosition(getIntent().getStringExtra("department"));
            department.setSelection(pos);
            int pos2=ad2.getPosition(getIntent().getStringExtra("semester"));
            semester.setSelection(pos2);
            rollno.setText(getIntent().getStringExtra("rollno"));
            phoneno.setText(getIntent().getStringExtra("phoneno"));
key=getIntent().getStringExtra("rollno");
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getvalues();
                    if (sname.equals("") || dep.equals("") || sem.equals("") || sroll.equals("") || sphone.equals("")) {
                        Toast.makeText(add_student.this, "Fill all field", Toast.LENGTH_SHORT).show();
                    } else if(sphone.length()<10 || sphone.length()>10){
                        Toast.makeText(add_student.this, "enter valid phone number", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        HashMap<String , Object> userMap = new HashMap<>();
                        userMap.put("Name" , sname);
                        userMap.put("Department" , dep);
                        userMap.put("Semester" , sem);
                        userMap.put("Rollno" , sroll);
                        userMap.put("Phoneno" , sphone);
                        getyear();
                        rootnode= FirebaseDatabase.getInstance().getReference("students/"+dep+"/"+year);
                        rootnode.child(key).removeValue();
                        rootnode.child(sroll).updateChildren(userMap);
                        DatabaseReference data = FirebaseDatabase.getInstance().getReference("users/students");
                        data.child(getIntent().getStringExtra("phoneno").trim()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String currentpass = dataSnapshot.child("password").getValue(String.class);
                                studentuser users1 = new studentuser(sphone,currentpass.trim(),sroll,dep,year);
                                data.child(getIntent().getStringExtra("phoneno").trim()).removeValue();
                                data.child(sphone).setValue(users1);
                            }

                            public void onCancelled(DatabaseError databaseError) {}
                        });
                        Toast.makeText(add_student.this, " Updated..", Toast.LENGTH_SHORT).show();
                        add_student.this.finish();

                    }
                }
            });
            clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        dep = department.getSelectedItem().toString();
                        sem = semester.getSelectedItem().toString();
                        getyear();
                        rootnode= FirebaseDatabase.getInstance().getReference("students/"+dep+"/"+year);
                        rootnode.child(rollno.getText().toString()).removeValue();
                        Toast.makeText(add_student.this, "Deleted", Toast.LENGTH_SHORT).show();
                        add_student.this.finish();
                    } catch (Exception e) {
                        Toast.makeText(add_student.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public void getyear(){
        if (sem.equals("S1") || sem.equals("S2")) {
            year = "y1";
        }
        if (sem.equals("S3") || sem.equals("S4")) {
            year = "y2";
        }
        if (sem.equals("S5") || sem.equals("S6")) {
            year = "y3";
        }
    }
    public void getvalues(){
        dep = department.getSelectedItem().toString();
        sem = semester.getSelectedItem().toString();
        sname = name.getText().toString();
        sroll = rollno.getText().toString();
        sphone = phoneno.getText().toString();
        spass = password.getText().toString();
    }

}
