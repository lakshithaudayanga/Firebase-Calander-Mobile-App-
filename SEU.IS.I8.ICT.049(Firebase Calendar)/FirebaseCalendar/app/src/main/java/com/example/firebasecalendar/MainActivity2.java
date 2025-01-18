package com.example.firebasecalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private TextView textView;
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textView = findViewById(R.id.textViewid);
        databaseReference = FirebaseDatabase.getInstance().getReference("Calendar");

        // Retrieve the selected date passed from MainActivity
        String selectedDate = getIntent().getStringExtra("selectedDate");

        if (selectedDate != null) {
            fetchEventData(selectedDate);
        }
    }

    private void fetchEventData(String selectedDate) {
        databaseReference.child(selectedDate).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String eventData = snapshot.getValue(String.class);
                    textView.setText(eventData);
                } else {
                    textView.setText("No event found for this date");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MainActivity2", "Failed to fetch event data", error.toException());
            }
        });
    }
}
