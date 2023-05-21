package com.example.sara;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class attendancedep extends AppCompatActivity {
    String year = "";
    Button delete;
    private RecyclerView recyclerView;
    private FirebaseDatabase db=FirebaseDatabase.getInstance();
    private ArrayList<all_attendance>list;
    private attendaceadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendancedep);
        LinearLayout l1=findViewById(R.id.l1);
        delete = findViewById(R.id.iddelete);
        recyclerView = findViewById(R.id.idview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Bundle b = getIntent().getExtras();
        String dep = b.getString("department");
        String sem = b.getString("semester");
        String sub = b.getString("subject");
        if (sem.equals("S1") || sem.equals("S2")) {
            year = "y1";
        }
        if (sem.equals("S3") || sem.equals("S4")) {
            year = "y2";
        }
        if (sem.equals("S5") || sem.equals("S6")) {
            year = "y3";
        }

        DatabaseReference root=db.getReference("attendance/"+dep+"/"+year+"/"+sub+"/attendance");
        list = new ArrayList<>();
        adapter = new attendaceadapter(list ,this);
        recyclerView.setAdapter(adapter);
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
if(snapshot.exists()) {
    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
        all_attendance model = dataSnapshot.getValue(all_attendance.class);
        list.add(model);
    }
    adapter.notifyDataSetChanged();
}
else
    Toast.makeText(attendancedep.this, "no data found", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
delete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        DatabaseReference root=db.getReference("attendance/"+dep+"/"+year);
        root.child(sub).removeValue();
        finish();
    }
});
            }
    }
