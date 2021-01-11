package com.example.rentalservice.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.rentalservice.R;
import com.example.rentalservice.activity.AdminCommentActivity;
import com.example.rentalservice.adapter.RentalDetailAdapter;
import com.example.rentalservice.api.RetrofitAPI;
import com.example.rentalservice.models.RentalDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RentalFragment extends Fragment {

    ListView listView;
    RentalDetailAdapter rentalDetailAdapter;
    Retrofit retrofit;
    RetrofitAPI retrofitAPI;

    public RentalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.admin_fragment_rental, container, false);
        listView = v.findViewById(R.id.admin_rental_detail_list);
        rentalDetailAdapter = new RentalDetailAdapter();
        listView.setAdapter(rentalDetailAdapter);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.249.18.173:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<List<RentalDetail>> rental_call = retrofitAPI.getRentalDetail();
        rental_call.enqueue(new Callback<List<RentalDetail>>() {
            @Override
            public void onResponse(Call<List<RentalDetail>> call, Response<List<RentalDetail>> response) {
                if(response.isSuccessful()){
                    for(int i=0;i<response.body().size();i++){
                        RentalDetail rentalDetail = new RentalDetail();
                        rentalDetail.setUser_phone(response.body().get(i).getUser_phone());
                        rentalDetail.setItem_id(response.body().get(i).getItem_id());
                        rentalDetail.setInstitution_id(response.body().get(i).getInstitution_id());
                        rentalDetail.setCount(response.body().get(i).getCount());
                        rentalDetail.setUser_name(response.body().get(i).getUser_name());
                        rentalDetail.setApproval(response.body().get(i).getApproval());
                        rentalDetail.setRental_date(response.body().get(i).getRental_date());
                        rentalDetail.setServer_date(response.body().get(i).getServer_date());
                        rentalDetail.setComment(response.body().get(i).getComment());
                        rentalDetail.set_id(response.body().get(i).get_id());
                        rentalDetailAdapter.addItem(rentalDetail);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<RentalDetail>> call, Throwable t) {
                System.out.println(t.getCause());
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RentalDetail item = (RentalDetail) rentalDetailAdapter.getItem(position);
                Intent i = new Intent(getContext(), AdminCommentActivity.class);
                i.putExtra(" is_user", false);
                i.putExtra("institution_id", item.getInstitution_id());
                i.putExtra("user_name", item.getUser_name());
                i.putExtra("user_phone", item.getUser_phone());
                i.putExtra("apply_date", item.getServer_date());
                i.putExtra("item_id", item.getItem_id());
                i.putExtra("item_count", item.getCount());
                i.putExtra("rental_date", item.getRental_date());
                i.putExtra("approval", item.getApproval());
                i.putExtra("comment", item.getComment());
                i.putExtra("position",position);
                startActivityForResult(i, 3);
            }
        });

        SearchView searchView = v.findViewById(R.id.admin_rental_detail_search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query == ""){
                    listView.setAdapter(rentalDetailAdapter);
                }
                else{
                    RentalDetailAdapter new_adapter = new RentalDetailAdapter();
                    int list_num = rentalDetailAdapter.getCount();
                    for(int i=0;i<list_num;i++){
                        RentalDetail rentalDetail = (RentalDetail) rentalDetailAdapter.getItem(i);
                        if(rentalDetail.getInstitution_name().contains(query)){
                            new_adapter.addItem(rentalDetail);
                        }
                    }
                    listView.setAdapter(new_adapter);
                }
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText == ""){
                    listView.setAdapter(rentalDetailAdapter);
                }
                else{
                    RentalDetailAdapter new_adapter = new RentalDetailAdapter();
                    int list_num = rentalDetailAdapter.getCount();
                    for(int i=0;i<list_num;i++){
                        RentalDetail rentalDetail = (RentalDetail) rentalDetailAdapter.getItem(i);
                        if(rentalDetail.getInstitution_name().contains(newText)){
                            new_adapter.addItem(rentalDetail);
                        }
                    }
                    listView.setAdapter(new_adapter);
                }
                return true;
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == 4){
            String comment = data.getStringExtra("comment");
            int position = data.getIntExtra("position", 0);
            int approval = data.getIntExtra("approval", 0);
            RentalDetail rentalDetail = (RentalDetail) rentalDetailAdapter.getItem(position);
            rentalDetail.setApproval(approval);
            rentalDetail.setComment(comment);
            Call<RentalDetail> rentalDetailCall = retrofitAPI.putRentalDetail(rentalDetail.get_id(), rentalDetail);
            rentalDetailCall.enqueue(new Callback<RentalDetail>() {
                @Override
                public void onResponse(Call<RentalDetail> call, Response<RentalDetail> response) {
                    if(response.isSuccessful()){
                      rentalDetailAdapter.notifyDataSetChanged();
                      listView.setAdapter(rentalDetailAdapter);
                    }
                }
                @Override
                public void onFailure(Call<RentalDetail> call, Throwable t) {
                }
            });
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}