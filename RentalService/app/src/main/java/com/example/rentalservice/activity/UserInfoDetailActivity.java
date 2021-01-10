package com.example.rentalservice.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rentalservice.R;
import com.example.rentalservice.adapter.GalleryAdapter;
import com.example.rentalservice.api.RetrofitAPI;
import com.example.rentalservice.models.Item;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserInfoDetailActivity extends AppCompatActivity implements GalleryAdapter.OnListItemSelectedInterface, GalleryAdapter.OnListItemLongSelectedInterface {

    private ArrayList<Item> mArrayList = new ArrayList<Item>();
    private GalleryAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private String institution_id;
    private String institution_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_info_detail);

        Intent i = getIntent();

        institution_id = i.getStringExtra("id");
        institution_name = i.getStringExtra("name");
        String number = i.getStringExtra("number");
        String location = i.getStringExtra("location");

        System.out.println(institution_id);

        TextView inst_name = findViewById(R.id.user_info_detail_institution_name);
        TextView inst_location = findViewById(R.id.user_info_detail_institution_location);
        TextView inst_number = findViewById(R.id.user_info_detail_institution_number);

        inst_name.setText("사무실 명: " + institution_name);
        inst_location.setText("위치: " + location);
        inst_number.setText("번호: " + number);

        mRecyclerView = (RecyclerView) findViewById(R.id.user_gallery_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new GalleryAdapter(mArrayList, UserInfoDetailActivity.this, UserInfoDetailActivity.this);
        mRecyclerView.setAdapter(mAdapter);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.249.18.173:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<List<Item>> call = retrofitAPI.getItem();
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if(response.isSuccessful()){
                    List<Item> items = response.body();
                    int i = 0;
                    String content = "";
                    while(i < items.size()) {
                        Item item = new Item();
                        item.setCount(items.get(i).getCount());
                        item.setName(items.get(i).getName());
                        item.set_id(items.get(i).get_id());
                        item.setPhoto(items.get(i).getPhoto());
//                        item.setInstitution_id(items.get(i).getInstitution_id()); //이미 같은 기관거 받아오니까 필요없음
                        item.setInstitution_id(institution_id);
                        mAdapter.addItem(item);
                        i++;
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
            }
        });

    }

    @Override
    public void onItemSelected(View v, int position) {
        Item item = mAdapter.getItem(position);
        Intent intent = new Intent(this, UserRentalSelectActivity.class);
        intent.putExtra("institution_name",institution_name);
        intent.putExtra("institution_id",institution_id);
        intent.putExtra("item_name", item.getName());
        intent.putExtra("item_count", item.getCount());
        intent.putExtra("item_photo", item.getPhoto());
        intent.putExtra("item_id", item.get_id());
        startActivityForResult(intent, 2);
    }

    @Override
    public void onItemLongSelected(View v, int position) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1){
            finish();
        }
    }
}