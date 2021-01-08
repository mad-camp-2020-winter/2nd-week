package com.example.rentalservice;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.TestLooperManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rentalservice.models.Institution;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public class InfoFragment extends Fragment {

    public InfoFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info, container, false);
        ListView listView = v.findViewById(R.id.institution_list);
        ListViewAdapter adapter = new ListViewAdapter();
        listView.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.249.18.173:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<List<Institution>> call = retrofitAPI.getInstitution();
        call.enqueue(new Callback<List<Institution>>() {
            @Override
            public void onResponse(Call<List<Institution>> call, Response<List<Institution>> response) {
                if(response.isSuccessful()){
                    List<Institution> institutions = response.body();
                    int i = 0;
                    String content = "";
                    while(i < institutions.size()) {
                        ListViewItem item = new ListViewItem();
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position);

                String item_id = item.getInstitution_id();
                String item_name = item.getInstitution_name();
                String item_number = item.getInstitution_number();
                String item_location = item.getInstitution_location();

                MainActivity mainActivity = (MainActivity) getActivity();
                System.out.println(mainActivity);
                mainActivity.FragmentChange(1, item);


            }
        });

        return v;
    }



}