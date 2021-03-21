package com.example.easyemail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button eContinue;
    TextView tips;
    String[] categories;
    int pos;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("EZ Email");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        tips = findViewById(R.id.txtTips);


        spinner.setOnItemSelectedListener(this);
        int initialSelectedPosition=spinner.getSelectedItemPosition();
        spinner.setSelection(initialSelectedPosition, false); //clear selection

        categories = getResources().getStringArray(R.array.categories);
        eContinue = findViewById(R.id.btnContinueUnfixed);

        eContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DisplayActivity.class);
                intent.putExtra("pos",pos);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String tipText = snapshot.child("tips").getValue(String.class);
                tips.setText(tipText);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        eContinue.setText("Click to view templates/info about " + categories[position] + " emails.");
        pos = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

