package com.example.rentalservice.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rentalservice.ListViewItem;
import com.example.rentalservice.R;
import com.example.rentalservice.adapter.GalleryAdapter;
import com.example.rentalservice.api.RetrofitAPI;
import com.example.rentalservice.models.Institution;
import com.example.rentalservice.models.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    private RecyclerView mRecyclerView;

    //갤러리 추가 액티비티에 넘겨줄 변수
    private String institution_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_info_detail);
        Intent i = getIntent();

        institution_id = i.getStringExtra("id");
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
        mRecyclerView = (RecyclerView) findViewById(R.id.gallery_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new GalleryAdapter(mArrayList);
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

        //+버튼 클릭시 사진 추가 액티비티로 넘어감
        ImageView addButton = (ImageView) findViewById(R.id.info_detail_add_photo);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoDetailActivity.this, GalleryAddActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_FIRST_USER){
            super.onActivityResult(requestCode, resultCode, data);
            String name = data.getStringExtra("name");
            String count = data.getStringExtra("count");
            String photo = data.getStringExtra("photo");
//            Institution institution = new Institution();
//            institution.setName(name);
//            institution.setLocation(location);
//            institution.setNumber(number);
            Item item = new Item();
            item.setInstitution_id(institution_id);
            item.setName(name);
            item.setCount(count);
            item.setPhoto(photo);


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.249.18.173:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
            Call<Item> call = retrofitAPI.createItem(item);
            call.enqueue(new Callback<Item>() {
                @Override
                public void onResponse(Call<Item> call, Response<Item> response) {
                    if(response.isSuccessful()){
                        //response 받아서 RecyclerView 갱신
                        Item newItem = new Item();
                        newItem.setName(name);
                        newItem.setCount(count);
                        newItem.setPhoto(photo);
                        newItem.setInstitution_id(response.body().getInstitution_id());
                        newItem.set_id(response.body().get_id());
//                        System.out.println(response.body().get_id());
                        mAdapter.addItem(newItem);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                }

                @Override
                public void onFailure(Call<Item> call, Throwable t) {
                    System.out.println("망했어요망했어요망했어요망했어요망했어요망했어요");
                }
            });

        }
        else if(resultCode == RESULT_OK){
//            System.out.println("1");
//            super.onActivityResult(requestCode, resultCode, data);
//            String id = data.getStringExtra("id");
//            String name = data.getStringExtra("name");
//            String location = data.getStringExtra("location");
//            String number = data.getStringExtra("number");
//            int position = data.getIntExtra("position", 0);
//            Institution institution = new Institution();
//            institution.set_id(id);
//            institution.setName(name);
//            institution.setLocation(location);
//            institution.setNumber(number);
//
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl("http://192.249.18.173:8080")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//            RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
//            Call<Institution> call = retrofitAPI.putInstitution(id,institution);
//            call.enqueue(new Callback<Institution>() {
//                @Override
//                public void onResponse(Call<Institution> call, Response<Institution> response) {
//                    if(response.isSuccessful()){
//                        ListViewItem item = (ListViewItem) adapter.getItem(position);
//                        item.setInstitution_name(name);
//                        item.setInstitution_number(number);
//                        item.setInstitution_location(location);
//                        adapter.notifyDataSetChanged();
//                        listView.setAdapter(adapter);
//                    }
//                }
//                @Override
//                public void onFailure(Call<Institution> call, Throwable t) {
//                }
//            });
        }
    }
}