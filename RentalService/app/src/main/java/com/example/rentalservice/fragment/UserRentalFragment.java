package com.example.rentalservice.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.rentalservice.InstitutionItem;
import com.example.rentalservice.R;
import com.example.rentalservice.activity.UserInfoDetailActivity;
import com.example.rentalservice.adapter.InstitutionAdapter;
import com.example.rentalservice.api.RetrofitAPI;
import com.example.rentalservice.models.Institution;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRentalFragment extends Fragment {

    InstitutionAdapter adapter;
    ListView listView;

    public UserRentalFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.user_fragment_rental, container, false);
        listView = v.findViewById(R.id.user_institution_list);
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
        SearchView searchView = v.findViewById(R.id.user_search_bar);
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InstitutionItem item = (InstitutionItem) parent.getItemAtPosition(position);

                String item_id = item.getInstitution_id();
                String item_name = item.getInstitution_name();
                String item_number = item.getInstitution_number();
                String item_location = item.getInstitution_location();

                Intent i = new Intent(getContext(), UserInfoDetailActivity.class);
                i.putExtra("id", item_id);
                i.putExtra("name", item_name);
                i.putExtra("number", item_number);
                i.putExtra("location", item_location);

                startActivity(i);
            }
        });

        return v;
    }
}