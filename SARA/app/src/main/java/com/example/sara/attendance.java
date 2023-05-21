package com.example.sara;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class attendance extends AppCompatActivity {
TextView present,absent,persentage,t;
int a,total;
String p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        present=findViewById(R.id.idpresent);
        absent=findViewById(R.id.idabsent);
        persentage=findViewById(R.id.idp);
        t=findViewById(R.id.idtotal);
        Bundle b=getIntent().getExtras();
        String dep=b.getString("department");
        String year=b.getString("year");
        String rollno=b.getString("rollno");
        String sub=b.getString("subject");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("attendance/"+dep+"/"+year+"/"+sub);
        databaseReference.child("attendance").child(rollno).child("present")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            p = dataSnapshot.getValue(String.class);
                            databaseReference.child("total").child("total")
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                total = dataSnapshot.getValue(Integer.class);
                                                a=total-Integer.parseInt(p);
                                                float s=(float)((Integer.parseInt(p))/total)*100;
                                                present.setText(String.valueOf(p));
                                                absent.setText(String.valueOf(a));
                                                persentage.setText(s+"%");
                                                t.setText(String.valueOf(total));

                                            }
                                        }
                                        @Override
                                        public void onCancelled(DatabaseError error) {
                                        }});
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                    }});

    }
}