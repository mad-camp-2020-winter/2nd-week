package com.example.rentalservice.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rentalservice.R;

public class InfoDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detail);
        Intent i = getIntent();

        String _id = i.getStringExtra("id");
        String name = i.getStringExtra("name");
        String number = i.getStringExtra("number");
        String location = i.getStringExtra("location");

        TextView inst_name = findViewById(R.id.info_detail_institution_name);
        TextView inst_location = findViewById(R.id.info_detail_institution_location);
        TextView inst_number = findViewById(R.id.info_detail_institution_number);

        inst_name.setText("사무실 명: " + name);
        inst_location.setText("위치: " + location);
        inst_number.setText("번호: " + number);

    }
}