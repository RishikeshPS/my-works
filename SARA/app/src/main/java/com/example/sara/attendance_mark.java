package com.example.sara;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class attendance_mark extends AppCompatActivity {
     int a =0,t=0;
    Button save;
    String year,sem,dep,sub;
    private ArrayList<view_allstudent>list;
    private adapter adapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_mark);
        LinearLayout l1=findViewById(R.id.l1);
        save = findViewById(R.id.idsave);
        recyclerView = findViewById(R.id.idviewdetails);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Bundle b = getIntent().getExtras();
         dep = b.getString("department");
         sem = b.getString("semester");
        sub = b.getString("subject");

        if (sem.equals("S1") || sem.equals("S2")) {
            year = "y1";
        }
        if (sem.equals("S3") || sem.equals("S4")) {
            year = "y2";
        }
        if (sem.equals("S5") || sem.equals("S6")) {
            year = "y3";
        }
        list = new ArrayList<>();
        adapter = new adapter(list ,this);
        recyclerView.setAdapter(adapter);
        DatabaseReference root1=FirebaseDatabase.getInstance().getReference("students/"+dep).child(year);
        root1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    view_allstudent model = dataSnapshot.getValue(view_allstudent.class);
                    list.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference chatSpaceRef = rootRef.child("status");
                chatSpaceRef.addListenerForSingleValueEvent(new  ValueEventListener() {
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            String no = ds.child("rollno").getValue(String.class);
                            String s = ds.child("status").getValue(String.class);
                            a=0;
                            String p="present";
try {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance()
            .getReference("attendance/"+dep+"/"+year+"/"+sub+"/attendance");
    databaseReference.child(no.trim()).child("present")
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                       String b = dataSnapshot.getValue(String.class);
                       a=Integer.parseInt(b);
                    }
                        if (s.equals(p)) {
                            a = a + 1;
                            HashMap<String, String> userMap1 = new HashMap<>();
                            userMap1.put("present", String.valueOf(a));
                            userMap1.put("department", dep);
                            userMap1.put("subject", sub);
                            userMap1.put("year", year);
                            userMap1.put("rollno", no);
                            DatabaseReference root = FirebaseDatabase.getInstance().getReference("attendance/" + dep + "/" + year + "/" + sub+"/attendance");
                            root.child(no).setValue(userMap1);
                        }
                        else{
                            HashMap<String, String> userMap1 = new HashMap<>();
                            userMap1.put("present", String.valueOf(a));
                            userMap1.put("department", dep);
                            userMap1.put("subject", sub);
                            userMap1.put("year", year);
                            userMap1.put("rollno", no);
                            DatabaseReference root = FirebaseDatabase.getInstance().getReference("attendance/" + dep + "/" + year + "/" + sub+"/attendance");
                            root.child(no).setValue(userMap1);
                        }
                    a = 0;
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(attendance_mark.this, "cancelled", Toast.LENGTH_SHORT).show();
                }
            });
} catch (Exception e) {
    Toast.makeText(attendance_mark.this, "error", Toast.LENGTH_SHORT).show();
}



                        }
                    }

                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                    });
//total
                DatabaseReference Reference = FirebaseDatabase.getInstance()
                        .getReference("attendance/"+dep+"/"+year+"/"+sub);
                Reference.child("total").child("total")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int total=0;
                                if (dataSnapshot.exists()) {
                                     total= dataSnapshot.getValue(Integer.class);
                                }
                                    total=total+1;
                                HashMap<String, Integer> userMap2 = new HashMap<>();
                                userMap2.put("total", total);
                                DatabaseReference root = FirebaseDatabase.getInstance().getReference("attendance/" + dep + "/" + year + "/" + sub);
                                root.child("total").setValue(userMap2);

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(attendance_mark.this, "cancelled", Toast.LENGTH_SHORT).show();
                            }
                        });

                Toast.makeText(attendance_mark.this, "Data saved", Toast.LENGTH_SHORT).show();
                finish();
                chatSpaceRef.child("status").removeValue();

            }
        });
    }
}