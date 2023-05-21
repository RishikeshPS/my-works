package com.example.sara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class addsubject extends AppCompatActivity {
Button save;
EditText sub;
ListView sublist;
Spinner dep,sem;
String department,semester,subject;
    String[] semesterlist={"---select---","S1","S2","S3","S4","S5","S6"};
    String[] departmentlist={"---select---","CT","CABM","EL","BM"};
    DatabaseReference rootnode;
    ArrayAdapter<String> adapter;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsubject);
       dep= findViewById(R.id.department);
        ArrayAdapter<String> d = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departmentlist);
        d.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dep.setAdapter(d);
        sem= findViewById(R.id.semester);
        ArrayAdapter<String> s = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, semesterlist);
        s.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sem.setAdapter(s);
        sub= findViewById(R.id.subject);
        save=findViewById(R.id.save);
        sublist=findViewById(R.id.sublist);
        list=new ArrayList<String>();
         adapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,list);
         sublist.setAdapter(adapter);
         sublist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
             @Override
             public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                 subject=String.valueOf(adapterView.getItemAtPosition(i));
                 AlertDialog.Builder builder=new AlertDialog.Builder(addsubject.this);
                 builder.setCancelable(true);
                 builder.setTitle("Alert");
                 builder.setMessage("Do you want to delete "+subject);
                 builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {
                         DatabaseReference root=FirebaseDatabase.getInstance().getReference("subjects/"+department+"/"+semester);
                         root.child(subject).removeValue();
                         list.remove(subject);
                     }
                 });
                 builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {
                     }
                 });
                 AlertDialog dialog=builder.create();
                 dialog.show();
                 return true;
             }
         });
        dep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sublistview();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sublistview();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                read();
                subject = sub.getText().toString().toUpperCase();
                if(dep.equals("") || sem.equals("") || sub.equals("")){
                    Toast.makeText(addsubject.this, "Fill all the field", Toast.LENGTH_SHORT).show();
                }
                else{
                    rootnode= FirebaseDatabase.getInstance().getReference("subjects/"+department+"/"+semester);
                    HashMap<String , String> Map = new HashMap<>();
                    Map.put("subject" , subject);
                    rootnode.child(subject).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Toast.makeText(addsubject.this, "subject allready exists", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                rootnode.child(subject).setValue(Map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(addsubject.this, "Data Saved", Toast.LENGTH_SHORT).show();
                                        sub.setText("");
                                        sublistview();
                                    }
                                });
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError error) {
                            Toast.makeText(addsubject.this, "cancelled", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

    }
    public void sublistview(){
        read();
        list.clear();
        adapter.notifyDataSetChanged();
        rootnode= FirebaseDatabase.getInstance().getReference("subjects/"+department+"/"+semester);
        rootnode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String sub3 = dataSnapshot.getKey();
                        list.add(sub3);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        sublist.setAdapter(adapter);
    }
    public void read(){
        department = dep.getSelectedItem().toString();
        semester = sem.getSelectedItem().toString();
    }

}