package com.example.rentalservice.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rentalservice.R;
import com.example.rentalservice.api.RetrofitAPI;
import com.example.rentalservice.models.Institution;
import com.example.rentalservice.models.Item;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminCommentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.admin_activity_comment);

        Intent i = getIntent();
        int position = i.getIntExtra("position", 0);
        String institution_id = i.getStringExtra("institution_id");
        String user_name = i.getStringExtra("user_name");
        String user_phone = i.getStringExtra("user_phone");
        int apply_date = i.getIntExtra("apply_date", 0);
        String item_id = i.getStringExtra("item_id");
        int item_count = i.getIntExtra("item_count", 0);
        int rental_date = i.getIntExtra("rental_date", 0);
        final int[] approval = {i.getIntExtra("approval", 0)};
        String comment = i.getStringExtra("comment");

        int apply_year = apply_date/10000;
        int apply_month = (apply_date-apply_year*10000)/100;
        int apply_day = (apply_date-apply_year*10000-apply_month*100);
        int rental_year = rental_date/10000;
        int rental_month = (rental_date-rental_year*10000)/100;
        int rental_day = (rental_date-rental_year*10000-rental_month*100);

        TextView View_user_name = findViewById(R.id.comment_user_name);
        TextView View_user_phone = findViewById(R.id.comment_user_phone);
        TextView View_apply_date = findViewById(R.id.comment_apply_date);
        TextView View_institution_name = findViewById(R.id.comment_institution_name);
        TextView View_item_info = findViewById(R.id.comment_item_name);
        TextView View_rental_date = findViewById(R.id.comment_rental_date);
        Spinner View_approval = findViewById(R.id.comment_approval_spinner);
        EditText View_comment = findViewById(R.id.comment_comment);

        View_user_name.setText("신청자 이름: " + user_name);
        View_user_phone.setText("신청자 번호: " + user_phone);
        View_apply_date.setText("신청한 날짜: " + String.valueOf(apply_year) + "년 " + String.valueOf(apply_month) + "월 " + String.valueOf(apply_day) + "일");
        View_rental_date.setText("대여 기간: " +String.valueOf(rental_year) + "년 " + String.valueOf(rental_month) + "월 " + String.valueOf(rental_day) + "일");
        View_comment.setText(comment);

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
                    View_institution_name.setText("사무소 이름: " + response.body().getName());
                }
            }
            @Override
            public void onFailure(Call<Institution> call, Throwable t) {
            }
        });

        Call<Item> itemCall = retrofitAPI.getItemById(item_id);
        itemCall.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                if(response.isSuccessful()){
                    View_item_info.setText("신청 물품: " + response.body().getName() + String.valueOf(item_count));
                }
            }
            @Override
            public void onFailure(Call<Item> call, Throwable t) {
            }
        });


        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(this, R.array.approval, android.R.layout.simple_spinner_dropdown_item);
        View_approval.setAdapter(arrayAdapter);
        View_approval.setSelection(approval[0]);

        View_approval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        approval[0] = 0;
                        break;
                    case 1:
                        approval[0] = 1;
                        break;
                    case 2:
                        approval[0] = 2;
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button save = findViewById(R.id.comment_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("approval", approval[0]);
                intent.putExtra("comment", View_comment.getText().toString());
                intent.putExtra("position", position);
                setResult(4, intent);
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