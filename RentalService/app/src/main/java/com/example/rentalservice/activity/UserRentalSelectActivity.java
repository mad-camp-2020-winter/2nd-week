package com.example.rentalservice.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rentalservice.R;
import com.example.rentalservice.api.RetrofitAPI;
import com.example.rentalservice.models.Institution;
import com.example.rentalservice.models.Item;

import java.lang.reflect.Array;
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
        String institution_id = i.getStringExtra("id");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.249.18.173:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<Institution> institutionCall = retrofitAPI.getInstitutionById(institution_id);
        institutionCall.enqueue(new Callback<Institution>() {
            @Override
            public void onResponse(Call<Institution> call, Response<Institution> response) {
                if(response.isSuccessful()){
                    String institution_name = response.body().getName();
                    TextView inst_name = findViewById(R.id.user_rental_institution_name);
                    inst_name.setText(institution_name);
                }
            }

            @Override
            public void onFailure(Call<Institution> call, Throwable t) {

            }
        });

        Button date_btn = findViewById(R.id.user_rental_date_button);
        Calendar calendar = Calendar.getInstance();
        date_btn.setText(calendar.getTime().toString());
        date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Spinner spinner = findViewById(R.id.user_rental_item_spinner);
        ArrayList<String> item_array = new ArrayList<String>();
        Call<List<Item>> item_call = retrofitAPI.getItemByInstitution(institution_id);
        item_call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if(response.isSuccessful()){
                    for(int i=0;i<response.body().size();i++){
                        item_array.add(response.body().get(i).getName());
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {

            }
        });
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,item_array);
        spinner.setAdapter(arrayAdapter);

    }
}