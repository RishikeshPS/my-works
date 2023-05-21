package com.example.sara;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
EditText username,password;
Spinner tloger;
    Button login;
    String[] lname = { "Admin", "Teacher","Student"};
String loger;
ImageView show;
    DatabaseReference rootnode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username=findViewById(R.id.tuser);
        password=findViewById(R.id.tpass);
        login=findViewById(R.id.btln);
         tloger = findViewById(R.id.tloger);
        tloger.setOnItemSelectedListener(this);
        show=findViewById(R.id.showpass);
        ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lname);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tloger.setAdapter(ad);
        Bundle bundle=new Bundle();
        login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(loger.equals("Admin")) {
            String uname=username.getText().toString();
            String pass=password.getText().toString();
            DatabaseReference rootnode1;
            rootnode1=FirebaseDatabase.getInstance().getReference("users/admin/"+uname);
            rootnode1.child("password").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                    String value = dataSnapshot.getValue(String.class);
                    if(value.equals(pass)) {
                       Toast.makeText(MainActivity.this, "login successfull", Toast.LENGTH_SHORT).show();
                        bundle.putString("username", uname);
                        bundle.putString("password", pass);
                        Intent l = new Intent(getApplicationContext(), adminhome.class);
                        l.putExtras(bundle);
                        startActivity(l);
                        finish();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "password error", Toast.LENGTH_SHORT).show();
                    }
                }
                    else{
                        Toast.makeText(MainActivity.this, "Enter valid username", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else if(loger.equals("Teacher")){
            String uname=username.getText().toString().trim();
            String pass=password.getText().toString().trim();
            rootnode=FirebaseDatabase.getInstance().getReference("users/teachers/"+uname+"/password");

            // Read from the database
            rootnode.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        String value = dataSnapshot.getValue(String.class);
                        if (value.equals(pass)) {
                            Toast.makeText(MainActivity.this, "login successfull", Toast.LENGTH_SHORT).show();
                            bundle.putString("username", uname);
                            bundle.putString("password", pass);
                            finish();
                            Intent l = new Intent(getApplicationContext(), Teacher_home.class);
                            l.putExtras(bundle);
                            startActivity(l);
                        } else {
                            Toast.makeText(MainActivity.this, "password error", Toast.LENGTH_SHORT).show();
                        }
                    }
                        else{
                        Toast.makeText(MainActivity.this, "Enter valid username", Toast.LENGTH_SHORT).show();
                        }
                    }
                @Override
                public void onCancelled(@NonNull DatabaseError error){

                }
            });
        }else if(loger=="Student"){
            String uname=username.getText().toString();
            String pass=password.getText().toString();
            rootnode=FirebaseDatabase.getInstance().getReference("users/students/"+uname+"/password");

            // Read from the database
            rootnode.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        String value = dataSnapshot.getValue(String.class);
                        if (value.equals(pass)) {
                            Toast.makeText(MainActivity.this, "login successfull", Toast.LENGTH_SHORT).show();
                            bundle.putString("username", uname);
                            bundle.putString("password", pass);
                            Intent l = new Intent(getApplicationContext(), Student_home.class);
                            l.putExtras(bundle);
                            startActivity(l);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "password error", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Enter valid username", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        else{
            Toast.makeText(MainActivity.this, "select loger", Toast.LENGTH_SHORT).show();
        }
    }
});

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View arg1, int posision, long id) {
    loger=parent.getItemAtPosition(posision).toString();
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public void hideshow(View view){
        if(show.getId()== R.id.showpass){

            if(password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                show.setImageResource(R.drawable.hide);

                //Show Password
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                show.setImageResource(R.drawable.show);

                //Hide Password
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }
}