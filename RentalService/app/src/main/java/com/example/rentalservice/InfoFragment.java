package com.example.rentalservice;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.TestLooperManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
        PostResult postResult = new PostResult();

        Call<List<PostResult>> call = retrofitAPI.getretrofitdata();
        call.enqueue(new Callback<List<PostResult>>() {
            @Override
            public void onResponse(Call<List<PostResult>> call, Response<List<PostResult>> response) {
                if(response.isSuccessful()){
                    List<PostResult> postResults = response.body();
                    TextView textView = v.findViewById(R.id.test_box);
                    int i = 0;
                    String content = "";
                    while(i < postResults.size()) {
                        ListViewItem item = new ListViewItem();
                        item.setInstitution_name(postResults.get(i).getName());
                        item.setInstitution_number(postResults.get(i).getNumber());
                        item.setInstitution_location(postResults.get(i).getLocation());
                        adapter.addItem(item.getInstitution_name(),item.getInstitution_number(),item.getInstitution_location());
                        i++;
                    }
                    textView.setText(content);
                }
            }

            @Override
            public void onFailure(Call<List<PostResult>> call, Throwable t) {

            }
        });

//        Call<PostResult> call = retrofitAPI.getretrofitdata();
//        call.enqueue(new Callback<PostResult>() {
//            @Override
//            public void onResponse(Call<PostResult> call, Response<PostResult> response) {
//                if(response.isSuccessful()){
//                    PostResult postResponse = response.body();
//                    TextView textView = v.findViewById(R.id.test_box);
//                    String content = "";
//                    content += "Name: " + postResponse.getName() + "\n";
//                    content += "Number: " + postResponse.getNumber() + "\n";
//                    content += "Location: " + postResponse.getLocation() + "\n";
//                     textView.setText(content);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PostResult> call, Throwable t) {
//                TextView textView = v.findViewById(R.id.test_box);
//                textView.setText(t.getMessage());
//            }
//        });


        return v;
    }

    public class PostResult{
        @SerializedName("name")
        private String name;
        @SerializedName("number")
        private String number;
        @SerializedName("location")
        private String location;

        public String getName(){ return name; }
        public String getNumber(){ return number; }
        public String getLocation(){ return location; }

        public void setName(String name){this.name = name;}
        public void setNumber(String number){this.number = number;}
        public void setLocation(String location){this.location = location;}
    }

    public interface RetrofitAPI {
        @GET("/api/institutions")
        Call<List<PostResult>> getretrofitdata();
    }

}