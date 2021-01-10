package com.example.rentalservice.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

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

public class InfoDetailActivity extends AppCompatActivity implements GalleryAdapter.OnListItemSelectedInterface, GalleryAdapter.OnListItemLongSelectedInterface{

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

        mAdapter = new GalleryAdapter(mArrayList, InfoDetailActivity.this, InfoDetailActivity.this);
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

        // 길게 누르면 팝업
//        mRecyclerView.onItemlong
//
//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                PopupMenu popupMenu = new PopupMenu(getContext(), view);
//                popupMenu.getMenuInflater().inflate(R.menu.popup_info, popupMenu.getMenu());
//                popupMenu.show();
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()){
//                            case R.id.info_edit:
//                                Intent intent = new Intent(getContext(), InfoEditActivity.class);
//                                ListViewItem listViewItem = (ListViewItem) adapter.getItem(position);
//                                intent.putExtra("position",position);
//                                intent.putExtra("id",listViewItem.getInstitution_id());
//                                intent.putExtra("name",listViewItem.getInstitution_name());
//                                intent.putExtra("number",listViewItem.getInstitution_number());
//                                intent.putExtra("location",listViewItem.getInstitution_location());
//                                startActivityForResult(intent,2);
//                                break;
//                            case R.id.info_delete:
//                                ListViewItem item2 = (ListViewItem) adapter.getItem(position);
//                                Call<Void> call2 = retrofitAPI.deleteInstitution(item2.getInstitution_id());
//                                call2.enqueue(new Callback<Void>() {
//                                    @Override
//                                    public void onResponse(Call<Void> call, Response<Void> response) {
//                                        if(response.isSuccessful()){
//                                            adapter.removeItem(position);
//                                            listView.setAdapter(adapter);
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onFailure(Call<Void> call, Throwable t) {
//
//                                    }
//                                });
//                                break;
//                        }
//                        return true;
//                    }
//                });
//                return true;
//
//            }
//        });
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

    @Override
    public void onItemLongSelected(View v, int position) {
        GalleryAdapter.CustomViewHolder viewHolder = (GalleryAdapter.CustomViewHolder) mRecyclerView.findViewHolderForAdapterPosition(position);
        Toast.makeText(this, position + " clicked",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(View v, int position) {
        GalleryAdapter.CustomViewHolder viewHolder = (GalleryAdapter.CustomViewHolder) mRecyclerView.findViewHolderForAdapterPosition(position);
        Toast.makeText(this, position + " clicked",Toast.LENGTH_SHORT).show();

    }
}