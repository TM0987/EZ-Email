package com.example.easyemail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {

    TextView catTitle;
    TextView templates;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference categoryRef = mRootRef.child("categories");
    ArrayList<String> cats = new ArrayList<>();
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        setTitle("EZ Email");



        catTitle = findViewById(R.id.txtCatTitle);
        templates = findViewById(R.id.txtTemplate1);

        String[] categories = getResources().getStringArray(R.array.categories);
        Intent intent = getIntent();
        position = intent.getIntExtra("pos",0);
        catTitle.setText("Email templates for requesting "+categories[position]);

    }

    @Override
    protected void onStart() {
        super.onStart();

        categoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot categorySnapshot : snapshot.getChildren()) {
                        if (cats.contains(categorySnapshot.getKey()) == false) {
                            cats.add(categorySnapshot.getKey());
                        }
                    }
                    String currentCategory = cats.get(position);
                    String template1 = snapshot.child(currentCategory).child("template1").getValue(String.class);
                    String template2 = snapshot.child(currentCategory).child("template2").getValue(String.class);
                    templates.setText("Template 1:\n" + template1 + "\n\n\nTemplate 2:\n" + template2);
                }

                @Override
                public void onCancelled (@NonNull DatabaseError error){

                }
        });

    }
}