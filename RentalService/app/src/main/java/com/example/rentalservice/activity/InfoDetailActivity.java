package com.example.rentalservice.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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

public class InfoDetailActivity extends AppCompatActivity {

    private ArrayList<Item> mArrayList = new ArrayList<Item>();
    private GalleryAdapter mAdapter;

    //갤러리 추가 액티비티에 넘겨줄 변수
    private String _id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_info_detail);
        Intent i = getIntent();

        _id = i.getStringExtra("id");
        String name = i.getStringExtra("name");
        String number = i.getStringExtra("number");
        String location = i.getStringExtra("location");

        TextView inst_name = findViewById(R.id.info_detail_institution_name);
        TextView inst_location = findViewById(R.id.info_detail_institution_location);
        TextView inst_number = findViewById(R.id.info_detail_institution_number);

        inst_name.setText("사무실 명: " + name);
        inst_location.setText("위치: " + location);
        inst_number.setText("번호: " + number);

        //RecyclerView 세팅
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.gallery_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        GalleryAdapter mAdapter = new GalleryAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 3); // 한줄에 세 사진
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        // DB로부터 데이터 불러와서 리사이클러뷰로 나타냄
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
                        mAdapter.addItem(item);
                        i++;
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
            }
        });

        //+버튼 클릭시 사진 추가 액티비티로 넘어감
        ImageView addButton = (ImageView) findViewById(R.id.info_detail_add_photo);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoDetailActivity.this,GalleryAddActivity.class);
                intent.putExtra("institution_id", _id);
                startActivity(intent);
            }
        });
    }
}