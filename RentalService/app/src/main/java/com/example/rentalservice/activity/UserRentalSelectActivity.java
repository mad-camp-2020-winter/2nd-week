package com.example.rentalservice.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rentalservice.R;
import com.example.rentalservice.api.RetrofitAPI;
import com.example.rentalservice.models.Institution;
import com.example.rentalservice.models.Item;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRentalSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_rental_select);

        Intent i = getIntent();
        String institution_name = i.getStringExtra("institution_name");
        String item_name = i.getStringExtra("item_name");
        String item_count = i.getStringExtra("item_count");
        String item_photo = i.getStringExtra("item_photo");

        TextView inst_name = findViewById(R.id.user_rental_institution_name);
        TextView it_name = findViewById(R.id.user_rental_item_name);
        TextView it_count = findViewById(R.id.user_rental_item_count);
        ImageView it_photo = findViewById(R.id.user_rental_item_image);


        inst_name.setText(institution_name);
        it_name.setText(item_name);
        it_count.setText(" (가능 수량: " + item_count + ")");


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.249.18.173:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Button date_btn = findViewById(R.id.user_rental_date_button);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        date_btn.setText(format1.format(calendar.getTime()));

        date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(UserRentalSelectActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date_btn.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                dialog.show();
            }
        });

        Button apply_btn = findViewById(R.id.user_rental_apply_button);
        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(1);
                finish();

            }
        });

        Button cancel_btn = findViewById(R.id.user_rental_cancel_button);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(2);
                finish();
            }
        });

    }
}