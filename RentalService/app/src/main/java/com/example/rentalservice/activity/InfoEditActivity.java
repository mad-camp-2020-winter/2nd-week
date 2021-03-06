package com.example.rentalservice.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.rentalservice.R;

public class InfoEditActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.admin_activity_info_edit);


        Intent i = getIntent();
        String id = i.getStringExtra("id");
        String name = i.getStringExtra("name");
        String number = i.getStringExtra("number");
        String location = i.getStringExtra("location");
        int position = i.getIntExtra("position", 0);

        Button save = findViewById(R.id.info_edit_save);
        EditText edit_name = findViewById(R.id.info_edit_institution_name);
        EditText edit_number = findViewById(R.id.info_edit_institution_number);
        EditText edit_location = findViewById(R.id.info_edit_institution_location);
        edit_name.setText(name);
        edit_number.setText(number);
        edit_location.setText(location);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_name = edit_name.getText().toString();
                String new_number = edit_number.getText().toString();
                String new_location = edit_location.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("position",position);
                intent.putExtra("id",id);
                intent.putExtra("name",new_name);
                intent.putExtra("number",new_number);
                intent.putExtra("location",new_location);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        Button cancel = findViewById(R.id.info_edit_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}