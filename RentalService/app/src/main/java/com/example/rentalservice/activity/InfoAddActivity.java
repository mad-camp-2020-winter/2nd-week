package com.example.rentalservice.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.rentalservice.R;

public class InfoAddActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.admin_activity_info_add);

        EditText inst_name = findViewById(R.id.info_add_institution_name);
        EditText inst_location = findViewById(R.id.info_add_institution_location);
        EditText inst_number = findViewById(R.id.info_add_institution_number);



        Button save = findViewById(R.id.info_add_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inst_name.getText().toString();
                String location = inst_location.getText().toString();
                String number = inst_number.getText().toString();

                Intent i = new Intent();
                i.putExtra("name",name);
                i.putExtra("location", location);
                i.putExtra("number",number);
                setResult(RESULT_FIRST_USER, i);
                finish();
            }
        });
        Button cancel = findViewById(R.id.info_add_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
    // 팝업 밖 눌렀을 때 안 꺼지게 하는 함수
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

//    @Override
//    public void onBackPressed() {
//        // 백버튼 막는거
////        super.onBackPressed();
//        return;
//    }
}