package com.example.rentalservice.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RenderNode;
import android.os.Bundle;
import android.util.Base64;
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
import com.example.rentalservice.models.RentalDetail;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        String item_id = i.getStringExtra("item_id");
        String institution_id = i.getStringExtra("institution_id");

        TextView inst_name = findViewById(R.id.user_rental_institution_name);
        TextView it_name = findViewById(R.id.user_rental_item_name);
        TextView it_count_hint = findViewById(R.id.user_rental_item_count_hint);
        ImageView it_photo = findViewById(R.id.user_rental_item_image);
        if(it_photo != null) {
            //bitmap -> stream -> image 변환
            byte[] bytePlainOrg = Base64.decode(item_photo, 0);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytePlainOrg);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            it_photo.setImageBitmap(bitmap);
        }
        else{
            it_photo.setImageResource(R.drawable.contruction);
        }

        EditText it_count = findViewById(R.id.user_rental_item_count);
        EditText user_name = findViewById(R.id.user_rental_name);
        EditText user_number = findViewById(R.id.user_rental_phone_number);

        inst_name.setText(institution_name);
        it_name.setText(item_name);
        it_count_hint.setText(" (가능 수량: " + item_count + ")");


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.249.18.173:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Button date_btn = findViewById(R.id.user_rental_date_button);
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy년 MM월 dd일");
        date_btn.setText(format1.format(calendar.getTime()));
        int server_date = calendar.get(Calendar.YEAR) * 10000 + (calendar.get(Calendar.MONTH) + 1) * 100 + calendar.get(Calendar.DATE);
        final int[] date_n = {calendar.get(Calendar.YEAR) * 10000 + (calendar.get(Calendar.MONTH) + 1) * 100 + calendar.get(Calendar.DATE)};

        date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(UserRentalSelectActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date_btn.setText(year + "년 " + (month + 1) + "월 " + dayOfMonth + "일");
                        date_n[0] = year * 10000 + (month + 1) * 100 + dayOfMonth;
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                dialog.show();
            }
        });

        Button apply_btn = findViewById(R.id.user_rental_apply_button);
        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RentalDetail rentalDetail = new RentalDetail();
                rentalDetail.setUser_name(user_name.getText().toString());
                rentalDetail.setCount(Integer.parseInt(it_count.getText().toString()));
                rentalDetail.setInstitution_id(institution_id);
                rentalDetail.setRental_date(date_n[0]);
                rentalDetail.setServer_date(server_date);
                rentalDetail.setItem_id(item_id);
                rentalDetail.setUser_phone(user_number.getText().toString());
                Call<RentalDetail> call = retrofitAPI.createRentalDetail(rentalDetail);
                call.enqueue(new Callback<RentalDetail>() {
                    @Override
                    public void onResponse(Call<RentalDetail> call, Response<RentalDetail> response) {
                        if(response.isSuccessful()){
                            rentalDetail.set_id(response.body().get_id());
                            setResult(1);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<RentalDetail> call, Throwable t) {

                    }
                });
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