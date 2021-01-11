package com.example.rentalservice.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;

import com.example.rentalservice.InstitutionItem;
import com.example.rentalservice.R;
import com.example.rentalservice.activity.InfoAddActivity;
import com.example.rentalservice.activity.InfoDetailActivity;
import com.example.rentalservice.activity.InfoEditActivity;
import com.example.rentalservice.adapter.InstitutionAdapter;
import com.example.rentalservice.api.RetrofitAPI;
import com.example.rentalservice.models.Institution;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_FIRST_USER;
import static android.app.Activity.RESULT_OK;


public class InfoFragment extends Fragment {

    InstitutionAdapter adapter;
    ListView listView;

    public InfoFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.admin_fragment_info, container, false);
        listView = v.findViewById(R.id.institution_list);
        adapter = new InstitutionAdapter();
        listView.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.249.18.173:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        
        // DB로부터 데이터 불러와서 리스트뷰로 나타냄
        Call<List<Institution>> call = retrofitAPI.getInstitution();
        call.enqueue(new Callback<List<Institution>>() {
            @Override
            public void onResponse(Call<List<Institution>> call, Response<List<Institution>> response) {
                if(response.isSuccessful()){
                    List<Institution> institutions = response.body();
                    int i = 0;
                    String content = "";
                    while(i < institutions.size()) {
                        InstitutionItem item = new InstitutionItem();
                        item.setInstitution_id(institutions.get(i).get_id());
                        item.setInstitution_name(institutions.get(i).getName());
                        item.setInstitution_number(institutions.get(i).getNumber());
                        item.setInstitution_location(institutions.get(i).getLocation());
                        adapter.addItem(item);
                        i++;
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Institution>> call, Throwable t) {
            }
        });
        
        //검색할 경우 listview layout 변경
        SearchView searchView = v.findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query == ""){
                    listView.setAdapter(adapter);
                }
                else{
                    InstitutionAdapter search_adapter = new InstitutionAdapter();
                    int num_item = adapter.getCount();
                    for(int i=0;i<num_item;i++){
                        InstitutionItem item = (InstitutionItem) adapter.getItem(i);
                        if(item.getInstitution_name().contains(query) || item.getInstitution_location().contains(query) || item.getInstitution_number().contains(query)){
                            search_adapter.addItem(item);
                        }
                    }
                    listView.setAdapter(search_adapter);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText == ""){
                    listView.setAdapter(adapter);
                }
                else{
                    InstitutionAdapter search_adapter = new InstitutionAdapter();
                    int num_item = adapter.getCount();
                    for(int i=0;i<num_item;i++){
                        InstitutionItem item = (InstitutionItem) adapter.getItem(i);
                        if(item.getInstitution_name().contains(newText) || item.getInstitution_location().contains(newText) || item.getInstitution_number().contains(newText)){
                            search_adapter.addItem(item);
                        }
                    }
                    listView.setAdapter(search_adapter);
                }
                return true;
            }
        });
        
        // 아이템 클릭할 경우 상세 페이지로 이동
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InstitutionItem item = (InstitutionItem) parent.getItemAtPosition(position);

                String item_id = item.getInstitution_id();
                String item_name = item.getInstitution_name();
                String item_number = item.getInstitution_number();
                String item_location = item.getInstitution_location();

                Intent i = new Intent(getContext(), InfoDetailActivity.class);
                i.putExtra("id", item_id);
                i.putExtra("name", item_name);
                i.putExtra("number", item_number);
                i.putExtra("location", item_location);

                startActivity(i);

            }
        });
        
        // 길게 누르면 팝업
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                PopupMenu popupMenu = new PopupMenu(getContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_info, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.info_edit:
                                Intent intent = new Intent(getContext(), InfoEditActivity.class);
                                InstitutionItem institutionItem = (InstitutionItem) adapter.getItem(position);
                                intent.putExtra("position",position);
                                intent.putExtra("id", institutionItem.getInstitution_id());
                                intent.putExtra("name", institutionItem.getInstitution_name());
                                intent.putExtra("number", institutionItem.getInstitution_number());
                                intent.putExtra("location", institutionItem.getInstitution_location());
                                startActivityForResult(intent,2);
                                break;
                            case R.id.info_delete:
                                InstitutionItem item2 = (InstitutionItem) adapter.getItem(position);
                                Call<Void> call2 = retrofitAPI.deleteInstitution(item2.getInstitution_id());
                                call2.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if(response.isSuccessful()){
                                            adapter.removeItem(position);
                                            listView.setAdapter(adapter);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                    }
                                });
                                break;
                        }
                        return true;
                    }
                });
                return true;

            }
        });
        
        // 아이템 추가를 위해 이동
        FloatingActionButton floatingActionButton = v.findViewById(R.id.frag_info_btn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), InfoAddActivity.class);
                startActivityForResult(i, 1);
            }
        });


        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_FIRST_USER){
            super.onActivityResult(requestCode, resultCode, data);
            String name = data.getStringExtra("name");
            String location = data.getStringExtra("location");
            String number = data.getStringExtra("number");
            Institution institution = new Institution();
            institution.setName(name);
            institution.setLocation(location);
            institution.setNumber(number);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.249.18.173:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
            Call<Institution> call = retrofitAPI.createInstitution(institution);
            call.enqueue(new Callback<Institution>() {
                @Override
                public void onResponse(Call<Institution> call, Response<Institution> response) {
                    if(response.isSuccessful()){
                        InstitutionItem item = new InstitutionItem();
                        item.setInstitution_name(name);
                        item.setInstitution_number(number);
                        item.setInstitution_location(location);
                        System.out.println(response.body().get_id());
                        item.setInstitution_id(response.body().get_id());
                        adapter.addItem(item);
                        listView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<Institution> call, Throwable t) {

                }
            });

        }
        else if(resultCode == RESULT_OK){
            System.out.println("1");
            super.onActivityResult(requestCode, resultCode, data);
            String id = data.getStringExtra("id");
            String name = data.getStringExtra("name");
            String location = data.getStringExtra("location");
            String number = data.getStringExtra("number");
            int position = data.getIntExtra("position", 0);
            Institution institution = new Institution();
            institution.set_id(id);
            institution.setName(name);
            institution.setLocation(location);
            institution.setNumber(number);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.249.18.173:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
            Call<Institution> call = retrofitAPI.putInstitution(id,institution);
            call.enqueue(new Callback<Institution>() {
                @Override
                public void onResponse(Call<Institution> call, Response<Institution> response) {
                    if(response.isSuccessful()){
                        InstitutionItem item = (InstitutionItem) adapter.getItem(position);
                        item.setInstitution_name(name);
                        item.setInstitution_number(number);
                        item.setInstitution_location(location);
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);
                    }
                }
                @Override
                public void onFailure(Call<Institution> call, Throwable t) {
                }
            });
        }
    }
}