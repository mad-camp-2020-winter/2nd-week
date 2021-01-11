package com.example.rentalservice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rentalservice.InstitutionItem;
import com.example.rentalservice.R;
import com.example.rentalservice.api.RetrofitAPI;
import com.example.rentalservice.models.Institution;
import com.example.rentalservice.models.Item;
import com.example.rentalservice.models.RentalDetail;

import java.util.ArrayList;

import kotlin.reflect.KVisibility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RentalDetailAdapter extends BaseAdapter {
    private ArrayList<RentalDetail> RentalDetailItems = new ArrayList<RentalDetail>();

    @Override
    public int getCount() {
        return RentalDetailItems.size();
    }

    @Override
    public Object getItem(int position) {
        return RentalDetailItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.admin_rentaldetailitem, parent, false);
        }

        TextView nameView = convertView.findViewById(R.id.rental_user_name);
        TextView numberview = convertView.findViewById(R.id.user_rental_phone_number);
        TextView locationview = convertView.findViewById(R.id.rental_location);
        TextView itemnameView = convertView.findViewById(R.id.rental_item_name);
        TextView itemcountView = convertView.findViewById(R.id.rental_item_number);
        TextView dateView = convertView.findViewById(R.id.rental_date);
        TextView approvalView = convertView.findViewById(R.id.rental_approval);
        TextView commentView = convertView.findViewById(R.id.rental_comment);

        RentalDetail rentalDetail = RentalDetailItems.get(position);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.249.18.173:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<Institution> institutionCall = retrofitAPI.getInstitutionById(rentalDetail.getInstitution_id());
        institutionCall.enqueue(new Callback<Institution>() {
            @Override
            public void onResponse(Call<Institution> call, Response<Institution> response) {
                if(response.isSuccessful()){
                    locationview.setText(response.body().getName() + "    " + response.body().getLocation());
                }
            }
            @Override
            public void onFailure(Call<Institution> call, Throwable t) {
            }
        });

        Call<Item> itemCall = retrofitAPI.getItemById(rentalDetail.getItem_id());
        itemCall.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                if(response.isSuccessful()){
                    itemnameView.setText(response.body().getName());
                }
            }
            @Override
            public void onFailure(Call<Item> call, Throwable t) {
            }
        });

        nameView.setText(rentalDetail.getUser_name());
        numberview.setText(rentalDetail.getUser_phone());
        itemcountView.setText(rentalDetail.getCount());
        int date = rentalDetail.getRental_date();
        int year = date/10000;
        int month = (date-year*10000)/100;
        int day = (date-year*10000-month*100);
        dateView.setText(year + "년 " + month + "월 " + day + "일");
        int approval = rentalDetail.getApproval();
        if(approval == 0){
            approvalView.setText("승인대기중");
            commentView.setVisibility(View.INVISIBLE);
        }
        else if(approval == 1){
            approvalView.setText("승인");
            commentView.setVisibility(View.VISIBLE);
            commentView.setText(rentalDetail.getComment());
        }
        else{
            approvalView.setText("반려");
            commentView.setVisibility(View.VISIBLE);
            commentView.setText(rentalDetail.getComment());
        }

        return convertView;
    }

    public void addItem(RentalDetail item){
        RentalDetailItems.add(item);
        notifyDataSetChanged();
    }

    public void removeItem(int position){
        RentalDetailItems.remove(position);
    }
}
