package com.example.rentalservice.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

public class RentalFragment extends Fragment {



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
        ListView listView = v.findViewById(R.id.admin_rental_detail_list);
        RentalDetailAdapter rentalDetailAdapter = new RentalDetailAdapter();
        listView.setAdapter(rentalDetailAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.249.18.173:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<List<RentalDetail>> rental_call = retrofitAPI.getRentalDetail();
        rental_call.enqueue(new Callback<List<RentalDetail>>() {
            @Override
            public void onResponse(Call<List<RentalDetail>> call, Response<List<RentalDetail>> response) {
                System.out.println("__________________a");
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
                        rentalDetailAdapter.addItem(rentalDetail);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<RentalDetail>> call, Throwable t) {
                System.out.println(t.getCause());
            }
        });

        return v;
    }
}