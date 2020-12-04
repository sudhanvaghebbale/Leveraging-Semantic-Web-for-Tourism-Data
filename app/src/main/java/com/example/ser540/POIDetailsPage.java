package com.example.ser540;

import android.content.Intent;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class POIDetailsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi_details);
        String ID = getIntent().getStringExtra("id");

        TextView textView = findViewById(R.id.detailPageTextView);
        textView.setText("Put some random text here \n and then check if it is \n working still");

        if(ID.startsWith("station")) {

        }
        else if (ID.startsWith("pub")) {

        }
        else {

        }
    }


}
