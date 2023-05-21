package com.example.sara;

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

public class Add_teacher extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
EditText name,id, phone,password;
TextView pass;
Spinner department;
String tchrname,tdep,tid,tphone,tpass,key;
    String[] departmentlist={"CT","CABM","EL","BM"};
Button save,clear;
    DatabaseReference rootnode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);
        name=findViewById(R.id.txtname);
        id=findViewById(R.id.txtid);
        phone =findViewById(R.id.txtphoneno);
        password=findViewById(R.id.txtpassword);
        pass=findViewById(R.id.view5);
        department=findViewById(R.id.txtdepartment);
        ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departmentlist);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        department.setAdapter(ad);
        save=findViewById(R.id.btnsave);
        clear=findViewById(R.id.btnclear);
        String a=getIntent().getStringExtra("a");
        if(a.equals("add")){
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    readvalues();
                    if (tchrname.equals("") || tid.equals("") || tphone.equals("") || tpass.equals("")) {
                        Toast.makeText(Add_teacher.this, "Fill all the field", Toast.LENGTH_SHORT).show();
                    } else if(tphone.length()<10 || tphone.length()>10){
                        Toast.makeText(Add_teacher.this, "enter valid phone number", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        rootnode=FirebaseDatabase.getInstance().getReference("teachers");
                        HashMap<String , String> userMap = new HashMap<>();
                        userMap.put("Name" , tchrname);
                        userMap.put("Department" , tdep);
                        userMap.put("Id" , tid);
                        userMap.put("Phoneno" , tphone);
                        rootnode.child(tid).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(Add_teacher.this, "Data Saved", Toast.LENGTH_SHORT).show();
                            }
                        });
                        users users = new users(tphone,tpass,tid);
                        rootnode= FirebaseDatabase.getInstance().getReference("users/teachers");
                        rootnode.child(tphone).setValue(users);
                            name.setText("");
                            id.setText("");
                            phone.setText("");
                            password.setText("");
                        }

                }
            });
            clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    name.setText("");
                    id.setText("");
                    phone.setText("");
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
            id.setText(getIntent().getStringExtra("id"));
            phone.setText(getIntent().getStringExtra("phoneno"));
            int pos=ad.getPosition(getIntent().getStringExtra("department"));
            department.setSelection(pos);
save.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
       readvalues();
        if(tphone.length()<10 || tphone.length()>10){
            Toast.makeText(Add_teacher.this, "enter valid phone number", Toast.LENGTH_SHORT).show();
        }
        else {
            HashMap<String, Object> userMap = new HashMap<>();
            userMap.put("Name", tchrname);
            userMap.put("Department", tdep);
            userMap.put("Id", getIntent().getStringExtra("id"));
            userMap.put("Phoneno", tphone);
            rootnode = FirebaseDatabase.getInstance().getReference("teachers");
            rootnode.child(getIntent().getStringExtra("id")).updateChildren(userMap);
            Toast.makeText(Add_teacher.this, " Updated..", Toast.LENGTH_SHORT).show();
            Add_teacher.this.finish();
        }
    }
});
clear.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        try {
            rootnode= FirebaseDatabase.getInstance().getReference("teachers");
            rootnode.child(id.getText().toString()).removeValue();
            Toast.makeText(Add_teacher.this, "Deleted", Toast.LENGTH_SHORT).show();
            Add_teacher.this.finish();
        } catch (Exception e) {

            Toast.makeText(Add_teacher.this, "Error", Toast.LENGTH_SHORT).show();
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
    public void readvalues(){
        tchrname =name.getText().toString();
        tid=id.getText().toString();
        tphone= phone.getText().toString();
        tdep=department.getSelectedItem().toString();
        tpass=password.getText().toString();
    }
}