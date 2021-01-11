package com.example.rentalservice.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.rentalservice.R;
import com.example.rentalservice.adapter.RentalDetailAdapter;
import com.example.rentalservice.api.RetrofitAPI;
import com.example.rentalservice.models.RentalDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRentalInfoFragment extends Fragment {

    public UserRentalInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.user_fragment_rental_info, container, false);
        ListView listView = v.findViewById(R.id.user_rental_detail_list);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.249.18.173:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        SearchView searchView = v.findViewById(R.id.user_rental_detail_search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Call<List<RentalDetail>> call = retrofitAPI.getRentalDetailByPhone(query);
                call.enqueue(new Callback<List<RentalDetail>>() {
                    @Override
                    public void onResponse(Call<List<RentalDetail>> call, Response<List<RentalDetail>> response) {
                        if(response.isSuccessful()){
                            RentalDetailAdapter rentalDetailAdapter = new RentalDetailAdapter();
                            for(int i=0;i<response.body().size();i++){
                                rentalDetailAdapter.addItem(response.body().get(i));
                            }
                            listView.setAdapter(rentalDetailAdapter);
                        }
                    }
                    @Override
                    public void onFailure(Call<List<RentalDetail>> call, Throwable t) {

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return v;
    }
}