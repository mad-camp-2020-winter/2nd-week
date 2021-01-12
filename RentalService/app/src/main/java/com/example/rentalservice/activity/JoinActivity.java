package com.example.rentalservice.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rentalservice.R;
import com.example.rentalservice.api.RetrofitAPI;
import com.example.rentalservice.models.Login;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JoinActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.admin_activity_join);

        final String[] checked_id  = {""};
        String checkcode = "dud6!tjr4&wl7@dud9?=";

        EditText id = findViewById(R.id.join_id);
        EditText password = findViewById(R.id.join_password);
        EditText name = findViewById(R.id.join_name);
        EditText number = findViewById(R.id.join_number);
        EditText check_code = findViewById(R.id.join_check_code);
        Button id_check = findViewById(R.id.join_id_check);
        Button join = findViewById(R.id.join_btn);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.249.18.173:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        id_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checking_id = id.getText().toString();
                Call<List<Login>> login_call = retrofitAPI.getLoginById(checking_id);
                login_call.enqueue(new Callback<List<Login>>() {
                    @Override
                    public void onResponse(Call<List<Login>> call, Response<List<Login>> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(JoinActivity.this, "이미 존재하는 아이디입니다!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(JoinActivity.this, "사용가능한 아이디입니다!",Toast.LENGTH_SHORT).show();
                            checked_id[0] = checking_id;
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Login>> call, Throwable t) {

                    }
                });
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(checked_id[0].equals(id.getText().toString()))){
                    Toast.makeText(JoinActivity.this, "아이디 중복 확인을 해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(password.getText().length() < 8){
                    Toast.makeText(JoinActivity.this, "비밀번호는 8자 이상으로 입력해 주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(name.getText().length() == 0){
                    Toast.makeText(JoinActivity.this, "이름을 입력해 주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(number.getText().length() < 10 ){
                    Toast.makeText(JoinActivity.this, "전화번호를 입력해 주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!check_code.getText().toString().contentEquals(checkcode)){
                    Toast.makeText(JoinActivity.this, "올바른 인증 코드를 입력해 주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    Login new_login = new Login();
                    new_login.setId(checked_id[0]);
                    new_login.setPassword(password.getText().toString());
                    Call<Login> create_call = retrofitAPI.createLogin(new_login);
                    create_call.enqueue(new Callback<Login>() {
                        @Override
                        public void onResponse(Call<Login> call, Response<Login> response) {
                            if(response.isSuccessful()){
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<Login> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }
}