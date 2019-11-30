package com.example.bushoppingcartproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.NumberPicker;

public class PriorityActivity extends AppCompatActivity {

    NumberPicker priorityPicker = findViewById(R.id.priorityPicker);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priority);

        priorityPicker.setMaxValue(7);
        priorityPicker.setMinValue(1);
    }
}
